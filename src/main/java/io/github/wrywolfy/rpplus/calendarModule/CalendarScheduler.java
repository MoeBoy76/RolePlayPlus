package io.github.wrywolfy.rpplus.calendarModule;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;

import static io.github.wrywolfy.rpplus.UtilityMethods.calculateInterval;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class CalendarScheduler
{
    private Task.Builder taskBuilder = Task.builder();
    private Task calendarTask;
    private RolePlayPlus plugin;
    public CalendarScheduler(RolePlayPlus plugin)
    {
        this.plugin = plugin;
    }

    public void StartCalendar()
    {
        Sponge.getServer().getDefaultWorld().get().setGameRule("doDaylightCycle", "false");
        if (plugin.getCalendar().getDaySeconds() <= 600 || plugin.getCalendar().getNightSeconds() <= 600)
        {
            plugin.getLogger().warn(rppLogger("Day and/or Night cycle is too short ..."));
            plugin.getCalendar().setStatus(false);
        }
        else if ((plugin.getCalendar().getDaySeconds() + plugin.getCalendar().getNightSeconds()) > 1200)
        {
            calendarTask = Task.builder().execute(plugin.getCalendar().getCycle())
                    .interval(calculateInterval(plugin, Sponge.getServer().getDefaultWorld().get().getWorldTime()), TimeUnit.MILLISECONDS)
                    .name("Calendar Task").submit(plugin);
            plugin.getCalendar().setStatus(true);
        }
        else
        {
            plugin.getLogger().error(rppLogger("Something went seriously wrong ..."));
            plugin.getCalendar().setStatus(false);
        }
    }
    public void StopCalendar()
    {
        calendarTask.cancel();
        plugin.getCalendar().setStatus(false);
    }
}
