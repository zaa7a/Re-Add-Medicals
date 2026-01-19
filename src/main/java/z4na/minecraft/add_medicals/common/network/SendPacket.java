package z4na.minecraft.add_medicals.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SendPacket {
    public static void sendBloodPacket(Player player, int blood) {
        if (player instanceof ServerPlayer serverPlayer) {
            net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                    serverPlayer,
                    new SyncPacket.BloodSyncPacket(blood)
            );
        }
    }
    public static void sendBleedingPacket(Player player, int bleeding) {
        if (player instanceof ServerPlayer serverPlayer) {
            net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                    serverPlayer,
                    new SyncPacket.BleedingSyncPacket(bleeding)
            );
        }
    }
    public static void sendFracturePacket(Player player, int bleeding) {
        if (player instanceof ServerPlayer serverPlayer) {
            net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                    serverPlayer,
                    new SyncPacket.FractureSyncPacket(bleeding)
            );
        }
    }
}
