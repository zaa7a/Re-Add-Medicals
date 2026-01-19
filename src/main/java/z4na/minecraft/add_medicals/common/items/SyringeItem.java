package z4na.minecraft.add_medicals.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.damage_sources.AMDamageSources;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import z4na.minecraft.add_medicals.common.implement.BloodRegenImplements;
import z4na.minecraft.add_medicals.common.implement.BloodTypeImplements;
import z4na.minecraft.add_medicals.common.network.SendPacket;
import z4na.minecraft.add_medicals.common.registries.AMDataComponents;
import z4na.minecraft.add_medicals.common.registries.AMItems;

public class SyringeItem extends Item {

    public SyringeItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        //server only
        if (!level.isClientSide()) {
            ItemStack syringe = player.getItemInHand(hand);
            BloodTypeImplements playerBloodData = player.getData(AMAttachments.BLOOD_TYPE_ATTACHMENT.get());
            ItemStack bloodTransfusion = new ItemStack(AMItems.BLOOD_TRANSFUSION.get());
            bloodTransfusion.set(AMDataComponents.BLOOD_TYPE.get(), new BloodTypeImplements(
                    playerBloodData.getType(),
                    playerBloodData.isRh(),
                    true
            ));
            //item (if drop)
            if (!player.getInventory().add(bloodTransfusion)) {
                player.drop(bloodTransfusion, false);
            }
            if (!player.isCreative() && !player.isSpectator()) {
                player.hurt(AMDamageSources.bleeding(player.level()), 4.0F);
                BloodImplements bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
                int blood = bloodData.get();
                bloodData.set(Math.max(0, blood - 200));
                player.setData(AMAttachments.BLOOD_REGEN_TICK.get(), new BloodRegenImplements(300));
                SendPacket.sendBloodPacket(player, Math.max(0, blood - 200));

                player.getCooldowns().addCooldown(this, 40);
                // remove 1 durability (& break)
                syringe.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

            }
            return InteractionResultHolder.sidedSuccess(syringe, level.isClientSide());
        }
        else {

            ItemStack syringe = player.getItemInHand(hand);
            return InteractionResultHolder.fail(syringe);
        }
    }
}