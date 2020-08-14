package com.noto0648.stations.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySlideDoor extends TileEntity implements ITickable
{
    private int coolTime;
    @Override
    public void update()
    {
        if(coolTime > 0)
            coolTime--;

    }

    public int getCoolTime()
    {
        return coolTime;
    }

    public void setCoolTime(int coolTime)
    {
        this.coolTime = coolTime;
    }

    public float getCoolPercent()
    {
        return this.coolTime / 10f;
    }
}
