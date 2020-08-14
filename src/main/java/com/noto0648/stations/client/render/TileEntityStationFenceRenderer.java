package com.noto0648.stations.client.render;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.client.model.ModelStationIronFence;
import com.noto0648.stations.tiles.TileEntityStationFence;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityStationFenceRenderer extends TileEntitySpecialRenderer<TileEntityStationFence>
{
    private static final ModelStationIronFence MODEL = new ModelStationIronFence();
    private static final ResourceLocation TEXTURE = new ResourceLocation(StationsMod.MOD_ID, "textures/models/station_iron_fence.png");

    public TileEntityStationFenceRenderer() {}

    @Override
    public void render(TileEntityStationFence tile, double x, double y, double z, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        super.render(tile, x, y, z, p_render_8_, p_render_9_, p_render_10_);

        int meta = 0;
        if(tile.getWorld() == null)
        {
            renderInventoryBlock(x, y, z);
            return;
        }
        if(meta <=4)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);

            if(tile.getWorld() != null)
            {
                if (tile.getBlockMetadata() == 0) GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                if (tile.getBlockMetadata() == 1) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                if (tile.getBlockMetadata() == 3) GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            }
            bindTexture(TEXTURE);
            MODEL.allRender(0.065F);
            GL11.glPopMatrix();
        }
        else if(meta >= 14)
        {

        }
    }

    private void renderInventoryBlock(double x, double y, double z)
    {
        GL11.glPushMatrix();
        bindTexture(TEXTURE);

        GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        GlStateManager.rotate(-180, 0.0F, 1.0F, 0.0F);

        //GL11.glTranslatef(0.5F, 1.4F, 0.5F);
        //GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        MODEL.allRender(0.065F);
        GL11.glPopMatrix();
    }
}
