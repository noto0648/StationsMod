package com.noto0648.stations.common;

import com.noto0648.stations.Stations;
import com.noto0648.stations.packet.IPacketSender;
import com.noto0648.stations.packet.PacketSendTile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Noto on 14/08/14.
 */
public class Utils
{
    public static Utils INSTANCE = new Utils();

    private final Random rand = new Random();

    private Utils() {}

    public boolean haveWrench(EntityPlayer ep)
    {
        return ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() == Stations.instance.torqueWrench;
    }

    public boolean haveTicket(EntityPlayer ep)
    {
        return ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() == Stations.instance.ticket;
    }

    public void sendPacket(IPacketSender packet)
    {
        List<Object> result = new ArrayList();
        packet.setSendData(result);
        TileEntity te = packet.getTile();

        Stations.packetDispatcher.sendToServer(new PacketSendTile(result, te.xCoord, te.yCoord, te.zCoord));
    }


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
}
