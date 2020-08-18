package com.noto0648.stations.client.render;

import com.noto0648.stations.client.fontrenderer.NewFontRenderer;
import com.noto0648.stations.tiles.TileEntityNumberPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityNumberPlateRenderer extends TileEntitySpecialRenderer<TileEntityNumberPlate>
{
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

    @Override
    public void render(TileEntityNumberPlate namePlate, double par2, double par3, double par4, float p_render_9_, int k, float p_render_10_)
    {
        final ColorCode strColor = new ColorCode(namePlate.getStrColorCode());

        GL11.glPushMatrix();

        GL11.glTranslated(par2 + 0.5, par3 + 0.65, par4 + 0.5);
        GL11.glScaled(0.6F, 0.6F, 0.6F);
        if(namePlate.getBlockMetadata() % 2 == 1) GL11.glRotated(90, 0, 1, 0);

        final int width = NewFontRenderer.INSTANCE.drawString(namePlate.getDrawStr(), false);
        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
            GlStateManager.color(0F, 0F, 0F);

            GL11.glRotatef(90F + 180f * i, 0F, 1F, 0F);
            GL11.glTranslated(0, -0.655, 0.15);

            GL11.glScalef(0.030F, 0.030F, 0.030F);
            GL11.glTranslated(- width/ 2, 0, 0);
            GlStateManager.color(strColor.getR(), strColor.getG(), strColor.getB());
            NewFontRenderer.INSTANCE.drawString(namePlate.getDrawStr());
            GlStateManager.color(1F, 1F, 1F);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    public class ColorCode
    {
        public int r;
        public int g;
        public int b;

        public ColorCode(String str)
        {
            if(str==null||str.length()==0)
            {
                r = g = b = 0;
                return;
            }

            try
            {
                int code = (int) Long.parseLong(str.toUpperCase(), 16);
                this.r = ((code >> 16) & 0xFF);
                this.g = ((code >> 8) & 0xFF);
                this.b = ((code >> 0) & 0xFF);
            }
            catch(Exception e)
            {
                r = g = b = 0;
            }
        }

        private float getFloat(int v)
        {
            if(v <= 127)
                return (float)(0.5 * v/127.0);
            else
                return (float)(0.5 + 0.5 * v/128.0);
        }

        public float getR()
        {
            return getFloat(this.r);
        }

        public float getG()
        {
            return getFloat(this.g);
        }

        public float getB()
        {
            return getFloat(this.b);
        }

        public int getInt()
        {
            return this.r << 16 | this.g << 8 | this.b;
        }
    }


}
