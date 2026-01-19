package z4na.minecraft.add_medicals.common.registries;

import net.minecraft.world.item.Item;
import z4na.minecraft.add_medicals.common.items.*;
import java.util.function.Supplier;
import static z4na.minecraft.add_medicals.common.AMRegistries.*;
//import z4na.minecraft.add_medicals.client.models.item.*;

public class AMItems {

    public static final Supplier<Item> BLOOD_TRANSFUSION = registryItem("blood_transfusion", () -> new BloodTransfusionItem(new Item.Properties()));
    public static final Supplier<Item> BANDAGE = registryItem("bandage", () -> new BandageItem(new Item.Properties()));
    public static final Supplier<Item> HEMOSTATIC_PAD = registryItem("hemostatic_pad", () -> new HemostaticPadItem(new Item.Properties()));
    public static final Supplier<Item> MEDICAL_KIT = registryItem("medical_kit", () -> new MedicalKitItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ADVANCED_MEDICAL_KIT = registryItem("advanced_medical_kit", () -> new AdvancedMedicalKitItem(new Item.Properties().stacksTo(4)));
    public static final Supplier<Item> SYRINGE = registryItem("syringe", () -> new SyringeItem(new Item.Properties().durability(20)));

    //public static final Supplier<Item> MEDICAL_KIT = registryGeoItem("medical_kit", () -> new AMGeoItem(new Item.Properties(), MedicalKitModel::new));
    //public static final Supplier<Item> MEDICAL_KIT_CASE = registrySimpleItem("medical_kit_case");

    //
    public static void init() {}
}
