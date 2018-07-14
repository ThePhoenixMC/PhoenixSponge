package com.lss233.phoenix.sponge.listener;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.cause.entity.DamageModifier;
import com.lss233.phoenix.event.player.PlayerExpChangeEvent;
import com.lss233.phoenix.world.Location;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityExperienceEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;

import static com.lss233.phoenix.sponge.SpongeMain.getTransformer;

/**
 * Listener about Entity
 */
public class EntityListener {

    @Listener
    public void onMoveEntity(MoveEntityEvent event) {
        Entity entity = event.getTargetEntity();
        Cause cause = Cause.builder()
                .add("entity", getTransformer().toPhoenix(entity))
                .build();
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.entity.MoveEntityEvent() {
            @Override
            public void setTo(Location location) {
            }

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

    @Listener
    public void onChangeEntityExperience(ChangeEntityExperienceEvent event){
        Entity entity = event.getTargetEntity();
        if (entity.getType() == EntityTypes.PLAYER){
            com.lss233.phoenix.entity.living.Player pPlayer = getTransformer().toPhoenix((Player) entity);
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

    @Listener
    public void onDamageEntity(DamageEntityEvent event){
        Entity entity = event.getTargetEntity();
        Cause cause = Cause.builder()
                .add("entity",getTransformer().toPhoenix(entity))
                .build();
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.entity.DamageEntityEvent() {
            @Override
            public double getDamage() {
                return event.getOriginalDamage();
            }

            @Override
            public double getFinalDamage() {
                return event.getFinalDamage();
            }

            @Override
            public double getDamage(DamageModifier damageModifier) {
                return event.getBaseDamage();
            }

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

}
