package io.github.wrywolfy.rpplus;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;

import java.io.File;
import java.io.IOException;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class ConfigManager
{
    @Inject
    @ConfigDir(sharedRoot = false)
    private File configFolder;

    private File calendarFile = new File(configFolder, "calendar.conf");
    private File networksFile = new File(configFolder, "networks.conf");
    private ConfigurationLoader<CommentedConfigurationNode> calendarNode = HoconConfigurationLoader.builder().setFile(calendarFile).build();
    private ConfigurationLoader<CommentedConfigurationNode> networksNode = HoconConfigurationLoader.builder().setFile(networksFile).build();
    private CommentedConfigurationNode calendarConfig = null;
    private CommentedConfigurationNode networksConfig = null;
    private RolePlayPlus plugin;

    public ConfigManager(RolePlayPlus plugin)
    {
        this.plugin = plugin;
        if (!calendarFile.exists())
        {
            createCalendarConfig();
        }
        else
        {
            loadCalendarConfig();
        }
        //if (!networksFile.exists())
        //{
        //    createNetworksConfig();
        //}
        //else
        //{
        //    loadNetworksConfig();
        //}
    }
    private void loadCalendarConfig()
    {
        try
        {
            calendarConfig = calendarNode.load();
        }
        catch (IOException x)
        {
            plugin.getLogger().error(rppLogger("Error loading calendar.conf ..."));
        }
    }
    private void createCalendarConfig()
    {
        try
        {
            calendarFile.createNewFile();
            calendarConfig = calendarNode.load();
            //start set default values
            calendarConfig.getNode("cycles", "daySeconds").setValue(1200);
            calendarConfig.getNode("cycles", "nightSeconds").setValue(1200);
            calendarConfig.getNode("cycles", "currentDay").setValue(1);
            calendarConfig.getNode("cycles", "currentMonth").setValue(1);
            calendarConfig.getNode("cycles", "currentYear").setValue(2000);
            calendarConfig.getNode("output").setValue("&3[&e{MM}/{DD}/{YYYY}&3]");
            calendarConfig.getNode("week", "dayCount").setValue(7);
            //set days
            calendarConfig.getNode("week", "day1").setValue("Sunday");
            calendarConfig.getNode("week", "day2").setValue("Monday");
            calendarConfig.getNode("week", "day3").setValue("Tuesday");
            calendarConfig.getNode("week", "day4").setValue("Wednesday");
            calendarConfig.getNode("week", "day5").setValue("Thursday");
            calendarConfig.getNode("week", "day6").setValue("Friday");
            calendarConfig.getNode("week", "day7").setValue("Saturday");
            calendarConfig.getNode("months", "monthCount").setValue(12);
            //set months
            calendarConfig.getNode("months", "month1", "name").setValue("January");
            calendarConfig.getNode("months", "month1", "days").setValue(31);
            calendarConfig.getNode("months", "month2", "name").setValue("February");
            calendarConfig.getNode("months", "month2", "days").setValue(28);
            calendarConfig.getNode("months", "month3", "name").setValue("March");
            calendarConfig.getNode("months", "month3", "days").setValue(31);
            calendarConfig.getNode("months", "month4", "name").setValue("April");
            calendarConfig.getNode("months", "month4", "days").setValue(30);
            calendarConfig.getNode("months", "month5", "name").setValue("May");
            calendarConfig.getNode("months", "month5", "days").setValue(31);
            calendarConfig.getNode("months", "month6", "name").setValue("June");
            calendarConfig.getNode("months", "month6", "days").setValue(30);
            calendarConfig.getNode("months", "month7", "name").setValue("July");
            calendarConfig.getNode("months", "month7", "days").setValue(31);
            calendarConfig.getNode("months", "month8", "name").setValue("August");
            calendarConfig.getNode("months", "month8", "days").setValue(31);
            calendarConfig.getNode("months", "month9", "name").setValue("September");
            calendarConfig.getNode("months", "month9", "days").setValue(30);
            calendarConfig.getNode("months", "month10", "name").setValue("October");
            calendarConfig.getNode("months", "month10", "days").setValue(31);
            calendarConfig.getNode("months", "month11", "name").setValue("November");
            calendarConfig.getNode("months", "month11", "days").setValue(30);
            calendarConfig.getNode("months", "month12", "name").setValue("December");
            calendarConfig.getNode("months", "month12", "days").setValue(31);
            //end set default values
            plugin.getLogger().info(rppLogger("New calendar.conf generated ..."));
            calendarNode.save(calendarConfig);
        }
        catch (IOException x)
        {
            plugin.getLogger().error(rppLogger("Error creating calendar.conf ..."));
        }
    }
    public void saveCalendarConfig()
    {
        try
        {
            calendarNode.save(calendarConfig);
            plugin.getLogger().info(rppLogger("calendar.conf has been saved ..."));
        }
        catch (IOException x)
        {
            plugin.getLogger().error(rppLogger("Error saving calendar.conf ..."));
        }
    }
    private void loadNetworksConfig()
    {
        try
        {
            networksConfig = networksNode.load();
        }
        catch (IOException x)
        {
            plugin.getLogger().error(rppLogger("Error loading networks.conf ..."));
        }
    }
    private void createNetworksConfig()
    {
        try
        {
            networksFile.createNewFile();
            networksConfig = networksNode.load();
            //start set default values





            //end set default values
            plugin.getLogger().info(rppLogger("New networks.conf generated ..."));
            networksNode.save(networksConfig);
        }
        catch (IOException x)
        {
            plugin.getLogger().error(rppLogger("Error creating networks.conf ..."));
        }
    }
    public void saveNetworksConfig()
    {
        try
        {
            networksNode.save(networksConfig);
            plugin.getLogger().info(rppLogger("networks.conf has been saved ..."));
        }
        catch (IOException x)
        {
            plugin.getLogger().error(rppLogger("Error saving networks.conf ..."));
        }
    }
    public CommentedConfigurationNode getCalendarConfig()
    {
        return calendarConfig;
    }
    public CommentedConfigurationNode getNetworksConfig()
    {
        return networksConfig;
    }
}
