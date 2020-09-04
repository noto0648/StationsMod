package com.noto0648.stations.packet;

import com.noto0648.stations.common.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/19.
 */
public class PacketSendTile implements IMessage
{
    private List<Object> data;
    public int posX, posY, posZ;

    public PacketSendTile() {}


    public PacketSendTile(List<Object> par1, BlockPos pos)
    {
        data = par1;
        posX = pos.getX();
        posY = pos.getY();
        posZ = pos.getZ();
    }

    public PacketSendTile(List<Object> par1, int x, int y, int z)
    {
        data = par1;
        posX = x;
        posY = y;
        posZ = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        posX = buf.readInt();
        posY = buf.readInt();
        posZ = buf.readInt();

        Utils.INSTANCE.analyzePacket(buf, data);
/*
        data = new ArrayList(size);
        for(int i = 0; i < size; i++)
        {
            byte tag = buf.readByte();
            if(tag == 0x00)
            {
                data.add(buf.readChar());
            }
            else if(tag == 0x01)
            {
                data.add(readString(buf));
            }
            else if(tag == 0x02)
            {
                data.add(buf.readFloat());
            }
            else if(tag == 0x03)
            {
                data.add(buf.readDouble());
            }
            else if(tag == 0x04)
            {
                data.add(buf.readByte());
            }
            else if(tag == 0x05)
            {
                data.add(buf.readLong());
            }
            else if(tag == 0x06)
            {
                data.add(buf.readBoolean());
            }
            else
            {
                data.add(buf.readInt());
            }
        }
        */
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        //buf.writeInt(data.size());
        Utils.INSTANCE.writePacket(buf, data);
    }

    public List<Object> getDataList()
    {
        return data;
    }
}
