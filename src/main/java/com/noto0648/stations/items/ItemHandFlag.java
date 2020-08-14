package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemHandFlag extends Item
{

    public ItemHandFlag()
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
            return "item.notomod.handflag_white";
        if(meta == 2)
            return "item.notomod.handflag_green";

        return "item.notomod.handflag_red";
    }

    @Override
    public void getSubItems(CreativeTabs p_getSubItems_1_, NonNullList<ItemStack> p_getSubItems_2_)
    {
        if (!this.isInCreativeTab(p_getSubItems_1_)) return;
        for(int i = 0; i < 3; i++)
            p_getSubItems_2_.add(new ItemStack(this, 1, i));
    }
}
