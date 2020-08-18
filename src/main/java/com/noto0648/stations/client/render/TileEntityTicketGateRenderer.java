package com.noto0648.stations.client.render;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.blocks.BlockTicketMachine;
import com.noto0648.stations.tiles.TileEntityTicketGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class TileEntityTicketGateRenderer extends TileEntitySpecialRenderer<TileEntityTicketGate>
{
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
    private final ModelResourceLocation openModel = new ModelResourceLocation(StationsMod.MOD_ID + ":block/ticket_gate_open.obj", "normal");

    @Override
    public void render(TileEntityTicketGate tile, double x, double y, double z, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        if(getWorld() == null || tile == null)
            return;

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        final IBlockState actualState = getWorld().getBlockState(tile.getPos());
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if(tile.isGateOpen())
        {
            final IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(openModel);
            final EnumFacing f = actualState.getValue(BlockTicketMachine.FACING);
            GlStateManager.translate(0.5f,0,0.5f);
            if(f == EnumFacing.SOUTH)
                GL11.glRotated(270, 0, 1, 0);
            if(f == EnumFacing.NORTH)
                GL11.glRotated(90, 0, 1, 0);
            if(f == EnumFacing.WEST)
                GL11.glRotated(180, 0, 1, 0);
            GlStateManager.translate(-0.5f,0,-0.5f);
            blockRenderer.getBlockModelRenderer().renderModelBrightnessColor(model, 1f, 1f, 1f, 1f);
        }
        else
        {
            final IBakedModel model = blockRenderer.getModelForState(actualState);
            blockRenderer.getBlockModelRenderer().renderModelBrightnessColor(model, 1f, 1f, 1f, 1f);
        }
        GL11.glPopMatrix();
    }
}
