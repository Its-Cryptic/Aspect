package dev.cryptic.aspects.entity.client.mizaru;

import dev.cryptic.aspects.Aspects;
import net.minecraft.resources.ResourceLocation;
import dev.cryptic.aspects.entity.threewisemonkeys.Mizaru;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MizaruModel extends AnimatedGeoModel<Mizaru> {
    @Override
    public ResourceLocation getModelResource(Mizaru object) {
        return new ResourceLocation(Aspects.MODID, "geo/chomper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mizaru object) {
        return new ResourceLocation(Aspects.MODID, "textures/entity/chomper_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mizaru animatable) {
        return new ResourceLocation(Aspects.MODID, "animations/chomper.animation.json");
    }
}
