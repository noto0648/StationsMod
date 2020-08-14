package com.noto0648.stations.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketDiagramBook implements IMessage
{
    private ItemStack itemStack;

    public PacketDiagramBook(){}

    public PacketDiagramBook(ItemStack book)
    {
        itemStack = book;
    }
    @Override
    public void fromBytes(ByteBuf byteBuf)
    {
        try
        {
            PacketBuffer pb = new PacketBuffer(byteBuf);
            itemStack = pb.readItemStack();
        }
        catch (Exception e)
        {
            itemStack = ItemStack.EMPTY;
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf)
    {
        PacketBuffer pb = new PacketBuffer(byteBuf);
        pb.writeItemStack(itemStack);
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }
}
