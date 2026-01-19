package z4na.minecraft.add_medicals.client.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z4na.minecraft.add_medicals.common.attachments.AMAttachments;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {


    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        // 骨折レベルを取得
        int level = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get()).get();

        if (level >= 1) {
            // if sprinting
            if (player.isSprinting()) {
                player.setSprinting(false);
            }
        }
    }

    @Inject(method = "canStartSprinting", at = @At("HEAD"), cancellable = true)
    private void onCanStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        int level = player.getData(AMAttachments.FRACTURE_ATTACHMENT.get()).get();

        if (level >= 1) {
            cir.setReturnValue(false);
        }
    }
}