package z4na.minecraft.add_medicals.common.events.player;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;

import java.util.Random;

public class AMPlayerRespawnEvent {
    public static void onEvent(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
            player.getData(AMAttachments.BLEEDING_ATTACHMENT.get());
            player.getData(AMAttachments.FRACTURE_ATTACHMENT.get());
        }

    }
}
