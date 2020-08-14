package com.noto0648.stations.container;

import com.noto0648.stations.StationsItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

public class ContainerMARS extends Container
{
    private InventoryCraftResult inventory;

    public ContainerMARS(InventoryPlayer p_i45794_1_)
    {
        inventory = new InventoryCraftResult();
        int lvt_3_2_;
        for(lvt_3_2_ = 0; lvt_3_2_ < 3; ++lvt_3_2_) {
            for(int lvt_4_1_ = 0; lvt_4_1_ < 9; ++lvt_4_1_) {
                this.addSlotToContainer(new Slot(p_i45794_1_, lvt_4_1_ + lvt_3_2_ * 9 + 9, 8 + lvt_4_1_ * 18, 84 + lvt_3_2_ * 18));
            }
        }

        for(lvt_3_2_ = 0; lvt_3_2_ < 9; ++lvt_3_2_) {
            this.addSlotToContainer(new Slot(p_i45794_1_, lvt_3_2_, 8 + lvt_3_2_ * 18, 142));
        }
        this.addSlotToContainer(new Slot(inventory, 0,116, 35)
        {
            @Override
            public boolean isItemValid(ItemStack p_isItemValid_1_) {
                return false;
            }

            @Override
            public ItemStack onTake(EntityPlayer p_onTake_1_, ItemStack p_onTake_2_)
            {
                return p_onTake_2_;
            }
        });

    }

    public void updateTicket(String str1, String str2)
    {
        if(str1 == null || str2 == null || (StringUtils.isNullOrEmpty(str1) || StringUtils.isNullOrEmpty(str2)))
        {
            inventory.setInventorySlotContents(0, ItemStack.EMPTY);
        }
        else
        {

            ItemStack stack = new ItemStack(StationsItems.itemTicket, 1, 3);

            if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());

            stack.getTagCompound().setString("station_from", str1);
            stack.getTagCompound().setString("station_to", str2);

            inventory.setInventorySlotContents(0, stack);
        }
        detectAndSendChanges();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true;
    }
}
