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
    private long daySeconds, nightSeconds, currentTime;
    private int dayCount, monthCount, currentDay, currentMonth, currentYear;
    private String outputStr;
    private Task.Builder calendarTask = Task.builder();
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
        currentYear = config.getNode("cycles", "currentYear").getInt();
        outputStr = config.getNode("output").getString();
        dayCount = config.getNode("week", "dayCount").getInt();
        days = new String[dayCount];
        for (int i = 1; i <= dayCount; i++)
        {
            days[i - 1] = config.getNode("week", ("day" + i)).getString();
        }
        monthCount = config.getNode("months", "monthCount").getInt();
        months = new Month[monthCount];
        for (int i = 1; i <= monthCount; i++)
        {
            months[i - 1] = new Month(config.getNode("months", ("month" + i), "name").getString(), config.getNode("months", ("month" + i), "days").getInt());
        }
    }
    public void saveCalendar()
    {
        config.getNode("cycles", "currentDay").setValue(currentDay);
        config.getNode("cycles", "currentMonth").setValue(currentMonth);
        config.getNode("cycles", "currentYear").setValue(currentYear);
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
            if (outputStr.charAt(i) == '{' && !isArgument)
            {
                isArgument = true;
            }
            else if (outputStr.charAt(i) == '}' && isArgument)
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
                    {
                        if ((currentYear % 100) < 10)
                        {
                            tempStr += currentYear;
                        } else
                        {
                            tempStr += (currentYear % 100);
                        }
                        break;
                    }
                    case "YYYY":
                        tempStr += currentYear;
                        break;
                    case "H":
                    {
                        int hour = getHour();
                        if (hour > 12)
                        {
                            tempStr += (hour - 12);
                        }
                        else
                        {
                            tempStr += hour;
                        }
                        break;
                    }
                    case "HH":
                    {
                        int hour = getHour();
                        if (hour > 12)
                        {
                            hour -= 12;
                        }
                        if (hour < 10)
                        {
                            tempStr += 0;
                        }
                        tempStr += hour;
                        break;
                    }
                    case "H+":
                        tempStr += getHour();
                        break;
                    case "HH+":
                    {
                        int hour = getHour();
                        if (hour < 10)
                        {
                            tempStr += 0;
                        }
                        tempStr += hour;
                        break;
                    }
                    case "MIN":
                    {
                        tempStr += getMinuteStr();
                        break;
                    }
                    case "ampm":
                    {
                        if (isDay())
                        {
                            tempStr += "am";
                        }
                        else
                        {
                            tempStr += "pm";
                        }
                        break;
                    }
                    case "AMPM":
                    {
                        if (isDay())
                        {
                            tempStr += "AM";
                        }
                        else
                        {
                            tempStr += "PM";
                        }
                        break;
                    }
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
        plugin.getLogger().info(rppLogger(tempStr));
        return tempStr;
    }
    private int getHour()
    {
        int hour = (int)(currentTime / 1000 + 6);
        if (hour == 24)
        {
            return hour;
        }
        else
        {
            return hour  % 24;
        }
    }
    private String getMinuteStr()
    {
        int minute = (int) ((currentTime % 1000) / (100. / 6.));
        String str = "";
        if (minute < 10)
        {
            str += 0;
        }
        str += minute;
        return str;
    }
    private boolean isDay()
    {
        if (currentTime < 12000)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private class cycleTask implements Consumer<Task>
    {
        public void accept(Task task)
        {
            if (!calendarOn)
            {
                task.cancel();
            }
            else if (currentTime == 12000)
            {
                world.setWorldTime(++currentTime);
                startCalendar();
                task.cancel();
            }
            else if (currentTime >= 23999)
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
                currentTime = 0;
                world.setWorldTime(currentTime);
                startCalendar();
                task.cancel();
            }
            else
            {
                world.setWorldTime(++currentTime);
            }
        }
    }
    public void startNewCalendar()
    {
        Sponge.getServer().getDefaultWorld().get().setGameRule("doDaylightCycle", "false");
        if (plugin.getCalendar().getDaySeconds() <= 600 || plugin.getCalendar().getNightSeconds() <= 600)
        {
            plugin.getLogger().warn(rppLogger("Day and/or Night cycle is too short ..."));
            plugin.getCalendar().setStatus(false);
        }
        else if ((plugin.getCalendar().getDaySeconds() + plugin.getCalendar().getNightSeconds()) > 1200)
        {
            calendarTask.execute(new cycleTask())
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
    public void startCalendar()
    {
        calendarTask.execute(new cycleTask())
                .interval(calculateInterval(plugin, Sponge.getServer().getDefaultWorld().get().getWorldTime()), TimeUnit.MILLISECONDS)
                .name("Calendar Task").submit(plugin);
        plugin.getCalendar().setStatus(true);
    }
    public void stopCalendar()
    {
        calendarOn = false;
        plugin.getCalendar().setStatus(false);
    }
}
