package com.noto0648.stations.packet;

import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * Created by Noto on 14/08/19.
 */
public interface IPacketSender
{
    public TileEntity getTile();

    public void setSendData(List<Object> list);
}
