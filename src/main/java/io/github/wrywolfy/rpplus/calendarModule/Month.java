package io.github.wrywolfy.rpplus.calendarModule;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Month
{
    @Setting(comment="Name of Month")
    private String name = "Empty";
    @Setting(comment="Number of days in Month")
    private int days = 0;

    public String getName()
    {
        return name;
    }
    public int getDays()
    {
        return days;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setDays(int Days)
    {
        this.days = days;
    }
}
