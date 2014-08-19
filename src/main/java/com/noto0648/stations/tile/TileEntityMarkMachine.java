package com.noto0648.stations.tile;

import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.common.MinecraftDate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/05.
 */
public class TileEntityMarkMachine extends TileEntity
{
    public List<MarkData> markDataList = new ArrayList();

    public TileEntityMarkMachine()
    {

    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        markDataList = new ArrayList();
        markDataList.clear();
        NBTTagList tags = (NBTTagList)p_145839_1_.getTag("marks");
        for(int i = 0; i < tags.tagCount(); i++)
        {
            NBTTagCompound compound = tags.getCompoundTagAt(i);
            MarkData mkd = new MarkData();
            mkd.readFromNBTTag(compound);
            markDataList.add(mkd);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);

        NBTTagList tags = new NBTTagList();
        for(int i = 0; i < markDataList.size(); i++)
        {
            NBTTagCompound compound = new NBTTagCompound();
            markDataList.get(i).writeToNBTTag(compound);
            tags.appendTag(compound);
        }

        p_145841_1_.setTag("marks", tags);
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

    public void setMarkDataList(List<MarkData> list)
    {
        markDataList.clear();
        for(int i = 0; i < list.size(); i++)
        {
            markDataList.add(list.get(i));
        }
    }


    public List<MarkData> getMarkDataList()
    {
        return markDataList;
    }

    public MarkData[] getStringIndex()
    {
        MinecraftDate md = new MinecraftDate(worldObj.getWorldTime());
        MarkData[] result = new MarkData[2];
        int resultIndex = 0;

        for(int i = 0; i < markDataList.size(); i++)
        {
            MarkData mkd = markDataList.get(i);
            if(mkd.hours >= md.getHours())
            {
                if((mkd.hours > md.getHours()) || (mkd.minutes >= md.getMinutes() && (mkd.hours >= md.getHours())))
                {
                    result[resultIndex] = mkd;
                    resultIndex++;
                }
            }

            if(resultIndex >= 2)
                break;
        }
        return result;
    }

}
