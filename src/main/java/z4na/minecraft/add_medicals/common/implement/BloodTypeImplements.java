package z4na.minecraft.add_medicals.common.implement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import z4na.minecraft.add_medicals.common.interfaces.BloodType;

import java.util.Objects;

public class BloodTypeImplements implements BloodType {
    // codec
    public static final Codec<BloodTypeImplements> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("type").forGetter(BloodTypeImplements::getType),
                    Codec.BOOL.fieldOf("rh").forGetter(BloodTypeImplements::isRh),
                    Codec.BOOL.fieldOf("initialized").forGetter(BloodTypeImplements::isInitialized)
            ).apply(instance, BloodTypeImplements::new)
    );
    //stream codec
    public static final StreamCodec<RegistryFriendlyByteBuf, BloodTypeImplements> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BloodTypeImplements::getType,
            ByteBufCodecs.BOOL, BloodTypeImplements::isRh,
            ByteBufCodecs.BOOL, BloodTypeImplements::isInitialized,
            BloodTypeImplements::new
    );

    private String type;
    private boolean rh;
    private boolean initialized;

    // init
    public BloodTypeImplements() {
        this.type = "O";
        this.rh = true;
        this.initialized = false;
    }

    // Codec constructor
    public BloodTypeImplements(String type, boolean rh, boolean initialized) {
        this.type = type;
        this.rh = rh;
        this.initialized = initialized;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRh() { return rh; }
    public void setRh(boolean rh) { this.rh = rh; }

    public boolean isInitialized() { return initialized; }
    public void setInitialized(boolean initialized) { this.initialized = initialized; }

    // クラス内の適切な場所（コンストラクタの後など）に追記してください
    public static boolean canReceive(String donorType, boolean donorRh, String patientType, boolean patientRh) {

        boolean bl = false;
        switch (patientType) {
            case "O":
                //O Only
                if (donorType.equals("O")) {
                    if (patientRh) {
                        //Rh+ -> Rh+/-
                        bl = true;
                    } else {
                        //Rh- -> Rh-
                        if (!donorRh) {
                            bl = true;
                        }
                    }
                }
                break;
            case "A":
                //A O Only
                if (donorType.equals("A") || donorType.equals("O")) {
                    if (patientRh) {
                        //Rh+ -> Rh+/-
                        bl = true;
                    } else {
                        //Rh- -> Rh-
                        if (!donorRh) {
                            bl = true;
                        }
                    }
                }
                break;
            case "B":
                //B O Only
                if (donorType.equals("B") || donorType.equals("O")) {
                    if (patientRh) {
                        //Rh+ -> Rh+/-
                        bl = true;
                    } else {
                        //Rh- -> Rh-
                        if (!donorRh) {
                            bl = true;
                        }
                    }
                }
                break;
            case "AB":
                //All
                if (patientRh) {
                    //Rh+ -> Rh+/-
                    bl = true;
                } else {
                    //Rh- -> Rh-
                    if (!donorRh) {
                        bl = true;
                    }
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + patientType);
        }
        return bl;
    }

    // interface
    @Override
    public int get() { return 0; }
    @Override
    public void set(int amount) {}
    @Override
    public int getMax() { return 1000; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodTypeImplements that = (BloodTypeImplements) o;
        return rh == that.rh &&
                initialized == that.initialized &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rh, initialized);
    }
}