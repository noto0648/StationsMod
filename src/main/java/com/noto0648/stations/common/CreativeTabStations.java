package com.noto0648.stations.common;

import com.noto0648.stations.StationsItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabStations extends CreativeTabs
{
    public CreativeTabStations()
    {
        super("stationsItems");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(StationsItems.itemTicket, 1);
    }
}
