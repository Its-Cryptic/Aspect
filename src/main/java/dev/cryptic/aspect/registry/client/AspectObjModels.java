package dev.cryptic.aspect.registry.client;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.encryptedapi.api.vfx.model.ObjModel;
import dev.cryptic.encryptedapi.registries.ObjModelRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AspectObjModels {
    private static final Map<String, ObjModel> MODELS = new HashMap<>();

    public static final ObjModel IcoSphereModel = registerModel("ico_sphere");
    public static final ObjModel IcoSphereHDModel = registerModel("ico_sphere_hd_4");
    public static final ObjModel MonkeyModel = registerModel("monkey");
    public static final ObjModel MonkeyHDModel = registerModel("monkeyhd");
    public static final ObjModel SphereShieldModel = registerModel("shield01");

    public static ObjModel registerModel(String name) {
        ObjModel model = new ObjModel() {
            @Override
            public ResourceLocation getModelLocation() {
                return Aspect.id(name);
            }
        };
        MODELS.put(name, model);
        return model;
    }

    public static void init() {
        MODELS.forEach((name, model) -> {
            ObjModelRegistry.registerModel(model);
            Aspect.LOGGER.debug("Registered model: " + model.getModelLocation());
        });
    }
}
