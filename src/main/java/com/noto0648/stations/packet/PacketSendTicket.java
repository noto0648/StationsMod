package com.noto0648.stations.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Noto on 14/08/12.
 */
public class PacketSendTicket implements IMessage
{
    private int ID;

    public PacketSendTicket() {}

    public PacketSendTicket(int id)
    {
        ID = id;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        ID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(ID);
    }

    public int getIDValue()
    {
        return ID;
    }
}
