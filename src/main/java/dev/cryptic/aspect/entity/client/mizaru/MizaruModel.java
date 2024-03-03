package dev.cryptic.aspect.entity.client.mizaru;

import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;
import dev.cryptic.aspect.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import software.bernie.geckolib.model.GeoModel;

public class MizaruModel extends GeoModel<Mizaru> {
    @Override
    public ResourceLocation getModelResource(Mizaru object) {
        return new ResourceLocation(Aspect.MODID, "geo/chomper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mizaru object) {
        return new ResourceLocation(Aspect.MODID, "textures/entity/chomper_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mizaru animatable) {
        return new ResourceLocation(Aspect.MODID, "animations/chomper.animation.json");
    }
}
