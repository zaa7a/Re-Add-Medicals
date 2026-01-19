package z4na.minecraft.add_medicals.common.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.CapabilityRegistry;
import net.neoforged.neoforge.capabilities.EntityCapability;
import z4na.minecraft.add_medicals.common.interfaces.Bleeding;
import z4na.minecraft.add_medicals.common.interfaces.Blood;
import z4na.minecraft.add_medicals.common.interfaces.BloodType;
import z4na.minecraft.add_medicals.common.interfaces.Fracture;

public class AMCapabilities {
    public static final EntityCapability<Blood, Void> BLOOD =
            EntityCapability.create(
                    ResourceLocation.fromNamespaceAndPath("add_medicals", "blood"),
                    Blood.class,
                    Void.class
            );
    public static final EntityCapability<Bleeding, Void> BLEEDING =
            EntityCapability.create(
                    ResourceLocation.fromNamespaceAndPath("add_medicals", "bleeding"),
                    Bleeding.class,
                    Void.class
            );
    public static final EntityCapability<Fracture, Void> FRACTURE =
            EntityCapability.create(
                    ResourceLocation.fromNamespaceAndPath("add_medicals", "fracture"),
                    Fracture.class,
                    Void.class
            );
    public static final EntityCapability<BloodType, Void> BLOOD_TYPE =
            EntityCapability.create(
                    ResourceLocation.fromNamespaceAndPath("add_medicals", "blood_type"),
                    BloodType.class,
                    Void.class
            );
}
