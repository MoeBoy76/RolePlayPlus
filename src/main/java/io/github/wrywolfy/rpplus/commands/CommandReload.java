package io.github.wrywolfy.rpplus.commands;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import static io.github.wrywolfy.rpplus.UtilityMethods.*;

public class CommandReload implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandReload(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            player.sendMessage(rppLog("Reloading configs ..."));
            if (plugin.getCalendar().getStatus())
            {
                player.sendMessage(rppLog("Pausing the Calendar ..."));
                StopCalendar(plugin);
                player.sendMessage(rppLog("Reloading calendar.conf ..."));
                plugin.getCalendar().setCalendar();
                player.sendMessage(rppLog("Resuming the Calendar ..."));
                StartCalendar(plugin);
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(rppLog("Reloading calendar.conf ..."));
                plugin.getCalendar().setCalendar();
                return CommandResult.success();
            }
        }
        else if (src instanceof ConsoleSource)
        {
            src.sendMessage(rppLog("Reloading configs ..."));
            if (plugin.getCalendar().getStatus())
            {
                src.sendMessage(rppLog("Pausing the Calendar ..."));
                StopCalendar(plugin);
                src.sendMessage(rppLog("Reloading calendar.conf ..."));
                plugin.getCalendar().setCalendar();
                src.sendMessage(rppLog("Resuming the Calendar ..."));
                StartCalendar(plugin);
                return CommandResult.success();
            }
            else
            {
                src.sendMessage(rppLog("Reloading configs ..."));
                plugin.getCalendar().setCalendar();
                return CommandResult.success();
            }
        }
        return CommandResult.empty();
    }
}
