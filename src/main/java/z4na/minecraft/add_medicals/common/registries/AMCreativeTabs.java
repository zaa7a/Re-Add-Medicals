package z4na.minecraft.add_medicals.common.registries;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;

import java.util.function.Supplier;



import static z4na.minecraft.add_medicals.common.AMRegistries.*;


public class AMCreativeTabs {



    public static final Supplier<CreativeModeTab> MAIN_TAB = CREATIVE_TABS.register(
            "add_medicals",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.add_medicals"))
                    .icon(() -> new ItemStack(AMItems.BLOOD_TRANSFUSION.get()))
                    .displayItems((params, output) -> {
                        // normal items
                        getAmItems().forEach(s -> {
                            if (s.get() != AMItems.BLOOD_TRANSFUSION.get()) output.accept(s.get());
                        });

                        // Blood Transfusion O rh-
                        ItemStack oNegPack = new ItemStack(AMItems.BLOOD_TRANSFUSION.get());
                        oNegPack.set(AMDataComponents.BLOOD_TYPE.get(), new BloodTypeImplements("O", false, true));
                        output.accept(oNegPack);
                    })
                    .build()
    );

    public static void init() {
    }
}
