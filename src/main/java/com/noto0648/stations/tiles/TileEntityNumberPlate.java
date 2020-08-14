package com.noto0648.stations.tiles;

import com.noto0648.stations.common.Utils;
import com.noto0648.stations.packet.IPacketReceiver;
import com.noto0648.stations.packet.IPacketSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class TileEntityNumberPlate extends TileEntityBase implements IPacketReceiver, IPacketSender
{
    private String drawStr = "1";
    private String colorCode = "ffffff";
    private String strColorCode = "000000";

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        drawStr = p_145839_1_.getString("number");
        if(p_145839_1_.hasKey("colorCode"))
        {
            colorCode = p_145839_1_.getString("colorCode");
        }
        if(p_145839_1_.hasKey("strColorCode"))
        {
            strColorCode = p_145839_1_.getString("strColorCode");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setString("number", drawStr);
        p_145841_1_.setString("colorCode", colorCode);
        p_145841_1_.setString("strColorCode", strColorCode);
        return p_145841_1_;
    }

    public String getDrawStr()
    {
        return drawStr;
    }

    public void setDrawStr(String par1)
    {
        drawStr = par1;
    }

    public String getColorCode()
    {
        return colorCode;
    }

    public void setColorCode(String par1)
    {
        colorCode = par1;
    }

    public String getStrColorCode()
    {
        return strColorCode;
    }

    public void setStrColorCode(String par1)
    {
        strColorCode = par1;
    }

    @Override
    public void receive(List<Object> data)
    {
        if((Byte)data.get(0) == 0x03)
        {
            drawStr = (String)data.get(1);
            colorCode = (String)data.get(2);
            strColorCode = (String)data.get(3);
            //getWorld().markBlockForUpdate(xCoord, yCoord, zCoord);

            if(!getWorld().isRemote)
            {
                Utils.INSTANCE.sendToPlayers(this);
            }
        }
    }

    @Override
    public TileEntity getTile()
    {
        return this;
    }

    @Override
    public void setSendData(List<Object> list)
    {
        list.add((byte)0x03);
        list.add(drawStr);
        list.add(colorCode);
        list.add(strColorCode);
    }
}
