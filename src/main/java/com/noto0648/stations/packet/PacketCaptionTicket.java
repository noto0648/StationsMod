package com.noto0648.stations.packet;

import com.noto0648.stations.Stations;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tile.TileEntityTicketMachine;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Noto on 14/08/12.
 */
public class PacketCaptionTicket implements IMessageHandler<PacketSendTicket, IMessage>
{
    @Override
    public IMessage onMessage(PacketSendTicket message, MessageContext ctx)
    {
        int ticketOutPutId = message.getIDValue();
        EntityPlayer targetPlayer = ctx.getServerHandler().playerEntity;
        if(targetPlayer != null)
        {
            if(ticketOutPutId == 2)
            {
                int emptySlot = targetPlayer.inventory.getFirstEmptyStack();
                ItemStack ticket = new ItemStack(Stations.instance.ticketCase, 1);
                if(emptySlot != -1)
                {
                    targetPlayer.inventory.setInventorySlotContents(emptySlot, ticket);
                }
                else
                {
                    Utils.INSTANCE.dropItemStack(targetPlayer.worldObj, ticket, message.x, message.y, message.z);
                }
            }

            int loopMax = ticketOutPutId == 0 ? 1 : ticketOutPutId == 1 ? 2 : ticketOutPutId == 2 ? 11 : ticketOutPutId == 3 ? 1 : 0;

            for(int i = 0; i < loopMax; i++)
            {
                int emptySlot = targetPlayer.inventory.getFirstEmptyStack();
                ItemStack ticket = new ItemStack(Stations.instance.ticket, 1, 0);
                ticket.setTagCompound(new NBTTagCompound());
                ticket.getTagCompound().setLong("issue", targetPlayer.worldObj.getWorldTime());
                if(emptySlot != -1)
                {
                    targetPlayer.inventory.setInventorySlotContents(emptySlot, ticket);
                }
                else
                {
                    Utils.INSTANCE.dropItemStack(targetPlayer.worldObj, ticket, message.x, message.y, message.z);
                }
            }
        }
        return null;
    }
}
