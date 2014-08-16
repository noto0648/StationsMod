package com.noto0648.stations.items;

import com.noto0648.stations.Stations;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Created by Noto on 14/08/04.
 */
public class ItemTicket extends Item
{
    private IIcon usingTicket;
    private IIcon normalTicket;
    private IIcon icTicket;

    public ItemTicket()
    {
        super();
        setCreativeTab(Stations.tab);
        setUnlocalizedName("NotoMod.ticket");
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return "item." + (p_77667_1_.getItemDamage() == 1 ? "NotoMod.usingTicket" : (p_77667_1_.getItemDamage() == 0 ? "NotoMod.ticket" : "NotoMod.icCard"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return p_77617_1_ == 0 ? normalTicket : (p_77617_1_ == 1 ? usingTicket : icTicket);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        normalTicket = p_94581_1_.registerIcon("notomod:ticket");
        usingTicket = p_94581_1_.registerIcon("notomod:ticket2");
        icTicket = p_94581_1_.registerIcon("notomod:ticket_ic");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 2));

    }

}
