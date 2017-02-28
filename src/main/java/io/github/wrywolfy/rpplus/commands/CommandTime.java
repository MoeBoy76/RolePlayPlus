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

public class CommandTime implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandTime(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            player.sendMessage(getTimeOutput(plugin));
            return CommandResult.success();
        }
        else if (src instanceof ConsoleSource)
        {
            src.sendMessage(getTimeOutput(plugin));
            return CommandResult.success();
        }
        return CommandResult.empty();
    }
}