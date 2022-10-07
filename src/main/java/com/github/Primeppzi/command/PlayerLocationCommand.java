package com.github.Primeppzi.command;

import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.github.Primeppzi.DRCAdditionSettings;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import javax.xml.stream.Location;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static net.minecraft.command.CommandSource.suggestMatching;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PlayerLocationCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        LiteralArgumentBuilder<ServerCommandSource> command = literal("locateplayer").
                requires((source) -> source.hasPermissionLevel(2)).requires((player)-> SettingsManager.
                        canUseCommand(player, DRCAdditionSettings.locateplayer)).
                then(argument("player", StringArgumentType.word())
                .suggests( (c, b) -> suggestMatching(getPlayers(c.getSource()), b))
                        .executes(PlayerLocationCommand::location));

        dispatcher.register(command);
    }
    private static Collection<String> getPlayers(ServerCommandSource source)
    {
        Set<String> players = Sets.newLinkedHashSet(Arrays.asList());
        players.addAll(source.getPlayerNames());
        return players;
    }

    private static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context)
    {
        String playerName = StringArgumentType.getString(context, "player");
        MinecraftServer server = context.getSource().getServer();
        return server.getPlayerManager().getPlayer(playerName);
    }

    private static int location(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity player =getPlayer(context);
        String coordinate = "§3[x: "+player.getBlockPos().getX()+"  y: "+player.getBlockPos().getY()+"  Z: "+player.getBlockPos().getZ()+"§f]";
        RegistryKey<World> registryKey = player.getWorld().getRegistryKey();
        String dimension;
        Messenger.m(context.getSource(),"g ====================");
        if(registryKey == World.OVERWORLD){
            dimension = "Overworld";
            Messenger.m(context.getSource(),"w "+getPlayer(context).getName().toString()+" is at\n"+coordinate);
            Messenger.m(context.getSource(),"w Dimension: §b"+dimension);
            Messenger.m(context.getSource(),"w §a Nether coordinate:\n"+"§3[x: "+player.getBlockPos().getX()/8+"  y: "+player.getBlockPos().getY()+"  Z: "+player.getBlockPos().getZ()/8+"§f]");
        }
        if (registryKey == World.NETHER){
            dimension = "Nether";
            Messenger.m(context.getSource(),"w "+getPlayer(context).getName().toString()+" is at\n"+coordinate);
            Messenger.m(context.getSource(),"w Dimension: §a"+dimension);
            Messenger.m(context.getSource(),"w §b Overworld coordinate:\n"+"§3[x: "+player.getBlockPos().getX()*8+"  y: "+player.getBlockPos().getY()+"  Z: "+player.getBlockPos().getZ()*8+"§f]");
        }
        if(registryKey == World.END){
            dimension = "End";
            Messenger.m(context.getSource(),"w "+getPlayer(context).getName().toString()+" is at\n"+coordinate);
            Messenger.m(context.getSource(),"w Dimension: §5"+dimension);
        }
        return 0;
    }
}
