package com.noto0648.stations.items;

import com.noto0648.stations.Stations;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Noto on 14/08/15.
 */
public class ItemClock extends Item
{
    public ItemClock()
    {
        super();
        setMaxStackSize(1);
        setTextureName("notomod:digital_clock");
        setCreativeTab(Stations.tab);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return "item." + (p_77667_1_.getItemDamage() == 1 ? "NotoMod.analogClock" : "NotoMod.digitalClock");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
    }

}
