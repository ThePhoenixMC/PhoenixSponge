package com.lss233.phoenix.sponge;

import com.lss233.phoenix.command.CommandSender;
import com.lss233.phoenix.module.Module;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.entity.living.player.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 */
public class SpongeUtils {
    public static com.lss233.phoenix.World toPhoenix(World world) {
        com.lss233.phoenix.World PWorld;
        PWorld = new com.lss233.phoenix.World() {
            @Override
            public String getName() {
                return world.getName();
            }

            @Override
            public List<com.lss233.phoenix.Player> getPlayers() {
                List<com.lss233.phoenix.Player> players = new ArrayList<>();
                for (Player player : world.getPlayers())
                    players.add(toPhoenix(player));

                return players;
            }

            @Override
            public UUID getUUID() {
                return world.getUniqueId();
            }

            @Override
            public boolean equals(Object object) {
                if (object instanceof com.lss233.phoenix.Player) {
                    com.lss233.phoenix.World that = (com.lss233.phoenix.World) object;
                    return Objects.equals(this.getUUID(), that.getUUID());
                }
                return false;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.getName(), this.getUUID());
            }
        };
        return PWorld;
    }
    public static com.lss233.phoenix.Player toPhoenix(Player player) {
        com.lss233.phoenix.Player PPlayer;
        PPlayer = new com.lss233.phoenix.Player() {
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
                for (String msg:message) {
                    this.sendMessage(msg);
                }
            }

            @Override
            public com.lss233.phoenix.Location getLocation() {
                return toPhoenix(player.getLocation());
            }
        };
        return PPlayer;

    }

    private static com.lss233.phoenix.Location toPhoenix(Location<World> location) {
        com.lss233.phoenix.Location PLocation;
        PLocation = new com.lss233.phoenix.Location(toPhoenix(location.getExtent()), location.getX(), location.getY(), location.getZ());
        return PLocation;

    }

    public static CommandSender toPhoenix(CommandSource src) {
        throw new NotImplementedException();
    }
}
