package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.ITicketItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTicket extends Item implements ITicketItem
{
    private static final int[][] TICKET_CUT_TABLE = new int[][]{{0,1}, {1, -1}, {2, 2}, {3, 4}, {4, -1}};
    public ItemTicket()
    {
        super();
        setCreativeTab(StationsMod.tab);
        setMaxStackSize(1);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack metadata)
    {
        int meta = metadata.getMetadata();
        if(meta == 1)
            return "item.notomod.used_ticket";
        if(meta == 2)
            return "item.notomod.ic_ticket";
        if(meta == 3)
            return "item.notomod.mars_ticket";
        if(meta == 4)
            return "item.notomod.used_mars_ticket";
        if(meta == 5)
            return "item.notomod.replenishing_ticket";

        return "item.notomod.ticket";
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(!isInCreativeTab(tab)) return;
        for(int i = 0; i < 6; i++)
        {
            if(i != 2)
            items.add(new ItemStack(this, 1, i));
        }
        items.add(new ItemStack(this, 1, 2));

    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
        if(stack.getTagCompound() == null || !stack.getTagCompound().hasKey("station_from") || !stack.getTagCompound().hasKey("station_to"))
            return;

        String str1 = stack.getTagCompound().getString("station_from");
        String str2 = stack.getTagCompound().getString("station_to");

        list.add(str1 + " -> " + str2);
    }

    @Override
    public boolean validItem(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemTicket;
    }

    @Override
    public ItemStack cutTicket(EntityLivingBase ep, ItemStack itemStack)
    {
        int meta = itemStack.getMetadata();
        for(int i = 0; i < TICKET_CUT_TABLE.length; i++)
        {
            if(TICKET_CUT_TABLE[i][0] == meta)
            {
                int next = TICKET_CUT_TABLE[i][1];
                if(next != -1)
                {
                    return new ItemStack(this, 1, next);
                }
                else
                {
                    return ItemStack.EMPTY;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canStoreTicketCase(ItemStack itemStack)
    {
        return itemStack.getMetadata() != 2;
    }

    @Override
    public boolean isICTicket(ItemStack itemStack)
    {
        return itemStack.getMetadata() == 2;
    }
}
