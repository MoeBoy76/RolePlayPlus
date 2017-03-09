package io.github.wrywolfy.rpplus.commands.networks;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLog;

public class CommandSetNode implements CommandExecutor
{
    private RolePlayPlus plugin;
    public CommandSetNode(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if (src instanceof Player)
        {
            Player player = (Player) src;
            if (plugin.getNetworks().getInputNode().isValid())
            {
                player.sendMessage(rppLog("Location 1 heading = " + plugin.getNetworks().getInputNode().getLoc(0).getHeadingValue() + " ..."));
                player.sendMessage(rppLog("Location 2 heading = " + plugin.getNetworks().getInputNode().getLoc(1).getHeadingValue() + " ..."));

                plugin.getNetworks().pushNetworkList();
                player.sendMessage(rppLog("Node successfully set ..."));
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(rppLog("Error setting node ..."));
                return CommandResult.empty();
            }
        }
        return CommandResult.empty();
    }
}
