package com.noto0648.stations.client.render;

import com.noto0648.stations.client.model.ModelRailToyCorner;
import com.noto0648.stations.tile.TileEntityRailToy;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/09/07.
 */
public class TileEntityRailToyCornerRender extends TileEntitySpecialRenderer
{
    public static ModelRailToyCorner model = new ModelRailToyCorner();
    public static ResourceLocation texture = new ResourceLocation("notomod", "textures/models/rail_corner_texture.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double par2, double par3, double par4, float p_147500_8_)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float)par2 + 0.5F, (float)par3 + 1.6F, (float)par4 + 0.5F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);

        if(((TileEntityRailToy)tile).getRotate() == 0) GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        if(((TileEntityRailToy)tile).getRotate() == 1) GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        if(((TileEntityRailToy)tile).getRotate() == 2) GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);

        bindTexture(texture);
        model.allRender(0.065F);
        GL11.glPopMatrix();
    }
}
