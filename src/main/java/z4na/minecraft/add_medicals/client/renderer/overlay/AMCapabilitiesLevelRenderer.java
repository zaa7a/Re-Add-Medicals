package z4na.minecraft.add_medicals.client.renderer.overlay;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;

public class AMCapabilitiesLevelRenderer implements LayeredDraw.Layer {
    private static final ResourceLocation ICONS_BLEEDING = ResourceLocation.fromNamespaceAndPath("add_medicals", "textures/ui/bleeding.png");
    private static final ResourceLocation ICONS_FRACTURE = ResourceLocation.fromNamespaceAndPath("add_medicals", "textures/ui/fracture.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker tracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        // 基準座標（ホットバー左端から20px左）
        int baseStartX = screenWidth / 2 - 91 - 20;
        int startY = screenHeight - 16 - 3;
        int offsetX = baseStartX;

        // ========================
        // --- 出血 (常に表示) ---
        // ========================
        int bleeding = mc.player.getData(AMAttachments.BLEEDING_ATTACHMENT.get()).get();

        if (bleeding > 0) {
            guiGraphics.pose().pushPose();
            // 縮小のためにスケールをかける
            guiGraphics.pose().scale(0.5f, 0.5f, 1f);

            // レベル0のときは少し透明にするなどの演出を入れる場合はここを調整
            // 今回はシンプルにアイコンのみ常に描画
            guiGraphics.blit(ICONS_BLEEDING, offsetX * 2, startY * 2, 0, 0, 32, 32, 32, 32);
            guiGraphics.pose().popPose();

            // 数字は 0 より大きいときだけ表示する（0を表示すると邪魔なため）
            if (bleeding > 0) {
                String text = String.valueOf(bleeding);
                guiGraphics.drawString(mc.font, text, offsetX + 12, startY + 8, 0xFFFFFF, true);
            }
            // 次のアイコン（骨折）のために座標をずらす
            offsetX -= 18;
        }


        // ========================
        // --- 骨折 (0より大きい時のみ) ---
        // ========================
        int fracture = mc.player.getData(AMAttachments.FRACTURE_ATTACHMENT.get()).get();
        if (fracture > 0) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5f, 0.5f, 1f);
            guiGraphics.blit(ICONS_FRACTURE, offsetX * 2, startY * 2, 0, 0, 32, 32, 32, 32);
            guiGraphics.pose().popPose();

            String text = String.valueOf(fracture);
            guiGraphics.drawString(mc.font, text, offsetX + 12, startY + 8, 0xFFFFFF, true);
        }
    }
}