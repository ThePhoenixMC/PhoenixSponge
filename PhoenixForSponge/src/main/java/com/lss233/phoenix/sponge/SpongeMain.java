package com.lss233.phoenix.sponge;

import com.google.inject.Inject;
import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.command.Command;
import com.lss233.phoenix.command.PhoenixCommand;
import com.lss233.phoenix.entity.living.Player;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.phoenix.PhoenixShutdownEvent;
import com.lss233.phoenix.sponge.listener.EntityListener;
import com.lss233.phoenix.sponge.listener.NetworkListener;
import com.lss233.phoenix.world.World;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Plugin(id = "phoenixsponge", name = "Phoenix For Sponge", version = "1.0", description = "Phoenix Framework for Sponge.")
public class SpongeMain {
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dataFolder;
    @Inject
    private Game game;
    @Inject
    private Logger logger;

    private SpongeMain instance;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        SpongeServer server = new SpongeServer();
        Phoenix.setServer(server);
        initSpongeSide();
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
        Phoenix.getEventManager().fire((PhoenixShutdownEvent) () -> Cause.builder().build());
    }

    private void initSpongeSide() {
        Sponge.getEventManager().registerListeners(this, new NetworkListener());
        Sponge.getEventManager().registerListeners(this, new EntityListener());
    }

    private class SpongeServer implements Phoenix.Server {
        private Server server = game.getServer();

        public String getName() {
            return "Sponge";
        }

        public String getVersion() {
            return game.getPlatform().getMinecraftVersion().getName();
        }

        public String getIp() {
            if (!game.isServerAvailable()) {
                return "0.0.0.0";
            }
            Optional<InetSocketAddress> bound = server.getBoundAddress();
            return bound.map(InetSocketAddress::getHostString).orElse("0.0.0.0");
        }

        public String getServerName() {
            return null;
        }

        public String getServerId() {
            return null;
        }

        public int getMaxPlayers() {
            if (!game.isServerAvailable()) {
                return 1;
            }
            return server.getMaxPlayers();
        }

        public int getViewDistance() {
            return -1; // TODO: Update API is required.
        }

        public boolean hasAllowNether() {
            return true; // TODO: Not Implemented yet.
        }

        public boolean hasWhitelist() {
            return game.isServerAvailable() && server.hasWhitelist();
        }

        public boolean hasGenerateStructures() {
            return false; // TODO: Not Implemented yet.
        }

        public List<World> getWorlds() {
            List<World> list = new ArrayList<>();
            server.getWorlds().forEach((item) -> list.add(SpongeUtils.toPhoenix(item)));
            return list;
        }

        public List<Player> getOnlinePlayers() {
            List<Player> list = new ArrayList<>();
            server.getOnlinePlayers().forEach((item) -> list.add(SpongeUtils.toPhoenix(item)));
            return list;
        }

        public File getPhoenixDataDir() {
            return dataFolder.toFile();
        }

        public com.lss233.phoenix.logging.Logger getLogger() {
            return new com.lss233.phoenix.logging.Logger() {
                @Override
                public void info(String msg) {
                    logger.info(msg);
                }

                @Override
                public void warn(String msg) {
                    logger.warn(msg);
                }

                @Override
                public void debug(String msg) {
                    logger.debug(msg);
                }
            };
        }

        public Interface getInterface() {
            return new Interface() {
                @Override
                public void loadModules() {
                    File moduleDir = new File(dataFolder.toFile(), "modules");
                    if (!moduleDir.exists()) {
                        moduleDir.mkdirs();
                        return;
                    }

                    for (File file : moduleDir.listFiles()) {
                        if (!file.getName().endsWith(".jar"))
                            continue;
                        try {
                            Phoenix.getModuleManager().loadModule(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void registerCommand(Command command) {
                    String b_label = command.getClass().getAnnotation(PhoenixCommand.class).label(),
                            description = command.getClass().getAnnotation(PhoenixCommand.class).description(),
                            permission = command.getClass().getAnnotation(PhoenixCommand.class).permission();
                    CommandSpec.Builder commandSpecBuilder = CommandSpec.builder()
                            .description(Text.of(description))
                            .permission(permission)
                            .arguments(
                                    GenericArguments.remainingJoinedStrings(Text.of("args")))
                            .executor((src, args) -> {
                                Phoenix.getCommandManager().handleCommand(SpongeUtils.toPhoenix(src), b_label, args.getAll("args").stream().toArray(String[]::new));
                                return null;
                            });
                    game.getCommandManager().register(instance, commandSpecBuilder.build(), "helloworld", "hello", "test");
                }

                @Override
                public MessageChannelManager getMessageChannelManager() {
                    return null;
                }
            };
        }
    }
}
