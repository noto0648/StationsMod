package com.noto0648.stations.client.render;

import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.tile.TileEntityMark;
import com.noto0648.stations.tile.TileEntityMarkMachine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/07.
 */
public class TileEntityMarkRender extends TileEntitySpecialRenderer
{

    public static IModelCustom departureModel = AdvancedModelLoader.loadModel(new ResourceLocation("notomod", "objs/departure_mark.obj"));
    public static ResourceLocation departureTexture = new ResourceLocation("notomod", "textures/models/departure_mark.png");

    private FontRenderer font;

    @Override
    public void renderTileEntityAt(TileEntity tile, double par2, double par3, double par4, float p_147500_8_)
    {
        int meta = tile.getBlockMetadata();

        if(font == null) font = this.func_147498_b();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2 + 0.5F, (float) par3 + 0.5F, (float) par4 + 0.5F);
        if(meta == 14) GL11.glRotatef(90F, 0, 1, 0);

        GL11.glPushMatrix();
        GL11.glScaled(0.5F, 0.5F, 0.5F);
        bindTexture(departureTexture);
        //NewFontRenderer.INSTANCE.bindTexture(NewFontRenderer.INSTANCE.toImage(""));
        departureModel.renderAll();
        GL11.glPopMatrix();

        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();

            if(i == 0)
            {
                if(meta == 14) GL11.glRotatef(90F, 0, 1, 0);
                if(meta == 15) GL11.glRotatef(-90F, 0, 1, 0);
            }
            else
            {
                if(meta == 14) GL11.glRotatef(-90F, 0, 1, 0);
                if(meta == 15) GL11.glRotatef(90F, 0, 1, 0);
            }

            GL11.glRotatef(180F, 0, 0, 1);
            GL11.glTranslated(-1.13, -0.1, -0.146);
            GL11.glScaled(0.018, 0.018, 0.018);
            GL11.glColor3f(1F, 1F, 1F);
            drawStrings((TileEntityMark)tile);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }


    private void drawStrings(TileEntityMark tile)
    {
        if(tile.isRegistered())
        {
            int _x = tile.getParentX();
            int _y = tile.getParentY();
            int _z = tile.getParentZ();
            TileEntity te = tile.getWorldObj().getTileEntity(_x, _y, _z);
            if(te != null && te instanceof TileEntityMarkMachine)
            {
                MarkData[] mka = ((TileEntityMarkMachine)te).getStringIndex();
                if(mka != null)
                {
                    if(mka.length > 0 && mka[0] != null)
                    {
                        font.drawString(mka[0].type, 0, 0, mka[0].typeColor);
                        font.drawString(toEm(mka[0].getTimeString()), 44, 0, mka[0].timeColor);
                        font.drawString(mka[0].dest, 74, 0, mka[0].destColor);

                    }

                    if(mka.length > 1 && mka[1] != null)
                    {
                        font.drawString(mka[1].type, 0, 18, mka[1].typeColor);
                        font.drawString(toEm(mka[1].getTimeString()), 44, 18, mka[1].timeColor);
                        font.drawString(mka[1].dest, 74, 18, mka[1].destColor);
                    }
                }
            }
            else
            {
                font.drawString("Disconnect", 0, 0, 0xFFFFFF);
            }
        }
        else
        {
            font.drawString("準    備    中", 0, 0, 0xFFFFFF);
        }

    }

    public static String toEm(String s)
    {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
            {
                sb.setCharAt(i, (char) (c - '0' + '０'));
            }
            if(c == ':')
            {
                sb.setCharAt(i, '：');
            }
        }
        return sb.toString();
    }
}
