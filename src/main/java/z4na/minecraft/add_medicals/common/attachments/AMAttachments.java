package z4na.minecraft.add_medicals.common.attachments;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import z4na.minecraft.add_medicals.common.implement.*;

import java.util.function.Supplier;

import static z4na.minecraft.add_medicals.common.AMRegistries.ATTACHMENT_TYPES;

public class AMAttachments {
    public static final Supplier<AttachmentType<BloodImplements>> BLOOD_ATTACHMENT = ATTACHMENT_TYPES.register(
            "blood",
            () -> AttachmentType.builder(() -> new BloodImplements())
                    .serialize(BloodImplements.CODEC)
                    .build()
    );
    public static final Supplier<AttachmentType<BleedingImplements>> BLEEDING_ATTACHMENT = ATTACHMENT_TYPES.register(
            "bleeding",
            () -> AttachmentType.builder(() -> new BleedingImplements())
                    .serialize(BleedingImplements.CODEC)
                    .build()
    );
    public static final Supplier<AttachmentType<FractureImplements>> FRACTURE_ATTACHMENT = ATTACHMENT_TYPES.register(
            "fracture",
            () -> AttachmentType.builder(() -> new FractureImplements())
                    .serialize(FractureImplements.CODEC)
                    .build()
    );
    public static final Supplier<AttachmentType<BloodTypeImplements>> BLOOD_TYPE_ATTACHMENT = ATTACHMENT_TYPES.register(
            "blood_type",
            () -> AttachmentType.builder(() -> new BloodTypeImplements())
                    .serialize(BloodTypeImplements.CODEC)
                    .copyOnDeath()
                    .build()
            );
    public static final Supplier<AttachmentType<BloodRegenImplements>> BLOOD_REGEN_TICK = ATTACHMENT_TYPES.register(
            "blood_regen",
            () -> AttachmentType.builder(() -> new BloodRegenImplements())
                    .serialize(BloodRegenImplements.CODEC)
                    .build()
    );

    public static void init() {}
}
