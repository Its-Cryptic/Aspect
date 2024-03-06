package dev.cryptic.aspect.common.blockentities.fluxcore;

import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FluxCoreModel extends GeoModel<FluxCoreBlockEntity> {
    @Override
    public ResourceLocation getModelResource(FluxCoreBlockEntity object) {
        return new ResourceLocation(Aspect.MODID, "geo/flux_core.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FluxCoreBlockEntity object) {
        return new ResourceLocation(Aspect.MODID, "textures/block/flux_core.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FluxCoreBlockEntity object) {
        return new ResourceLocation(Aspect.MODID, "animations/flux_core.animation.json");
    }
}
