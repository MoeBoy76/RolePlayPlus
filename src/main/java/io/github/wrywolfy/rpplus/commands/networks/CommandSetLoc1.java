package io.github.wrywolfy.rpplus.commands.networks;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;

import static io.github.wrywolfy.rpplus.UtilityMethods.getTimeOutput;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLog;

public class CommandSetLoc1 implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandSetLoc1(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            plugin.getNetworks().getInputNodeLoc().setHeading(player.getRotation());
            plugin.getNetworks().setInputNodeLocRegion();
            if (plugin.getNetworks().getInputNodeLoc().isValid())
            {
                plugin.getNetworks().getInputNode().setLoc1(plugin.getNetworks().getInputNodeLoc());
                player.sendMessage(rppLog("Location 1 heading = " + plugin.getNetworks().getInputNodeLoc().getHeadingValue() + " ..."));
                player.sendMessage(rppLog("Node location 1 set ..."));
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(rppLog("Incomplete location info ..."));
                return CommandResult.empty();
            }
        }
        return CommandResult.empty();
    }
}
