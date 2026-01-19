package z4na.minecraft.add_medicals.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.events.player.AMPlayerJoinEvent;
import z4na.minecraft.add_medicals.common.implement.BleedingImplements;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;
import z4na.minecraft.add_medicals.common.registries.AMDataComponents;
import z4na.minecraft.add_medicals.common.registries.AMItems;

public class AMCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        String[] bloodTypes = {"A", "B", "O", "AB"};
        dispatcher.register(Commands.literal("add_medicals")
                .requires(source -> source.hasPermission(2)) // 管理者権限が必要
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.player())
                                // 血液量の設定
                                .then(Commands.literal("blood")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, 1000))
                                                .executes(context -> setBlood(context.getSource(), EntityArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "amount")))))
                                // 出血レベルの設定
                                .then(Commands.literal("bleeding")
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0, 7))
                                                .executes(context -> setBleeding(context.getSource(), EntityArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "level")))))
                                // 骨折レベルの設定
                                .then(Commands.literal("fracture")
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0, 3))
                                                .executes(context -> setFracture(context.getSource(), EntityArgument.getPlayer(context, "target"), IntegerArgumentType.getInteger(context, "level")))))
                        )
                )
                .then(Commands.literal("blood_type")
                        .then(Commands.literal("random")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .executes(context -> setBloodTypeRandom(context.getSource(), EntityArgument.getPlayer(context, "target")))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .then(Commands.argument("type", StringArgumentType.word())
                                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(bloodTypes, builder))
                                                .then(Commands.argument("rh", StringArgumentType.word())
                                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(new String[]{"+", "-"}, builder))
                                                        .executes(context -> setBloodType(
                                                                context.getSource(),
                                                                EntityArgument.getPlayer(context, "target"),
                                                                StringArgumentType.getString(context, "type"),
                                                                StringArgumentType.getString(context, "rh")
                                                        ))
                                                )
                                        )
                                )
                        )
                )
                .then(Commands.literal("blood_transfusion")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("type", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(bloodTypes, builder))
                                        .then(Commands.argument("rh", StringArgumentType.word())
                                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(new String[]{"+", "-"}, builder))
                                                        // --- ここから分岐 ---
                                                        // 1. 量を指定しない場合 (デフォルト 1)
                                                        .executes(context -> giveBloodTransfusion(
                                                                context.getSource(),
                                                                EntityArgument.getPlayer(context, "target"),
                                                                StringArgumentType.getString(context, "type"),
                                                                StringArgumentType.getString(context, "rh"),
                                                                1 // デフォルト値
                                                        ))
                                                        // 2. 量を指定する場合
                                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 64))
                                                                .executes(context -> giveBloodTransfusion(
                                                                        context.getSource(),
                                                                        EntityArgument.getPlayer(context, "target"),
                                                                        StringArgumentType.getString(context, "type"),
                                                                        StringArgumentType.getString(context, "rh"),
                                                                        IntegerArgumentType.getInteger(context, "amount")
                                                                ))
                                                        )
                                                // --- ここまで ---
                                        )
                                )
                        )
                )
        );
    }

    private static int setBlood(CommandSourceStack source, Player player, int amount) {
        player.setData(AMAttachments.BLOOD_ATTACHMENT.get(), new BloodImplements(amount));
        SendPacket.sendBloodPacket(player, amount);
        source.sendSuccess(() -> Component.literal(player.getName().getString() + " の血液量を " + amount + " に設定しました"), true);
        return 1;
    }

    private static int setBleeding(CommandSourceStack source, Player player, int level) {
        player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(level));
        // 必要に応じてパケット送信処理を追加
        source.sendSuccess(() -> Component.literal(player.getName().getString() + " の出血レベルを " + level + " に設定しました"), true);
        return 1;
    }

    private static int setFracture(CommandSourceStack source, Player player, int level) {
        // タイマーも0リセットして設定
        player.setData(AMAttachments.FRACTURE_ATTACHMENT.get(), new FractureImplements(level, 0));
        SendPacket.sendFracturePacket(player, level);
        source.sendSuccess(() -> Component.literal(player.getName().getString() + " の骨折レベルを " + level + " に設定しました"), true);
        return 1;
    }

    private static int setBloodType(CommandSourceStack source, Player player, String type, String rhStr) {
        // 文字列をbooleanに変換
        boolean isRhPositive = rhStr.equals("+");

        // アタッチメントデータを取得
        BloodTypeImplements data = player.getData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get());

        // データを更新
        data.setType(type);
        data.setRh(isRhPositive);
        data.setInitialized(true); // 手動設定したので初期化済みとする

        // アタッチメントに再セット（NeoForgeのアタッチメント仕様により必要）
        player.setData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get(), data);

        // 必要であればここでクライアントにパケットを送る（GUI表示用など）
        // SendPacket.sendBloodTypePacket(player, type, isRhPositive);

        source.sendSuccess(() -> Component.literal(
                String.format("%s の血液型を %s%s に設定しました",
                        player.getName().getString(), type, rhStr)), true);

        return 1;
    }
    private static int setBloodTypeRandom(CommandSourceStack source, Player player) {
        // AMAttachments から血液型のアタッチメントを取得して渡す
        BloodTypeImplements data = player.getData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get());

        // 既存のランダム設定ロジックを呼び出す
        AMPlayerJoinEvent.setupRandomBloodType(data);

        data = player.getData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get());
        String rh;
        if (data.isRh()) rh = "+";
        else {
            rh = "-";
        }
        // プレイヤーへの通知（任意）
        BloodTypeImplements finalData = data;
        source.sendSuccess(() -> Component.literal(player.getName().getString() + " の血液型を再設定しました(" + finalData.getType() + rh + ")"), true);

        return 1; // コマンドの実行成功を返す
    }
    private static int giveBloodTransfusion(CommandSourceStack source, Player player, String type, String rhStr, int amount) {
        // Rhの文字列をbooleanに変換
        boolean isRhPositive = rhStr.equals("+");

        // アイテムスタックを作成し、個数を設定
        ItemStack stack = new ItemStack(AMItems.BLOOD_TRANSFUSION.get(), amount);

        // データコンポーネントの設定
        stack.set(AMDataComponents.BLOOD_TYPE.get(), new BloodTypeImplements(type, isRhPositive, true));

        // プレイヤーにアイテムを渡す（インベントリがいっぱいの場合は足元にドロップされる）
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }

        source.sendSuccess(() -> Component.literal(
                String.format("%s に血液パック(%s%s) を %d 個付与しました",
                        player.getName().getString(), type, rhStr, amount)), true);

        return 1;
    }
}