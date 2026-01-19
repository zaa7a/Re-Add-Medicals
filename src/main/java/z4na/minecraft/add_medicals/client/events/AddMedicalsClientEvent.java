package z4na.minecraft.add_medicals.client.events;

import net.neoforged.neoforge.common.NeoForge;

public class AddMedicalsClientEvent {
    public static void init() {
        var bus = NeoForge.EVENT_BUS;
        //bus.addListener(AMClientTickEvent::onEvent);
        //bus.addListener(AMClientSprintEvent::onEvent);
    }
}
