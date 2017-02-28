package io.github.wrywolfy.rpplus;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.concurrent.TimeUnit;

public class UtilityMethods
{
    public static Text rppLog(String ads)
    {
        return Text.of(TextColors.DARK_AQUA, TextStyles.ITALIC, "RolePlay+ : " + ads);
    }
    public static String rppLogger(String ads)
    {
        return ("RolePlay+ : " + ads);
    }
    public static void StartCalendar(RolePlayPlus plugin)
    {
        Sponge.getServer().getDefaultWorld().get().setGameRule("doDaylightCycle", "false");
        if (plugin.getCalendar().getDaySeconds() <= 600 || plugin.getCalendar().getNightSeconds() <= 600)
        {
            plugin.getLogger().warn(rppLogger("Day and/or Night cycle is too short ..."));
            plugin.getCalendar().setStatus(false);
        }
        else if ((plugin.getCalendar().getDaySeconds() + plugin.getCalendar().getNightSeconds()) > 1200)
        {
            plugin.getCalendarTask().equals(Task.builder().execute(plugin.getCalendar().getCycle())
                    .interval(calculateInterval(plugin, Sponge.getServer().getDefaultWorld().get().getWorldTime()), TimeUnit.MILLISECONDS)
                    .name("Calendar Task").submit(plugin));
            plugin.getCalendar().setStatus(true);
        }
        else
        {
            plugin.getLogger().error(rppLogger("Something went seriously wrong ..."));
            plugin.getCalendar().setStatus(false);
        }
    }
    public static void StopCalendar(RolePlayPlus plugin)
    {
        plugin.getCalendarTask().cancel();
        plugin.getCalendar().setStatus(false);
    }
    public static long calculateInterval(RolePlayPlus plugin, long time)
    {
        if (time < 12000)
        {
            return ((plugin.getCalendar().getDaySeconds() * 1000) / 600000);
        }
        else
        {
            return ((plugin.getCalendar().getNightSeconds() * 1000) / 600000);
        }
    }
    public static Text getTimeOutput(RolePlayPlus plugin)
    {
        return TextSerializers.LEGACY_FORMATTING_CODE.deserialize(plugin.getCalendar().formatOutput());
    }
}
