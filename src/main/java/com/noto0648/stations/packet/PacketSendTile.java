package com.noto0648.stations.packet;

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
        int size = buf.readInt();

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
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        buf.writeInt(data.size());

        for(int i = 0; i < data.size(); i++)
        {
            Object obj = data.get(i);
            if(obj instanceof Character)
            {
                buf.writeByte(0x00);
                buf.writeChar((Character)obj);
            }
            else if(obj instanceof String)
            {
                buf.writeByte(0x01);
                writeString(buf, (String)obj);
            }
            else if(obj instanceof Float)
            {
                buf.writeByte(0x02);
                buf.writeFloat((Float) obj);
            }
            else if(obj instanceof Double)
            {
                buf.writeByte(0x03);
                buf.writeDouble((Double)obj);
            }
            else if(obj instanceof Byte)
            {
                buf.writeByte(0x04);
                buf.writeByte((Byte)obj);
            }
            else if(obj instanceof Long)
            {
                buf.writeByte(0x05);
                buf.writeLong((Long)obj);
            }
            else if(obj instanceof Boolean)
            {
                buf.writeByte(0x06);
                buf.writeBoolean((Boolean)obj);
            }
            else
            {
                buf.writeByte(0x0F);
                buf.writeInt((Integer)obj);
            }
        }
    }

    public void writeString(ByteBuf buf, String str)
    {
        try
        {
            byte[] typeBytes = str.getBytes("UTF-8");
            buf.writeInt(typeBytes.length);
            buf.writeBytes(typeBytes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private String readString(ByteBuf buf)
    {
        String str = null;
        try
        {
            int destLength = buf.readInt();
            byte[] destChars = new byte[destLength];
            buf.readBytes(destChars);
            str = new String(destChars, "UTF-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public List<Object> getDataList()
    {
        return data;
    }
}
