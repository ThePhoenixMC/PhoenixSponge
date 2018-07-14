package com.lss233.phoenix.sponge.utils.sponge.block;

import org.spongepowered.api.block.BlockState;

public interface BlockStateTransform {
    default BlockState toSponge(com.lss233.phoenix.block.BlockState state) {
        return BlockState.builder().build();
        //TODO
    }
    default com.lss233.phoenix.block.BlockState toPhoenix(BlockState state) {
        return new com.lss233.phoenix.block.BlockState() {
            @Override
            public int hashCode() {
                return state.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                //TODO
                return false;
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return state.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        };
    }
}
