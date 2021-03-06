package com.lss233.phoenix.sponge.utils.sponge.entity.living;

import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityType;
import com.lss233.phoenix.item.inventory.CarriedInventory;
import com.lss233.phoenix.item.inventory.Carrier;
import com.lss233.phoenix.item.inventory.Inventory;
import com.lss233.phoenix.item.inventory.ItemStack;
import com.lss233.phoenix.item.inventory.equipment.EquipmentType;
import com.lss233.phoenix.math.Vector;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.utils.Identifiable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

public interface PlayerTransform {
    default com.lss233.phoenix.entity.living.Player toPhoenix(Player player) {
        return new com.lss233.phoenix.entity.living.Player() {
            @Override
            public boolean hasPermission(String s) {
                return player.hasPermission(s);
            }

            @Override
            public CarriedInventory<? extends Carrier> getInventory() {
                //TODO
                return null;
            }

            @Override
            public boolean equip(EquipmentType equipmentType, @Nullable ItemStack itemStack) {
                player.equip(getTransformer().toSponge(equipmentType), getTransformer().toSponge(itemStack));
                return false;
            }

            @Override
            public Optional<ItemStack> getEquipped(EquipmentType equipmentType) {
                Optional<org.spongepowered.api.item.inventory.ItemStack> op = player.getEquipped(getTransformer().toSponge(equipmentType));
                return op.map(itemStack -> getTransformer().toPhoenix(itemStack));
            }

            @Override
            public String getName() {
                return player.getName();
            }

            @Override
            public void kick() {
                player.kick();
            }

            @Override
            public Optional<Inventory> openInventory(Inventory inventory) {
                return Optional.empty();
            }

            @Override
            public Optional<Inventory> getOpenInventory() {
                return Optional.empty();
            }

            @Override
            public boolean closeInventory() {
                return player.closeInventory();
            }

            @Override
            public EntityType getType() {
                return EntityType.valueOf(player.getType().toString());
            }

            @Override
            public boolean hasPassenger(com.lss233.phoenix.entity.Entity that) {
                return getTransformer().toSponge(that).filter(player::hasPassenger).isPresent();
            }

            @Override
            public List<com.lss233.phoenix.entity.Entity> getPassengers() {
                return player.getPassengers().stream().map(passenger -> getTransformer().toPhoenix(passenger)).collect(Collectors.toList());
            }

            @Override
            public boolean addPassenger(com.lss233.phoenix.entity.Entity that) {
                return getTransformer().toSponge(that).filter(player::addPassenger).isPresent();
            }

            @Override
            public void clearPassengers() {
                player.clearPassengers();
            }

            @Override
            public void removePassenger(com.lss233.phoenix.entity.Entity that) {
                getTransformer().toSponge(that).ifPresent(player::removePassenger);
            }

            @Override
            public Optional<com.lss233.phoenix.entity.Entity> getVehicle() {
                return player.getVehicle().map(vehicle -> getTransformer().toPhoenix(vehicle));
            }

            @Override
            public boolean setVehicle(com.lss233.phoenix.entity.Entity entity) {
                Optional<org.spongepowered.api.entity.Entity> op = getTransformer().toSponge(entity);
                if (!op.isPresent()) {
                    return false;
                }
                player.setVehicle(op.get());
                return true;
            }

            @Override
            public com.lss233.phoenix.entity.Entity getBaseVehicle() {
                return getTransformer().toPhoenix(player.getBaseVehicle());
            }

            @Override
            public Vector getVelocity() {
                return getTransformer().toPhoenix(player.getVelocity());
            }

            @Override
            public boolean gravity() {
                return player.gravity().get();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                return getTransformer().toSponge(location).filter(player::setLocation).isPresent();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.entity.Entity that) {
                return getTransformer().toSponge(that.getLocation()).filter(player::setLocation).isPresent();
            }

            @Override
            public double getHealth() {
                return player.getHealthData().health().get();
            }

            @Override
            public void setHealth(double health) {
                player.getHealthData().health().set(health);
            }

            @Override
            public double getMaxHealth() {
                return player.getHealthData().maxHealth().get();
            }

            @Override
            public UUID getUniqueId() {
                return player.getUniqueId();
            }


            @Override
            public void sendPluginMessage(Module source, String channel, byte[] messaeg) {
                throw new NotImplementedException();
            }

            @Override
            public void sendMessage(String message) {
                player.sendMessage(Text.of(message));
            }

            @Override
            public void sendMessage(String[] message) {
                for (String msg : message) {
                    this.sendMessage(msg);
                }
            }

            @Override
            public com.lss233.phoenix.world.Location getLocation() {
                return getTransformer().toPhoenix(player.getLocation());
            }

        };

    }

    default Optional<Player> toSponge(com.lss233.phoenix.entity.living.Player SPlayer) {
        return Sponge.getServer().getPlayer(SPlayer.getUniqueId());
    }
}
