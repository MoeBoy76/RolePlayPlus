package io.github.wrywolfy.rpplus;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import io.github.wrywolfy.rpplus.calendarModule.Calendar;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;

import static io.github.wrywolfy.rpplus.UtilityMethods.getTimeOutput;
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
    private ConfigManager configs;

    @Listener
    public void onServerStart(GameStartedServerEvent e)
    {
        logger.info(rppLogger("Plugin is starting ..."));
        configs = new ConfigManager(this);
        calendar = new Calendar(configs.getCalendarConfig(), this);
        CommandInitializer commands = new CommandInitializer(this);
        CommandManager cmdManager = Sponge.getCommandManager();
        cmdManager.register(this, commands.InitializeCommands(), Lists.newArrayList("roleplayplus", "rpp", "roleplay+", "rp+"));
        calendar.startNewCalendar();
        logger.info(rppLogger("Plugin has started ..."));
    }
    @Listener
    public void onServerStop(GameStoppedServerEvent e)
    {
        logger.info(rppLogger("Plugin is stopping ..."));
        calendar.stopCalendar();
        logger.info(rppLogger("Saving to configs ..."));
        configs.saveCalendarConfig();
        //configs.saveNetworksConfig();
        logger.info(rppLogger("Plugin has stopped ..."));
    }
    @Listener
    public void rightClickCompass(InteractBlockEvent.Secondary.MainHand event, @Root Player player)
    {
        if (player.getItemInHand(HandTypes.MAIN_HAND).get().getItem() == ItemTypes.CLOCK)
        {
            player.sendMessage(getTimeOutput(this));
        }
    }
    //utility methods
    public Calendar getCalendar()
    {
        return calendar;
    }
    public ConfigManager getCalendarConfig()
    {
        return configs;
    }
}
