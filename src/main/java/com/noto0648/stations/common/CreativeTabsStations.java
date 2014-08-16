package com.noto0648.stations.common;

import com.noto0648.stations.Stations;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by Noto on 14/08/04.
 */
public class CreativeTabsStations extends CreativeTabs
{

    public CreativeTabsStations()
    {
        super("stationsTab");
    }

    @Override
    public Item getTabIconItem()
    {
        return Stations.instance.ticket;
    }
}
