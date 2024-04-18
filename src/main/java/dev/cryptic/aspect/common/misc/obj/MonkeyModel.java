package dev.cryptic.aspect.common.misc.obj;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.api.vfx.model.ObjModel;
import net.minecraft.resources.ResourceLocation;

public class MonkeyModel extends ObjModel {
    public static MonkeyModel INSTANCE = new MonkeyModel();
    @Override
    public ResourceLocation getModelLocation() {
        //return Aspect.id("monkey");
        return Aspect.id("monkeyhd");
    }
}
