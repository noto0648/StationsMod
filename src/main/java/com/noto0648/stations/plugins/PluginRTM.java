package com.noto0648.stations.plugins;

import com.noto0648.stations.common.ITicketItem;
import com.noto0648.stations.tiles.TileEntityTicketGate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PluginRTM implements ITicketItem
{
    public static final PluginRTM INSTANCE = new PluginRTM();

    public PluginRTM() {}


    public void postInit()
    {
        TileEntityTicketGate.addITicketItemHandler(this);
    }


    @Override
    public boolean validItem(ItemStack itemStack)
    {
        if(itemStack == ItemStack.EMPTY)
            return false;
        String name = itemStack.getUnlocalizedName();
        return name.equals("item.rtm:ticket") || name.equals("item.rtm:ticketBook") || name.equals("item.rtm:icCard");
    }

    @Override
    public ItemStack cutTicket(EntityLivingBase ep, ItemStack stack)
    {
        String name = stack.getUnlocalizedName();
        if(name.equals("item.rtm:ticket") || name.equals("item.rtm:ticketBook"))
        {
            if(!stack.hasTagCompound())
                stack.setTagCompound(new NBTTagCompound());

            NBTTagCompound comp = stack.getTagCompound();

            if(comp.hasKey("Entered"))
            {
                if(stack.getItemDamage() == 0)
                {
                    return ItemStack.EMPTY;
                }
                //stack = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
                //ep.setCurrentItemOrArmor(0, stack);
                return new ItemStack(stack.getItem(), 1, stack.getItemDamage());
            }
            else
            {
                ItemStack itemStack2 = new ItemStack(stack.getItem(), 1, stack.getItemDamage() - 1);
                NBTTagCompound tag = new NBTTagCompound();
                tag.setBoolean("Entered", true);
                itemStack2.setTagCompound(tag);
                //stack = itemStack2;
                //ep.setCurrentItemOrArmor(0, stack);
                return itemStack2;
            }
        }
        else if(name.equals("item.rtm:icCard"))
        {
            return stack;
        }
        return stack;
    }

    @Override
    public boolean canStoreTicketCase(ItemStack itemStack)
    {
        if(itemStack == ItemStack.EMPTY)
            return false;
        return !itemStack.getUnlocalizedName().equals("item.rtm:icCard");
    }

    @Override
    public boolean isICTicket(ItemStack itemStack)
    {
        return !canStoreTicketCase(itemStack);
    }

}
