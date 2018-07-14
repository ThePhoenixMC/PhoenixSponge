package com.lss233.phoenix.sponge.listener;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.event.cause.Cause;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.network.RemoteConnection;

import java.util.Optional;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

/**
 * Listener about player
 */
public class NetworkListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Cause cause = Cause.builder()
                .add("player", getTransformer().toPhoenix(event.getTargetEntity()))
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Join) () -> cause);
    }

    @Listener
    public void onPlayerQuit(ClientConnectionEvent.Disconnect event) {
        Cause cause = Cause.builder()
                .add("player", getTransformer().toPhoenix(event.getTargetEntity()))
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Disconnect) () -> cause);
    }

    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event) {
        Optional<Player> playerOptional = event.getTargetUser().getPlayer();
        com.lss233.phoenix.entity.living.Player phoenixPlayer = null;
        if (playerOptional.isPresent()) {
            phoenixPlayer = getTransformer().toPhoenix(playerOptional.get());
        }
        Cause cause = Cause.builder()
                .add("player", phoenixPlayer)
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Login) () -> cause);
    }

    @Listener
    public void onPlayerAuth(ClientConnectionEvent.Auth event) {
        RemoteConnection conn = event.getConnection();
        Cause cause = Cause.builder()
                .add("connection", conn)
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Auth) () -> cause);
    }

}
