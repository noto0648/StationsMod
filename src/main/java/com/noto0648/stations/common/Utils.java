package com.noto0648.stations.common;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.packet.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils
{
    public static final Utils INSTANCE = new Utils();

    private final Random rand = new Random();

    private Utils() {}

    public String posToString(BlockPos pos)
    {
        return "(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";
    }

    public boolean haveWrench(EntityPlayer ep)
    {
        if(StationsMod.DEBUG_MODE)
            return true;

        if(ep.getHeldItemMainhand().getItem() == StationsItems.itemWrench || ep.getHeldItemOffhand().getItem() == StationsItems.itemWrench)
        {
            //return ep.getHeldItemMainhand();
            return true;
        }
        return false;
        //return ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() == Stations.instance.torqueWrench;
    }

    public ItemStack getHasDiagram(EntityPlayer ep)
    {
        if(ep.getHeldItemMainhand().getItem() == StationsItems.itemDiagramBook)
        {
            return ep.getHeldItemMainhand();
        }
        if(ep.getHeldItemOffhand().getItem() == StationsItems.itemDiagramBook)
        {
            return ep.getHeldItemOffhand();
        }
        return ItemStack.EMPTY;
    }

    public boolean haveHammer(EntityPlayer ep)
    {
        if(StationsMod.DEBUG_MODE)
            return true;

        if(ep.getHeldItemMainhand().getItem() == StationsItems.itemHammer || ep.getHeldItemOffhand().getItem() == StationsItems.itemHammer)
        {
            //return ep.getHeldItemMainhand();
            return true;
        }
        return false;
        //return ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() == Stations.instance.torqueWrench;
    }

    public void sendPacket(IPacketSender packet)
    {
        List<Object> result = new ArrayList();
        packet.setSendData(result);
        TileEntity te = packet.getTile();
        if(te != null)
            StationsMod.PACKET_DISPATCHER.sendToServer(new PacketSendTile(result, te.getPos()));
    }

/*
    public void dropItemStack(World world, ItemStack stack, int x, int y, int z)
    {
        ItemStack itemstack = stack;

        if (itemstack != null)
        {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityitem;

            for (float f2 = this.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
            {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.stackSize)
                {
                    j1 = itemstack.stackSize;
                }

                itemstack.stackSize -= j1;
                entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);

                if (itemstack.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                }
            }
        }
    }
    */

    public void sendToPlayers(Entity entity, IEntityPacketSender packetSender)
    {
        List<Object> result = new ArrayList();
        packetSender.setSendData(result);
        sendToPlayers(new PacketEntityUpdate(entity, result), entity.getEntityWorld(), (int)entity.posX, (int)entity.posY, (int)entity.posZ, 192);
    }

    public void sendToServer(Entity entity, IEntityPacketSender packetSender)
    {
        List<Object> result = new ArrayList();
        packetSender.setSendData(result);
        StationsMod.PACKET_DISPATCHER.sendToServer(new PacketEntityUpdate(entity, result));
        //sendToPlayers(, entity.getEntityWorld(), (int)entity.posX, (int)entity.posY, (int)entity.posZ, 192);
    }

    public void sendToPlayers(IPacketSender packetSender)
    {
        List<Object> result = new ArrayList();
        packetSender.setSendData(result);
        TileEntity te = packetSender.getTile();
        if(te != null)
            sendToPlayers(new PacketTileClient(result, te.getPos()), te);
    }

    public void sendToPlayers(IMessage mes, TileEntity te)
    {
        /*
        if(!te.hasWorld())
            return;
        */
        BlockPos pos = te.getPos();
        sendToPlayers(mes, te.getWorld(), pos.getX(), pos.getY(), pos.getZ(), 192);
    }

    public void sendToPlayers(IMessage mes, World world, int x, int y, int z, int maxDistance)
    {
        if ((!world.isRemote) && (mes != null))
        {
            for (int j = 0; j < world.playerEntities.size(); j++)
            {
                EntityPlayerMP player = (EntityPlayerMP)world.playerEntities.get(j);

                if ((Math.abs(player.posX - x) > maxDistance) || (Math.abs(player.posY - y) > maxDistance) || (Math.abs(player.posZ - z) > maxDistance))
                    continue;
                //player.playerNetServerHandler.sendPacket(StationsMod.PACKET_DISPATCHER.getPacketFrom(mes));
//                System.out.println("send!" + player.getNamePlateId());

                StationsMod.PACKET_DISPATCHER.sendTo(mes, player);
            }
        }
    }

    public void writePacket(ByteBuf buf, List<Object> data)
    {
        buf.writeInt(data.size());

        for(int i = 0; i < data.size(); i++)
        {
            Object obj = data.get(i);
            if(obj instanceof Character)
            {
                buf.writeByte(0x00);
                buf.writeChar((Character)obj);
            }
            else if(obj instanceof String)
            {
                buf.writeByte(0x01);
                writeString(buf, (String)obj);
            }
            else if(obj instanceof Float)
            {
                buf.writeByte(0x02);
                buf.writeFloat((Float) obj);
            }
            else if(obj instanceof Double)
            {
                buf.writeByte(0x03);
                buf.writeDouble((Double)obj);
            }
            else if(obj instanceof Byte)
            {
                buf.writeByte(0x04);
                buf.writeByte((Byte)obj);
            }
            else if(obj instanceof Long)
            {
                buf.writeByte(0x05);
                buf.writeLong((Long)obj);
            }
            else if(obj instanceof Boolean)
            {
                buf.writeByte(0x06);
                buf.writeBoolean((Boolean)obj);
            }
            else
            {
                buf.writeByte(0x0F);
                buf.writeInt((Integer)obj);
            }
        }
    }

    public void analyzePacket(ByteBuf buf, List<Object> data)
    {
        int size = buf.readInt();
        if(data == null)
        {
            data = new ArrayList();
        }
        for(int i = 0; i < size; i++)
        {
            byte tag = buf.readByte();
            if(tag == 0x00)
            {
                data.add(buf.readChar());
            }
            else if(tag == 0x01)
            {
                data.add(readString(buf));
            }
            else if(tag == 0x02)
            {
                data.add(buf.readFloat());
            }
            else if(tag == 0x03)
            {
                data.add(buf.readDouble());
            }
            else if(tag == 0x04)
            {
                data.add(buf.readByte());
            }
            else if(tag == 0x05)
            {
                data.add(buf.readLong());
            }
            else if(tag == 0x06)
            {
                data.add(buf.readBoolean());
            }
            else
            {
                data.add(buf.readInt());
            }
        }
    }

    public String readString(ByteBuf buf)
    {
        String str = null;
        try
        {
            int destLength = buf.readInt();
            byte[] destChars = new byte[destLength];
            buf.readBytes(destChars);
            str = new String(destChars, "UTF-8");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public void writeString(ByteBuf buf, String str)
    {
        try
        {
            byte[] typeBytes = str.getBytes("UTF-8");
            buf.writeInt(typeBytes.length);
            buf.writeBytes(typeBytes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
