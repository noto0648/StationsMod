package com.noto0648.stations.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITicketItem
{
    ItemStack cutTicket(EntityPlayer ep,  ItemStack itemStack);
    boolean canStoreTicketCase(ItemStack itemStack);
}
