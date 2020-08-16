package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemTicketCase extends Item
{
    public ItemTicketCase()
    {
        super();
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.ticket_case");
        setMaxStackSize(1);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand p_onItemRightClick_3_)
    {
        /*
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

            for(int i = 0; i < player.inventory.mainInventory.size(); i++)
            {
                ItemStack stack = player.inventory.mainInventory.get(i);
                if(stack != null && stack.getItem() == StationsItems.itemTicket && stack.getItemDamage() < 2)
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
                            player.inventory.mainInventory.set(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
            }
        }
        else
        {
        }*/
        player.openGui(StationsMod.instance, 30, world, (int)player.posX, (int)player.posY, (int)player.posZ);

        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(p_onItemRightClick_3_));
    }

}
