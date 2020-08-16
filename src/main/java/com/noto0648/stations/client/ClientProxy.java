package com.noto0648.stations.client;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.blocks.BlockPillar;
import com.noto0648.stations.client.render.*;
import com.noto0648.stations.client.texture.NewFontRenderer;
import com.noto0648.stations.common.ServerProxy;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.tiles.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy
{
    @Override
    public void construct()
    {
        MinecraftForge.EVENT_BUS.register(new RenderPocketWatch());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void preInit()
    {
    }


    @Override
    public void init()
    {

        ClientRegistry.registerTileEntity(TileEntityNumberPlate.class, StationsMod.MOD_ID + ":number_plate", new TileEntityNumberPlateRenderer());
        ClientRegistry.registerTileEntity(TileEntityNamePlate.class, StationsMod.MOD_ID + ":name_plate", new TileEntityNamePlateRenderer());
        ClientRegistry.registerTileEntity(TileEntitySlideDoor.class, StationsMod.MOD_ID + ":slide_door", new TileEntitySlideDoorRenderer());
        ClientRegistry.registerTileEntity(TileEntityStringSeal.class, StationsMod.MOD_ID + ":string_seal", new TileEntityStringSealRenderer());
        ClientRegistry.registerTileEntity(TileEntityDeparturePlate.class, StationsMod.MOD_ID + ":departure_plate", new TileEntityDeparturePlateRenderer());

        GameRegistry.registerTileEntity(TileEntityShutter.class, new ResourceLocation(StationsMod.MOD_ID, "shutter"));
        GameRegistry.registerTileEntity(TileEntityTicketGate.class, new ResourceLocation(StationsMod.MOD_ID, "ticket_gate"));
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent evt)
    {
        OBJLoader.INSTANCE.addDomain(StationsMod.MOD_ID);

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicket, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicket, 1, new ModelResourceLocation(StationsMod.MOD_ID + ":used_ticket", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicket, 2, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket_ic", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicket, 3, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket_mars", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicket, 4, new ModelResourceLocation(StationsMod.MOD_ID + ":used_mars_ticket", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicket, 5, new ModelResourceLocation(StationsMod.MOD_ID + ":replenishing_ticket", "inventory"));


        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicketCase, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket_case", "inventory"));

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemWrench, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":torque_wrench", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemHammer, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":hammer", "inventory"));

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemPocketWatch, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":digital_clock", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemPocketWatch, 1, new ModelResourceLocation(StationsMod.MOD_ID + ":analog_clock", "inventory"));

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemStaffHelmet, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":staff_hat", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemStaffSuit, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":staff_suit", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemStaffPants, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":staff_pants", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemStaffBoots, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":staff_boots", "inventory"));

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemHandFlag, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":handflag_red", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemHandFlag, 1, new ModelResourceLocation(StationsMod.MOD_ID + ":handflag_white", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemHandFlag, 2, new ModelResourceLocation(StationsMod.MOD_ID + ":handflag_green", "inventory"));

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemDiagramBook, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":diagram_book", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemSealSensor, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":seal_sensor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(StationsItems.itemTicketPunch, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket_punch", "inventory"));

        ModelLoader.setCustomModelResourceLocation(StationsItems.itemSlideDoor, 0, new ModelResourceLocation(StationsMod.MOD_ID + ":slide_door", "inventory"));

        //blocks
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockMaterial1), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":whitebrick_platform", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockMaterial1), 9, new ModelResourceLocation(StationsMod.MOD_ID + ":asphalt", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockMaterial1), 1, new ModelResourceLocation(StationsMod.MOD_ID + ":gray_platform", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockMaterial1), 5, new ModelResourceLocation(StationsMod.MOD_ID + ":lined_whitebrick_platform", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockMaterial1), 10, new ModelResourceLocation(StationsMod.MOD_ID + ":old_platform_brick", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockMaterial1), 14, new ModelResourceLocation(StationsMod.MOD_ID + ":shutter_core", "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockFence), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":platform_fence", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockFence), 4, new ModelResourceLocation(StationsMod.MOD_ID + ":rail_fence", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockFence), 14, new ModelResourceLocation(StationsMod.MOD_ID + ":platform_display", "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockSeal), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":string_seal", "normal"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockPillar), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":pillar_white", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockPillar), 2, new ModelResourceLocation(StationsMod.MOD_ID + ":yellow_line", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockPillar), 6, new ModelResourceLocation(StationsMod.MOD_ID + ":pillar_black", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockPillar), 7, new ModelResourceLocation(StationsMod.MOD_ID + ":number_plate", "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockPillar), 9, new ModelResourceLocation(StationsMod.MOD_ID + ":white_line", "inventory"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockTicketMachine), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket_machine", "normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockTicketMachine), 4, new ModelResourceLocation(StationsMod.MOD_ID + ":ticket_gate", "normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockTicketMachine), 8, new ModelResourceLocation(StationsMod.MOD_ID + ":staff_ticket_machine", "normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockTicketMachine), 12, new ModelResourceLocation(StationsMod.MOD_ID + ":ic_ticket_gate", "normal"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockNamePlate), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":name_plate", "normal"));

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(StationsItems.blockShutter), 0, new ModelResourceLocation(StationsMod.MOD_ID + ":block_shutter", "normal"));

    }


    @Override
    public void postInit()
    {
        NewFontRenderer.INSTANCE.init();
        NamePlateManager.INSTANCE.init();
    }

    @SubscribeEvent
    public void registerBakedModels(ModelBakeEvent evt)
    {
        NamePlateManager.INSTANCE.loadRetexturedModels(evt.getModelRegistry());

        /*
        try
        {
            OBJModel numberPlateModel = (OBJModel)ModelLoaderRegistry.getModel(new ResourceLocation(StationsMod.MOD_ID + ":/block/ticket_gate.obj"));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        */

        final EnumFacing[] dirs = {EnumFacing.NORTH ,EnumFacing.EAST ,EnumFacing.WEST, EnumFacing.SOUTH};
        for(int i = 0; i < dirs.length; i++)
        {
            final ModelResourceLocation mrl = new ModelResourceLocation(StationsMod.MOD_ID + ":station_pillar", "facing=" + dirs[i].getName() + ",variant=number_plate");
            final OBJModel.OBJBakedModel model = (OBJModel.OBJBakedModel) evt.getModelRegistry().getObject(mrl);
            if(model == null)
                continue;
            final IBlockState state = StationsItems.blockPillar.getDefaultState().withProperty(BlockPillar.VARIANT, BlockPillar.EnumType.NUMBER_PLATE).withProperty(BlockPillar.FACING, dirs[i]);
            for (BakedQuad q : model.getQuads(state, null, 0))
            {
                try
                {
                    for (Field f : BakedQuad.class.getDeclaredFields())
                    {
                        if (!(f.getType() == int.class || f.getType() == Integer.class)) continue;
                        f.setAccessible(true);
                        f.set(q, 0);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent.Pre event)
    {
        NamePlateManager.INSTANCE.registerTextures(event.getMap());
    }

    @SubscribeEvent
    public void onColorHandlerEvent(ColorHandlerEvent.Block event)
    {
        event.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(IBlockState iBlockState, @Nullable IBlockAccess iBlockAccess, @Nullable BlockPos blockPos, int i)
            {
                if(iBlockAccess == null || blockPos == null)
                    return 0xFFFFFF;

                final IBlockState block = iBlockState.getActualState(iBlockAccess, blockPos);
                if(block.getValue(BlockPillar.VARIANT) != BlockPillar.EnumType.NUMBER_PLATE)
                    return 0xFFFFFF;

                TileEntity te =iBlockAccess.getTileEntity(blockPos);
                if(te != null && te instanceof TileEntityNumberPlate)
                {
                    try
                    {
                        return (int) (Long.parseLong(((TileEntityNumberPlate) te).getColorCode().toUpperCase(), 16));
                    }
                    catch (Exception e) {}
                }
                return 0xFFFFFF;
            }
        }, StationsItems.blockPillar);
    }


}
