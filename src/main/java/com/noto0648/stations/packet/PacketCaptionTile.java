package com.noto0648.stations.packet;

import com.noto0648.stations.StationsMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCaptionTile implements IMessageHandler<PacketSendTile, IMessage>
{
    @Override
    public IMessage onMessage(PacketSendTile message, MessageContext ctx)
    {
        final int x = message.posX;
        final int y = message.posY;
        final int z = message.posZ;

        final World world = StationsMod.proxy.getWorld(ctx);
        final TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        if(te != null && te instanceof IPacketReceiver)
        {
            ((IPacketReceiver) te).receive(message.getDataList());
        }
        return null;
    }
}
