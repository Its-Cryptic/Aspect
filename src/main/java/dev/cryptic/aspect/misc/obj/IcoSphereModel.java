package dev.cryptic.aspect.misc.obj;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.EncryptedAPI;
import dev.cryptic.encryptedapi.util.model.ObjModel;
import net.minecraft.resources.ResourceLocation;

public class IcoSphereModel extends ObjModel {
    public static IcoSphereModel INSTANCE = new IcoSphereModel();
    @Override
    public ResourceLocation getModelLocation() {
        return Aspect.resourceLocation("ico_sphere");
    }
}
