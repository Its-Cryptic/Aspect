package dev.cryptic.aspect.blockentities.fluxcore;

import dev.cryptic.aspect.blockentities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class FluxCoreBlockEntity extends BlockEntity implements IAnimatable {

    private static int MAX_FLUX = 100;
    private int flux;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
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

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
