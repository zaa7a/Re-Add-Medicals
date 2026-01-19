package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import z4na.minecraft.add_medicals.common.interfaces.Bleeding;

public class BleedingImplements implements Bleeding {
    // 複数のフィールドを保存できるようにCodecを拡張
    public static final Codec<BleedingImplements> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("bleeding").forGetter(BleedingImplements::get),
                    Codec.INT.fieldOf("lastBleedingTick").forGetter(BleedingImplements::getLastBleedingTick)
            ).apply(instance, BleedingImplements::new)
    );

    private int bleeding;
    private int lastBleedingTick;

    // デフォルトコンストラクタ
    public BleedingImplements() {
        this(0, 0);
    }

    // 保存データからの復元、および新規作成用コンストラクタ
    public BleedingImplements(int bleeding, int lastBleedingTick) {
        this.bleeding = bleeding;
        this.lastBleedingTick = lastBleedingTick;
    }

    // 既存のint型1つのコンストラクタ（互換性のため、またはレベル更新用）
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
