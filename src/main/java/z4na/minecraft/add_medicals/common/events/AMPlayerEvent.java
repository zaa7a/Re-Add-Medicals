package z4na.minecraft.add_medicals.common.events;

import net.neoforged.neoforge.common.NeoForge;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerHurtEvent;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerJoinEvent;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerRespawnEvent;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerTickEvent;

public class AMPlayerEvent {
    public static void init() {
        NeoForge.EVENT_BUS.addListener(AMPlayerRespawnEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerJoinEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerHurtEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerTickEvent::onEvent);
    }
}
