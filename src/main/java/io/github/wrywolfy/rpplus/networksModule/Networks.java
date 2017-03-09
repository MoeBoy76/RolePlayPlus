package io.github.wrywolfy.rpplus.networksModule;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import io.github.wrywolfy.rpplus.RolePlayPlus;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;

import java.util.List;
import java.util.Map;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class Networks
{
    private RolePlayPlus plugin;
    public enum Direction {NORTH, EAST, SOUTH, WEST}
    private List<NetworkNode> networkList;

    //assignment
    private NetworkNode inputNode = new NetworkNode();
    private NetworkNodeLocation inputNodeLoc = new NetworkNodeLocation();
    private Vector3d pos1, pos2;
    //assignment

    public Networks(CommentedConfigurationNode config, RolePlayPlus plugin)
    {
        this.plugin = plugin;
        networkList = Lists.newArrayList();
        setNetworks(config);
    }
    public void setNetworks(CommentedConfigurationNode config)
    {
        Map<Object, ? extends ConfigurationNode> entryMap = config.getNode("nodes").getChildrenMap();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : entryMap.entrySet())
        {
            NetworkNode route = new NetworkNode();
            for (int c = 0; c < 2; c++)
            {
                route.getLoc(c).setHeadingValue(config.getNode("nodes", entry.getKey(), "location" + (c + 1), "heading").getString());
                route.getLoc(c).setRegion(
                        new Vector3d(config.getNode("nodes", entry.getKey(), "location" + (c + 1), "position1", "X").getDouble(),
                                config.getNode("nodes", entry.getKey(), "location" + (c + 1), "position1", "Y").getDouble(),
                                config.getNode("nodes", entry.getKey(), "location" + (c + 1), "position1", "Z").getDouble()),
                        new Vector3d(config.getNode("nodes", entry.getKey(), "location" + (c + 1), "position2", "X").getDouble(),
                                config.getNode("nodes", entry.getKey(), "location" + (c + 1), "position2", "Y").getDouble(),
                                config.getNode("nodes", entry.getKey(), "location" + (c + 1), "position2", "Z").getDouble()));
            }
            if (route.isValid())
            {
                networkList.add(route);
            }
            else
            {
                plugin.getLogger().error(rppLogger("Error reading " + entry.getKey() + " from config ..."));
            }
        }
    }
    public void pushNetworkList()
    {
        networkList.add(inputNode);
    }
    public void saveNetworks(CommentedConfigurationNode config)
    {
        for (int i = 0; i < networkList.size(); i++)
        {
            for (int c = 0; c < 2; c++)
            {
                config.getNode("nodes", "node" + i, "location" + (c + 1), "heading").setValue(networkList.get(i).getLoc(c).getHeadingValue());
                config.getNode("nodes", "node" + i, "location" + (c + 1), "position1", "X").setValue(networkList.get(i).getLoc(c).getMaxX());
                config.getNode("nodes", "node" + i, "location" + (c + 1), "position1", "Y").setValue(networkList.get(i).getLoc(c).getMaxY());
                config.getNode("nodes", "node" + i, "location" + (c + 1), "position1", "Z").setValue(networkList.get(i).getLoc(c).getMaxZ());
                config.getNode("nodes", "node" + i, "location" + (c + 1), "position2", "X").setValue(networkList.get(i).getLoc(c).getMinX());
                config.getNode("nodes", "node" + i, "location" + (c + 1), "position2", "Y").setValue(networkList.get(i).getLoc(c).getMinY());
                config.getNode("nodes", "node" + i, "location" + (c + 1), "position2", "Z").setValue(networkList.get(i).getLoc(c).getMinZ());
            }
        }
    }
    public NetworkNode getInputNode()
    {
        return inputNode;
    }
    public NetworkNodeLocation getInputNodeLoc()
    {
        return inputNodeLoc;
    }
    public void setPos1(Vector3d pos)
    {
        this.pos1 = pos;
    }
    public void setPos2(Vector3d pos)
    {
        this.pos2 = pos;
    }
    public void setInputNodeLocRegion()
    {
        if (pos1 != null && pos2 != null)
        {
            inputNodeLoc.setRegion(pos1, pos2);
        }
    }
//    public NetworkNode getNode(String inputName)
//    {
//        for (int i = 0; i < networkList.size(); i++)
//        {
//            if (networkList.get(i).getRouteName().compareToIgnoreCase(inputName) == 0)
//            {
//                return networkList.get(i);
//            }
//        }
//        return null;
//    }
    public void initiateTransports()
    {
        for (int i = 0; i < networkList.size(); i++)
        {
            networkList.get(i).transport(plugin);
        }
    }
}
