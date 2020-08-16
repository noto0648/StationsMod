package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.item.Item;

public class ItemHammer extends Item
{
    public ItemHammer()
    {
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.hammer");
        setMaxStackSize(1);
        setMaxDamage(250);
    }
}
