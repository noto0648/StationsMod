package com.noto0648.stations.packet;

import com.noto0648.stations.items.ItemDiagramBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCaptionDiagramBook implements IMessageHandler<PacketDiagramBook, IMessage>
{
    @Override
    public IMessage onMessage(PacketDiagramBook packetDiagramBook, MessageContext messageContext)
    {
        EntityPlayer ep = messageContext.getServerHandler().player;


        ItemStack diagramBook = ItemStack.EMPTY;
        if(ep.getHeldItemMainhand().getItem() instanceof ItemDiagramBook)
        {
            diagramBook = ep.getHeldItemMainhand();
        }
        else
        {
            diagramBook = ep.getHeldItemOffhand();
        }

        if(packetDiagramBook.getItemStack().hasTagCompound())
            diagramBook.setTagCompound(packetDiagramBook.getItemStack().getTagCompound());

        return null;
    }
}
