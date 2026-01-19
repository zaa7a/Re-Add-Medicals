package z4na.minecraft.add_medicals.common.events;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerTickEvent;
import z4na.minecraft.add_medicals.common.registries.AMGameRules;

public class AMWorldLoadEvent {
    public static void onEvent(LevelEvent.Load event) {
        if (event.getLevel() instanceof Level level && !level.isClientSide()) {
            GameRules rules = level.getGameRules();
            if (rules.getBoolean(GameRules.RULE_NATURAL_REGENERATION)) {
                rules.getRule(AMGameRules.RULE_AM_REGEN).set(true, level.getServer());
                rules.getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, level.getServer());
            }
        }
    }
    public static void init() {
        NeoForge.EVENT_BUS.addListener(AMWorldLoadEvent::onEvent);
    }
}
