package dev.cryptic.aspect.common.misc.obj;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.api.vfx.model.ObjModel;
import net.minecraft.resources.ResourceLocation;

public class IcoSphereModel extends ObjModel {
    public static IcoSphereModel INSTANCE = new IcoSphereModel();
    @Override
    public ResourceLocation getModelLocation() {
        return Aspect.id("ico_sphere");
    }
}
