package z4na.minecraft.add_medicals.common.events.player;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;

import java.util.Random;

public class AMPlayerJoinEvent {
    public static void onEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        BloodTypeImplements data = player.getData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get());
        int bloodLevel = player.getData(AMAttachments.BLOOD_ATTACHMENT.get()).get();
        int bleedingLevel = player.getData(AMAttachments.BLEEDING_ATTACHMENT.get()).get();
        int fractureLevel = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get()).get();
        SendPacket.sendBloodPacket(player, bloodLevel);
        SendPacket.sendBleedingPacket(player, bleedingLevel);
        SendPacket.sendFracturePacket(player, fractureLevel);
        if (!data.isInitialized()) {
            setupRandomBloodType(data);
        }
    }

    public static void setupRandomBloodType(BloodTypeImplements data) {
        Random random = new Random();
        String[] types = {"A", "B", "O", "AB"};

        data.setType(types[random.nextInt(types.length)]);
        data.setRh(random.nextBoolean());
        data.setInitialized(true);
    }
}