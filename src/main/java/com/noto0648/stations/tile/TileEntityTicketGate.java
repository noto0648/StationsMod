package com.noto0648.stations.tile;

import com.noto0648.stations.Stations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Noto on 14/08/13.
 */
public class TileEntityTicketGate extends TileEntity
{
    public static final int OPEN_INTERVAL = 20 * 3;
    private int openInterval = -1;
    private int lastEntityId;

    @Override
    public void updateEntity()
    {
        if(openInterval >= 0)
        {
            openInterval++;
            if(openInterval == OPEN_INTERVAL)
            {
                openInterval = -1;

                Entity e = worldObj.getEntityByID(lastEntityId);
                if(e != null && e instanceof EntityPlayer)
                {
                    worldObj.playAuxSFXAtEntity((EntityPlayer)e, 1003, xCoord, yCoord, zCoord, 0);
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        openInterval = p_145839_1_.getInteger("interval");
        lastEntityId = p_145839_1_.getInteger("playerId");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("interval", openInterval);
        p_145841_1_.setInteger("playerId", lastEntityId);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    public void openGate(int playerId)
    {
        openInterval = 0;
        lastEntityId = playerId;
    }

    public boolean isGateOpen()
    {
        return openInterval != -1;
    }

    public static ItemStack cutTicket(ItemStack ticket)
    {
        if(ticket == null || ticket.getItem() != Stations.instance.ticket || ticket.stackSize != 1)
            return null;

        if(ticket.getItemDamage() == 0)
        {
            ItemStack result = new ItemStack(ticket.getItem(), 1, 1);
            result.setTagCompound(ticket.getTagCompound());
            return result;
        }
        if(ticket.getItemDamage() == 2)
        {
            return ticket;
        }
        return null;
    }
}
