package com.lss233.phoenix.sponge.listener;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.player.PlayerExpChangeEvent;
import com.lss233.phoenix.sponge.SpongeUtils;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityExperienceEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;

import java.util.Optional;

/**
 * Listener about Entity
 */
public class EntityListener {

    @Listener
    public void onMoveEntity(MoveEntityEvent event) {
        Entity entity = event.getTargetEntity();
        Cause cause = Cause.builder()
                .add("entity", SpongeUtils.toPhoenix(entity))
                .build();
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.entity.MoveEntityEvent() {
            @Override
            public boolean isCancelled() {
                return event.isCancelled();
            }

            @Override
            public void setCancelled(boolean cancel) {
                event.setCancelled(cancel);
            }

            @Override
            public Cause getCause() {
                return cause;
            }
        });
    }

    @Listener void onChangeEntityExperience(ChangeEntityExperienceEvent event){
        Entity entity = event.getTargetEntity();
        if (entity.getType() == EntityTypes.PLAYER){
            com.lss233.phoenix.Player pPlayer = SpongeUtils.toPhoenix((Player) entity);
            Cause cause = Cause.builder()
                    .add("player",pPlayer)
                    .build();
            Phoenix.getEventManager().fire(new PlayerExpChangeEvent() {
                @Override
                public Cause getCause() {
                    return cause;
                }
            });
        }
    }

}
