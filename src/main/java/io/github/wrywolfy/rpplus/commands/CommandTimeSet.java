package io.github.wrywolfy.rpplus.commands;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import static io.github.wrywolfy.rpplus.UtilityMethods.getTimeOutput;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLog;
import static java.lang.Long.parseLong;

public class CommandTimeSet implements CommandExecutor
{
    private RolePlayPlus plugin;
    private long time;
    public CommandTimeSet(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        try
        {
            time = parseLong(args.<String>getOne("time").get());
        }
        catch(NumberFormatException x)
        {
            src.sendMessage(rppLog("Invalid input ..."));
            return CommandResult.empty();
        }
        if (src instanceof Player)
        {
            Player player = (Player) src;
            plugin.getCalendar().setTime(time);
            player.sendMessage(rppLog("Time set to ..."));
            player.sendMessage(getTimeOutput(plugin));
            return CommandResult.success();
        }
        else if (src instanceof ConsoleSource)
        {
            plugin.getCalendar().setTime(time);
            src.sendMessage(rppLog("Time set to ..."));
            src.sendMessage(getTimeOutput(plugin));
            return CommandResult.success();
        }
        return CommandResult.empty();
    }
}
