package com.noto0648.stations.tile;

import com.noto0648.stations.packet.IPacketReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * Created by Noto on 14/08/04.
 */
public class TileEntityNumberPlate extends TileEntity implements IPacketReceiver
{
    private String drawStr = "";

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        drawStr = p_145839_1_.getString("number");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setString("number", drawStr);
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

    public String getDrawStr()
    {
        return drawStr;
    }

    public void setDrawStr(String par1)
    {
        drawStr = par1;
    }

    @Override
    public void receive(List<Object> data)
    {
        if((Byte)data.get(0) == 0x03)
        {
            drawStr = (String)data.get(1);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
}
