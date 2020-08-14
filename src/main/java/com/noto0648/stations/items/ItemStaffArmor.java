package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemStaffArmor extends ItemArmor
{
    public ItemStaffArmor(int p_i46750_2_, EntityEquipmentSlot p_i46750_3_)
    {
        super(StationsMod.staffArmorMaterial, p_i46750_2_, p_i46750_3_);
        setCreativeTab(StationsMod.tab);
    }
}
