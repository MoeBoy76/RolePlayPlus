package io.github.wrywolfy.rpplus;

import io.github.wrywolfy.rpplus.commands.*;
import io.github.wrywolfy.rpplus.commands.calendar.CommandPause;
import io.github.wrywolfy.rpplus.commands.calendar.CommandResume;
import io.github.wrywolfy.rpplus.commands.calendar.CommandTime;
import io.github.wrywolfy.rpplus.commands.calendar.CommandTimeSet;
import io.github.wrywolfy.rpplus.commands.networks.*;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandInitializer
{
    private RolePlayPlus plugin;
    public CommandInitializer(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }
    public CommandSpec InitializeCommands()
    {
        //start core commands
        CommandSpec reload = CommandSpec.builder()
                .description(Text.of("Reloads the configs."))
                .permission("rpp.reload")
                .executor(new CommandReload(plugin))
                .build();
        //end core commands
        //------------------------------------------------------------------
        //start calendar commands
        CommandSpec pause = CommandSpec.builder()
                .description(Text.of("Pauses the Calendar."))
                .permission("rpp.pause")
                .executor(new CommandPause(plugin))
                .build();
        CommandSpec resume = CommandSpec.builder()
                .description(Text.of("Resumes the Calendar."))
                .permission("rpp.resume")
                .executor(new CommandResume(plugin))
                .build();
        CommandSpec timeSet = CommandSpec.builder()
                .description(Text.of("Sets the current time."))
                .permission("rpp.time.set")
                .arguments(GenericArguments.onlyOne(GenericArguments.longNum(Text.of("time"))))
                .executor(new CommandTimeSet(plugin))
                .build();
        CommandSpec time = CommandSpec.builder()
                .description(Text.of("Displays the current time."))
                .permission("rpp.time.base")
                .child(timeSet, "set")
                .executor(new CommandTime(plugin))
                .build();
        //end calendar commands
        //------------------------------------------------------------------
        //start networks commands
        CommandSpec pos1 = CommandSpec.builder()
                .permission("rpp.set.node")
                .description(Text.of("Sets node position 1"))
                .executor(new CommandSetPos1(plugin))
                .build();
        CommandSpec pos2 = CommandSpec.builder()
                .permission("rpp.set.node")
                .description(Text.of("Sets node position 1"))
                .executor(new CommandSetPos2(plugin))
                .build();
        CommandSpec loc1 = CommandSpec.builder()
                .permission("rpp.set.node")
                .description(Text.of("Sets node position 1"))
                .executor(new CommandSetLoc1(plugin))
                .build();
        CommandSpec loc2 = CommandSpec.builder()
                .permission("rpp.set.node")
                .description(Text.of("Sets node position 1"))
                .executor(new CommandSetLoc2(plugin))
                .build();
        CommandSpec setNode = CommandSpec.builder()
                .permission("rpp.set.node")
                .description(Text.of("Sets node from positions"))
                .executor(new CommandSetNode(plugin))
                .build();







        CommandSpec set = CommandSpec.builder()
                .permission("rpp.set")
                .description(Text.of("Set certain RolePlay+ values:"))
                .child(pos1, "pos1", "position1")
                .child(pos2, "pos2", "position2")
                .child(loc1, "loc1", "location1")
                .child(loc2, "loc2", "location2")
                .child(setNode, "node", "route", "network")
                .build();
        //end networks commands
        //------------------------------------------------------------------
        CommandSpec rpp = CommandSpec.builder()
                .permission("rpp")
                .description(Text.of("RolePlay+ commands:"))
                .child(reload,"reload")
                .child(pause,"pause", "stop")
                .child(resume,"resume", "start")
                .child(time,"time")
                .child(set, "set")
                .build();
        return rpp;
    }
}
