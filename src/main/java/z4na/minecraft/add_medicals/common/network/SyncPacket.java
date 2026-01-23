package z4na.minecraft.add_medicals.common.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import z4na.minecraft.add_medicals.common.AddMedicals;

public class SyncPacket {

    public record BloodSyncPacket(int blood) implements CustomPacketPayload {

        public static final Type<BloodSyncPacket> TYPE_BLOOD = new Type<>(
                ResourceLocation.fromNamespaceAndPath(AddMedicals.MOD_ID, "blood_sync")
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE_BLOOD;
        }
    }

    public record BleedingSyncPacket(int bleeding) implements CustomPacketPayload {

        public static final Type<BleedingSyncPacket> TYPE_BLEEDING = new Type<>(
                ResourceLocation.fromNamespaceAndPath(AddMedicals.MOD_ID, "bleeding_sync")
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE_BLEEDING;
        }
    }

    public record FractureSyncPacket(int fracture) implements CustomPacketPayload {

        public static final Type<FractureSyncPacket> TYPE_FRACTURE = new Type<>(
                ResourceLocation.fromNamespaceAndPath(AddMedicals.MOD_ID, "fracture_sync")
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE_FRACTURE;
        }
    }
    public record IsDownedSyncPacket(boolean isDowned) implements CustomPacketPayload {

        public static final Type<IsDownedSyncPacket> TYPE_IS_DOWNED = new Type<>(
                ResourceLocation.fromNamespaceAndPath(AddMedicals.MOD_ID, "is_downed_sync")
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE_IS_DOWNED;
        }
    }
}