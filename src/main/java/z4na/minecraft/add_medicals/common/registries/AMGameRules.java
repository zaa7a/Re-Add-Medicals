package z4na.minecraft.add_medicals.common.registries;

import net.minecraft.world.level.GameRules;

public class AMGameRules {
    public static GameRules.Key<GameRules.BooleanValue> RULE_AM_REGEN;

    public static void init() {
        RULE_AM_REGEN = GameRules.register(
                "naturalHealthRegeneration",
                GameRules.Category.PLAYER,
                GameRules.BooleanValue.create(false)
        );
    }
}
