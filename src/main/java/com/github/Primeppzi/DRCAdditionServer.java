package com.github.Primeppzi;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.github.Primeppzi.command.HighlightCommand;
import com.github.Primeppzi.command.PlayerLocationCommand;
import com.github.Primeppzi.command.WeatherCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

import java.awt.event.HierarchyListener;

public class DRCAdditionServer implements CarpetExtension, ModInitializer
{
    @Override
    public String version()
    {
        return "carpet-extra";
    }

    public static void loadExtension()
    {
        CarpetServer.manageExtension(new DRCAdditionServer());
    }

    @Override
    public void onInitialize()
    {
        DRCAdditionServer.loadExtension();
    }

    @Override
    public void onGameStarted()
    {
        // let's /carpet handle our few simple settings
        CarpetServer.settingsManager.parseSettingsClass(DRCAdditionSettings.class);
    }
    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        // here goes extra stuff
        WeatherCommand.register(dispatcher);
        PlayerLocationCommand.register(dispatcher);
        HighlightCommand.register(dispatcher);
    }
}
