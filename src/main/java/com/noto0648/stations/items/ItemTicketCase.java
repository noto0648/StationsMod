package com.noto0648.stations.items;

import com.noto0648.stations.Stations;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/21.
 */
public class ItemTicketCase extends Item
{
    public ItemTicketCase()
    {
        super();
        setCreativeTab(Stations.tab);
        setUnlocalizedName("NotoMod.ticketCase");
        setMaxStackSize(1);
        setTextureName("notomod:ticket_case");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(player.isSneaking())
        {
            boolean[] stacks = new boolean[11];
            if(!itemStack.hasTagCompound())
            {
                itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setTag("Items", new NBTTagList());
            }
            NBTTagList tags = (NBTTagList)itemStack.getTagCompound().getTag("Items");
            for(int j = 0; j < tags.tagCount(); j++)
            {
                NBTTagCompound tagCompound = tags.getCompoundTagAt(j);
                int slot = tagCompound.getByte("Slot");
                if(slot >= 0 && slot < stacks.length)
                {
                    stacks[slot] = true;
                }
            }

            for(int i = 0; i < player.inventory.mainInventory.length; i++)
            {
                ItemStack stack = player.inventory.mainInventory[i];
                if(stack != null && stack.getItem() == Stations.instance.ticket && stack.getItemDamage() < 2)
                {
                    for(int j = 0; j < stacks.length; j++)
                    {
                        if(!stacks[j])
                        {
                            NBTTagCompound compound = new NBTTagCompound();
                            compound.setInteger("Slot", (byte)j);
                            stack.writeToNBT(compound);
                            tags.appendTag(compound);
                            stacks[j] = true;
                            player.inventory.mainInventory[i] = null;
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            player.openGui(Stations.instance, 30, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
        return itemStack;
    }
}
