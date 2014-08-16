package com.noto0648.stations.packet;

import com.noto0648.stations.Stations;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.item.ItemStack;

/**
 * Created by Noto on 14/08/12.
 */
public class PacketCaptionTicket implements IMessageHandler<PacketSendTicket, IMessage>
{
    @Override
    public IMessage onMessage(PacketSendTicket message, MessageContext ctx)
    {
        int id = message.getIDValue();
        int count = ctx.getServerHandler().playerEntity.inventory.getFirstEmptyStack();
        int meta = id == 1 ? 0 : 0;

        if(count != -1)
        {
            ItemStack result = null;
            result = new ItemStack(Stations.instance.ticket, 1, meta);
            ctx.getServerHandler().playerEntity.inventory.setInventorySlotContents(count, result);
        }

        if(id == 1)
        {
            int c = ctx.getServerHandler().playerEntity.inventory.getFirstEmptyStack();
            if(c != -1)
            {
                ctx.getServerHandler().playerEntity.inventory.setInventorySlotContents(c, new ItemStack(Stations.instance.ticket, 1, meta));
            }
        }

        return null;
    }
}
