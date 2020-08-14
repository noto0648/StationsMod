package com.noto0648.stations.tiles;

import com.noto0648.stations.common.ITicketItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityTicketGate extends TileEntityBase  implements ITickable
{
    public static final int OPEN_INTERVAL = 20 * 3;
    private int openInterval = -1;
    private int lastEntityId;

    @Override
    public void update()
    {
        if(openInterval >= 0)
        {
            openInterval++;
            if(openInterval == OPEN_INTERVAL)
            {
                openInterval = -1;

                Entity e = getWorld().getEntityByID(lastEntityId);
                if(e != null && e instanceof EntityPlayer)
                {
                    //getWorld().playN
                    //getWorld().playAuxSFXAtEntity((EntityPlayer)e, 1003, xCoord, yCoord, zCoord, 0);
                    // 1014
                    getWorld().playEvent((EntityPlayer)e, 1014, getPos(), 0);

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
    public NBTTagCompound writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("interval", openInterval);
        p_145841_1_.setInteger("playerId", lastEntityId);
        return p_145841_1_;
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

    public static ItemStack cutTicket(EntityPlayer ep, ItemStack ticket)
    {
        ItemStack stack = ticket.copy();
        if(ticket.getItem() instanceof ITicketItem)
        {
            return ((ITicketItem)ticket.getItem()).cutTicket(ep, stack);
        }
        else
        {
            //RTMWrapper.INSTANCE.cutTicket(ep, stack);
        }
        return null;
    }
}
