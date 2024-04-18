package dev.cryptic.aspect.common.misc.obj;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.api.vfx.model.ObjModel;
import net.minecraft.resources.ResourceLocation;

public class IcoSphereHDModel extends ObjModel {
    public static IcoSphereHDModel INSTANCE = new IcoSphereHDModel();
    @Override
    public ResourceLocation getModelLocation() {
        return Aspect.id("ico_sphere_hd_4");
    }
}