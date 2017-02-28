package io.github.wrywolfy.rpplus.calendarModule;

import io.github.wrywolfy.rpplus.RolePlayPlus;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.github.wrywolfy.rpplus.UtilityMethods.calculateInterval;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class Calendar
{
    private boolean calendarOn = true;
    private WorldProperties world;
    private CommentedConfigurationNode config;
    private RolePlayPlus plugin;
    private CycleTask cycle;
    private long daySeconds, nightSeconds, currentTime;
    private int dayCount, monthCount, currentDay, currentMonth, currentYear;
    private String outputStr;
    String[] days;
    Month[] months;
    public Calendar(CommentedConfigurationNode config, RolePlayPlus plugin)
    {
        this.config = config;
        this.plugin = plugin;
        world = Sponge.getServer().getDefaultWorld().get();
        setCalendar();
    }
    public void setCalendar()
    {
        daySeconds = config.getNode("cycles", "daySeconds").getLong();
        nightSeconds = config.getNode("cycles", "nightSeconds").getLong();
        currentDay = config.getNode("cycles", "currentDay").getInt();
        currentMonth = config.getNode("cycles", "currentMonth").getInt();
        currentYear = config.getNode("months", "currentYear").getInt();
        outputStr = config.getNode("output").getString();
        dayCount = config.getNode("week", "dayCount").getInt();
        days = new String[dayCount];
        for (int i = 1; i <= monthCount; i++)
        {
            days[i - 1] = config.getNode("week", ("day" + i)).getString();
        }
        monthCount = config.getNode("months", "monthCount").getInt();
        months = new Month[monthCount];
        for (int i = 1; i <= monthCount; i++)
        {
            months[i - 1].setName(config.getNode("months", ("month" + i), "name").getString());
            months[i - 1].setDays(config.getNode("months", ("month" + i), "days").getInt());
        }
    }
    public boolean getStatus()
    {
        return calendarOn;
    }
    public void setStatus(boolean status)
    {
        calendarOn = status;
    }
    public void setTime(long timeInTicks)
    {
        currentTime = timeInTicks;
    }
    public long getDaySeconds()
    {
        return daySeconds;
    }
    public long getNightSeconds()
    {
        return nightSeconds;
    }
    private String getDay()
    {
        int temp = 0;
        for (int i = 0; i < monthCount; i++)
        {
            temp += months[i].getDays();
        }
        temp *= currentYear;
        for (int i = 0; i < currentMonth - 1; i++)
        {
            temp += months[i].getDays();
        }
        temp += currentDay;
        temp %= dayCount;
        return days[temp];
    }
    public String formatOutput()
    {
        boolean isArgument = false;
        String tempStr = "", argument = "";
        for (int i = 0; i < outputStr.length(); i++)
        {
            if (outputStr.charAt(i) == '{' && isArgument == false)
            {
                isArgument = true;
            }
            else if (outputStr.charAt(i) == '}' && isArgument == true)
            {
                switch (argument)
                {
                    case "D":
                        tempStr += currentDay;
                        break;
                    case "DD":
                        tempStr += String.format("%02d", currentDay);
                        break;
                    case "DAY":
                        tempStr += getDay();
                        break;
                    case "M":
                        tempStr += currentMonth;
                        break;
                    case "MM":
                        tempStr += String.format("%02d", currentMonth);
                        break;
                    case "MONTH":
                        tempStr += months[currentMonth - 1].getName();
                        break;
                    case "YY":
                        tempStr += (currentYear % 100);
                        break;
                    case "YYYY":
                        tempStr += currentYear;
                        break;
                    default:
                        plugin.getLogger().warn(rppLogger("Error reading Calendar output format ..."));
                        break;
                }
                argument = "";
                isArgument = false;
            }
            else if(isArgument)
            {
                argument += outputStr.charAt(i);
            }
            else
            {
                tempStr += outputStr.charAt(i);
            }
        }
        return tempStr;
    }
    private class CycleTask implements Consumer<Task>
    {
        //task
        @Override
        public void accept(Task task)
        {
            if (world.getWorldTime() == 12000)
            {
                plugin.getCalendarTask().equals(Task.builder().execute(plugin.getCalendar().getCycle())
                        .interval(calculateInterval(plugin, world.getWorldTime()), TimeUnit.MILLISECONDS)
                        .name("Calendar Task").submit(plugin));
                world.setWorldTime(currentTime++);
            }
            else if (world.getWorldTime() == 23999)
            {
                if (currentDay == months[currentMonth - 1].getDays())
                {
                    if (currentMonth == monthCount)
                    {
                        currentYear++;
                        currentMonth = 1;
                    }
                    else
                    {
                        currentMonth++;
                    }
                    currentDay = 1;
                }
                else
                {
                    currentDay++;
                }
                world.setWorldTime(currentTime++);
                plugin.getCalendarTask().equals(Task.builder().execute(plugin.getCalendar().getCycle())
                        .interval(calculateInterval(plugin, world.getWorldTime()), TimeUnit.MILLISECONDS)
                        .name("Calendar Task").submit(plugin));
            }
            else
            {
                world.setWorldTime(currentTime++);
            }
        }
    }
    public CycleTask getCycle()
    {
        return cycle;
    }
}
