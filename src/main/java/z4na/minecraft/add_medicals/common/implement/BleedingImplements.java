package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import z4na.minecraft.add_medicals.common.interfaces.Bleeding;

public class BleedingImplements implements Bleeding {
    //codec
    public static final Codec<BleedingImplements> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("bleeding").forGetter(BleedingImplements::get),
                    Codec.INT.fieldOf("lastBleedingTick").forGetter(BleedingImplements::getLastBleedingTick)
            ).apply(instance, BleedingImplements::new)
    );

    private int bleeding;
    private int lastBleedingTick;

    public BleedingImplements() {
        this(0, 0);
    }

    public BleedingImplements(int bleeding, int lastBleedingTick) {
        this.bleeding = bleeding;
        this.lastBleedingTick = lastBleedingTick;
    }

    public BleedingImplements(Integer integer) {
        this(integer, 0);
    }

    public int getLastBleedingTick() {
        return lastBleedingTick;
    }

    public void setLastBleedingTick(int tick) {
        this.lastBleedingTick = tick;
    }

    @Override
    public int get() {
        return bleeding;
    }

    @Override
    public void set(int amount) {
        this.bleeding = Math.max(0, Math.min(amount, getMax()));
    }

    @Override
    public int getMax() {
        return 7;
    }
}
