package z4na.minecraft.add_medicals.common.interfaces;

public interface BloodType {
    String getType();
    void setType(String type);

    boolean isRh();
    void setRh(boolean rh);

    int get();

    void set(int amount);

    int getMax();
}