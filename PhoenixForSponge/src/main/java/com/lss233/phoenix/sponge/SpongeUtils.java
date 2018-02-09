package com.lss233.phoenix.sponge;

import com.flowpowered.math.vector.Vector3d;
import com.lss233.phoenix.block.Block;
import com.lss233.phoenix.command.CommandSender;
import com.lss233.phoenix.entity.EntityType;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.world.Difficulty;
import com.lss233.phoenix.world.WorldBorder;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.storage.WorldProperties;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.text.html.Option;
import java.util.*;

/**
 *
 */
public class SpongeUtils {
    public static com.lss233.phoenix.world.World toPhoenix(World world) {
        com.lss233.phoenix.world.World PWorld;
        PWorld = new com.lss233.phoenix.world.World() {
            @Override
            public Optional<com.lss233.phoenix.entity.Entity> createEntity(EntityType entityType, com.lss233.phoenix.math.Vector vector) {
                Optional<org.spongepowered.api.entity.EntityType> type = Sponge.getRegistry().getAllOf(org.spongepowered.api.entity.EntityType.class).stream().filter(e -> e.getName().equals(entityType.name())).findFirst();
                return type.map(entityType1 -> toPhoenix(world.createEntity(entityType1, PhoenixUtils.toSponge(vector))));
            }

            @Override
            public List<com.lss233.phoenix.entity.Entity> getNearbyEntities(com.lss233.phoenix.math.Vector location, double distance) {
                List<com.lss233.phoenix.entity.Entity> entities = new ArrayList<>();
                world.getNearbyEntities(PhoenixUtils.toSponge(location), distance).forEach(e -> entities.add(toPhoenix(e)));
                return entities;
            }

            @Override
            public List<com.lss233.phoenix.entity.Entity> getEntities() {
                List<com.lss233.phoenix.entity.Entity> entities = new ArrayList<>();
                world.getEntities().forEach(e-> entities.add(toPhoenix(e)));
                return entities;
            }

            @Override
            public Optional<com.lss233.phoenix.entity.Entity> getEntity(UUID uniqueId) {
                Optional<Entity> result = world.getEntity(uniqueId);
                return result.map(SpongeUtils::toPhoenix);
            }

            @Override
            public String getName() {
                return world.getName();
            }

            @Override
            public List<com.lss233.phoenix.entity.living.Player> getPlayers() {
                List<com.lss233.phoenix.entity.living.Player> players = new ArrayList<>();
                for (Player player : world.getPlayers())
                    players.add(toPhoenix(player));

                return players;
            }

            @Override
            public UUID getUniqueId() {
                return world.getUniqueId();
            }

            @Override
            public com.lss233.phoenix.world.WorldProperties getProperties() {
                return toPhoenix(world.getProperties());
            }

            @Override
            public boolean setBlock(Block block, boolean force) {
                BlockSnapshot bs = PhoenixUtils.toSponge(block);
                world.setBlock(bs.getPosition(), bs.getState());
                return true;
            }

            @Override
            public boolean equals(com.lss233.phoenix.world.World other) {
                return equals(other);
            }

            @Override
            public boolean equals(Object object) {
                if (object instanceof com.lss233.phoenix.world.World) {
                    com.lss233.phoenix.world.World that = (com.lss233.phoenix.world.World) object;
                    return Objects.equals(this.getUniqueId(), that.getUniqueId());
                }
                return false;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.getName(), this.getUniqueId());
            }
        };
        return PWorld;
    }

    public static com.lss233.phoenix.entity.living.Player toPhoenix(Player player) {
        com.lss233.phoenix.entity.living.Player PPlayer;
        PPlayer = new com.lss233.phoenix.entity.living.Player() {
            @Override
            public String getName() {
                return player.getName();
            }

            @Override
            public void kick() {
                player.kick();
            }

            @Override
            public EntityType getType() {
                return EntityType.valueOf(player.getType().toString());
            }

            @Override
            public boolean hasPassenger(com.lss233.phoenix.entity.Entity entity) {
                Entity that = PhoenixUtils.toSponge(entity).get();
                return player.hasPassenger(that);
            }

            @Override
            public List<com.lss233.phoenix.entity.Entity> getPassengers() {
                List<com.lss233.phoenix.entity.Entity> PList = new ArrayList<>();
                player.getPassengers().forEach(it -> PList.add(toPhoenix(it)));
                return PList;
            }

            @Override
            public boolean addPassenger(com.lss233.phoenix.entity.Entity entity) {
                Entity that = PhoenixUtils.toSponge(entity).get();
                return player.addPassenger(that);
            }

            @Override
            public void clearPassengers() {
                player.clearPassengers();
            }

            @Override
            public void removePassenger(com.lss233.phoenix.entity.Entity entity) {
                Entity that = PhoenixUtils.toSponge(entity).get();
                player.removePassenger(that);
            }

            @Override
            public Optional<com.lss233.phoenix.entity.Entity> getVehicle() {
                return Optional.empty();
            }

            @Override
            public boolean setVehicle(com.lss233.phoenix.entity.Entity entity) {
                return false;
            }

            @Override
            public com.lss233.phoenix.entity.Entity getBaseVehicle() {
                return null;
            }

            @Override
            public Vector getVelocity() {
                return null;
            }

            @Override
            public boolean gravity() {
                return player.gravity().get();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                Location SLocation = PhoenixUtils.toSponge(location);
                return player.setLocation(SLocation);
            }

            @Override
            public boolean teleport(com.lss233.phoenix.entity.Entity entity) {
                Location SLocation = PhoenixUtils.toSponge(entity.getLocation());
                return player.setLocation(SLocation);
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
                return toPhoenix(player.getLocation());
            }

            @Override
            public com.lss233.phoenix.world.World getWorld() {
                return toPhoenix(player.getWorld());
            }
        };
        return PPlayer;

    }

    private static com.lss233.phoenix.world.Location toPhoenix(Location<World> location) {
        com.lss233.phoenix.world.Location PLocation;
        PLocation = new com.lss233.phoenix.world.Location(toPhoenix(location.getExtent()), location.getX(), location.getY(), location.getZ());
        return PLocation;

    }

    public static com.lss233.phoenix.entity.Entity toPhoenix(Entity entity) {
        com.lss233.phoenix.entity.Entity PEntity;
        PEntity = new com.lss233.phoenix.entity.Entity() {
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
                Entity SEntity = PhoenixUtils.toSponge(that).get();
                return entity.hasPassenger(SEntity);
            }

            @Override
            public List<com.lss233.phoenix.entity.Entity> getPassengers() {
                List<com.lss233.phoenix.entity.Entity> PList = new ArrayList<>();
                entity.getPassengers().forEach(it -> PList.add(toPhoenix(entity)));
                return PList;
            }

            @Override
            public boolean addPassenger(com.lss233.phoenix.entity.Entity that) {
                Entity SEntity = PhoenixUtils.toSponge(that).get();
                return entity.addPassenger(SEntity);
            }

            @Override
            public void clearPassengers() {
                entity.clearPassengers();
            }

            @Override
            public void removePassenger(com.lss233.phoenix.entity.Entity that) {
                Entity SEntity = PhoenixUtils.toSponge(that).get();
                entity.removePassenger(SEntity);
            }

            @Override
            public Optional<com.lss233.phoenix.entity.Entity> getVehicle() {
                return Optional.empty();
            }

            @Override
            public boolean setVehicle(com.lss233.phoenix.entity.Entity entity) {
                return false;
            }

            @Override
            public com.lss233.phoenix.entity.Entity getBaseVehicle() {
                return null;
            }

            @Override
            public Vector getVelocity() {
                return null;
            }

            @Override
            public boolean gravity() {
                return entity.gravity().get();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                Location SLocation = PhoenixUtils.toSponge(location);
                return entity.setLocation(SLocation);
            }

            @Override
            public boolean teleport(com.lss233.phoenix.entity.Entity that) {
                Location SLocation = PhoenixUtils.toSponge(that.getLocation());
                return entity.setLocation(SLocation);
            }

            @Override
            public com.lss233.phoenix.world.Location getLocation() {
                return toPhoenix(entity.getLocation());
            }

            @Override
            public com.lss233.phoenix.world.World getWorld() {
                return toPhoenix(entity.getWorld());
            }
        };
        return PEntity;
    }

    public static com.lss233.phoenix.world.WorldProperties toPhoenix(WorldProperties properties) {
        return new com.lss233.phoenix.world.WorldProperties() {
            @Override
            public Difficulty getDifficulty() {
                return Difficulty.valueOf(properties.getDifficulty().getName());
            }

            @Override
            public void setDifficulty(Difficulty difficulty) {
                switch (difficulty) {
                    case EASY:
                        properties.setDifficulty(Difficulties.EASY);
                        break;
                    case HARD:
                        properties.setDifficulty(Difficulties.HARD);
                        break;
                    case NORMAL:
                        properties.setDifficulty(Difficulties.NORMAL);
                        break;
                    case PEACEFUL:
                        properties.setDifficulty(Difficulties.PEACEFUL);
                        break;
                }
            }

            @Override
            public boolean isHardcore() {
                return properties.isHardcore();
            }

            @Override
            public void setHardcore(boolean hardcore) {
                properties.setHardcore(hardcore);
            }

            @Override
            public long getSeed() {
                return properties.getSeed();
            }

            @Override
            public void setSeed(long seed) {
                properties.setSeed(seed);
            }

            @Override
            public long getTotalTime() {
                return properties.getTotalTime();
            }

            @Override
            public long getWorldTime() {
                return properties.getWorldTime();
            }

            @Override
            public void setWorldTime(long time) {
                properties.getWorldTime();
            }

            @Override
            public int getThunderDuration() {
                return properties.getThunderTime();
            }

            @Override
            public void setThunderDuration(int thunderDuration) {
                properties.setThunderTime(thunderDuration);
            }

            @Override
            public boolean isThundering() {
                return properties.isThundering();
            }

            @Override
            public void setThundering(boolean thundering) {
                properties.setThundering(thundering);
            }

            @Override
            public int getRainDuration() {
                return properties.getRainTime();
            }

            @Override
            public void setRainDuration(int rainDuration) {
                properties.setRainTime(rainDuration);
            }

            @Override
            public boolean isRaining() {
                return properties.isRaining();
            }

            @Override
            public void setRaining(boolean raining) {
                properties.setRaining(raining);
            }

            @Override
            public Optional<String> getGameRules(String gameRule) {
                return properties.getGameRule(gameRule);
            }

            @Override
            public void setGameRule(String key, String value) {
                properties.setGameRule(key, value);
            }

            @Override
            public com.lss233.phoenix.world.Location getSpawnLocation() {
                //TODO
                return null;
            }

            @Override
            public void setSpawnLocation(com.lss233.phoenix.world.Location spawnLocation) {
                double x = spawnLocation.getX();
                double y = spawnLocation.getY();
                double z = spawnLocation.getZ();
                Vector3d position = new Vector3d(x, y, z);
                properties.setSpawnPosition(position.toInt());
            }

            @Override
            public WorldBorder getWorldBorderCenter() {
                //TODO
                return null;
            }
        };
    }

    public static Block toPhoenix(BlockSnapshot block) {
        return Block.builder().creator(block.getCreator().get()).location(toPhoenix(block.getLocation().get())).state(toPhoenix(block.getState())).build();
    }

    public static com.lss233.phoenix.block.BlockState toPhoenix(BlockState state) {
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

    public static CommandSender toPhoenix(CommandSource src) {
        return new CommandSender() {

            @Override
            public void sendMessage(String message) {
                src.sendMessage(Text.of(message));
            }

            @Override
            public void sendMessage(String[] message) {
                src.sendMessage(Text.of((Object[]) message));
            }
        };
    }
}
