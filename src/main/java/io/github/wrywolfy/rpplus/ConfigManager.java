package io.github.wrywolfy.rpplus;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class ConfigManager
{
    private String configPath = "./config/roleplayplus/";
    private ConfigurationLoader<CommentedConfigurationNode> calendarNode = HoconConfigurationLoader.builder().setPath(Paths.get(configPath + "calendar.conf")).build();
    private ConfigurationLoader<CommentedConfigurationNode> networksNode = HoconConfigurationLoader.builder().setPath(Paths.get(configPath + "networks.conf")).build();
    private CommentedConfigurationNode calendarConfig;
    private CommentedConfigurationNode networksConfig;
    private RolePlayPlus plugin;

    public ConfigManager(RolePlayPlus plugin)
    {
        this.plugin = plugin;
        initializeConfigs();
    }
    public void initializeConfigs()
    {
        if (Files.notExists(Paths.get(configPath + "calendar.conf")))
        {
            createCalendarConfig();
        }
        else
        {
            loadCalendarConfig();
        }
        if (Files.notExists(Paths.get(configPath + "networks.conf")))
        {
            createNetworksConfig();
        }
        else
        {
            loadNetworksConfig();
        }
    }
    public void loadCalendarConfig()
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
            Files.createDirectories(Paths.get("./config/roleplayplus/"));
            Sponge.getAssetManager().getAsset(plugin, "calendar.conf").get().copyToFile(Paths.get(configPath + "calendar.conf"));
            calendarConfig = calendarNode.load();
            plugin.getLogger().info(rppLogger("New calendar.conf generated ..."));
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
            plugin.getCalendar().saveCalendar(calendarConfig);
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
            Files.createDirectories(Paths.get("./config/roleplayplus/"));
            Files.createFile(Paths.get(configPath + "networks.conf"));
            networksConfig = networksNode.load();
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
            plugin.getNetworks().saveNetworks(networksConfig);
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
