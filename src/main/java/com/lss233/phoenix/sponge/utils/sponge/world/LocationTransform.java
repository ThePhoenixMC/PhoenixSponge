package com.lss233.phoenix.sponge.utils.sponge.world;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

public interface LocationTransform {
    default com.lss233.phoenix.world.Location toPhoenix(Location<World> location) {
        return new com.lss233.phoenix.world.Location(getTransformer().toPhoenix(location.getExtent()), location.getX(), location.getY(), location.getZ());
    }
    default Optional<Location> toSponge(com.lss233.phoenix.world.Location SLocation) {
        return getTransformer().toSponge(SLocation.getWorld()).map(world -> world.getLocation(SLocation.getX(), SLocation.getY(), SLocation.getZ()));
    }
}
