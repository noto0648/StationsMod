package com.noto0648.stations.common;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.tiles.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy
{
    public void construct() {}

    public void preInit() {}

    public void init()
    {
        GameRegistry.registerTileEntity(TileEntityNumberPlate.class, new ResourceLocation(StationsMod.MOD_ID, "number_plate"));
        GameRegistry.registerTileEntity(TileEntityNamePlate.class, new ResourceLocation(StationsMod.MOD_ID, "name_plate"));
        GameRegistry.registerTileEntity(TileEntitySlideDoor.class, new ResourceLocation(StationsMod.MOD_ID, "slide_door"));
        GameRegistry.registerTileEntity(TileEntityStringSeal.class, new ResourceLocation(StationsMod.MOD_ID, "string_seal"));
        GameRegistry.registerTileEntity(TileEntityDeparturePlate.class, new ResourceLocation(StationsMod.MOD_ID, "departure_plate"));
        GameRegistry.registerTileEntity(TileEntityShutter.class, new ResourceLocation(StationsMod.MOD_ID, "shutter"));
    }

    public void postInit()
    {
    }

}
