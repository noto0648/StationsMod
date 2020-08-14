package com.noto0648.stations.container;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.common.ITicketItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerTicketCase extends Container
{
    private InventoryTicketCase inventory;
    private ItemStack ticketCase;

    public ContainerTicketCase(InventoryPlayer inv, ItemStack itemStack, World world, EntityPlayer ep)
    {
        ticketCase = itemStack;
        inventory = new InventoryTicketCase(ticketCase, inv, world);
        inventory.openInventory(ep);

        for(int i = 0; i < 11; i++)
        {
            this.addSlotToContainer(new Slot(inventory, i, i * 18 + 8, 20)
            {
                @Override
                public boolean isItemValid(ItemStack p_75214_1_)
                {
                    if(p_75214_1_.getItem()  != null && p_75214_1_.getItem()  instanceof ITicketItem)
                    {
                        return ((ITicketItem)p_75214_1_.getItem()).canStoreTicketCase(p_75214_1_);
                    }
                    return false;
                }
            });

        }

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 26 + j * 18, 51 + i * 18)
                {
                    @Override
                    public boolean canTakeStack(EntityPlayer p_82869_1_)
                    {
                        return (!getHasStack() ? true : getStack().getItem() != StationsItems.itemTicketCase);
                    }
                });
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inv, i, 26 + i * 18, 109)
            {
                @Override
                public boolean canTakeStack(EntityPlayer p_82869_1_)
                {
                    return (!getHasStack() ? true : getStack().getItem() != StationsItems.itemTicketCase);
                }
            });
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_transferStackInSlot_1_, int p_transferStackInSlot_2_)
    {
        ItemStack lvt_3_1_ = ItemStack.EMPTY;
        Slot lvt_4_1_ = (Slot)this.inventorySlots.get(p_transferStackInSlot_2_);
        if (lvt_4_1_ != null && lvt_4_1_.getHasStack()) {
            ItemStack lvt_5_1_ = lvt_4_1_.getStack();
            lvt_3_1_ = lvt_5_1_.copy();
            if (p_transferStackInSlot_2_ < inventory.getSizeInventory()) {
                if (!this.mergeItemStack(lvt_5_1_, inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(lvt_5_1_, 0, inventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (lvt_5_1_.isEmpty()) {
                lvt_4_1_.putStack(ItemStack.EMPTY);
            } else {
                lvt_4_1_.onSlotChanged();
            }
        }

        return lvt_3_1_;
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.inventory.closeInventory(p_75134_1_);
    }

}
