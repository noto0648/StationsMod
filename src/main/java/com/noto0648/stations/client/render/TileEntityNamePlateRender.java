package com.noto0648.stations.client.render;

import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.tile.TileEntityNamePlate;
import com.noto0648.stations.client.texture.TextureImporter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */
public class TileEntityNamePlateRender extends TileEntitySpecialRenderer
{
    public static IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("notomod", "objs/name_plate.obj"));
    public static ResourceLocation texture = new ResourceLocation("notomod", "textures/models/name_plate.png");

    @Override
    public void renderTileEntityAt(TileEntity p_147500_1_, double par2, double par3, double par4, float p_147500_8_)
    {
        int meta = p_147500_1_.getBlockMetadata();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2 + 0.5F, (float) par3 + 0.5F, (float) par4 + 0.5F);


        if(meta == 5) GL11.glTranslatef(-0.5F, 0F, 0F);
        if(meta == 4) GL11.glTranslatef(0.5F, 0F, 0F);

        if(meta == 2)
        {
            GL11.glTranslatef(0F, 0F, 0.5F);
            GL11.glRotatef(-90F, 0, 1, 0);
        }
        if(meta == 3)
        {
            GL11.glTranslatef(0F, 0F, -0.5F);
            GL11.glRotatef(-90F, 0, 1, 0);
        }

        GL11.glPushMatrix();
        GL11.glScaled(0.5F, 0.5F, 0.5F);

        if(meta == 1)
            GL11.glRotatef(-90F, 0, 1, 0);


        String tex = ((TileEntityNamePlate)p_147500_1_).texture;
        if(tex.equalsIgnoreCase("DefaultTexture"))
        {
            bindTexture(texture);
        }
        else
        {
            if(!TextureImporter.INSTANCE.bindTexture(tex))
            {
                bindTexture(texture);
            }
        }

        model.renderAll();
        GL11.glPopMatrix();

        NamePlateBase renderPlate = null;

        for(int i = 0; i < NamePlateManager.INSTANCE.getNamePlates().size(); i++)
        {
            String name = NamePlateManager.INSTANCE.getNamePlates().get(i).getName();
            if(name.equalsIgnoreCase(((TileEntityNamePlate)p_147500_1_).currentType))
            {
                renderPlate = NamePlateManager.INSTANCE.getNamePlates().get(i);
            }
        }
        Map<String, String> strMap = ((TileEntityNamePlate)p_147500_1_).getHashMap();

        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
            if(meta == 0 || meta == 3 || meta == 2 || meta == 5 || meta == 4)
            {
                if(i == 0) GL11.glRotatef(-90F, 0, 1, 0);
                if(i == 1) GL11.glRotatef(-270F, 0, 1, 0);
            }
            else
            {
                if(i == 0) GL11.glRotatef(-0F, 0, 1, 0);
                if(i == 1) GL11.glRotatef(-180F, 0, 1, 0);
            }

            if(renderPlate != null && strMap != null)
            {
                renderPlate.render(strMap, i == 0);
            }
            /*
            GL11.glTranslated(-0, 0, 0.1);
            GL11.glScalef(0.01F, 0.01F, 0.01F);
            GL11.glColor3f(0F, 0F, 0F);
            int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
            GL11.glTranslated(-width / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(nowStation);
            GL11.glTranslated(width / 2, 0, 0);

            String station = i == 0 ? prevStation : nextStation;

            {
                GL11.glPushMatrix();
                GL11.glTranslated(-110, -43, 0);
                GL11.glScaled(0.5F, 0.5F, 0.5F);
                NewFontRenderer.INSTANCE.drawString(station);
                GL11.glPopMatrix();
            }

            station = i == 1 ? prevStation : nextStation;

            {
                GL11.glPushMatrix();
                GL11.glTranslated(110, -43, 0);
                GL11.glScaled(0.5F, 0.5F, 0.5F);
                int w = NewFontRenderer.INSTANCE.drawString(station, false);
                GL11.glTranslated(-w, 0, 0);
                NewFontRenderer.INSTANCE.drawString(station);
                GL11.glPopMatrix();
            }
            {
                GL11.glPushMatrix();
                GL11.glColor3f(1F, 1F, 1F);
                GL11.glTranslated(0, -25, 0);
                GL11.glScaled(0.45F, 0.45F, 0.45F);
                int w = NewFontRenderer.INSTANCE.drawString(englishStation, false);
                GL11.glTranslated(-w / 2, 0, 0);
                NewFontRenderer.INSTANCE.drawString(englishStation);
                GL11.glPopMatrix();
            }
*/
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
}
