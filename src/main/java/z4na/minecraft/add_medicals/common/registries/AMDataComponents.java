package z4na.minecraft.add_medicals.common.registries;

import net.minecraft.core.component.DataComponentType;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;

import java.util.function.Supplier;

import static z4na.minecraft.add_medicals.common.AMRegistries.COMPONENTS;

public class AMDataComponents {

    public static final Supplier<DataComponentType<BloodTypeImplements>> BLOOD_TYPE =
            COMPONENTS.register("blood_type", () -> DataComponentType.<BloodTypeImplements>builder()
                    .persistent(BloodTypeImplements.CODEC)
                    .networkSynchronized(BloodTypeImplements.STREAM_CODEC)
                    .build());
    public static void init() {}
}