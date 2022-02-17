package com.github.Primeppzi.command;

import carpet.settings.SettingsManager;
import com.github.Primeppzi.DRCAdditionSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class WeatherCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)    {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("tellweather").requires((source) -> source.hasPermissionLevel(2)).
                requires((player) -> SettingsManager.canUseCommand(player, DRCAdditionSettings.tellweather)).
                executes( c ->
                {
                    ServerPlayerEntity playerEntity = c.getSource().getPlayer();
                    String weather = weatheridentify(c.getSource());
                    while(DRCAdditionSettings.tellweather == true){
                        playerEntity.sendSystemMessage(new LiteralText("§7Weather is: "+weather),Util.NIL_UUID);
                        return 1;
                    }
                    return 1;
                });

        dispatcher.register(command);
    }
    public static String weatheridentify(ServerCommandSource source){
        if(source.getWorld().isThundering()){
            return  "§8Thunder";
        }
        if (source.getWorld().isRaining()&& !source.getWorld().isThundering()){
            return  "§7Rain";
        }
        else{
            return "§fClear";
        }
    }

}
