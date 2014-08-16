package com.noto0648.stations.client.render;

import com.noto0648.stations.client.model.ModelFence;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/04.
 */
public class TileEntityFenceRender extends TileEntitySpecialRenderer
{
    private static ModelFence model = new ModelFence();
    private static ResourceLocation texture = new ResourceLocation("notomod", "textures/models/fence_texture.png");

    public TileEntityFenceRender()
    {

    }

    public void renderTileEntityAt(TileEntity tile, double par2, double par3, double par4, float p_147500_8_)
    {
        int meta = tile.getBlockMetadata();
        if(meta <=4)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2 + 0.5F, (float)par3 + 1.5F, (float)par4 + 0.5F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            if(tile.getBlockMetadata() == 0) GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            if(tile.getBlockMetadata() == 1) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            if(tile.getBlockMetadata() == 3) GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            bindTexture(texture);
            model.allRender(0.065F);
            GL11.glPopMatrix();
        }
        else if(meta >= 14)
        {

        }
    }

}
