package io.github.wrywolfy.rpplus.networksModule;

import com.flowpowered.math.vector.Vector3d;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.util.AABB;

import static io.github.wrywolfy.rpplus.networksModule.Networks.Direction.*;

public class NetworkNodeLocation
{
    private AABB region;
    private Networks.Direction heading;

    public Networks.Direction getHeading()
    {
        return heading;
    }
    public void setHeadingValue(String heading)
    {
        this.heading = this.heading.valueOf(heading);
    }
    public String getHeadingValue()
    {
        return this.heading.toString();
    }
    public void setHeading(Vector3d heading)
    {
        if ((heading.getX() >= 135) || (heading.getX() <= -135))
        {
            this.heading = NORTH;
        }
        else if((heading.getX() > -135) && (heading.getX() < -45))
        {
            this.heading = EAST;
        }
        else if((heading.getX() < 135) && (heading.getX() > 45))
        {
            this.heading = SOUTH;
        }
        else if((heading.getX() >= -45) && (heading.getX() <= 45))
        {
            this.heading = WEST;
        }
        else
        {
            this.heading = null;
        }
    }
    public void setRegion(Vector3d pos1, Vector3d pos2)
    {
        region = new AABB(pos1, pos2);
    }
    public boolean isValid()
    {
        if (region != null && heading != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public AABB getRegion()
    {
        return region;
    }
    public double getMaxX()
    {
        return region.getMax().getX();
    }
    public double getMaxY()
    {
        return region.getMax().getY();
    }
    public double getMaxZ()
    {
        return region.getMax().getZ();
    }
    public double getMinX()
    {
        return region.getMin().getX();
    }
    public double getMinY()
    {
        return region.getMin().getY();
    }
    public double getMinZ()
    {
        return region.getMin().getZ();
    }
}
