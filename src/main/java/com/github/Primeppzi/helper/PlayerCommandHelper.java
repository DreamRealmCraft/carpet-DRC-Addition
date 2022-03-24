package com.github.Primeppzi.helper;

import carpet.CarpetSettings;
import carpet.commands.PlayerCommand;
import carpet.fakes.ServerPlayerEntityInterface;
import carpet.helpers.EntityPlayerActionPack;
import carpet.patches.EntityPlayerMPFake;
import carpet.utils.Messenger;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.RotationArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * This code are copied from PlayerCommand in carpet mod but with some modification
 */
public class PlayerCommandHelper {
    public static LiteralArgumentBuilder<ServerCommandSource> makeActionCommand(String actionName, EntityPlayerActionPack.ActionType type) {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal(actionName).executes((c) -> {
            return action(c, type, EntityPlayerActionPack.Action.once());
        })).then(CommandManager.literal("once").executes((c) -> {
            return action(c, type, EntityPlayerActionPack.Action.once());
        }))).then(CommandManager.literal("continuous").executes((c) -> {
            return action(c, type, EntityPlayerActionPack.Action.continuous());
        }))).then(CommandManager.literal("interval").then(CommandManager.argument("ticks", IntegerArgumentType.integer(1)).executes((c) -> {
            return action(c, type, EntityPlayerActionPack.Action.interval(IntegerArgumentType.getInteger(c, "ticks")));
        })));
    }

    public static LiteralArgumentBuilder<ServerCommandSource> makeDropCommand(String actionName, boolean dropAll) {
        return (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(actionName).then(CommandManager.literal("all").executes((c) -> {
            return manipulate(c, (ap) -> {
                ap.drop(-2, dropAll);
            });
        }))).then(CommandManager.literal("mainhand").executes((c) -> {
            return manipulate(c, (ap) -> {
                ap.drop(-1, dropAll);
            });
        }))).then(CommandManager.literal("offhand").executes((c) -> {
            return manipulate(c, (ap) -> {
                ap.drop(40, dropAll);
            });
        }))).then(CommandManager.argument("slot", IntegerArgumentType.integer(0, 40)).executes((c) -> {
            return manipulate(c, (ap) -> {
                ap.drop(IntegerArgumentType.getInteger(c, "slot"), dropAll);
            });
        }));
    }

    public static Collection<String> getPlayers(ServerCommandSource source) {
        Set<String> players = Sets.newLinkedHashSet(Arrays.asList("deliver", "kuaidi"));
        return players;
    }

    public static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context) {
        String playerName = StringArgumentType.getString(context, "prefix");
        MinecraftServer server = ((ServerCommandSource)context.getSource()).getServer();
        return server.getPlayerManager().getPlayer(playerName);
    }

    public static boolean cantManipulate(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = getPlayer(context);
        if (player == null) {
            Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Can only manipulate existing players"});
            return true;
        } else {
            ServerPlayerEntity sendingPlayer;
            try {
                sendingPlayer = ((ServerCommandSource)context.getSource()).getPlayer();
            } catch (CommandSyntaxException var4) {
                return false;
            }

            if (!((ServerCommandSource)context.getSource()).getServer().getPlayerManager().isOperator(sendingPlayer.getGameProfile()) && sendingPlayer != player && !(player instanceof EntityPlayerMPFake)) {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Non OP players can't control other real players"});
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean cantReMove(CommandContext<ServerCommandSource> context) {
        if (cantManipulate(context)) {
            return true;
        } else {
            PlayerEntity player = getPlayer(context);
            if (player instanceof EntityPlayerMPFake) {
                return false;
            } else {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Only fake players can be moved or killed"});
                return true;
            }
        }
    }

    public static boolean cantSpawn(CommandContext<ServerCommandSource> context) {
        String playerName = String.valueOf(IntegerArgumentType.getInteger(context, "number")) ;
        MinecraftServer server = ((ServerCommandSource)context.getSource()).getServer();
        PlayerManager manager = server.getPlayerManager();
        PlayerEntity player = manager.getPlayer(playerName);
        if (player != null) {
            Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Player ", "rb " + playerName, "r  is already logged on"});
            return true;
        } else {
            GameProfile profile = (GameProfile)server.getUserCache().findByName(playerName).orElse(null);
            if (profile == null) {
                if (!CarpetSettings.allowSpawningOfflinePlayers) {
                    Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Player " + playerName + " is either banned by Mojang, or auth servers are down. Banned players can only be summoned in Singleplayer and in servers in off-line mode."});
                    return true;
                }

                profile = new GameProfile(PlayerEntity.getOfflinePlayerUuid(playerName), playerName);
            }

            if (manager.getUserBanList().contains(profile)) {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Player ", "rb " + playerName, "r  is banned on this server"});
                return true;
            } else if (manager.isWhitelistEnabled() && manager.isWhitelisted(profile) && !((ServerCommandSource)context.getSource()).hasPermissionLevel(2)) {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Whitelisted players can only be spawned by operators"});
                return true;
            } else {
                return false;
            }
        }
    }

    public static int kill(CommandContext<ServerCommandSource> context) {
        if (cantReMove(context)) {
            return 0;
        } else {
            getPlayer(context).kill();
            return 1;
        }
    }

    public static int lookAt(CommandContext<ServerCommandSource> context) {
        return manipulate(context, (ap) -> {
            ap.lookAt(Vec3ArgumentType.getVec3(context, "position"));
        });
    }

    public static <T> T tryGetArg(PlayerCommandHelper.SupplierWithCommandSyntaxException<T> a, PlayerCommandHelper.SupplierWithCommandSyntaxException<T> b) throws CommandSyntaxException {
        try {
            return a.get();
        } catch (IllegalArgumentException var3) {
            return b.get();
        }
    }

    public static int spawn(CommandContext<ServerCommandSource> context, int a) throws CommandSyntaxException {
        if (cantSpawn(context)) {
            return 0;
        } else {
            ServerCommandSource source = (ServerCommandSource)context.getSource();
            PlayerCommandHelper.SupplierWithCommandSyntaxException var10000 = () -> {
                return Vec3ArgumentType.getVec3(context, "position");
            };
            Objects.requireNonNull(source);
            Vec3d pos = (Vec3d)tryGetArg(var10000, source::getPosition);
            var10000 = () -> {
                return RotationArgumentType.getRotation(context, "direction").toAbsoluteRotation((ServerCommandSource)context.getSource());
            };
            Objects.requireNonNull(source);
            Vec2f facing = (Vec2f)tryGetArg(var10000, source::getRotation);
            RegistryKey<World> dimType = (RegistryKey)tryGetArg(() -> {
                return DimensionArgumentType.getDimensionArgument(context, "dimension").getRegistryKey();
            }, () -> {
                return source.getWorld().getRegistryKey();
            });
            GameMode mode = GameMode.CREATIVE;
            boolean flying = false;

            try {
                ServerPlayerEntity player = ((ServerCommandSource)context.getSource()).getPlayer();
                mode = player.interactionManager.getGameMode();
                flying = player.getAbilities().flying;
            } catch (CommandSyntaxException var11) {
            }

            String playerName;
            try {
                playerName = StringArgumentType.getString(context, "gamemode");
                mode = GameMode.byName(playerName, (GameMode)null);
                if (mode == null) {
                    Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"rb Invalid game mode: " + playerName + "."});
                    return 0;
                }
            } catch (IllegalArgumentException var10) {
            }

            if (mode == GameMode.SPECTATOR) {
                flying = true;
            } else if (mode.isSurvivalLike()) {
                flying = false;
            }

            playerName = StringArgumentType.getString(context,"prefix") ;
            if (playerName.length() > maxPlayerLength(source.getServer())) {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"rb Player name: " + playerName + " is too long"});
                return 0;
            } else {
                MinecraftServer server = source.getServer();
                if (!World.isValid(new BlockPos(pos.x, pos.y, pos.z))) {
                    Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"rb Player " + playerName + " cannot be placed outside of the world"});
                    return 0;
                } else {
                    for (int i = 1; i <= a; i++) {
                        PlayerEntity player = EntityPlayerMPFake.createFake(playerName+i, server, pos.x, pos.y, pos.z, (double) facing.y, (double) facing.x, dimType, mode, flying);


                        if (player == null) {
                            Messenger.m((ServerCommandSource) context.getSource(), new Object[]{"rb Player " + StringArgumentType.getString(context, "player") + " doesn't exist and cannot spawn in online mode. Turn the server offline to spawn non-existing players"});
                            return 0;
                        } else {
                        }
                    }
                    return 1;
                }
            }
        }
    }

    public static int maxPlayerLength(MinecraftServer server) {
        return server.getServerPort() >= 0 ? 16 : 40;
    }

    public static int stop(CommandContext<ServerCommandSource> context) {
        if (cantManipulate(context)) {
            return 0;
        } else {
            ServerPlayerEntity player = getPlayer(context);
            ((ServerPlayerEntityInterface)player).getActionPack().stopAll();
            return 1;
        }
    }

    public static int manipulate(CommandContext<ServerCommandSource> context, Consumer<EntityPlayerActionPack> action) {
        if (cantManipulate(context)) {
            return 0;
        } else {
            ServerPlayerEntity player = getPlayer(context);
            action.accept(((ServerPlayerEntityInterface)player).getActionPack());
            return 1;
        }
    }

    public static Command<ServerCommandSource> manipulation(Consumer<EntityPlayerActionPack> action) {
        return (c) -> {
            return manipulate(c, action);
        };
    }

    public static int action(CommandContext<ServerCommandSource> context, EntityPlayerActionPack.ActionType type, EntityPlayerActionPack.Action action) {
        return manipulate(context, (ap) -> {
            ap.start(type, action);
        });
    }

    public static int shadow(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = getPlayer(context);
        if (player instanceof EntityPlayerMPFake) {
            Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Cannot shadow fake players"});
            return 0;
        } else {
            ServerPlayerEntity sendingPlayer = null;

            try {
                sendingPlayer = ((ServerCommandSource)context.getSource()).getPlayer();
            } catch (CommandSyntaxException var4) {
            }

            if (sendingPlayer != player && cantManipulate(context)) {
                return 0;
            } else {
                EntityPlayerMPFake.createShadow(player.server, player);
                return 1;
            }
        }
    }

    @FunctionalInterface
    interface SupplierWithCommandSyntaxException<T> {
        T get() throws CommandSyntaxException;
    }
}
