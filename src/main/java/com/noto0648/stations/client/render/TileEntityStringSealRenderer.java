package com.noto0648.stations.client.render;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.client.fontrenderer.NewFontRenderer;
import com.noto0648.stations.tiles.TileEntityStringSeal;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public class TileEntityStringSealRenderer extends TileEntitySpecialRenderer<TileEntityStringSeal>
{
    private Minecraft minecraft = Minecraft.getMinecraft();
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

    @Override
    public void render(TileEntityStringSeal tile, double x, double y, double z, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        if(getWorld() == null)
            return;

        if(minecraft.player != null)
        {
            for(EnumHand hand : EnumHand.values())
            {
                if(minecraft.player.getHeldItem(hand) != ItemStack.EMPTY && minecraft.player.getHeldItem(hand).getItem() == StationsItems.itemSealSensor)
                {
                    GlStateManager.pushAttrib();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x, y, z);
                    GlStateManager.disableRescaleNormal();
                    IBakedModel model = minecraft.getRenderItem().getItemModelMesher().getModelManager().getModel(null);
                    GlStateManager.pushMatrix();
                    this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    blockRenderer.getBlockModelRenderer().renderModelBrightnessColor(model, 1f, 1f, 1f, 1f);
                    GlStateManager.popMatrix();
                    GlStateManager.popMatrix();
                    GlStateManager.popAttrib();
                    return;
                }
            }

        }

        IBlockState state =  getWorld().getBlockState(tile.getPos());
        EnumFacing face = state.getValue(BlockDirectional.FACING);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5f, y + 0.5f, z + 0.5f);
        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.translate(0,-0.5f,0f);


        GlStateManager.pushMatrix();
        GlStateManager.scale(1/32f, 1/32f, 1/32f);
        if(face == EnumFacing.NORTH)
        {
            GlStateManager.rotate(180f, 0, 1f, 0f);
        }
        if(face == EnumFacing.EAST)
        {
            GlStateManager.rotate(90f, 0, 1f, 0f);
        }
        if(face == EnumFacing.WEST)
        {
            GlStateManager.rotate(-90f, 0, 1f, 0f);
        }
        if(face == EnumFacing.UP)
        {
            //GlStateManager.translate(0,0.5f,0f);
            GlStateManager.rotate(90f * tile.getRotation(), 0, 1f, 0f);
            GlStateManager.rotate(-90f, 1, 0f, 0f);
            GlStateManager.translate(0f,-16f,16f);
        }
        if(face == EnumFacing.DOWN)
        {
            //GlStateManager.translate(0,0.5f,0f);
            GlStateManager.rotate(90f * tile.getRotation(), 0, 1f, 0f);
            GlStateManager.rotate(90f, 1, 0f, 0f);
            GlStateManager.translate(0f,-16f,-16f);
        }

        GlStateManager.translate(0,0,-15.5f);
        for(int i = 0; i < tile.getColorCodes().size(); i++)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0f, 32f / tile.getColorCodes().size() * i, 0f);
            GlStateManager.scale(1f / tile.getColorCodes().size(), 1f / tile.getColorCodes().size(), 1f);
            int width = NewFontRenderer.INSTANCE.drawString(tile.getStrings().get(tile.getColorCodes().size() -  i - 1), false);
            GlStateManager.translate(-width/2,0,0f);
            NewFontRenderer.INSTANCE.drawString(tile.getStrings().get(tile.getColorCodes().size() -  i - 1));
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
    }
}