package dev.cryptic.aspect.misc.obj;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.EncryptedAPI;
import dev.cryptic.encryptedapi.util.model.ObjModel;
import net.minecraft.resources.ResourceLocation;

public class MonkeyModel extends ObjModel {
    public static MonkeyModel INSTANCE = new MonkeyModel();
    @Override
    public ResourceLocation getModelLocation() {
        return Aspect.resourceLocation("monkey");
    }
}
