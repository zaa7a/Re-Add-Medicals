package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import z4na.minecraft.add_medicals.common.interfaces.Fracture;

public class FractureImplements implements Fracture {
    // 複数のフィールドを保存できるようにCodecを拡張
    public static final Codec<FractureImplements> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("fracture").forGetter(FractureImplements::get),
                    Codec.INT.fieldOf("lastFractureTick").forGetter(FractureImplements::getLastFractureTick)
            ).apply(instance, FractureImplements::new)
    );

    private int fracture;
    private int lastFractureTick;

    // デフォルトコンストラクタ
    public FractureImplements() {
        this(0, 0);
    }

    // 保存データからの復元、および新規作成用コンストラクタ
    public FractureImplements(int fracture, int lastFractureTick) {
        this.fracture = fracture;
        this.lastFractureTick = lastFractureTick;
    }

    // 既存のint型1つのコンストラクタ（互換性のため、またはレベル更新用）
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
