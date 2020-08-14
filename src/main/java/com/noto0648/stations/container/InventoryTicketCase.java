package com.noto0648.stations.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Iterator;

public class InventoryTicketCase  implements IInventory
{
    //private ItemStack[] items;
    private final int inventoryMaxSize = 11;
    private final NonNullList<ItemStack> items;

    private World worldObj;
    private ItemStack ticketCase;
    private InventoryPlayer player;

    public InventoryTicketCase(ItemStack item, InventoryPlayer inventoryPlayer, World world)
    {
        //items = new ItemStack[11];
        worldObj = world;
        ticketCase = item;
        player = inventoryPlayer;
        items = NonNullList.withSize(inventoryMaxSize, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory()
    {
        return inventoryMaxSize;
    }


    public ItemStack addItem(ItemStack p_addItem_1_)
    {
        ItemStack lvt_2_1_ = p_addItem_1_.copy();

        for(int lvt_3_1_ = 0; lvt_3_1_ < this.inventoryMaxSize; ++lvt_3_1_) {
            ItemStack lvt_4_1_ = this.getStackInSlot(lvt_3_1_);
            if (lvt_4_1_.isEmpty()) {
                this.setInventorySlotContents(lvt_3_1_, lvt_2_1_);
                this.markDirty();
                return ItemStack.EMPTY;
            }

            if (ItemStack.areItemsEqual(lvt_4_1_, lvt_2_1_)) {
                int lvt_5_1_ = Math.min(this.getInventoryStackLimit(), lvt_4_1_.getMaxStackSize());
                int lvt_6_1_ = Math.min(lvt_2_1_.getCount(), lvt_5_1_ - lvt_4_1_.getCount());
                if (lvt_6_1_ > 0) {
                    lvt_4_1_.grow(lvt_6_1_);
                    lvt_2_1_.shrink(lvt_6_1_);
                    if (lvt_2_1_.isEmpty()) {
                        this.markDirty();
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (lvt_2_1_.getCount() != p_addItem_1_.getCount()) {
            this.markDirty();
        }

        return lvt_2_1_;
    }

    @Override
    public ItemStack removeStackFromSlot(int p_removeStackFromSlot_1_)
    {
        ItemStack lvt_2_1_ = (ItemStack)this.items.get(p_removeStackFromSlot_1_);
        if (lvt_2_1_.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(p_removeStackFromSlot_1_, ItemStack.EMPTY);
            return lvt_2_1_;
        }
    }

    @Override
    public void setInventorySlotContents(int p_setInventorySlotContents_1_, ItemStack p_setInventorySlotContents_2_)
    {
        this.items.set(p_setInventorySlotContents_1_, p_setInventorySlotContents_2_);
        if (!p_setInventorySlotContents_2_.isEmpty() && p_setInventorySlotContents_2_.getCount() > this.getInventoryStackLimit())
        {
            p_setInventorySlotContents_2_.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public ItemStack getStackInSlot(int p_getStackInSlot_1_) {
        return p_getStackInSlot_1_ >= 0 && p_getStackInSlot_1_ < this.items.size() ? (ItemStack)this.items.get(p_getStackInSlot_1_) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int p_decrStackSize_1_, int p_decrStackSize_2_) {
        ItemStack lvt_3_1_ = ItemStackHelper.getAndSplit(this.items, p_decrStackSize_1_, p_decrStackSize_2_);
        if (!lvt_3_1_.isEmpty()) {
            this.markDirty();
        }

        return lvt_3_1_;
    }

    /*
    @Override
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return items[p_70301_1_];
    }

    public ItemStack decrStackSize(int p_decrStackSize_1_, int p_decrStackSize_2_) {
        ItemStack lvt_3_1_ = ItemStackHelper.getAndSplit(this.inventoryContents, p_decrStackSize_1_, p_decrStackSize_2_);
        if (!lvt_3_1_.isEmpty()) {
            this.markDirty();
        }

        return lvt_3_1_;
    }


    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.items[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.items[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.items[p_70298_1_];
                this.items[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.items[p_70298_1_].splitStack(p_70298_2_);

                if (this.items[p_70298_1_].stackSize == 0)
                {
                    this.items[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.items[p_70304_1_] != null)
        {
            ItemStack itemstack = this.items[p_70304_1_];
            this.items[p_70304_1_] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.items[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }
*/


    @Override
    public int getField(int p_getField_1_) {
        return 0;
    }

    @Override
    public void setField(int p_setField_1_, int p_setField_2_) { }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public String getName()
    {
        return "TicketCase";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }


    @Override
    public boolean isEmpty()
    {
        Iterator var1 = this.items.iterator();

        ItemStack lvt_2_1_;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            lvt_2_1_ = (ItemStack)var1.next();
        } while(lvt_2_1_.isEmpty());

        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer p_70300_1_)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer p_openInventory_1_)
    {
        if(!ticketCase.hasTagCompound())
        {
            ticketCase.setTagCompound(new NBTTagCompound());
            ticketCase.getTagCompound().setTag("Items", new NBTTagList());
        }
        NBTTagList tags = (NBTTagList)ticketCase.getTagCompound().getTag("Items");
        for(int i = 0; i < tags.tagCount(); i++)
        {
            NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
            int slot = tagCompound.getByte("Slot");
            if(slot >= 0 && slot < items.size())
            {
                items.set(slot, new ItemStack(tagCompound));
            }
        }
    }

    @Override
    public void closeInventory(EntityPlayer p_openInventory_1_)
    {
        saveItems();
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    private void saveItems()
    {
        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i) != ItemStack.EMPTY)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte)i);
                items.get(i).writeToNBT(compound);
                tagList.appendTag(compound);
            }
        }
        ItemStack result = new ItemStack(ticketCase.getItem(), 1);
        result.setTagCompound(new NBTTagCompound());
        result.getTagCompound().setTag("Items", tagList);
        player.setInventorySlotContents(player.currentItem, result);
    }
}
