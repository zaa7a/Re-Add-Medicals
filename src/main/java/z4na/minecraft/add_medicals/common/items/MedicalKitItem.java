package z4na.minecraft.add_medicals.common.items;

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
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;

import java.util.List;

public class MedicalKitItem extends Item {

    public MedicalKitItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 80;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide() && player.isAlive()) {
            // 現在の出血データを取得
            BloodImplements bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
            BleedingImplements bleedingData = player.getData(AMAttachments.BLEEDING_ATTACHMENT.get());
            FractureImplements fractureData = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get());
            int currentBleedingLevel = bleedingData.get();
            int currentFractureLevel = fractureData.get();
            int currentBloodLevel = bloodData.get();
            if (currentBleedingLevel > 0) {
                int nextBleedingLevel = Math.max(0, currentBleedingLevel - 2);

                player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(nextBleedingLevel, 0));

                SendPacket.sendBleedingPacket(player, nextBleedingLevel);
            }
            if (currentFractureLevel > 0) {
                int nextFractureLevel = Math.max(0, currentFractureLevel - 2);

                player.setData(AMAttachments.FRACTURE_ATTACHMENT.get(), new FractureImplements(nextFractureLevel, 0));

                SendPacket.sendFracturePacket(player, nextFractureLevel);
            }
            if (currentBloodLevel < 999) {
                int newBlood = currentBloodLevel + 200;

                bloodData.set(newBlood);

                SendPacket.sendBloodPacket(player, newBlood);
            }
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 4.0F));
            player.playSound(SoundEvents.ITEM_PICKUP, 1.0f, 1.2f);
            // shrink
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