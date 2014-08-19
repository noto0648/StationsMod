package com.noto0648.stations.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/19.
 */
public class PacketCaptionTile implements IMessageHandler<PacketSendTile, IMessage>
{
    @Override
    public IMessage onMessage(PacketSendTile message, MessageContext ctx)
    {
        int x = message.posX;
        int y = message.posY;
        int z = message.posZ;

        World world = ctx.getServerHandler().playerEntity.worldObj;
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof IPacketReceiver)
        {
            ((IPacketReceiver) te).receive(message.getDataList());
        }
        return null;
    }
}
