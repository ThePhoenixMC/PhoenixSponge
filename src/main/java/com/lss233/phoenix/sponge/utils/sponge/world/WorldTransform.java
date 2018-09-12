package com.lss233.phoenix.sponge.utils.sponge.world;

import com.lss233.phoenix.block.Block;
import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityType;
import com.lss233.phoenix.utils.Identifiable;
import com.lss233.phoenix.world.Chunk;
import com.lss233.phoenix.world.Location;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.util.*;
import java.util.stream.Collectors;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

public interface WorldTransform {

    default com.lss233.phoenix.world.World toPhoenix(World world) {
        return new com.lss233.phoenix.world.World() {
            @Override
            public Optional<com.lss233.phoenix.entity.Entity> createEntity(EntityType entityType, com.lss233.phoenix.math.Vector vector) {
                return Sponge
                        .getRegistry()
                        .getAllOf(org.spongepowered.api.entity.EntityType.class)
                        .stream()
                        .filter(e -> e.getName().equals(entityType.name()))
                        .findFirst()
                        .map(entityType1 -> getTransformer()
                                .toPhoenix(world
                                        .createEntity(entityType1, getTransformer().toSponge(vector))));
            }

            @Override
            public List<Entity> getNearbyEntities(com.lss233.phoenix.math.Vector location, double distance) {
                return world.getNearbyEntities(getTransformer().toSponge(location), distance).stream().map(getTransformer()::toPhoenix).collect(Collectors.toList());
            }

            @Override
            public List<Entity> getNearbyEntities(Location location, double distance) {
                return null;
            }

            @Override
            public List<com.lss233.phoenix.entity.Entity> getEntities() {
                return world.getEntities().stream().map(getTransformer()::toPhoenix).collect(Collectors.toList());
            }

            @Override
            public Optional<com.lss233.phoenix.entity.Entity> getEntity(UUID uniqueId) {
                return world.getEntity(uniqueId).map(getTransformer()::toPhoenix);
            }

            @Override
            public String getName() {
                return world.getName();
            }

            @Override
            public List<com.lss233.phoenix.entity.living.Player> getPlayers() {
                List<com.lss233.phoenix.entity.living.Player> players = new ArrayList<>();
                for (Player player : world.getPlayers())
                    players.add(getTransformer().toPhoenix(player));

                return players;
            }

            @Override
            public UUID getUniqueId() {
                return world.getUniqueId();
            }

            @Override
            public boolean equals(Identifiable other) {
                return false;
            }

            @Override
            public com.lss233.phoenix.world.WorldProperties getProperties() {
                return getTransformer().toPhoenix(world.getProperties());
            }

            @Override
            public boolean setBlock(Block block, boolean force) {
                BlockSnapshot bs = getTransformer().toSponge(block);
                world.setBlock(bs.getPosition(), bs.getState());
                return true;
            }

            @Override
            public boolean equals(com.lss233.phoenix.world.World other) {
                return false;
            }

            @Override
            public Location getSpawnLocation() {
                return null;
            }

            @Override
            public int getSeaLevel() {
                return 0;
            }

            @Override
            public void save() {

            }

            @Override
            public Chunk getChunk(int i, int i1) {
                return null;
            }

            @Override
            public Chunk getChunk(Location location) {
                return null;
            }

            @Override
            public Chunk getChunk(Block block) {
                return null;
            }

            @Override
            public boolean loadChunk(int i, int i1, boolean b) {
                return false;
            }

            @Override
            public boolean unloadChunk(Chunk chunk) {
                return false;
            }

            @Override
            public void createExplosion(double v, double v1, double v2, float v3) {

            }

            @Override
            public List<Chunk> getLoadedChunks() {
                return null;
            }
        };
    }

    default Optional<World> toSponge(com.lss233.phoenix.world.World SWorld) {
        return Sponge.getServer().getWorld(SWorld.getUniqueId());
    }
}
