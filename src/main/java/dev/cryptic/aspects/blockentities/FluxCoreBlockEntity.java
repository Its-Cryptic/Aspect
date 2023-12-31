package dev.cryptic.aspects.blockentities;

import dev.cryptic.aspects.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FluxCoreBlockEntity extends BlockEntity {

    private static int MAX_FLUX = 100;

    private int flux;
    public FluxCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUX_CORE.get(), pos, state);
    }

    public void tick() {

    }

    public boolean isCharging() {
        // if player is using the charge keybind on a block then return true
        return true;
    }

    public void increaseFlux(int flux) {
        if (this.flux + flux <= MAX_FLUX) {
            this.flux += flux;
            setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Flux", this.flux);
    }
}
