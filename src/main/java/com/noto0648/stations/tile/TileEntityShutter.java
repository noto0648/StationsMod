package com.noto0648.stations.tile;

import com.noto0648.stations.Stations;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Noto on 14/08/12.
 */
public class TileEntityShutter extends TileEntity
{
    private int count;
    private boolean undoFlag;
    private boolean finish;

    @Override
    public void updateEntity()
    {
        count++;
        if(count == 5)
        {
            count = 0;
            if(!undoFlag && !finish)
            {
                if(getBlockMetadata() % 8 == 0)
                {
                    Block bottomBlock = getWorldObj().getBlock(xCoord, yCoord - 1, zCoord);
                    finish = true;
                    if(bottomBlock == Blocks.air)
                    {
                        if(getBlockMetadata() < 8)
                            getWorldObj().setBlock(xCoord, yCoord - 1, zCoord, Stations.instance.shutter, 7, 2);
                        else
                            getWorldObj().setBlock(xCoord, yCoord - 1, zCoord, Stations.instance.shutter, 15, 2);
                        return;
                    }
                }
                else
                {
                    getWorldObj().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() - 1, 2);
                }
            }
            else if(undoFlag)
            {
                if(getBlockMetadata() % 8 == 7)
                {
                    Block bottomBlock = getWorldObj().getBlock(xCoord, yCoord + 1, zCoord);
                    TileEntity te = getWorldObj().getTileEntity(xCoord, yCoord + 1, zCoord);
                    if(bottomBlock == Stations.instance.shutter && te != null && te instanceof TileEntityShutter)
                    {
                        ((TileEntityShutter)te).setUndoFlag(true);
                    }
                    getWorldObj().setBlockToAir(xCoord, yCoord, zCoord);
                    getWorldObj().removeTileEntity(xCoord, yCoord, zCoord);
                }
                else
                {
                    getWorldObj().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() + 1, 2);
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        count = p_145839_1_.getInteger("counter");
        undoFlag = p_145839_1_.getBoolean("undo");
        finish = p_145839_1_.getBoolean("fin");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("counter", count);
        p_145841_1_.setBoolean("undo", undoFlag);
        p_145841_1_.setBoolean("fin", finish);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    public void setUndoFlag(boolean par1)
    {
        undoFlag = par1;
        if(!undoFlag)
        {
            finish = false;
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }
}
