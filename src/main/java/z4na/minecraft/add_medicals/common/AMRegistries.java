package z4na.minecraft.add_medicals.common;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.CapabilityRegistry;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.security.DrbgParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AMRegistries {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AddMedicals.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AddMedicals.MOD_ID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AddMedicals.MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "add_medicals");
    private static final List<Supplier<Item>> AM_ITEMS = new ArrayList<>();
    //private static final List<Supplier<Item>> AM_GEO_ITEMS = new ArrayList<>();
    public static Supplier<Item> registryItem(String id, Supplier<? extends Item> supplier) {
        Supplier<Item> item = ITEMS.register(id, supplier);
        AM_ITEMS.add(item);
        return item;
    }
    public static Supplier<Item> registryGeoItem(String id, Supplier<? extends Item> supplier) {
        Supplier<Item> item = ITEMS.register(id, supplier);
        AM_ITEMS.add(item);
        //AM_GEO_ITEMS.add(item);
        return item;
    }
    public static Supplier<Item> registrySimpleItem(String id) {
        Supplier<Item> item = ITEMS.registerItem(id, Item::new, new Item.Properties());
        AM_ITEMS.add(item);
        return item;
    }
    public static List<Supplier<Item>> getAmItems() { return AM_ITEMS; }



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
        ATTACHMENT_TYPES.register(bus);
        COMPONENTS.register(bus);
    }
}
