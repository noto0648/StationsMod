package com.noto0648.stations;

import com.noto0648.stations.blocks.*;
import com.noto0648.stations.items.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StationsItems
{
    public static Item itemTicket;
    public static Item itemTicketCase;
    public static Item itemWrench;
    public static Item itemPocketWatch;
    public static Item itemStaffHelmet;
    public static Item itemStaffSuit;
    public static Item itemStaffPants;
    public static Item itemStaffBoots;
    public static Item itemHandFlag;
    public static Item itemDiagramBook;
    public static Item itemSealSensor;
    public static Item itemTicketPunch;
    public static Item itemSlideDoor;

    public static Block blockMaterial1;
    public static Block blockFence;
    public static Block blockPillar;
    public static Block blockSeal;
    public static Block blockTicketMachine;
    public static Block blockNamePlate;
    public static Block blockSlideDoor;
    public static Block blockShutter;

    static
    {
        blockMaterial1 = new BlockStationMaterial().setUnlocalizedName("notomod.station_material").setRegistryName(StationsMod.MOD_ID, "material_block");
        blockFence = new BlockStationFence().setUnlocalizedName("notomod.station_fence").setRegistryName(StationsMod.MOD_ID, "station_fence");
        blockPillar = new BlockPillar().setUnlocalizedName("notomod.station_pillar").setRegistryName(StationsMod.MOD_ID, "station_pillar");
        blockSeal = new BlockStringSeal().setUnlocalizedName("notomod.string_seal").setRegistryName(StationsMod.MOD_ID, "string_seal");
        blockTicketMachine = new BlockTicketMachine().setUnlocalizedName("notomod.ticket_machine").setRegistryName(StationsMod.MOD_ID, "ticket_machine");
        blockNamePlate = new BlockNamePlate().setUnlocalizedName("notomod.name_plate").setRegistryName(StationsMod.MOD_ID, "name_plate");
        blockSlideDoor = new BlockSlideDoor().setUnlocalizedName("notomod.slide_door").setRegistryName(StationsMod.MOD_ID, "slide_door");
        blockShutter = new BlockShutter().setUnlocalizedName("notomod.shutter").setRegistryName(StationsMod.MOD_ID, "shutter");


        itemTicket = new ItemTicket().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "ticket"));
        itemTicketCase = new ItemTicketCase().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "ticket_case"));

        itemWrench = new ItemTorqueWrench().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "torque_wrench"));
        itemPocketWatch = new ItemPocketWatch().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "digital_clock"));

        itemStaffHelmet = new ItemStaffArmor(0, EntityEquipmentSlot.HEAD).setUnlocalizedName("notomod.staff_hat").setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "staff_hat"));
        itemStaffSuit = new ItemStaffArmor(0, EntityEquipmentSlot.CHEST).setUnlocalizedName("notomod.staff_suit").setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "staff_suit"));
        itemStaffPants = new ItemStaffArmor(0, EntityEquipmentSlot.LEGS).setUnlocalizedName("notomod.staff_pants").setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "staff_pants"));
        itemStaffBoots = new ItemStaffArmor(0, EntityEquipmentSlot.FEET).setUnlocalizedName("notomod.staff_boots").setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "staff_boots"));

        itemHandFlag = new ItemHandFlag().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "hand_flag"));
        itemDiagramBook = new ItemDiagramBook().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "diagram_book"));
        itemSealSensor = new ItemSealSensor().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "seal_sensor"));
        itemTicketPunch = new ItemTicketPunch().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "ticket_punch"));

        itemSlideDoor = new ItemSlideDoor().setRegistryName(new ResourceLocation(StationsMod.MOD_ID, "slide_door"));

    }

    @SubscribeEvent
    public void registerItem(RegistryEvent.Register<Item> evt)
    {
        evt.getRegistry().register(itemTicket);
        evt.getRegistry().register(itemTicketCase);
        evt.getRegistry().register(itemWrench);
        evt.getRegistry().register(itemPocketWatch);
        evt.getRegistry().register(itemStaffHelmet);
        evt.getRegistry().register(itemStaffSuit);
        evt.getRegistry().register(itemStaffPants);
        evt.getRegistry().register(itemStaffBoots);
        evt.getRegistry().register(itemHandFlag);
        evt.getRegistry().register(itemDiagramBook);
        evt.getRegistry().register(itemSealSensor);
        evt.getRegistry().register(itemTicketPunch);
        evt.getRegistry().register(itemSlideDoor);

        evt.getRegistry().register(new ItemBlockBase(blockMaterial1).setRegistryName(StationsMod.MOD_ID, "material_block"));
        evt.getRegistry().register(new ItemBlockBase(blockFence).setRegistryName(StationsMod.MOD_ID, "station_fence"));
        evt.getRegistry().register(new ItemBlockBase(blockPillar).setRegistryName(StationsMod.MOD_ID, "station_pillar"));
        evt.getRegistry().register(new ItemBlock(blockSeal).setRegistryName(StationsMod.MOD_ID, "string_seal"));
        evt.getRegistry().register(new ItemBlockBase(blockTicketMachine).setRegistryName(StationsMod.MOD_ID, "ticket_machine"));
        evt.getRegistry().register(new ItemBlockBase(blockNamePlate).setRegistryName(StationsMod.MOD_ID, "name_plate"));
        evt.getRegistry().register(new ItemBlock(blockShutter).setRegistryName(StationsMod.MOD_ID, "block_shutter"));
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> evt)
    {
        evt.getRegistry().register(blockMaterial1);
        evt.getRegistry().register(blockFence);
        evt.getRegistry().register(blockPillar);
        evt.getRegistry().register(blockSeal);
        evt.getRegistry().register(blockTicketMachine);
        evt.getRegistry().register(blockNamePlate);
        evt.getRegistry().register(blockSlideDoor);
        evt.getRegistry().register(blockShutter);
    }
}
