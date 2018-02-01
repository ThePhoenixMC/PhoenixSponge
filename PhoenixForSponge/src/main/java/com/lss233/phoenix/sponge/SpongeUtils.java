package com.lss233.phoenix.sponge;

import com.flowpowered.math.vector.Vector3d;
import com.lss233.phoenix.command.CommandSender;
import com.lss233.phoenix.entity.EntityTypes;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.world.Difficulty;
import com.lss233.phoenix.world.WorldBorder;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.storage.WorldProperties;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 *
 */
public class SpongeUtils {
    public static com.lss233.phoenix.world.World toPhoenix(World world) {
        com.lss233.phoenix.world.World PWorld;
        PWorld = new com.lss233.phoenix.world.World() {
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
            public EntityTypes getType() {
                return EntityTypes.valueOf(player.getType().toString());
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
            public EntityTypes getType() {
                return EntityTypes.valueOf(entity.getType().toString());
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

    public static com.lss233.phoenix.world.WorldProperties toPhoenix(WorldProperties properties){
        return new com.lss233.phoenix.world.WorldProperties() {
            @Override
            public Difficulty getDifficulty() {
                return Difficulty.valueOf(properties.getDifficulty().getName());
            }

            @Override
            public void setDifficulty(Difficulty difficulty) {
                switch (difficulty){
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
                properties.setGameRule(key,value);
            }

            @Override
            public com.lss233.phoenix.world.Location getSpawnLocation() {
                //TODO
                return null;
            }

            @Override
            public void setSpawnLocation(com.lss233.phoenix.world.Location spawnLocation){
                double x = spawnLocation.getX();
                double y = spawnLocation.getY();
                double z = spawnLocation.getZ();
                Vector3d position = new Vector3d(x,y,z);
                properties.setSpawnPosition(position.toInt());
            }

            @Override
            public WorldBorder getWorldBorderCenter() {
                //TODO
                return null;
            }
        };
    }

    public static CommandSender toPhoenix(CommandSource src) {
        throw new NotImplementedException();
    }
}
