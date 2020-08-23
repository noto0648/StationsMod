package com.noto0648.stations.client.render;

import com.noto0648.stations.api.DeparturePlateMode;
import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.tiles.TileEntityDeparturePlate;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_EQUAL;

public class TileEntityDeparturePlateRenderer extends TileEntitySpecialRenderer<TileEntityDeparturePlate>
{
    private FontRenderer font;

    @Override
    public void render(TileEntityDeparturePlate p_render_1_, double p_render_2_, double p_render_4_, double p_render_6_, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        super.render(p_render_1_, p_render_2_, p_render_4_, p_render_4_, p_render_8_, p_render_9_, p_render_8_);
        renderTileEntityAt(p_render_1_, p_render_2_, p_render_4_, p_render_6_, p_render_8_);
    }

    public void renderTileEntityAt(TileEntityDeparturePlate tile, double par2, double par3, double par4, float p_147500_8_)
    {
        int meta = tile.getBlockMetadata();

        if(font == null) font = this.getFontRenderer();

        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2 + 0.5F, (float) par3 + 0.5F, (float) par4 + 0.5F);
        if(meta == 14) GL11.glRotatef(90F, 0, 1, 0);

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
            GlStateManager.color(1F, 1F, 1F);
            drawStrings(tile);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }


    private void drawStrings(TileEntityDeparturePlate tile)
    {

        List<MarkData> marks = tile.getMarkDataList();

        final DeparturePlateMode mode =  tile.getDisplayMode();
        if(mode == DeparturePlateMode.UNDISPLAYED || mode == DeparturePlateMode.CUSTOM)
            return;

        if(mode == DeparturePlateMode.PREPARATION)
        {
            font.drawString(I18n.format("gui.notomod.inpreparation"), 0, 0, 0xFFFFFF);
            return;
        }

        if(mode == DeparturePlateMode.FIXED)
        {
            /*
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
            GlStateManager.depthMask(false);
            GL11.glStencilFunc(GL11.GL_EQUAL, 1, ~0);
            Tessellator tes = Tessellator.getInstance();
            tes.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            tes.getBuffer().pos(0, 0, 0).endVertex();
            tes.getBuffer().pos(0, 36, 0).endVertex();
            tes.getBuffer().pos(70, 36, 0).endVertex();
            tes.getBuffer().pos(70, 0, 0).endVertex();
            tes.draw();
            GlStateManager.depthMask(true);
            //GL11.glStencilFunc(GL11.GL_EQUAL, 1, ~0);
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
            font.drawString("駆け込み乗車は、ご迷惑となりますのでおやめください", 0, 0*18, 0xFFFFFFFF);
            */
            return;
        }

        if(mode == DeparturePlateMode.NORMAL || mode == DeparturePlateMode.REALTIME)
        {
            final MarkData[] mka = tile.getNextMarkDataWithMinecraftDate();
            GlStateManager.resetColor();
            for(int i = 0; i < 2; i++)
            {
                if(mka.length > 0 && mka[i] != null)
                {
                    font.drawString(mka[i].type, 0, i*18, mka[i].typeColor);
                    font.drawString(toEm(mka[i].getTimeString()), 44, i*18, mka[i].timeColor);
                    font.drawString(mka[i].dest, 74, i*18, mka[i].destColor);
                }
            }
        }

        /*
        if(tile.isRegistered())
        {
            int _x = tile.getParentX();
            int _y = tile.getParentY();
            int _z = tile.getParentZ();
            TileEntity te = tile.getWorldObj().getTileEntity(_x, _y, _z);
            if(te != null && te instanceof TileEntityDeparturePlate)
            {
                MarkData[] mka = ((TileEntityMarkMachine)te).getNextMarkDataWithMinecraftDate();
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
        }
*/
    }


    public static String toEm(String s)
    {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
            {
                //sb.setCharAt(i, (char) (c - '0' + '０'));
            }
            if(c == ':')
            {
                //sb.setCharAt(i, '：');
            }
        }
        return sb.toString();
    }
}
