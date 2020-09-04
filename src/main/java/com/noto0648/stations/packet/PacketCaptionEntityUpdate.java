package com.noto0648.stations.packet;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.ModLog;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCaptionEntityUpdate implements IMessageHandler<PacketEntityUpdate, IMessage>
{
    @Override
    public IMessage onMessage(PacketEntityUpdate packetEntityUpdate, MessageContext messageContext)
    {
        final World world = StationsMod.proxy.getWorld(messageContext);
        final Entity entity = world.getEntityByID(packetEntityUpdate.getEntityId());
        if(entity != null && entity instanceof IPacketReceiver)
        {
            ((IPacketReceiver)entity).receive(packetEntityUpdate.getData());
        }
        else
        {
            ModLog.getLog().error("Entity packet is lost at {}", packetEntityUpdate.getEntityId());
        }
        return null;
    }
}
