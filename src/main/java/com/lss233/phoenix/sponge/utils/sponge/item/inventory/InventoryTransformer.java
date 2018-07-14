package com.lss233.phoenix.sponge.utils.sponge.item.inventory;

import com.lss233.phoenix.item.inventory.Inventory;

public interface InventoryTransformer {
    default Inventory toPhoenix(org.spongepowered.api.item.inventory.Inventory inventory){
        return null; // TODO
    }

    default org.spongepowered.api.item.inventory.Inventory toSponge(Inventory inventory){
        return null; // TODO
    }
}
