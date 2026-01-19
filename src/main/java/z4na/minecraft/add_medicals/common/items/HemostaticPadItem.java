package z4na.minecraft.add_medicals.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.implement.BleedingImplements;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;
import z4na.minecraft.add_medicals.common.registries.AMDataComponents;

import java.util.List;

public class HemostaticPadItem extends Item {

    public HemostaticPadItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BleedingImplements bleedingData = player.getData(AMAttachments.BLEEDING_ATTACHMENT.get());

        if (bleedingData.get() > 0) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 40;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide() && player.isAlive()) {
            BleedingImplements bleedingData = player.getData(AMAttachments.BLEEDING_ATTACHMENT.get());
            int currentLevel = bleedingData.get();

            if (currentLevel > 0) {
                int nextLevel = currentLevel - 1;

                player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(nextLevel, 0));

                SendPacket.sendBleedingPacket(player, nextLevel);
            }
            player.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.2f);

            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        //tooltip.add(Component.translatable("tooltip.add_medicals.hemostatic_pad_desc").withStyle(ChatFormatting.GRAY));
    }
}