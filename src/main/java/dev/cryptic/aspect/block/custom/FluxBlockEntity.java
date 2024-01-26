package dev.cryptic.aspect.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FluxBlockEntity extends BlockEntity {
    private int flux = 0;
    public static final int MAX_FLUX = 100; // Example max flux

    public FluxBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
        super(type, blockPos, blockState);
    }

    public void increaseFlux() {
        if (flux < MAX_FLUX) {
            flux++;
            setChanged(); // Mark the tile entity as changed so it saves
        }
    }

    public void decreaseFlux() {
        if (flux > 0) {
            flux--;
            setChanged();
        }
    }

    public int getFlux() {
        return flux;
    }
}
