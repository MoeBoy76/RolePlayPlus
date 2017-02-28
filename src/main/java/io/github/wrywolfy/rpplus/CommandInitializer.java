package io.github.wrywolfy.rpplus;

import io.github.wrywolfy.rpplus.commands.*;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

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


        //end networks commands
        //------------------------------------------------------------------
        CommandSpec rpp = CommandSpec.builder()
                .permission("rpp")
                .description(Text.of("RolePlay+ commands:"))
                .child(reload,"reload")
                .child(pause,"pause", "stop")
                .child(resume,"resume", "start")
                .child(time,"time")
                .build();
        return rpp;
    }
}
