package io.github.wrywolfy.rpplus;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import io.github.wrywolfy.rpplus.calendarModule.Calendar;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

@Plugin(
        id = "roleplayplus",
        name = "RolePlayPlus",
        description = "A plugin to help facilitate roleplay server environments.",
        authors = { "WryWolfy" }
)
public class RolePlayPlus
{
    @Inject
    private Logger logger = null;
    public Logger getLogger()
    {
        return logger;
    }
    private Calendar calendar;
    private CommandInitializer commands;
    private ConfigManager configs;

    @Listener
    public void preInitialization(GamePreInitializationEvent e)
    {

    }
    @Listener
    public void onInitialization(GameInitializationEvent e)
    {

    }
    @Listener
    public void postInitialization(GamePostInitializationEvent e)
    {

    }
    @Listener
    public void onServerStart(GameStartedServerEvent e)
    {
        logger.info(rppLogger("Plugin is starting ..."));
        configs = new ConfigManager(this);
        calendar = new Calendar(configs.getCalendarConfig(), this);
        commands = new CommandInitializer(this);
        CommandManager cmdManager = Sponge.getCommandManager();
        cmdManager.register(this, commands.InitializeCommands(), Lists.newArrayList("roleplayplus", "rpp", "roleplay+", "rp+"));
        calendar.startNewCalendar();
        logger.info(rppLogger("Plugin has started ..."));
    }
    @Listener
    public void onServerStop(GameStoppedServerEvent e)
    {
        logger.info(rppLogger("Saving to configs ..."));
        configs.saveCalendarConfig();
        //configs.saveNetworksConfig();
        logger.info(rppLogger("Plugin has stopped ..."));
    }
    //utility methods
    public Calendar getCalendar()
    {
        return calendar;
    }
}
