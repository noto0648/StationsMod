package com.noto0648.stations.common;

import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.tile.*;
import cpw.mods.fml.common.registry.GameRegistry;

import java.io.InputStream;

/**
 * Created by Noto on 14/08/04.
 */
public class ServerProxy
{
    public void register()
    {
        GameRegistry.registerTileEntity(TileEntityFence.class, "NotoMod.fence");
        GameRegistry.registerTileEntity(TileEntityMark.class, "NotoMod.mark");
        GameRegistry.registerTileEntity(TileEntityNumberPlate.class, "NotoMod.numberPlates");
        GameRegistry.registerTileEntity(TileEntityNamePlate.class, "NotoMod.namePlate");
        GameRegistry.registerTileEntity(TileEntityTicketMachine.class, "NotoMod.ticketMachine");
        GameRegistry.registerTileEntity(TileEntityShutter.class, "NotoMod.shutter");
        GameRegistry.registerTileEntity(TileEntityTicketGate.class, "NotoMod.ticketGate");
        GameRegistry.registerTileEntity(TileEntityRailToy.class, "NotoMod.railToy");
        GameRegistry.registerTileEntity(TileEntityRailToyCorner.class, "NotoMod.railToyCorner");
    }

    public void preInit() {}

    public boolean readTexture(String key, InputStream stream) { return false; }

    public boolean readTexture(String path) { return false; }
}
