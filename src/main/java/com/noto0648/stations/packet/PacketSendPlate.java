package com.noto0648.stations.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noto on 14/08/08.
 */
public class PacketSendPlate implements IMessage
{
    public String currentType;
    public String texture;
    public Map<String, String> strMap;
    public int x, y, z;
    public boolean light;

    public PacketSendPlate() {}

    public PacketSendPlate(int _x, int _y, int _z, String type, Map<String, String> strings, String tex, boolean r)
    {
        currentType = type;
        strMap = strings;
        x = _x;
        y = _y;
        z = _z;
        texture = tex;
        light = r;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            x = buf.readInt();
            y = buf.readInt();
            z = buf.readInt();
            light = buf.readBoolean();

            currentType = readString(buf);
            texture = readString(buf);


            int size = buf.readInt();
            strMap = new HashMap<String, String>(size);

            for(int i = 0; i < size; i++)
            {
                String key = readString(buf);
                String value = readString(buf);
                strMap.put(key, value);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        try
        {
            buf.writeInt(x);
            buf.writeInt(y);
            buf.writeInt(z);
            buf.writeBoolean(light);
            writeString(buf, currentType);
            writeString(buf, texture);

            String[] keys = strMap.keySet().toArray(new String[0]);
            buf.writeInt(keys.length);
            for(int i = 0; i < keys.length; i++)
            {
                writeString(buf, keys[i]);
                writeString(buf, strMap.get(keys[i]));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
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

    public String readString(ByteBuf buf)
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

}
