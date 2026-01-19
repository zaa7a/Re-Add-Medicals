package z4na.minecraft.add_medicals.common.events.player;


import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;
import z4na.minecraft.add_medicals.common.damage_type.AMDamageTypes;
import z4na.minecraft.add_medicals.common.implement.BleedingImplements;
import z4na.minecraft.add_medicals.common.implement.BloodImplements;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import z4na.minecraft.add_medicals.common.implement.BloodRegenImplements;
import z4na.minecraft.add_medicals.common.implement.FractureImplements;
import z4na.minecraft.add_medicals.common.interfaces.Bleeding;
import z4na.minecraft.add_medicals.common.network.SendPacket;

public class AMPlayerHurtEvent {
    public static void onEvent(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (event.getSource().is(AMDamageTypes.BLEEDING)) return;
            //get blood
            BloodImplements bloodData = player.getData(AMAttachments.BLOOD_ATTACHMENT.get());
            //get damage
            float actualDamage = event.getNewDamage();
            if (actualDamage <= 0) return;
            //damage * 25 (config) -> 1.1 * 25 Ceil = 28(27.5 Ceil)
            double rawReduction = actualDamage * 25.0;
            int reduction = (int) Math.ceil(rawReduction);
            int newBlood = Math.max(0, bloodData.get() - reduction);
            //set blood
            bloodData.set(newBlood);
            //set player data
            player.setData(AMAttachments.BLOOD_ATTACHMENT.get(), new BloodImplements(newBlood));
            SendPacket.sendBloodPacket(player, newBlood);
            player.setData(AMAttachments.BLOOD_REGEN_TICK.get(), new BloodRegenImplements(0));

            if (reduction > 50) {
                BleedingImplements bleedingData = player.getData(AMAttachments.BLEEDING_ATTACHMENT.get());
                int increaseLevel = (int) Math.floor((double) reduction / 50);
                int newBleedingLevel = Math.min(7, bleedingData.get() + increaseLevel);
                // set data
                player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(newBleedingLevel));
                SendPacket.sendBleedingPacket(player, newBleedingLevel);
                player.setData(AMAttachments.BLEEDING_ATTACHMENT.get(), new BleedingImplements(newBleedingLevel, 0));
            }

            if (event.getSource().is(DamageTypes.FALL)) {
                if (actualDamage >= 4) {

                    FractureImplements fractureData = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get());
                    int fractureLevel = fractureData.get();
                    int newFractureLevel = 1;
                    if (actualDamage >= 10) {
                        newFractureLevel = 2;
                    }
                    if (actualDamage >= 15) {
                        newFractureLevel = 3;
                    }
                    if (fractureLevel < newFractureLevel) player.setData(AMAttachments.FRACTURE_ATTACHMENT.get(), new FractureImplements(newFractureLevel));
                    fractureData = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get());

                    SendPacket.sendFracturePacket(player, fractureData.get());
                }
            }
        }
    }
}