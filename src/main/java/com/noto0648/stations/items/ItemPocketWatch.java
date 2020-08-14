package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemPocketWatch extends Item
{
    public ItemPocketWatch()
    {
        super();
        setMaxStackSize(1);
        setCreativeTab(StationsMod.tab);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        int meta = stack.getMetadata();
        if(meta == 1)
            return "item.notomod.analog_clock";

        return "item.notomod.digital_clock";
    }

    @Override
    public void getSubItems(CreativeTabs p_getSubItems_1_, NonNullList<ItemStack> p_getSubItems_2_)
    {
        if (!this.isInCreativeTab(p_getSubItems_1_)) return;
            p_getSubItems_2_.add(new ItemStack(this, 1, 0));
            p_getSubItems_2_.add(new ItemStack(this, 1, 1));
    }
}
