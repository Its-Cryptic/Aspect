package dev.cryptic.aspects.entity.client.mizaru;

import dev.cryptic.aspects.Aspect;
import net.minecraft.resources.ResourceLocation;
import dev.cryptic.aspects.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MizaruModel extends AnimatedGeoModel<Mizaru> {
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
