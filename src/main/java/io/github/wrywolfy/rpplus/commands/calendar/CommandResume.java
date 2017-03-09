package io.github.wrywolfy.rpplus.commands.calendar;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLog;

public class CommandResume implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandResume(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            player.sendMessage(rppLog("Resuming the Calendar ..."));
            if (!plugin.getCalendar().getStatus())
            {
                plugin.getCalendar().startNewCalendar();
                player.sendMessage(rppLog("Calendar resumed ..."));
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(rppLog("Calendar is already active ..."));
                return CommandResult.empty();
            }
        }
        else if (src instanceof ConsoleSource)
        {
            src.sendMessage(rppLog("Resuming the Calendar ..."));
            if (!plugin.getCalendar().getStatus())
            {
                plugin.getCalendar().startNewCalendar();
                src.sendMessage(rppLog("Calendar resumed ..."));
                return CommandResult.success();
            }
            else
            {
                src.sendMessage(rppLog("Calendar is already active ..."));
                return CommandResult.empty();
            }
        }
        return CommandResult.empty();
    }
}