package dev.cryptic.aspect.block.custom;

import net.minecraft.world.level.block.Block;

public abstract class AbstractFluxBlock extends Block {
    public AbstractFluxBlock(Properties properties) {
        super(properties);
    }

//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new FluxBlockEntity();
//    }
}
