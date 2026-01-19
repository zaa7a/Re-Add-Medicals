package z4na.minecraft.add_medicals.common.interfaces;

public interface BloodType {
    String getType();
    void setType(String type);

    boolean isRh();
    void setRh(boolean rh);

    // インターフェースの古いメソッドは不要であれば削除、またはデフォルト実装
    int get();

    void set(int amount);

    int getMax();
}