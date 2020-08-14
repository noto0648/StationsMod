package com.noto0648.stations.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity
{
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
        return new SPacketUpdateTileEntity(this.pos, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound tag)
    {
        readFromNBT(tag);
    }

}
