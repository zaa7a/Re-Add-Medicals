package z4na.minecraft.add_medicals.common.events.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.damage_sources.AMDamageSources;
import z4na.minecraft.add_medicals.common.implement.BleedingImplements;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import z4na.minecraft.add_medicals.common.implement.BloodRegenImplements;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;

public class AMPlayerTickEvent {

    private static final ResourceLocation FRACTURE_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("add_medicals", "fracture_speed");

    public static void onEvent(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // サーバー側、生存中、サバイバルモードのみ
        if (!player.level().isClientSide() && player.isAlive() && !player.isCreative()) {

            BleedingImplements bleedingData = player.getData(AMAttachments.BLEEDING_ATTACHMENT.get());
            BloodImplements bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
            FractureImplements fractureData = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get());

            int bleedingLevel = bleedingData.get();
            int currentBlood = bloodData.get();
            int currentBleedingTick = bleedingData.getLastBleedingTick(); // これを「0からの蓄積カウント」として扱う
            int oldBlood = currentBlood;

            // クライアントへ同期
            if (player instanceof ServerPlayer serverPlayer) {
                SendPacket.sendBleedingPacket(serverPlayer, bleedingLevel);
            }

            // --- 1. 出血レベルが1以上の場合のメイン処理 ---
            if (bleedingLevel > 0) {

                // A. 血液減少ロジック (ワールド時間を補助的に使用)
                int bloodInterval = (bleedingLevel == 1) ? 4 : (bleedingLevel == 2) ? 2 : 1;
                if (player.tickCount % bloodInterval == 0) {
                    int reduction = (bleedingLevel >= 3) ? (bleedingLevel - 2) : 1;
                    currentBlood = Math.max(0, currentBlood - reduction);
                    player.setData(AMAttachments.BLOOD_ATTACHMENT.get(), new BloodImplements(currentBlood));
                    player.setData(AMAttachments.BLOOD_REGEN_TICK.get(), new BloodRegenImplements(0));
                }

                // カウントを1進める (ダメージとレベル低下の両方に使用)
                currentBleedingTick++;

                // B. 出血レベル低下ロジック (240tick = 12秒ごと)
                // カウントが12秒に達したらレベルを下げ、カウントをリセットする
                if (currentBleedingTick >= 240) {
                    bleedingLevel--;
                    currentBleedingTick = 0; // カウントを0に戻す
                }

                // C. 体力ダメージロジック
                // ※ bleedingLevelが下がって0になった場合はダメージ判定を行わない
                if (bleedingLevel > 0) {
                    int damageInterval = switch (bleedingLevel) {
                        case 1 -> 120;
                        case 2 -> 100;
                        case 3 -> 80;
                        case 4 -> 60;
                        case 5, 6, 7 -> 40;
                        default -> 0;
                    };

                    float damageAmount = switch (bleedingLevel) {
                        case 1, 2, 3, 4, 5 -> 1.0f;
                        case 6 -> 2.0f;
                        case 7 -> 3.0f;
                        default -> 0.0f;
                    };

                    // ダメージ間隔に達したか判定 (currentBleedingTickをそのまま流用)
                    // 注: 120, 100, 80などはすべて240の約数ではないため、
                    // 12秒の間に複数回ダメージが入る形になります
                    if (damageInterval > 0 && currentBleedingTick % damageInterval == 0) {
                        player.hurt(AMDamageSources.bleeding(player.level()), damageAmount);
                    }
                }

                // 最後に更新された出血ステータスを保存
                player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(bleedingLevel, currentBleedingTick));

            } else {
                // 出血レベル0ならカウントも0
                if (currentBleedingTick != 0) {
                    player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(0, 0));
                }
            }

            // --- 2. 即死判定 ---
            if (currentBlood <= 0) {
                player.hurt(AMDamageSources.bleeding(player.level()), Float.MAX_VALUE);
            }

            // --- 3. 骨折ロジック (既存) ---
            int fractureLevel = fractureData.get();
            int currentFractureTick = fractureData.getLastFractureTick();
            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (movementSpeed != null) {
                movementSpeed.removeModifier(FRACTURE_SPEED_MODIFIER_ID);
                if (fractureLevel > 0) {
                    double reduction = Math.pow(fractureLevel, 2) * -0.10;
                    movementSpeed.addTransientModifier(new AttributeModifier(FRACTURE_SPEED_MODIFIER_ID, reduction, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                }
            }

            if (fractureLevel > 0) {
                currentFractureTick++;
                if (currentFractureTick >= 7200) {
                    int newFractureLevel = fractureLevel - 1;
                    player.setData(AMAttachments.FRACTURE_ATTACHMENT.get(), new FractureImplements(newFractureLevel, 0));
                    SendPacket.sendFracturePacket(player, newFractureLevel);
                } else {
                    player.setData(AMAttachments.FRACTURE_ATTACHMENT.get(), new FractureImplements(fractureLevel, currentFractureTick));
                }
            }

            // --- 4. 血液回復・パケット同期 ---
            // (血液回復ロジックは既存のまま)
            var regenData = player.getData(AMAttachments.BLOOD_REGEN_TICK.get());
            int currentTimer = regenData.get();
            if (currentTimer < 602) regenData.set(currentTimer + 1);
            if (currentTimer >= 601) regenData.set(600);
            if (currentTimer >= 600 && currentTimer % 2 == 0) {
                bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
                if (bloodData.get() < bloodData.getMax()) {
                    int nextBlood = bloodData.get() + 1;
                    bloodData.set(nextBlood);
                    currentBlood = nextBlood; // 同期用に更新
                }
            }

            // 血液量に変化があれば同期
            if (oldBlood != currentBlood && player instanceof ServerPlayer serverPlayer) {
                SendPacket.sendBloodPacket(serverPlayer, currentBlood);
            }
        }
        AMNaturalRegenerationEvent.onEvent(event);
    }
}