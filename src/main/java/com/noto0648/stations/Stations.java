package com.noto0648.stations;

import com.noto0648.stations.blocks.*;
import com.noto0648.stations.client.render.RenderClockData;
import com.noto0648.stations.client.texture.NewFontRenderer;
import com.noto0648.stations.common.CreativeTabsStations;
import com.noto0648.stations.common.ServerProxy;
import com.noto0648.stations.common.StationsGuiHandler;
import com.noto0648.stations.items.*;
import com.noto0648.stations.nameplate.*;
import com.noto0648.stations.packet.*;
import com.noto0648.stations.tile.TileEntityMarkMachine;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Noto on 14/08/04.
 */
@Mod(modid = "stationsMod", name = "Stations Mod", version = "1.7.10")
public class Stations
{
    @Mod.Instance("stationsMod")
    public static Stations instance;

    @SidedProxy(clientSide = "com.noto0648.stations.client.ClientProxy", serverSide = "com.noto0648.stations.common.ServerProxy")
    public static ServerProxy proxy;

    public static final SimpleNetworkWrapper packetDispatcher = NetworkRegistry.INSTANCE.newSimpleChannel("stationsMod");

    public static CreativeTabsStations tab = new CreativeTabsStations();

    public Block stationMaterial;
    public Block stationFence;
    public Block numberPlates;
    public Block pillarBlock;
    public Block namePlate;
    public Block ticketMachine;
    public Block shutter;

    public Item ticket;
    public Item lanSetter;
    public Item torqueWrench;
    public Item clock;
    public Item staff_armor[] = new Item[4];
    public Item ticketCase;

    public int fenceRendererId;
    public int numberPlateId;
    public int pillarRenderId;
    public int namePlateRenderId;
    public int tickerMachineRenderId;

    public boolean isLoadedEconomy;

    public static ItemArmor.ArmorMaterial staffArmorMaterial = EnumHelper.addArmorMaterial("station_staff", 18, new int[]{1, 1, 1, 1}, 0);
    public int armorRenderId = RenderingRegistry.addNewArmourRendererPrefix("station_staff");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.register();

        GameRegistry.registerTileEntity(TileEntityMarkMachine.class, "NotoMod.MarkMachine");

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new StationsGuiHandler());

        packetDispatcher.registerMessage(PacketCaptionMarkData.class, PacketSendMarkData.class, 0, Side.SERVER);
        packetDispatcher.registerMessage(PacketCaptionPlate.class, PacketSendPlate.class, 1, Side.SERVER);
        packetDispatcher.registerMessage(PacketCaptionTicket.class, PacketSendTicket.class, 2, Side.SERVER);

        packetDispatcher.registerMessage(PacketCaptionTile.class, PacketSendTile.class, 32, Side.SERVER);

        NewFontRenderer.INSTANCE.init();
        NamePlateManager.INSTANCE.init();

        isLoadedEconomy = Loader.isModLoaded("mceconomy2");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RenderClockData());

        stationMaterial = new BlockStation();
        GameRegistry.registerBlock(stationMaterial, ItemBlockBase.class, "NotoMod.stationMaterial");

        stationFence = new BlockFence();
        GameRegistry.registerBlock(stationFence, ItemBlockBase.class,"NotoMod.stationFence");


        //numberPlates = new BlockNumberPlate();
        //GameRegistry.registerBlock(numberPlates, ItemBlockBase.class, "NotoMod.numberPlates");

        pillarBlock = new BlockPillar();
        GameRegistry.registerBlock(pillarBlock, ItemBlockBase.class, "NotoMod.blockPillar");

        namePlate = new BlockNamePlate();
        GameRegistry.registerBlock(namePlate, ItemBlockBase.class, "NotoMod.namePlate");

        ticketMachine = new BlockTicketMachine();
        GameRegistry.registerBlock(ticketMachine, ItemBlockBase.class, "NotoMod.ticketMachine");

        shutter = new BlockShutter();
        GameRegistry.registerBlock(shutter, "NotoMod.shutter");

        ticket = new ItemTicket();
        GameRegistry.registerItem(ticket, "NotoMod.ticket");

        lanSetter = new ItemLanSetter();
        GameRegistry.registerItem(lanSetter, "NotoMod.lanSetter");

        torqueWrench = new ItemTorqueWrench();
        GameRegistry.registerItem(torqueWrench, "NotoMod.torqueWrench");

        clock = new ItemClock();
        GameRegistry.registerItem(clock, "NotoMod.clock");

        ticketCase = new ItemTicketCase();
        GameRegistry.registerItem(ticketCase, "NotoMod.ticketCase");

        staff_armor[0] = new ItemStaffArmor(staffArmorMaterial, armorRenderId, 0).setUnlocalizedName("NotoMod.armorStaffHelmet").setTextureName("notomod:staff_helmet");
        staff_armor[1] = new ItemStaffArmor(staffArmorMaterial, armorRenderId, 1).setUnlocalizedName("NotoMod.armorStaffChestPlate").setTextureName("notomod:staff_chest_plate");
        staff_armor[2] = new ItemStaffArmor(staffArmorMaterial, armorRenderId, 2).setUnlocalizedName("NotoMod.armorStaffLeg").setTextureName("notomod:staff_leggings");
        staff_armor[3] = new ItemStaffArmor(staffArmorMaterial, armorRenderId, 3).setUnlocalizedName("NotoMod.armorStaffBoots").setTextureName("notomod:staff_boots");

        GameRegistry.registerItem(staff_armor[0], "NotoMod.armorStaffHelmet");
        GameRegistry.registerItem(staff_armor[1], "NotoMod.armorStaffChestPlate");
        GameRegistry.registerItem(staff_armor[2], "NotoMod.armorStaffLeg");
        GameRegistry.registerItem(staff_armor[3], "NotoMod.armorStaffBoots");

    }
}
