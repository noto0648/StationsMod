package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.item.Item;

public class ItemSealSensor extends Item
{
    public ItemSealSensor()
    {
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.seal_sensor");
        setMaxStackSize(1);
    }
}
