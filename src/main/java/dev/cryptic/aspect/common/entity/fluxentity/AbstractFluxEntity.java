package dev.cryptic.aspect.common.entity.fluxentity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class AbstractFluxEntity extends PathfinderMob implements GeoEntity {

    protected AbstractFluxEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }
}
