package z4na.minecraft.add_medicals.common.registries;

import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.events.AMCommandEvent;
import z4na.minecraft.add_medicals.common.events.AMPlayerEvent;
import z4na.minecraft.add_medicals.common.events.AMWorldLoadEvent;


public class AMRegisterInitializer {
    public static void init() {
        AMAttachments.init();
        AMItems.init();
        AMCreativeTabs.init();
        AMPlayerEvent.init();
        AMDataComponents.init();
        AMGameRules.init();
        AMWorldLoadEvent.init();
        AMCommandEvent.init();
        AMCommandsRegister.init();
    }
}