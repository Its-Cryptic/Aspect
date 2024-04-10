package dev.cryptic.aspect.common.misc.obj;


import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.api.vfx.model.ObjModel;
import net.minecraft.resources.ResourceLocation;

public class SphereShieldModel extends ObjModel {
    public static SphereShieldModel INSTANCE = new SphereShieldModel();
    @Override
    public ResourceLocation getModelLocation() {
        return Aspect.id("shield01");
    }
}
