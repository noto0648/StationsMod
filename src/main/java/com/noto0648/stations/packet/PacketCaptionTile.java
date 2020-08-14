package com.noto0648.stations.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketCaptionTile implements IMessageHandler<PacketSendTile, IMessage>
{
    @Override
    public IMessage onMessage(PacketSendTile message, MessageContext ctx)
    {
        int x = message.posX;
        int y = message.posY;
        int z = message.posZ;

        World world = null;
        if(ctx.side == Side.CLIENT)
        {
            world = Minecraft.getMinecraft().world;
        }
        else
        {
            world = ctx.getServerHandler().player.world;
        }
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        //System.out.println("get receive package + te + " + (te == null));

        if(te != null && te instanceof IPacketReceiver)
        {
            ((IPacketReceiver) te).receive(message.getDataList());
        }
        return null;
    }
}
