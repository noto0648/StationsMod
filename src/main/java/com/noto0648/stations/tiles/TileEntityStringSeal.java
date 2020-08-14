package com.noto0648.stations.tiles;

import com.noto0648.stations.common.Utils;
import com.noto0648.stations.packet.IPacketReceiver;
import com.noto0648.stations.packet.IPacketSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TileEntityStringSeal extends TileEntityBase implements IPacketReceiver, IPacketSender
{
    private List<String> strings;
    private List<String> colorCodes;
    private int rotation;

    public TileEntityStringSeal()
    {
        strings = new ArrayList<>();
        colorCodes = new ArrayList<>();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if(nbt.hasKey("strings"))
        {
            NBTTagList strList = nbt.getTagList("strings", Constants.NBT.TAG_STRING);
            List<String> strCopy = new ArrayList<>(strList.tagCount());
            for(int i = 0; i < strList.tagCount(); i++)
                strCopy.add(((NBTTagString)strList.get(i)).getString());

            NBTTagList colList = nbt.getTagList("colorCodes", Constants.NBT.TAG_STRING);
            List<String> colCopy = new ArrayList<>(colList.tagCount());
            for(int i = 0; i < colList.tagCount(); i++)
                colCopy.add(((NBTTagString)colList.get(i)).getString());

            strings = strCopy;
            colorCodes = colCopy;
        }

        if(nbt.hasKey("rotation"))
        {
            rotation = nbt.getByte("rotation");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setByte("rotation", (byte)rotation);
        NBTTagList strList = nbt.getTagList("strings", Constants.NBT.TAG_STRING);
        for(String val : strings)
            strList.appendTag(new NBTTagString(val));
        NBTTagList colList = nbt.getTagList("colorCodes", Constants.NBT.TAG_STRING);
        for(String val : colorCodes)
            colList.appendTag(new NBTTagString(val));
        nbt.setTag("strings", strList);
        nbt.setTag("colorCodes", colList);
        return nbt;
    }

    public int getRotation()
    {
        return rotation;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public List<String> getStrings()
    {
        return strings;
    }

    public List<String> getColorCodes()
    {
        return colorCodes;
    }

    @Override
    public void receive(List<Object> data)
    {
        List<String> strs = new ArrayList<>();
        List<String> cols = new ArrayList<>();
        int count = (byte)data.get(1);
        int j = 2;
        for(int i = 0; i < count; i++)
        {
            cols.add((String)data.get(j));
            j++;
            strs.add((String)data.get(j));
            j++;
        }
        strings = strs;
        colorCodes = cols;

        Utils.INSTANCE.sendToPlayers(this);
    }

    @Override
    public TileEntity getTile()
    {
        return this;
    }

    @Override
    public void setSendData(List<Object> list)
    {
        list.add((byte)0x05);
        list.add((byte)strings.size());
        for(int i = 0; i < strings.size(); i++)
        {
            list.add(colorCodes.get(i));
            list.add(strings.get(i));
        }
    }
}
