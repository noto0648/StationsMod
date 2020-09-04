package com.noto0648.stations.packet;

import com.noto0648.stations.common.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;

public class PacketEntityUpdate implements IMessage
{
    private int entityId;
    private List<Object> data;

    public PacketEntityUpdate()
    {
        data = new ArrayList<>();
    }

    public PacketEntityUpdate(Entity entity)
    {
        entityId = entity.getEntityId();
        data = new ArrayList<>();
    }

    public PacketEntityUpdate(Entity entity, List<Object> objects)
    {
        entityId = entity.getEntityId();
        data = objects;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf)
    {
        entityId = byteBuf.readInt();
        Utils.INSTANCE.analyzePacket(byteBuf, data);
    }

    @Override
    public void toBytes(ByteBuf byteBuf)
    {
        byteBuf.writeInt(entityId);
        Utils.INSTANCE.writePacket(byteBuf, data);
    }

    public int getEntityId()
    {
        return entityId;
    }

    public List<Object> getData()
    {
        return data;
    }
}
