package com.github.Primeppzi.command;

import carpet.fakes.ServerPlayerEntityInterface;
import carpet.helpers.EntityPlayerActionPack;
import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.github.Primeppzi.DRCAdditionSettings;
import com.github.Primeppzi.helper.PlayerCommandHelper;
import com.google.common.collect.Sets;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;


import static com.github.Primeppzi.helper.PlayerCommandHelper.*;
import static net.minecraft.command.CommandSource.suggestMatching;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ExtraPlayerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        LiteralArgumentBuilder<ServerCommandSource> command = literal("playerset").
                requires((source) -> source.hasPermissionLevel(2)).requires((player)-> SettingsManager.
                        canUseCommand(player, DRCAdditionSettings.multiplePlayerSpawning)).
                then(argument("prefix",StringArgumentType.word())
                        .suggests( (c, b) -> suggestMatching(getPlayers(c.getSource()), b))
                        .then(literal("spawn")
                .then((argument("number", IntegerArgumentType.integer()).suggests((c, b)->
                {return CommandSource.suggestMatching(new String[]{"1","3","6","10"},b);
                }).executes(ExtraPlayerCommand::spawn))))
                        .then(literal("kill").executes(ExtraPlayerCommand::kill))
                        .then(literal("unload").executes(ExtraPlayerCommand::unload)));

        dispatcher.register(command);
    }

    private static int unload(CommandContext<ServerCommandSource> context) {
        for (int i = 1;true;i++) {
            String playerName = StringArgumentType.getString(context,"prefix")+i;
            MinecraftServer server = ((ServerCommandSource)context.getSource()).getServer();
            if(server.getPlayerManager().getPlayer(playerName) == null){
                return 1;
            }
            EntityPlayerActionPack ap = ((ServerPlayerEntityInterface)server.getPlayerManager().getPlayer(playerName)).getActionPack();
            ap.drop(-2,true);

        }
        /*String playerName = StringArgumentType.getString(context,"prefix");
        MinecraftServer server = ((ServerCommandSource)context.getSource()).getServer();
        EntityPlayerActionPack ap = ((ServerPlayerEntityInterface)server.getPlayerManager().getPlayer(playerName)).getActionPack();
        ap.drop(-2,true);
        Messenger.m(context.getSource(),"w "+ap.toString());
        return 1;*/
    }



    private static int kill(CommandContext<ServerCommandSource> context) {
        for (int i = 1;true;i++) {
            String playerName = StringArgumentType.getString(context,"prefix")+i;
            MinecraftServer server = ((ServerCommandSource)context.getSource()).getServer();
            if(server.getPlayerManager().getPlayer(playerName) == null){
                return 1;
            }
            server.getPlayerManager().getPlayer(playerName).kill();

        }
    }


    private static int spawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        int playernumber = spawnnumber(context);
        PlayerCommandHelper.spawn(context,playernumber);

        return 1;
    }
    private static int spawnnumber(CommandContext<ServerCommandSource> context){
        int integer = IntegerArgumentType.getInteger(context,"number");
        return integer;
    }





}
