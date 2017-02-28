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
