package com.noto0648.stations.items;

import com.noto0648.stations.Stations;
import net.minecraft.item.Item;

/**
 * Created by Noto on 14/08/14.
 */
public class ItemTorqueWrench extends Item
{
    public ItemTorqueWrench()
    {
        setCreativeTab(Stations.tab);
        setTextureName("notomod:torque_wrench");
        setUnlocalizedName("NotoMod.torqueWrench");
        setMaxStackSize(1);

    }
}
