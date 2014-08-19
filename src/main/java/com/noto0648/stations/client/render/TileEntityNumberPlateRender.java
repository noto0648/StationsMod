package com.noto0648.stations.client.render;

import com.noto0648.stations.client.texture.NewFontRenderer;
import com.noto0648.stations.tile.TileEntityNumberPlate;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/04.
 */
public class TileEntityNumberPlateRender extends TileEntitySpecialRenderer
{
    public static IModelCustom plate = AdvancedModelLoader.loadModel(new ResourceLocation("notomod", "objs/number_plate.obj"));
    public static ResourceLocation newTexture = new ResourceLocation("notomod", "textures/models/number_plate.png");

    public TileEntityNumberPlateRender()
    {

    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double par2, double par3, double par4, float p_147500_8_)
    {
        int value = tileEntity.getBlockMetadata() / 2;

        GL11.glPushMatrix();
        GL11.glTranslated(par2 + 0.5, par3 + 0.65, par4 + 0.5);
        GL11.glScaled(0.6F, 0.6F, 0.6F);
        if(tileEntity.getBlockMetadata() % 2 == 1) GL11.glRotated(90, 0, 1, 0);

        bindTexture(newTexture);
        plate.renderAll();

        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
            GL11.glColor3f(0F, 0F, 0F);

            if(i == 0) GL11.glRotatef(90F, 0F, 1F, 0F);
            else GL11.glRotatef(270F, 0F, 1F, 0F);

            GL11.glTranslated(0, -0.455, 0.15);

            GL11.glScalef(0.030F, 0.030F, 0.030F);
            GL11.glTranslated(-NewFontRenderer.INSTANCE.drawString(((TileEntityNumberPlate)tileEntity).getDrawStr(), false) / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(((TileEntityNumberPlate)tileEntity).getDrawStr());
            GL11.glColor3f(1F, 1F, 1F);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

}
