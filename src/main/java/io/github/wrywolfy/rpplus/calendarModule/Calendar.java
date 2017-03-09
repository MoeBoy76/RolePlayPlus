package io.github.wrywolfy.rpplus.calendarModule;

import com.google.common.collect.Lists;
import io.github.wrywolfy.rpplus.RolePlayPlus;
import io.github.wrywolfy.rpplus.networksModule.Networks;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.github.wrywolfy.rpplus.UtilityMethods.calculateInterval;
import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class Calendar
{
    private boolean calendarOn = true;
    private WorldProperties world;
    private RolePlayPlus plugin;
    private long daySeconds, nightSeconds, currentTime;
    private int currentDay, currentMonth, currentYear;
    private String outputStr;
    private Task.Builder calendarTask = Task.builder();
    List<String> days;
    List<Month> months;
    public Calendar(CommentedConfigurationNode config, RolePlayPlus plugin)
    {
        this.plugin = plugin;
        world = Sponge.getServer().getDefaultWorld().get();
        setCalendar(config);
    }
    public void setCalendar(CommentedConfigurationNode config)
    {
        daySeconds = config.getNode("cycles", "daySeconds").getLong();
        nightSeconds = config.getNode("cycles", "nightSeconds").getLong();
        currentDay = config.getNode("cycles", "currentDay").getInt();
        currentMonth = config.getNode("cycles", "currentMonth").getInt();
        currentYear = config.getNode("cycles", "currentYear").getInt();
        outputStr = config.getNode("output").getString();
        days = Lists.newArrayList();
        months = Lists.newArrayList();
        Map<Object, ? extends ConfigurationNode> entryMap = config.getNode("week").getChildrenMap();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : entryMap.entrySet())
        {
            days.add(config.getNode("week", entry.getKey()).getString());
        }
        entryMap = config.getNode("months").getChildrenMap();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : entryMap.entrySet())
        {
            months.add(new Month(config.getNode("months", entry.getKey(), "name").getString(), config.getNode("months", entry.getKey(), "days").getInt()));
        }
    }
    public void saveCalendar(CommentedConfigurationNode config)
    {
        config.getNode("cycles", "currentDay").setValue(currentDay);
        config.getNode("cycles", "currentMonth").setValue(currentMonth);
        config.getNode("cycles", "currentYear").setValue(currentYear);
    }
    public boolean getStatus()
    {
        return calendarOn;
    }
    private void setStatus(boolean status)
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
        int day = 0;
        for (int i = 0; i < months.size(); i++)
        {
            day += months.get(i).getDays();
        }
        day *= currentYear;
        for (int i = 0; i < currentMonth - 1; i++)
        {
            day += months.get(i).getDays();
        }
        day += currentDay;
        day %= days.size();
        return days.get(day);
    }
    public String formatOutput()
    {
        boolean isArgument = false;
        StringBuilder tempStr = new StringBuilder();
        StringBuilder argument = new StringBuilder();
        for (int i = 0; i < outputStr.length(); i++)
        {
            if (outputStr.charAt(i) == '{' && !isArgument)
            {
                isArgument = true;
            }
            else if (outputStr.charAt(i) == '}' && isArgument)
            {
                switch (argument.toString())
                {
                    case "D":
                        tempStr.append(currentDay);
                        break;
                    case "DD":
                        tempStr.append(String.format("%02d", currentDay));
                        break;
                    case "Day":
                        tempStr.append(getDay());
                        break;
                    case "DAY":
                        tempStr.append(getDay().toUpperCase());
                        break;
                    case "M":
                        tempStr.append(currentMonth);
                        break;
                    case "MM":
                        tempStr.append(String.format("%02d", currentMonth));
                        break;
                    case "Month":
                        tempStr.append(months.get(currentMonth - 1).getName());
                        break;
                    case "MONTH":
                        tempStr.append(months.get(currentMonth - 1).getName().toUpperCase());
                        break;
                    case "YY":
                    {
                        if ((currentYear % 100) < 10)
                        {
                            tempStr.append(currentYear);
                        } else
                        {
                            tempStr.append(currentYear % 100);
                        }
                        break;
                    }
                    case "YYYY":
                        tempStr.append(currentYear);
                        break;
                    case "H":
                    {
                        int hour = getHour();
                        if (hour > 12)
                        {
                            tempStr.append(hour - 12);
                        }
                        else
                        {
                            tempStr.append(hour);
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
                            tempStr.append(0);
                        }
                        tempStr.append(hour);
                        break;
                    }
                    case "H+":
                        tempStr.append(getHour());
                        break;
                    case "HH+":
                    {
                        int hour = getHour();
                        if (hour < 10)
                        {
                            tempStr.append(0);
                        }
                        tempStr.append(hour);
                        break;
                    }
                    case "MIN":
                    {
                        tempStr.append(getMinuteStr());
                        break;
                    }
                    case "ampm":
                    {
                        if (isDay())
                        {
                            tempStr.append("am");
                        }
                        else
                        {
                            tempStr.append("pm");
                        }
                        break;
                    }
                    case "AMPM":
                    {
                        if (isDay())
                        {
                            tempStr.append("AM");
                        }
                        else
                        {
                            tempStr.append("PM");
                        }
                        break;
                    }
                    default:
                        plugin.getLogger().warn(rppLogger("Error reading Calendar output format ..."));
                        break;
                }
                argument = new StringBuilder();
                isArgument = false;
            }
            else if(isArgument)
            {
                argument.append(outputStr.charAt(i));
            }
            else
            {
                tempStr.append(outputStr.charAt(i));
            }
        }
        return tempStr.toString();
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
    public boolean isDay()
    {
        if (currentTime >= 6000 && currentTime < 18000)
        {
            return false;
        }
        else
        {
            return true;
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
                if (currentDay == months.get(currentMonth - 1).getDays())
                {
                    if (currentMonth == months.size())
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
            //networks
            if (world.getWorldTime() == 1500 || world.getWorldTime() == 7250 || world.getWorldTime() == 11000)
            {
                plugin.getNetworks().initiateTransports();
            }
            //end networks
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
                    .interval(calculateInterval(plugin), TimeUnit.MILLISECONDS)
                    .name("Calendar Task").submit(plugin);
            plugin.getCalendar().setStatus(true);
        }
        else
        {
            plugin.getLogger().error(rppLogger("Something went seriously wrong ..."));
            plugin.getCalendar().setStatus(false);
        }
    }
    private void startCalendar()
    {
        calendarTask.execute(new cycleTask())
                .interval(calculateInterval(plugin), TimeUnit.MILLISECONDS)
                .name("Calendar Task").submit(plugin);
        plugin.getCalendar().setStatus(true);
    }
    public void stopCalendar()
    {
        calendarOn = false;
        plugin.getCalendar().setStatus(false);
    }
}
