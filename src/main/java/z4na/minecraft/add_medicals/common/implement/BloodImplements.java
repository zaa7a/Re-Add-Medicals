package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import z4na.minecraft.add_medicals.common.interfaces.Blood;

public class BloodImplements implements Blood {
    public static final Codec<BloodImplements> CODEC = Codec.INT.xmap(
            (Integer i) -> new BloodImplements(i),
            BloodImplements::get
    );
    private int blood;

    public BloodImplements() {
        this.blood = 1000;
    }

    public BloodImplements(Integer integer) {
        this.blood = integer;
    }


    @Override
    public int get() {
        return blood;
    }

    @Override
    public void set(int amount) {
        blood = Math.max(0, Math.min(amount, getMax()));
    }

    @Override
    public int getMax() {
        return 1000;
    }
}
