package com.lss233.phoenix.sponge.utils;

import com.lss233.phoenix.sponge.utils.sponge.block.BlockStateTransform;
import com.lss233.phoenix.sponge.utils.sponge.block.BlockTransform;
import com.lss233.phoenix.sponge.utils.sponge.command.CommandSourceTransform;
import com.lss233.phoenix.sponge.utils.sponge.entity.EntityTransformer;
import com.lss233.phoenix.sponge.utils.sponge.entity.living.PlayerTransform;
import com.lss233.phoenix.sponge.utils.sponge.inventory.EquipmentTypeTransformer;
import com.lss233.phoenix.sponge.utils.sponge.inventory.InventoryTransformer;
import com.lss233.phoenix.sponge.utils.sponge.inventory.ItemStackTransformer;
import com.lss233.phoenix.sponge.utils.sponge.utils.Vector3dTransform;
import com.lss233.phoenix.sponge.utils.sponge.world.LocationTransform;
import com.lss233.phoenix.sponge.utils.sponge.world.WorldPropertiesTransform;
import com.lss233.phoenix.sponge.utils.sponge.world.WorldTransform;

public interface Transform extends
        /* block */
        BlockTransform,
        BlockStateTransform,
        /* entity */
        EntityTransformer,
        /* entity.living */
        PlayerTransform,
        /* inventory */
        InventoryTransformer,
        EquipmentTypeTransformer,
        ItemStackTransformer,
        /* world */
        WorldTransform,
        LocationTransform,
        WorldPropertiesTransform,
        /* command */
        CommandSourceTransform,
        /* utils */
        Vector3dTransform {
}
