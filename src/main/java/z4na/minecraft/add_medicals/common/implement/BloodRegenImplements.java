package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import java.util.Objects;

public class BloodRegenImplements {
    public static final Codec<BloodRegenImplements> CODEC = Codec.INT.xmap(BloodRegenImplements::new, BloodRegenImplements::get);

    private int tick;
    public BloodRegenImplements() { this.tick = 0; }
    public BloodRegenImplements(int tick) { this.tick = tick; }

    public int get() { return tick; }
    public void set(int tick) { this.tick = tick; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return tick == ((BloodRegenImplements) o).tick;
    }
    @Override
    public int hashCode() { return Objects.hash(tick); }
}