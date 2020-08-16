package com.noto0648.stations.items;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import net.minecraft.item.ItemDoor;

public class ItemSlideDoor extends ItemDoor
{
    public ItemSlideDoor()
    {
        super(StationsItems.blockSlideDoor);
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.slide_door");
    }
}
