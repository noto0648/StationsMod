package com.noto0648.stations.packet;

import com.noto0648.stations.common.MarkData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/06.
 */
public class PacketSendMarkData implements IMessage
{
    private List<MarkData> markList;
    public int posX, posY, posZ;

    public PacketSendMarkData() {}

    public PacketSendMarkData(List<MarkData> list, int x, int y, int z)
    {
        markList = list;
        posX = x;
        posY = y;
        posZ = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            posX = buf.readInt();
            posY = buf.readInt();
            posZ = buf.readInt();

            int size = buf.readInt();
            markList = new ArrayList(size);
            markList.clear();
            for(int i = 0; i < size; i++)
            {
                int hours = buf.readInt();
                int minutes = buf.readInt();

                int typeLength = buf.readInt();
                byte[] typeChars = new byte[typeLength];
                buf.readBytes(typeChars);

                int destLength = buf.readInt();
                byte[] destChars = new byte[destLength];
                buf.readBytes(destChars);


                MarkData md = new MarkData();
                md.hours = hours;
                md.minutes = minutes;
                md.dest = new String(destChars, "UTF-8");
                md.type = new String(typeChars, "UTF-8");
                md.destColor = buf.readInt();
                md.timeColor = buf.readInt();
                md.typeColor = buf.readInt();

                markList.add(md);
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
            buf.writeInt(posX);
            buf.writeInt(posY);
            buf.writeInt(posZ);

            buf.writeInt(markList.size());

            for(int i = 0; i < markList.size(); i++)
            {
                MarkData mkd = markList.get(i);
                buf.writeInt(mkd.hours);
                buf.writeInt(mkd.minutes);


                byte[] typeBytes = mkd.type.getBytes("UTF-8");
                buf.writeInt(typeBytes.length);
                buf.writeBytes(typeBytes);

                byte[] destBytes = mkd.dest.getBytes("UTF-8");
                buf.writeInt(destBytes.length);
                buf.writeBytes(destBytes);

                buf.writeInt(mkd.destColor);
                buf.writeInt(mkd.timeColor);
                buf.writeInt(mkd.typeColor);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<MarkData> getLists()
    {
        return markList;
    }

}
