package com.noto0648.stations;
import com.noto0648.stations.common.CreativeTabStations;
import com.noto0648.stations.common.ServerProxy;
import com.noto0648.stations.common.StationsGuiHandler;
import com.noto0648.stations.packet.*;
import com.noto0648.stations.plugins.PluginRTM;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = StationsMod.MOD_ID, name = StationsMod.NAME, version = StationsMod.VERSION)
public class StationsMod
{
    public static final Boolean DEBUG_MODE = false;
    public static final String MOD_ID = "stations_mod";
    public static final String NAME = "Stations Mod";
    public static final String VERSION = "1.12.2.0";

    @Mod.Instance(MOD_ID)
    public static StationsMod instance;

    @SidedProxy(clientSide = "com.noto0648.stations.client.ClientProxy", serverSide = "com.noto0648.stations.common.ServerProxy")
    public static ServerProxy proxy;

    public static final SimpleNetworkWrapper PACKET_DISPATCHER = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    public static CreativeTabStations tab = new CreativeTabStations();

    public static ItemArmor.ArmorMaterial staffArmorMaterial = EnumHelper.addArmorMaterial("STATION_STAFF", MOD_ID + ":station_staff", 18, new int[]{1, 2, 3, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f);


    @Mod.EventHandler
    public void construct(FMLConstructionEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new StationsItems());
        proxy.construct();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new StationsGuiHandler());
        PACKET_DISPATCHER.registerMessage(PacketCaptionMARSTicket.class, PacketMARSTicket.class, 3, Side.SERVER);
        PACKET_DISPATCHER.registerMessage(PacketCaptionDiagramBook.class, PacketDiagramBook.class, 4, Side.SERVER);

        PACKET_DISPATCHER.registerMessage(PacketCaptionTile.class, PacketSendTile.class, 32, Side.SERVER);
        PACKET_DISPATCHER.registerMessage(PacketCaptionTile.class, (Class)PacketTileClient.class, 33, Side.CLIENT);

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();

        if(Loader.isModLoaded("rtm"))
        {
            PluginRTM.INSTANCE.postInit();
        }
    }

}
