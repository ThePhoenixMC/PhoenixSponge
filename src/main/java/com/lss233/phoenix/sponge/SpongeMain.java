package com.lss233.phoenix.sponge;

import com.google.inject.Inject;
import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.command.Command;
import com.lss233.phoenix.command.PhoenixCommand;
import com.lss233.phoenix.entity.living.Player;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.phoenix.PhoenixShutdownEvent;
import com.lss233.phoenix.item.enchantment.Enchantment;
import com.lss233.phoenix.item.inventory.Inventory;
import com.lss233.phoenix.item.inventory.ItemStack;
import com.lss233.phoenix.sponge.listener.EntityListener;
import com.lss233.phoenix.sponge.listener.NetworkListener;
import com.lss233.phoenix.sponge.utils.Transform;
import com.lss233.phoenix.sponge.utils.TransformUtil;
import com.lss233.phoenix.world.World;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

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

    private Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private SpongeMain instance;

    private static Transform transformer = new TransformUtil();

    /**
     * Gets the transformer of this instance.
     *
     * @return The transformer
     */
    public static Transform getTransformer() {
        return transformer;
    }

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
            return "";
        }

        public String getServerId() {
            return "";
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
            server.getWorlds().forEach((item) -> list.add(getTransformer().toPhoenix(item)));
            return list;
        }

        public List<Player> getOnlinePlayers() {
            List<Player> list = new ArrayList<>();
            server.getOnlinePlayers().forEach((item) -> list.add(getTransformer().toPhoenix(item)));
            return list;
        }

        public File getPhoenixDataDir() {
            return dataFolder.toFile();
        }

        public com.lss233.phoenix.logging.Logger getLogger() {
            return new com.lss233.phoenix.logging.Logger() {
                @Override
                public void info(Object msg) {
                    Sponge.getServer().getConsole().sendMessage(Text.builder(String.valueOf(msg)).color(TextColors.WHITE).build());
                }

                @Override
                public void warn(Object msg) {
                    Sponge.getServer().getConsole().sendMessage(Text.builder(String.valueOf(msg)).color(TextColors.GOLD).build());

                }

                @Override
                public void debug(Object msg) {
                    if (Phoenix.getDebugMode()) {
                        Sponge.getServer().getConsole().sendMessage(Text.builder(String.valueOf(msg)).color(TextColors.GRAY).build());
                    }
                }

                @Override
                public void severe(Object msg) {
                    Sponge.getServer().getConsole().sendMessage(Text.builder(String.valueOf(msg)).color(TextColors.DARK_RED).build());

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
                                    GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("args"))))
                            .executor((src, args) -> {
                                if (args.hasAny("args")) {
                                    Phoenix.getCommandManager().handleCommand(getTransformer().toPhoenix(src), b_label, args.getAll("args").stream().toArray(String[]::new));
                                } else {
                                    Phoenix.getCommandManager().handleCommand(getTransformer().toPhoenix(src), b_label, new String[]{});
                                }
                                return CommandResult.success();
                            });
                    Sponge.getCommandManager().register(SpongeMain.this, commandSpecBuilder.build(), b_label);
                }

                @Override
                public MessageChannelManager getMessageChannelManager() {
                    return null;
                }

                @Override
                public Inventory registerInventory(Inventory.Builder builder) {
                    org.spongepowered.api.item.inventory.Inventory.Builder spongeBuilder = org.spongepowered.api.item.inventory.Inventory.builder();
                    if (builder.getProperties().containsKey(com.lss233.phoenix.item.inventory.property.InventoryTitle.PROPERTY_NAME)) {
                        com.lss233.phoenix.item.inventory.property.InventoryTitle title = (com.lss233.phoenix.item.inventory.property.InventoryTitle) builder.getProperties().get(com.lss233.phoenix.item.inventory.property.InventoryTitle.PROPERTY_NAME);
                        spongeBuilder.property(InventoryTitle.PROPERTY_NAME,
                                new InventoryTitle(Text.of(title.getText().toString())));
                    }
                    if (builder.getProperties().containsKey(com.lss233.phoenix.item.inventory.property.InventoryDimension.PROPERTY_NAME)) {
                        com.lss233.phoenix.item.inventory.property.InventoryDimension dimension = (com.lss233.phoenix.item.inventory.property.InventoryDimension) builder.getProperties().get(com.lss233.phoenix.item.inventory.property.InventoryDimension.PROPERTY_NAME);
                        spongeBuilder.property(InventoryDimension.PROPERTY_NAME,
                                new InventoryDimension(dimension.getColumns(), dimension.getRows()));
                    }
                    spongeBuilder.build(SpongeMain.this);
                    return getTransformer().toPhoenix(spongeBuilder.build(SpongeMain.this));
                }

                @Override
                public ItemStack registerItemStack(ItemStack.Builder builder) {
                    return null;
                }

                @Override
                public Enchantment registerEnchantment(Enchantment.Builder builder) {
                    return null;
                }
            };
        }
    }
}
