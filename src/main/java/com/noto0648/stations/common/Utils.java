package com.noto0648.stations.common;

import com.noto0648.stations.Stations;
import com.noto0648.stations.packet.IPacketSender;
import com.noto0648.stations.packet.PacketSendTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/14.
 */
public class Utils
{
    public static Utils INSTANCE = new Utils();
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


}
