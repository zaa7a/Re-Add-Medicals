package z4na.minecraft.add_medicals.client;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import z4na.minecraft.add_medicals.client.events.AddMedicalsClientEvent;
import z4na.minecraft.add_medicals.client.renderer.AddMedicalsOverlayRenderer;
import z4na.minecraft.add_medicals.common.implement.BleedingImplements;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;
import z4na.minecraft.add_medicals.common.implement.IsDownedImplements;
import z4na.minecraft.add_medicals.common.network.SyncPacket;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;

public class AddMedicalsClient {
    public static void init(IEventBus bus) {
        bus.addListener(AddMedicalsClient::onClientSetup);
        AddMedicalsOverlayRenderer.init(bus);
        AddMedicalsClientEvent.init();

    }
    private static void onClientSetup(FMLClientSetupEvent event) {
        System.out.println("AddMedicals: Client Setup Start.");
    }
    public static void handleBloodSync(SyncPacket.BloodSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.setData(AMAttachments.BLOOD_ATTACHMENT.get(), new BloodImplements(packet.blood()));
            }
        });
    }
    public static void handleBleedingSync(SyncPacket.BleedingSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(packet.bleeding()));
            }
        });
    }
    public static void handleFractureSync(SyncPacket.FractureSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.setData(AMAttachments.FRACTURE_ATTACHMENT.get(), new FractureImplements(packet.fracture()));
            }
        });
    }
    public static void handleIsDownedSync(SyncPacket.IsDownedSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.setData(AMAttachments.DOWNED_ATTACHMENT.get(), new IsDownedImplements(packet.isDowned()));
            }
        });
    }
}
