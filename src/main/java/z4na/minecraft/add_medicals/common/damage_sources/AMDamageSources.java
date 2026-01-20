package z4na.minecraft.add_medicals.common.damage_sources;


import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import z4na.minecraft.add_medicals.common.damage_type.AMDamageTypes;

public class AMDamageSources {
    public static DamageSource bleeding(Level level) {
        Holder<DamageType> holder = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(AMDamageTypes.BLEEDING);
        return new DamageSource(holder);
    }
}