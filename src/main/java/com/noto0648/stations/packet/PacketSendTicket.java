package com.noto0648.stations.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Noto on 14/08/12.
 */
public class PacketSendTicket implements IMessage
{
    private int ID;
    public int x, y, z;

    public PacketSendTicket() {}

    public PacketSendTicket(int id, int x, int y, int z)
    {
        ID = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        ID = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(ID);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public int getIDValue()
    {
        return ID;
    }
}
