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

    Month(String name, int days)
    {
        this.name = name;
        this.days = days;
    }
    String getName()
    {
        return name;
    }
    int getDays()
    {
        return days;
    }
}
