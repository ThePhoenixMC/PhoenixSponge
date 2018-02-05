package com.lss233.phoenix.sponge;


import com.flowpowered.math.vector.Vector3d;
import com.lss233.phoenix.block.Block;
import com.lss233.phoenix.math.Vector;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
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

    public static Optional<Player> toSponge(com.lss233.phoenix.entity.living.Player SPlayer) {
        return Sponge.getServer().getPlayer(SPlayer.getUniqueId());
    }

    public static Location toSponge(com.lss233.phoenix.world.Location SLocation) {
        World world = toSponge(SLocation.getWorld()).get();
        return world.getLocation(SLocation.getX(), SLocation.getY(), SLocation.getZ());
    }

    public static BlockState toSponge(com.lss233.phoenix.block.BlockState state) {
        return BlockState.builder().build();
        //TODO
    }

    public static Vector3d toSponge(Vector vector) {
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
    }

    public static BlockSnapshot toSponge(Block block) {
        return BlockSnapshot.builder()
                .creator(block.getCreator().get())
                .blockState(toSponge(block.getBlockState()))
                .position(toSponge(block.getBlockLocation().getVector()).toInt())
                .build();
    }

}
