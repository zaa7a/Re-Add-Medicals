package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import z4na.minecraft.add_medicals.common.interfaces.Fracture;

public class FractureImplements implements Fracture {
    public static final Codec<FractureImplements> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("fracture").forGetter(FractureImplements::get),
                    Codec.INT.fieldOf("lastFractureTick").forGetter(FractureImplements::getLastFractureTick)
            ).apply(instance, FractureImplements::new)
    );

    private int fracture;
    private int lastFractureTick;

    public FractureImplements() {
        this(0, 0);
    }

    public FractureImplements(int fracture, int lastFractureTick) {
        this.fracture = fracture;
        this.lastFractureTick = lastFractureTick;
    }

    public FractureImplements(Integer integer) {
        this(integer, 0);
    }

    public int getLastFractureTick() {
        return lastFractureTick;
    }

    public void setLastFractureTick(int tick) {
        this.lastFractureTick = tick;
    }

    @Override
    public int get() {
        return fracture;
    }

    @Override
    public void set(int amount) {
        this.fracture = Math.max(0, Math.min(amount, getMax()));
    }

    @Override
    public int getMax() {
        return 3;
    }
}
