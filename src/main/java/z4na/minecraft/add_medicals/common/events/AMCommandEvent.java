package z4na.minecraft.add_medicals.common.events;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.CommandEvent;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerTickEvent;
import z4na.minecraft.add_medicals.common.registries.AMGameRules;

public class AMCommandEvent {
    public static void onEvent(CommandEvent event) {
        String command = event.getParseResults().getReader().getString();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        if (command.startsWith("gamerule naturalRegeneration")) {
            // コマンドの実行をキャンセル
            event.setCanceled(true);

            // 実行者に誘導メッセージを送信
            if (event.getParseResults().getContext().getSource().getEntity() != null) {
                var source = event.getParseResults().getContext().getSource();

                source.sendSystemMessage(Component.literal("----------------------------------------").withStyle(ChatFormatting.RED));
                source.sendSystemMessage(Component.translatable("msg.add_medicals.gamerule_disabled")
                        .withStyle(ChatFormatting.RED));

                // クリックすると新しいコマンドが自動入力されるリンク付きメッセージ
                String suggest = "/gamerule naturalHealthRegeneration";
                source.sendSystemMessage(Component.translatable("msg.add_medicals.gamerule_suggestion", suggest)
                        .withStyle(ChatFormatting.RED)
                        .withStyle(style -> style
                                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, (suggest + " ")))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("msg.add_medicals.gamerule_hover"))))
                );
                source.sendSystemMessage(Component.literal("----------------------------------------").withStyle(ChatFormatting.RED));
            }
        }
    }

    public static void init() {
        NeoForge.EVENT_BUS.addListener(AMCommandEvent::onEvent);
    }
}