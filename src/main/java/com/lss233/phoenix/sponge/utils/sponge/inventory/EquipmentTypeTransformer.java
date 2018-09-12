package com.lss233.phoenix.sponge.utils.sponge.inventory;

import com.lss233.phoenix.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;

public interface EquipmentTypeTransformer {

    default EquipmentType toPhoenix(org.spongepowered.api.item.inventory.equipment.EquipmentType equipmentType) {
        if (equipmentType == EquipmentTypes.BOOTS) {
            return EquipmentType.BOOTS;
        }
        if (equipmentType == EquipmentTypes.CHESTPLATE) {
            return EquipmentType.CHESTPLATE;
        }
        if (equipmentType == EquipmentTypes.MAIN_HAND) {
            return EquipmentType.MAIN_HAND;
        }
        if (equipmentType == EquipmentTypes.OFF_HAND) {
            return EquipmentType.OFF_HAND;
        }
        if (equipmentType == EquipmentTypes.LEGGINGS) {
            return EquipmentType.LEGGINGS;
        }
        if (equipmentType == EquipmentTypes.HEADWEAR) {
            return EquipmentType.HEADWEAR;
        }
        return null;
    }

    default org.spongepowered.api.item.inventory.equipment.EquipmentType toSponge(EquipmentType equipmentType) {
        switch (equipmentType) {
            case BOOTS:
                return EquipmentTypes.BOOTS;
            case CHESTPLATE:
                return EquipmentTypes.CHESTPLATE;
            case MAIN_HAND:
                return EquipmentTypes.MAIN_HAND;
            case LEGGINGS:
                return EquipmentTypes.LEGGINGS;
            case HEADWEAR:
                return EquipmentTypes.HEADWEAR;
            case OFF_HAND:
                return EquipmentTypes.OFF_HAND;
        }
        return null;
    }
}
