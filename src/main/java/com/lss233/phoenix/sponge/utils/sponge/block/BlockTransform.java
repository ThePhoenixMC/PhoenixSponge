package com.lss233.phoenix.sponge.utils.sponge.block;

import com.lss233.phoenix.block.Block;
import org.spongepowered.api.block.BlockSnapshot;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

public interface BlockTransform {
    default BlockSnapshot toSponge(Block block) {
        return BlockSnapshot.builder()
                .creator(block.getCreator().get())
                .blockState(getTransformer().toSponge(block.getBlockState()))
                .position(getTransformer().toSponge(block.getBlockLocation().getVector()).toInt())
                .build();
    }
    default Block toPhoenix(BlockSnapshot block) {
        return Block.builder().creator(block.getCreator().get()).location(getTransformer().toPhoenix(block.getLocation().get())).state(getTransformer().toPhoenix(block.getState())).build();
    }
}
