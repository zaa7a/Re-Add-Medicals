package z4na.minecraft.add_medicals.common.events;

import net.neoforged.neoforge.common.NeoForge;
import z4na.minecraft.add_medicals.common.events.player.*;

public class AMPlayerEvent {
    public static void init() {
        NeoForge.EVENT_BUS.addListener(AMPlayerRespawnEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerJoinEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerHurtEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerTickEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerLivingIncomingDamageEvent::onEvent);
        NeoForge.EVENT_BUS.addListener(AMPlayerLivingJumpEvent::onEvent);
    }
}
