package com.lss233.phoenix.sponge;

import com.lss233.phoenix.command.CommandSender;
import com.lss233.phoenix.entity.EntityTypes;
import com.lss233.phoenix.module.Module;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
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
            public boolean equals(Object object) {
                if (object instanceof com.lss233.phoenix.entity.living.Player) {
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
            public String getName() {
                return player.getName();
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

    public static CommandSender toPhoenix(CommandSource src) {
        throw new NotImplementedException();
    }
}
