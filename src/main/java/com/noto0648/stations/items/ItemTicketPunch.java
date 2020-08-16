package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.tiles.TileEntityTicketGate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

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
            if(entityLiving.getHeldItem(hand) != ItemStack.EMPTY && entityLiving.getHeldItem(hand).getItem() != null)
            {
                ItemStack stack = TileEntityTicketGate.cutTicket(entityLiving, itemStack);
                if(stack == null)
                {
                    return false;
                }
                entityLiving.setHeldItem(hand, stack);
                entityPlayer.world.playSound((EntityPlayer)null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }
}
