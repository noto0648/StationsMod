package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.item.Item;

public class ItemTorqueWrench extends Item
{
    public ItemTorqueWrench()
    {
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.torque_wrench");
        setMaxStackSize(1);
    }
}
