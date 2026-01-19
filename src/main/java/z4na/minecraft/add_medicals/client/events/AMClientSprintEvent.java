package z4na.minecraft.add_medicals.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;
public class AMClientSprintEvent {
    public static void onEvent(PlayerTickEvent.Post event) {
        /*Player player = event.getEntity();

        // 属性インスタンスを取得
        var speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute == null) return;

        // 骨折データの取得
        int level = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get()).get();

        if (level >= 1) {
            // --- 骨折時 ---
            // 1. 属性デバフを付与（これがダッシュを物理的に封じる）
            if (!speedAttribute.hasModifier(FRACTURE_SPEED_MODIFIER_ID)) {
                speedAttribute.addTransientModifier(FRACTURE_LIMIT);
            }
            // 2. 念のため現在のダッシュ状態を強制解除
            if (player.isSprinting()) {
                player.setSprinting(false);
            }
        } else {
            // --- 完治時 ---
            if (speedAttribute.hasModifier(FRACTURE_SPEED_MODIFIER_ID)) {
                speedAttribute.removeModifier(FRACTURE_SPEED_MODIFIER_ID);
            }
        }*/
    }
}
