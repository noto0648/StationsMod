package com.noto0648.stations.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Noto on 14/08/07.
 */
public class TileEntityMark extends TileEntity
{
    private boolean parentRegistered;

    private int parentX;
    private int parentY;
    private int parentZ;

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        parentX = p_145839_1_.getInteger("parentX");
        parentY = p_145839_1_.getInteger("parentY");
        parentZ = p_145839_1_.getInteger("parentZ");
        parentRegistered = p_145839_1_.getBoolean("parReg");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("parentX", parentX);
        p_145841_1_.setInteger("parentY", parentY);
        p_145841_1_.setInteger("parentZ", parentZ);
        p_145841_1_.setBoolean("parReg", parentRegistered);
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

    public int getParentX()
    {
        return parentX;
    }

    public int getParentY()
    {
        return parentY;
    }

    public int getParentZ()
    {
        return parentZ;
    }

    public boolean isRegistered()
    {
        return parentRegistered;
    }

    public boolean setParent(int x, int y, int z)
    {
        if(getBlockMetadata() >= 14)
        {
            parentX = x;
            parentY = y;
            parentZ = z;
            parentRegistered = true;
            return true;
        }
        return false;
    }
}
