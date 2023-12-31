package dev.cryptic.aspects.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractFluxBlock extends Block {
    public AbstractFluxBlock(Properties properties) {
        super(properties);
    }

//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new FluxBlockEntity();
//    }
}
