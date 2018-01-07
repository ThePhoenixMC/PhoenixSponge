package com.lss233.phoenix.sponge;


import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class PhoenixUtils {

    public static Optional<Entity> toSponge(com.lss233.phoenix.entity.Entity SEntity) {
        com.lss233.phoenix.world.World SWorld = SEntity.getWorld();
        World world = toSponge(SWorld).get();
        return world.getEntity(SEntity.getUniqueId());
    }

    public static Optional<World> toSponge(com.lss233.phoenix.world.World SWorld) {
        return Sponge.getServer().getWorld(SWorld.getUniqueId());
    }

    public static Optional<Player> toSponge(com.lss233.phoenix.entity.living.Player SPlayer){
        return Sponge.getServer().getPlayer(SPlayer.getUniqueId());
    }

    public static Location toSponge(com.lss233.phoenix.world.Location SLocation){
        World world = toSponge(SLocation.getWorld()).get();
        return world.getLocation(SLocation.getX(),SLocation.getY(),SLocation.getZ());
    }


}
