package io.github.wrywolfy.rpplus.commands.networks;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLog;

public class CommandSetLoc2 implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandSetLoc2(RolePlayPlus plugin)
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
                plugin.getNetworks().getInputNode().setLoc2(plugin.getNetworks().getInputNodeLoc());
                player.sendMessage(rppLog("Location 2 heading = " + plugin.getNetworks().getInputNodeLoc().getHeadingValue() + " ..."));
                player.sendMessage(rppLog("Node location 2 set ..."));
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