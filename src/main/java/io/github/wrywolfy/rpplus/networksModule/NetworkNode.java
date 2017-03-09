package io.github.wrywolfy.rpplus.networksModule;

import com.flowpowered.math.vector.Vector3d;
import io.github.wrywolfy.rpplus.RolePlayPlus;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

import java.util.Set;

import static io.github.wrywolfy.rpplus.UtilityMethods.rppLogger;

public class NetworkNode
{
    private NetworkNodeLocation[] locations = new NetworkNodeLocation[2];

    public boolean isValid()
    {
        if (locations[0].isValid() && locations[1].isValid() && isSameSize())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean isSameSize()
    {
        if (locations[0].getRegion().getSize().getY() == locations[1].getRegion().getSize().getY())
        {
            if (locations[0].getRegion().getSize().getX() == locations[1].getRegion().getSize().getX()
                    && locations[0].getRegion().getSize().getZ() == locations[1].getRegion().getSize().getZ())
            {
                return true;
            }
            else if (locations[0].getRegion().getSize().getX() == locations[1].getRegion().getSize().getZ()
                    && locations[0].getRegion().getSize().getZ() == locations[1].getRegion().getSize().getX())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    public void setLoc1(NetworkNodeLocation loc)
    {
        locations[0] = loc;
    }
    public void setLoc2(NetworkNodeLocation loc)
    {
        locations[1] = loc;
    }
    public NetworkNodeLocation getLoc(int index)
    {
        return locations[index];
    }
    RolePlayPlus plugin;
    public void transport(RolePlayPlus plugin)
    {
        this.plugin = plugin;
        plugin.getLogger().info(rppLogger("Initiating transportAction(1) ..."));

        transportAction(0, 1);

        plugin.getLogger().info(rppLogger("Initiating transportAction(2) ..."));

        transportAction(1, 0);
    }
    private void transportAction(int origin, int destination)
    {
        World world = Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorld().get().getUniqueId()).get();
        Set<Entity> entities = world.getIntersectingEntities(locations[origin].getRegion());
        plugin.getLogger().info(rppLogger("Set created ..."));
        if (locations[origin].getHeading() == locations[destination].getHeading())
        {
            for (Entity e : entities)
            {
                if (e instanceof Player)
                {
                    Player player = (Player) e;
                    player.setLocation(world.getLocation((locations[destination].getRegion().getCenter().add(player.getLocation().getPosition().sub(locations[origin].getRegion().getCenter())))));
                }
            }
        }
        else if (locations[origin].getHeading() == Networks.Direction.NORTH)
        {
            if (locations[destination].getHeading() == Networks.Direction.EAST)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateRight(player, origin))));
                        player.setRotation(player.getRotation().add(90, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.SOUTH)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateOpposite(player, origin))));
                        player.setRotation(player.getRotation().add(180, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.WEST)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateLeft(player, origin))));
                        player.setRotation(player.getRotation().sub(90, 0, 0));
                    }
                }
            }
        }
        else if (locations[origin].getHeading() == Networks.Direction.EAST)
        {
            if (locations[destination].getHeading() == Networks.Direction.SOUTH)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateRight(player, origin))));
                        player.setRotation(player.getRotation().add(90, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.WEST)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateOpposite(player, origin))));
                        player.setRotation(player.getRotation().add(180, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.NORTH)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateLeft(player, origin))));
                        player.setRotation(player.getRotation().sub(90, 0, 0));
                    }
                }
            }
        }
        else if (locations[origin].getHeading() == Networks.Direction.SOUTH)
        {
            if (locations[destination].getHeading() == Networks.Direction.WEST)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateRight(player, origin))));
                        player.setRotation(player.getRotation().add(90, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.NORTH)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateOpposite(player, origin))));
                        player.setRotation(player.getRotation().add(180, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.EAST)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateLeft(player, origin))));
                        player.setRotation(player.getRotation().sub(90, 0, 0));
                    }
                }
            }
        }
        else if (locations[origin].getHeading() == Networks.Direction.WEST)
        {
            if (locations[destination].getHeading() == Networks.Direction.NORTH)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateRight(player, origin))));
                        player.setRotation(player.getRotation().add(90, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.EAST)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateOpposite(player, origin))));
                        player.setRotation(player.getRotation().add(180, 0, 0));
                    }
                }
            }
            else if (locations[destination].getHeading() == Networks.Direction.SOUTH)
            {
                for (Entity e : entities)
                {
                    if (e instanceof Player)
                    {
                        Player player = (Player) e;
                        player.setLocation(world.getLocation(locations[destination].getRegion().getCenter().add(rotateLeft(player, origin))));
                        player.setRotation(player.getRotation().sub(90, 0, 0));
                    }
                }
            }
        }
    }
    private Vector3d rotateOpposite(Player player, int origin)
    {
        Vector3d difference = player.getLocation().getPosition().sub(locations[origin].getRegion().getCenter());
        return new Vector3d((difference.getX() * (-1)), difference.getY(), (difference.getZ() * (-1)));
    }
    private Vector3d rotateRight(Player player, int origin)
    {
        Vector3d difference = player.getLocation().getPosition().sub(locations[origin].getRegion().getCenter());
        return new Vector3d(difference.getZ(), difference.getY(), (difference.getX() * (-1)));
    }
    private Vector3d rotateLeft(Player player, int origin)
    {
        Vector3d difference = player.getLocation().getPosition().sub(locations[origin].getRegion().getCenter());
        return new Vector3d((difference.getZ() * (-1)), difference.getY(), difference.getX());
    }
}
