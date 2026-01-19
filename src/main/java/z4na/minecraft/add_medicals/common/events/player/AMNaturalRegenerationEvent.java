package z4na.minecraft.add_medicals.common.events.player;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import z4na.minecraft.add_medicals.common.registries.AMGameRules;

public class AMNaturalRegenerationEvent {
    public static void onEvent(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (player.isCreative() || player.isSpectator()) return;

        // ゲームルールの値を確認
        boolean isRegenEnabled = player.level().getGameRules().getBoolean(AMGameRules.RULE_AM_REGEN);

        if (isRegenEnabled) {
            BloodImplements bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
            int foodLevel = player.getFoodData().getFoodLevel();

            // 血液800以上、食料18以上の独自回復ロジック
            if (bloodData.get() >= 800 && foodLevel >= 18) {
                if (player.getHealth() < player.getMaxHealth()) {
                    // 前に作ったカウンター処理...
                    if (player.tickCount % 80 == 0) {
                        player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 1.0F));
                        player.getFoodData().addExhaustion(2.0F);
                    }
                }
            }
        }
    }
}
