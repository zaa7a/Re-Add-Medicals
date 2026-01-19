package z4na.minecraft.add_medicals.common;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import z4na.minecraft.add_medicals.client.AddMedicalsClient;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.events.AMPlayerEvent;
import z4na.minecraft.add_medicals.common.events.AMWorldLoadEvent;
import z4na.minecraft.add_medicals.common.registries.*;


@Mod(AddMedicals.MOD_ID)
public class AddMedicals {
    public static final String MOD_ID = "add_medicals";


    public AddMedicals(IEventBus bus, ModContainer container) {
        AMRegistries.register(bus);
        AMCapabilitiesRegister.register(bus);
        AMRegisterInitializer.init();



        if (FMLEnvironment.dist == Dist.CLIENT) {
            AddMedicalsClient.init(bus);
        }
    }
}
