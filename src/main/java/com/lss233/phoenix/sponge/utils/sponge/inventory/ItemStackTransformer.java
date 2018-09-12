package com.lss233.phoenix.sponge.utils.sponge.inventory;

import com.lss233.phoenix.item.inventory.ItemStack;

public interface ItemStackTransformer {

    default ItemStack toPhoenix(org.spongepowered.api.item.inventory.ItemStack itemStack) {
        //TODO
        return null;
    }

    default org.spongepowered.api.item.inventory.ItemStack toSponge(ItemStack itemStack) {
        //TODO
        return null;
    }
}
