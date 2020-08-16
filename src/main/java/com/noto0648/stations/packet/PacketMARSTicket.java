package com.noto0648.stations.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMARSTicket implements IMessage
{
    private String strFrom;
    private String strTo;

    public PacketMARSTicket(){}

    public PacketMARSTicket(String str1, String str2)
    {
        strFrom = str1;
        strTo = str2;
    }

    @Override
    public void toBytes(ByteBuf byteBuf)
    {
        PacketBuffer buf = new PacketBuffer(byteBuf);
        buf.writeString(strFrom);
        buf.writeString(strTo);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf)
    {
        PacketBuffer buf = new PacketBuffer(byteBuf);
        strFrom = buf.readString(32);
        strTo = buf.readString(32);
    }

    public String getStrFrom()
    {
        return strFrom;
    }

    public String getStrTo()
    {
        return strTo;
    }

}
