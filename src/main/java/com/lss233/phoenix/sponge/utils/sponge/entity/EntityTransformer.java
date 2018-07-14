package com.lss233.phoenix.sponge.utils.sponge.entity;

import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityType;
import com.lss233.phoenix.math.Vector;
import org.spongepowered.api.world.World;

import java.util.*;
import java.util.stream.Collectors;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

public interface EntityTransformer {
    default com.lss233.phoenix.entity.Entity toPhoenix(org.spongepowered.api.entity.Entity entity) {
        return new Entity() {
            @Override
            public UUID getUniqueId() {
                return entity.getUniqueId();
            }

            @Override
            public EntityType getType() {
                return EntityType.valueOf(entity.getType().toString());
            }

            @Override
            public boolean hasPassenger(com.lss233.phoenix.entity.Entity that) {
                return getTransformer().toSponge(that).filter(entity::hasPassenger).isPresent();
            }

            @Override
            public List<Entity> getPassengers() {
                return entity.getPassengers().stream().map(passenger -> getTransformer().toPhoenix(passenger)).collect(Collectors.toList());
            }

            @Override
            public boolean addPassenger(com.lss233.phoenix.entity.Entity that) {
                return getTransformer().toSponge(that).filter(entity::addPassenger).isPresent();
            }

            @Override
            public void clearPassengers() {
                entity.clearPassengers();
            }

            @Override
            public void removePassenger(com.lss233.phoenix.entity.Entity that) {
                getTransformer().toSponge(that).ifPresent(entity::removePassenger);
            }

            @Override
            public Optional<Entity> getVehicle() {
                return entity.getVehicle().map(vehicle -> getTransformer().toPhoenix(vehicle));
            }

            @Override
            public boolean setVehicle(com.lss233.phoenix.entity.Entity entity) {
                return false;
            }

            @Override
            public com.lss233.phoenix.entity.Entity getBaseVehicle() {
                return getTransformer().toPhoenix(entity.getBaseVehicle());
            }

            @Override
            public Vector getVelocity() {
                return getTransformer().toPhoenix(entity.getVelocity());
            }

            @Override
            public boolean gravity() {
                return entity.gravity().get();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                return getTransformer().toSponge(location).filter(entity::setLocation).isPresent();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.entity.Entity that) {
                return getTransformer().toSponge(that.getLocation()).filter(entity::setLocation).isPresent();
            }

            @Override
            public com.lss233.phoenix.world.Location getLocation() {
                return getTransformer().toPhoenix(entity.getLocation());
            }

            @Override
            public com.lss233.phoenix.world.World getWorld() {
                return getTransformer().toPhoenix(entity.getWorld());
            }
        };
    }

    default Optional<org.spongepowered.api.entity.Entity> toSponge(Entity entity){
        Optional<World> world = getTransformer().toSponge(entity.getWorld());
        return world.flatMap(world1 -> world1.getEntity(entity.getUniqueId()));
    }
}
