package z4na.minecraft.add_medicals.client.renderer.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Gui;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;

public class AMBloodBarOverlay {

    public static void onEvent(RenderGuiLayerEvent.Post event) {
        // PLAYER_HEALTH ではなく、左側の描画がほぼ終わるタイミング(FOOD_LEVEL や AIR_LEVEL)に変更
        // これにより、Armor(防具)やHealth(体力)が積み重なった後の最終的な高さを取得できます。
        if (!event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.isCreative() || mc.player.isSpectator()) return;

        int blood = mc.player.getData(AMAttachments.BLOOD_ATTACHMENT.get()).get();
        int maxBlood = 1000;

        renderBloodBar(event.getGuiGraphics(), blood, maxBlood);
    }

    private static void renderBloodBar(GuiGraphics guiGraphics, int blood, int maxBlood) {
        Minecraft mc = Minecraft.getInstance();
        Gui gui = mc.gui;

        int centerX = guiGraphics.guiWidth() / 2;
        int bottom = guiGraphics.guiHeight();

        // バニラの全要素(体力、防具など)が積み上がった後の現在の高さを取得
        int leftHeight = gui.leftHeight;

        int x = centerX - 91;
        // y座標を上に計算 (- leftHeight で上に、さらに - 2 で隙間を作る)
        int y = bottom - leftHeight - 2;

        int width = 81;
        int height = 7;

        // 枠と背景の描画
        guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);
        guiGraphics.fill(x, y, x + width, y + height, 0xFF444444);

        // 血液バーの描画
        float ratio = (float) blood / maxBlood;
        int barWidth = (int) (width * Math.max(0.0f, Math.min(1.0f, ratio)));
        guiGraphics.fill(x, y, x + barWidth, y + height, 0xFFFF0000);

        // 高さを加算して、他のMODがさらに上に描画できるようにする
        gui.leftHeight += 10;
    }
}