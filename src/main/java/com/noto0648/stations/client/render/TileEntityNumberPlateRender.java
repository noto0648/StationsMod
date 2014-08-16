package com.noto0648.stations.client.render;

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
    public static ResourceLocation[] textures = new ResourceLocation[8];

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
        if(tileEntity.getBlockMetadata() % 2 == 0) GL11.glRotated(90, 0, 1, 0);

        bindTexture(textures[value]);

        plate.renderAll();
        GL11.glPopMatrix();
    }

    static
    {
        for(int i = 0; i < 8; i++)
            textures[i] = new ResourceLocation("notomod", "textures/models/number_plate" + (i + 1)+ ".png");
    }
}
