package z4na.minecraft.add_medicals.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import z4na.minecraft.add_medicals.client.renderer.overlay.AMBloodBarOverlay;
import z4na.minecraft.add_medicals.client.renderer.overlay.AMCapabilitiesLevelRenderer;
import z4na.minecraft.add_medicals.client.renderer.overlay.AMLowHealthOverlay;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class AddMedicalsOverlayRenderer {
    public static void init(IEventBus bus) {
        var neoForgeBus = NeoForge.EVENT_BUS;
        neoForgeBus.addListener(AMLowHealthOverlay::onEvent);
        neoForgeBus.addListener(AMBloodBarOverlay::onEvent);
        bus.addListener(AddMedicalsOverlayRenderer::registerGuiLayers);

    }
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR,
                ResourceLocation.fromNamespaceAndPath("add_medicals", "capabilities_overlay"),
                new AMCapabilitiesLevelRenderer());

    }
}
