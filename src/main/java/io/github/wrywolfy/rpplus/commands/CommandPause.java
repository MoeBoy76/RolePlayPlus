package io.github.wrywolfy.rpplus.commands;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import static io.github.wrywolfy.rpplus.UtilityMethods.StopCalendar;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLog;

public class CommandPause implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandPause(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            player.sendMessage(rppLog("Pausing the Calendar ..."));
            if (plugin.getCalendar().getStatus())
            {
                plugin.getCalendarScheduler().StopCalendar();
                player.sendMessage(rppLog("Calendar paused ..."));
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(rppLog("Calendar is already paused ..."));
                return CommandResult.empty();
            }
        }
        else if (src instanceof ConsoleSource)
        {
            src.sendMessage(rppLog("Pausing the Calendar ..."));
            if (plugin.getCalendar().getStatus())
            {
                plugin.getCalendarScheduler().StopCalendar();
                src.sendMessage(rppLog("Calendar paused ..."));
                return CommandResult.success();
            }
            else
            {
                src.sendMessage(rppLog("Calendar is already paused ..."));
                return CommandResult.empty();
            }
        }
        return CommandResult.empty();
    }
}