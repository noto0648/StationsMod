package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.ITicketItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemTicketPunch extends Item
{
    public ItemTicketPunch()
    {
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.ticket_punch");
        setMaxStackSize(1);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLiving, EnumHand enumHand)
    {
        for(EnumHand hand : EnumHand.values())
        {
            if(entityLiving.getHeldItem(hand) != ItemStack.EMPTY && entityLiving.getHeldItem(hand).getItem() != null && entityLiving.getHeldItem(hand).getItem() instanceof ITicketItem)
            {
                ITicketItem ticket =  (ITicketItem)entityLiving.getHeldItem(hand).getItem();
                ItemStack newItemStack = ticket.cutTicket(entityPlayer, entityLiving.getHeldItem(hand));
                entityLiving.setHeldItem(hand, newItemStack);
                entityPlayer.world.playSound((EntityPlayer)null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }
}
