package com.noto0648.stations.packet;

import com.noto0648.stations.container.ContainerMARS;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCaptionMARSTicket implements IMessageHandler<PacketMARSTicket, IMessage>
{

    @Override
    public IMessage onMessage(PacketMARSTicket packet, MessageContext messageContext)
    {
        World world = messageContext.getServerHandler().player.getEntityWorld();
        EntityPlayerMP player = messageContext.getServerHandler().player;

        if(player.openContainer != null && player.openContainer instanceof ContainerMARS)
        {
            ContainerMARS container = (ContainerMARS)player.openContainer;
            container.updateTicket(packet.getStrFrom(), packet.getStrTo());
        }
        return null;
    }
}
