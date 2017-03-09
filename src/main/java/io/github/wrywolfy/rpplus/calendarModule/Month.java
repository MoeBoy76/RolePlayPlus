package io.github.wrywolfy.rpplus.calendarModule;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
class Month
{
    @Setting(comment="Name of Month")
    private String name;
    @Setting(comment="Number of days in Month")
    private int days;

    public Month(String name, int days)
    {
        this.name = name;
        this.days = days;
    }
    public String getName()
    {
        return name;
    }
    public int getDays()
    {
        return days;
    }
}
