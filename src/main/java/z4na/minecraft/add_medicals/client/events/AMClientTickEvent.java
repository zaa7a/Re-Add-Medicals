package z4na.minecraft.add_medicals.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;

import java.util.Objects;

public class AMClientTickEvent {
    public static void onEvent(ClientTickEvent.Post event) {
        /*Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        if (player == null) {
            return;
        }

        FractureImplements fractureData = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get());

        if (fractureData.get() >= 1) {
            if (mc.player != null) {
                mc.player.setSprinting(false);
            }
        }*/
    }
}
