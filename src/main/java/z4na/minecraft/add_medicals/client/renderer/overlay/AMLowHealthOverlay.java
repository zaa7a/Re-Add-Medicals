package z4na.minecraft.add_medicals.client.renderer.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;

public class AMLowHealthOverlay {

    private static final ResourceLocation VIGNETTE_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/vignette.png");

    public static void onEvent(RenderGuiLayerEvent.Post event) {
        if (!event.getName().equals(VanillaGuiLayers.CAMERA_OVERLAYS)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.isCreative() || mc.player.isSpectator()) return;

        int blood = mc.player.getData(AMAttachments.BLOOD_ATTACHMENT.get()).get();

        if (blood < 800) {
            float intensity = (800.0f - blood) / 800.0f;
            renderVignette(event.getGuiGraphics(), Math.min(intensity, 0.9f));
        }
    }

    // ... 前後のコードは省略 ...

    private static void renderVignette(GuiGraphics guiGraphics, float intensity) {
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        // 標準的な透過設定
        RenderSystem.defaultBlendFunc();

        // 修正ポイント: RGB を (1.0, 0.0, 0.0) にすることで「赤色」を指定します。
        // 第4引数の intensity (0.0 ~ 0.9) で、血量に応じた赤の濃さを調整します。
        RenderSystem.setShaderColor(0.5F, 0.0F, 0.0F, intensity);

        // ビネットテクスチャ（周辺が不透明な画像）を描画
        // これにより、画像の不透明な部分（画面の端）だけが赤くなります。
        guiGraphics.blit(VIGNETTE_LOCATION, 0, 0, 0, 0, width, height, width, height);

        // 描画後は必ず元の色(白: 1,1,1,1)に戻す
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}