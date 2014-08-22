package com.noto0648.stations.client;

import com.noto0648.stations.*;
import com.noto0648.stations.client.render.*;
import com.noto0648.stations.common.ServerProxy;
import com.noto0648.stations.tile.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Noto on 14/08/04.
 */
public class ClientProxy extends ServerProxy
{
    @Override
    public void register()
    {
        Stations.instance.fenceRendererId = RenderingRegistry.getNextAvailableRenderId();
        Stations.instance.numberPlateId = RenderingRegistry.getNextAvailableRenderId();
        Stations.instance.pillarRenderId = RenderingRegistry.getNextAvailableRenderId();
        Stations.instance.namePlateRenderId = RenderingRegistry.getNextAvailableRenderId();
        Stations.instance.tickerMachineRenderId = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)Stations.instance.stationFence);
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)Stations.instance.pillarBlock);
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)Stations.instance.namePlate);
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)Stations.instance.ticketMachine);

        ClientRegistry.registerTileEntity(TileEntityFence.class, "NotoMod.fence", new TileEntityFenceRender());
        ClientRegistry.registerTileEntity(TileEntityMark.class, "NotoMod.mark", new TileEntityMarkRender());
        ClientRegistry.registerTileEntity(TileEntityNamePlate.class, "NotoMod.namePlate", new TileEntityNamePlateRender());
        ClientRegistry.registerTileEntity(TileEntityTicketMachine.class, "NotoMod.ticketMachine", new TileEntityTicketMachineRender());
        ClientRegistry.registerTileEntity(TileEntityShutter.class, "NotoMod.shutter", new TileEntityShutterRender());
        ClientRegistry.registerTileEntity(TileEntityNumberPlate.class, "NotoMod.numberPlates", new TileEntityNumberPlateRender());
        ClientRegistry.registerTileEntity(TileEntityTicketGate.class, "NotoMod.ticketGate", new TileEntityTicketGateRender());
    }

    @Override
    public void preInit()
    {
        MinecraftForge.EVENT_BUS.register(new RenderClockData());
        Stations.instance.armorRenderId = RenderingRegistry.addNewArmourRendererPrefix("station_staff");
    }
}
