package z4na.minecraft.add_medicals.common.network;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import z4na.minecraft.add_medicals.common.AddMedicals;
import z4na.minecraft.add_medicals.client.AddMedicalsClient;

// bus引数を削除。NeoForgeが引数の型から自動判別します。
@EventBusSubscriber(modid = AddMedicals.MOD_ID)
public class PacketHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                SyncPacket.BloodSyncPacket.TYPE_BLOOD,
                StreamCodec.composite(
                        ByteBufCodecs.VAR_INT, SyncPacket.BloodSyncPacket::blood,
                        SyncPacket.BloodSyncPacket::new
                ),
                AddMedicalsClient::handleBloodSync
        );
        registrar.playToClient(
                SyncPacket.BleedingSyncPacket.TYPE_BLEEDING,
                StreamCodec.composite(
                        ByteBufCodecs.VAR_INT, SyncPacket.BleedingSyncPacket::bleeding,
                        SyncPacket.BleedingSyncPacket::new
                ),
                AddMedicalsClient::handleBleedingSync
        );
        registrar.playToClient(
                SyncPacket.FractureSyncPacket.TYPE_FRACTURE,
                StreamCodec.composite(
                        ByteBufCodecs.VAR_INT, SyncPacket.FractureSyncPacket::fracture,
                        SyncPacket.FractureSyncPacket::new
                ),
                AddMedicalsClient::handleFractureSync
        );
        registrar.playToClient(
                SyncPacket.IsDownedSyncPacket.TYPE_IS_DOWNED,
                StreamCodec.composite(
                        ByteBufCodecs.BOOL, SyncPacket.IsDownedSyncPacket::isDowned,
                        SyncPacket.IsDownedSyncPacket::new
                ),
                AddMedicalsClient::handleIsDownedSync
        );
    }
}