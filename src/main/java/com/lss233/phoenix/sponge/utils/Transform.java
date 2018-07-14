package com.lss233.phoenix.sponge.utils;

import com.lss233.phoenix.sponge.utils.sponge.block.BlockStateTransform;
import com.lss233.phoenix.sponge.utils.sponge.block.BlockTransform;
import com.lss233.phoenix.sponge.utils.sponge.command.CommandSourceTransform;
import com.lss233.phoenix.sponge.utils.sponge.entity.EntityTransformer;
import com.lss233.phoenix.sponge.utils.sponge.entity.living.PlayerTransform;
import com.lss233.phoenix.sponge.utils.sponge.item.inventory.InventoryTransformer;
import com.lss233.phoenix.sponge.utils.sponge.utils.Vector3dTransform;
import com.lss233.phoenix.sponge.utils.sponge.world.LocationTransform;
import com.lss233.phoenix.sponge.utils.sponge.world.WorldTransform;
import com.lss233.phoenix.sponge.utils.sponge.world.WorldPropertiesTransform;

public interface Transform extends
        /* block */
        BlockTransform,
        BlockStateTransform,
        /* entity */
        EntityTransformer,
        /* entity.living */
        PlayerTransform,
        /* item.inventory */
        InventoryTransformer,
        /* world */
        WorldTransform,
        LocationTransform,
        WorldPropertiesTransform,
        /* command */
        CommandSourceTransform,
        /* utils */
        Vector3dTransform {
}
