package z4na.minecraft.add_medicals.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.damage_sources.AMDamageSources;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;
import z4na.minecraft.add_medicals.common.registries.AMDataComponents;

import java.util.List;

public class BloodTransfusionItem extends Item {

    public BloodTransfusionItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 40;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity user) {
        if (user instanceof Player player && !level.isClientSide && player.isAlive()) {

            BloodTypeImplements packData = stack.getOrDefault(AMDataComponents.BLOOD_TYPE.get(), new BloodTypeImplements());
            BloodTypeImplements playerData = player.getData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get());


            if (BloodTypeImplements.canReceive(packData.getType(), packData.isRh(), playerData.getType(), playerData.isRh())) {

                BloodImplements bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
                int newBlood = bloodData.get() + 200;
                bloodData.set(newBlood);


                SendPacket.sendBloodPacket(player, newBlood);
            } else {
                //if not compatibilities blood type
                if (!player.isCreative()) {
                    player.hurt(AMDamageSources.bleeding(player.level()), 6.0F);
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                }
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
        BloodTypeImplements data = stack.get(AMDataComponents.BLOOD_TYPE.get());
        if (data != null) {
            String sign = data.isRh() ? "+" : "-";
            tooltip.add(Component.translatable("tooltip.add_medicals.blood_type", data.getType(), sign)
                    .withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.add_medicals.blood_type.undefined")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}