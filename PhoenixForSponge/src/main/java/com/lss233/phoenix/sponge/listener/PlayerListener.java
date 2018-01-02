package com.lss233.phoenix.sponge.listener;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.event.cause.Cause;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

/**
 * Listener about player
 */
public class PlayerListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Cause cause = Cause.builder()
                .add("player", event.getCause().first(Player.class))
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Join) () -> cause);
    }

    @Listener
    public void onPlayerQuit(ClientConnectionEvent.Disconnect event){
        Cause cause = Cause.builder()
                .add("player",event.getCause().first(Player.class))
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Disconnect) ()-> cause);
    }

    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event){
        Cause cause = Cause.builder()
                .add("player",event.getCause().first(Player.class))
                .build();
        Phoenix.getEventManager().fire((com.lss233.phoenix.event.network.ClientConnectionEvent.Login) () -> cause);
    }


}
