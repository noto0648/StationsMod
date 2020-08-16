package com.noto0648.stations.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ITicketItem
{
    boolean validItem(ItemStack itemStack);
    ItemStack cutTicket(EntityLivingBase ep,  ItemStack itemStack);
    boolean canStoreTicketCase(ItemStack itemStack);
    boolean isICTicket(ItemStack itemStack);
}
