package com.noto0648.stations.packet;

import com.noto0648.stations.tile.TileEntityMarkMachine;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/07.
 */
public class PacketCaptionMarkData implements IMessageHandler<PacketSendMarkData, IMessage>
{

    @Override
    public IMessage onMessage(PacketSendMarkData message, MessageContext ctx)
    {
        int x = message.posX;
        int y = message.posY;
        int z = message.posZ;

        World world = ctx.getServerHandler().playerEntity.worldObj;
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileEntityMarkMachine)
        {
            ((TileEntityMarkMachine)te).setMarkDataList(message.getLists());
        }

        return null;
    }
}
