package z4na.minecraft.add_medicals.common.registries;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import z4na.minecraft.add_medicals.common.commands.AMCommands;

public class AMCommandsRegister {
    public static void onEvent(RegisterCommandsEvent event) {
        AMCommands.register(event.getDispatcher());
    }
    public static void init() {
        NeoForge.EVENT_BUS.addListener(AMCommandsRegister::onEvent);
    }
}
