package z4na.minecraft.add_medicals.common.registries;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import z4na.minecraft.add_medicals.common.capabilities.AMCapabilities;

import static z4na.minecraft.add_medicals.common.attachments.AMAttachments.*;

public class AMCapabilitiesRegister {

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(
                AMCapabilities.BLOOD, //Cap
                EntityType.PLAYER, //Target
                (player, side) -> player.getData(BLOOD_ATTACHMENT)
        );
        event.registerEntity(
                AMCapabilities.BLEEDING, //Cap
                EntityType.PLAYER, //Target
                (player, side) -> player.getData(BLEEDING_ATTACHMENT)
        );
        event.registerEntity(
                AMCapabilities.FRACTURE, //Cap
                EntityType.PLAYER, //Target
                (player, side) -> player.getData(FRACTURE_ATTACHMENT)
        );
        event.registerEntity(
                AMCapabilities.IS_DOWNED,
                EntityType.PLAYER,
                (player, side) -> player.getData(DOWNED_ATTACHMENT)
        );
    }

    public static void register(IEventBus bus) {
        bus.addListener(AMCapabilitiesRegister::registerCapabilities);
    }
}
