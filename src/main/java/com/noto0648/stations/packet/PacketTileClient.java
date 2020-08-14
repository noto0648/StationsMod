package com.noto0648.stations.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class PacketTileClient extends PacketSendTile
{
    public PacketTileClient() {}

    public PacketTileClient(List<Object> par1, BlockPos pos)
    {
        super(par1, pos);
    }

    public PacketTileClient(List<Object> par1, int x, int y, int z)
    {
        super(par1, x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        super.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
    }
}
