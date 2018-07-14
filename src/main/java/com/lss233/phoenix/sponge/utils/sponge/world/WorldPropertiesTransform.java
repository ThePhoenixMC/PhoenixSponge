package com.lss233.phoenix.sponge.utils.sponge.world;

import com.flowpowered.math.vector.Vector3d;
import com.lss233.phoenix.world.Difficulty;
import com.lss233.phoenix.world.WorldBorder;
import org.spongepowered.api.world.difficulty.Difficulties;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.Optional;

public interface WorldPropertiesTransform {
    default com.lss233.phoenix.world.WorldProperties toPhoenix(WorldProperties properties) {
        return new com.lss233.phoenix.world.WorldProperties() {
            @Override
            public Difficulty getDifficulty() {
                return Difficulty.valueOf(properties.getDifficulty().getName());
            }

            @Override
            public void setDifficulty(Difficulty difficulty) {
                switch (difficulty) {
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
                properties.setGameRule(key, value);
            }

            @Override
            public com.lss233.phoenix.world.Location getSpawnLocation() {
                //TODO
                return null;
            }

            @Override
            public void setSpawnLocation(com.lss233.phoenix.world.Location spawnLocation) {
                double x = spawnLocation.getX();
                double y = spawnLocation.getY();
                double z = spawnLocation.getZ();
                Vector3d position = new Vector3d(x, y, z);
                properties.setSpawnPosition(position.toInt());
            }

            @Override
            public WorldBorder getWorldBorderCenter() {
                //TODO
                return null;
            }
        };
    }
}
