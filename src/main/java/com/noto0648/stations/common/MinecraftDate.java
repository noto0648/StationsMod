package com.noto0648.stations.common;

import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/06.
 */
public class MinecraftDate
{
    public static final long ONE_DAY = 24000L;
    public static final long ONE_HOUR = 1000L;
    public static final double ONE_MINUTE = ONE_HOUR / 60D;

    private long time;

    private int hours;
    private int minutes;

    public MinecraftDate(long now)
    {
        time = now;
        init();
    }

    public MinecraftDate(World now)
    {
        time = now.getWorldTime();
        init();
    }

    private void init()
    {
        long z = time % ONE_DAY;
        hours = ((int)(z / ONE_HOUR) + 6) % 24;
        minutes = (int)((z % ONE_HOUR) / ONE_MINUTE);
    }

    @Override
    public String toString()
    {
        return String.format("%1$02d:%2$02d", hours, minutes);
    }

    public int getHours()
    {
        return hours;
    }

    public int getMinutes()
    {
        return minutes;
    }
}
