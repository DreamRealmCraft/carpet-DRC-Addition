package com.github.Primeppzi.command;

import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import carpet.utils.MobAI;
import carpet.utils.SpawnReporter;
import com.github.Primeppzi.DRCAdditionSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.command.CommandSource.suggestMatching;

public class HighlightCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        LiteralArgumentBuilder<ServerCommandSource> command = literal("highlight")
                .requires((player)-> SettingsManager.canUseCommand(player, DRCAdditionSettings.highlightEntity));

        command.then(argument("list",word())
                .suggests( (c, b) -> suggestMatching(MobAI.availableFor(Registry.ENTITY_TYPE.get(EntitySummonArgumentType.getEntitySummon(c, "entity type"))),b))
                .executes(HighlightCommand::highlightentity));

        dispatcher.register(command);
    }


    private static int highlightentity(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Messenger.m((ServerCommandSource) context.getSource(),"w successful");
        return 1;
    }


}
