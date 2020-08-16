package com.noto0648.stations.client.render;

import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.common.MinecraftDate;
import com.noto0648.stations.tiles.TileEntityDeparturePlate;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

import java.util.List;

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

        /*
        GL11.glPushMatrix();
        GL11.glScaled(0.5F, 0.5F, 0.5F);
        //bindCharTexture(departureTexture);
        //NewFontRenderer.INSTANCE.bindCharTexture(NewFontRenderer.INSTANCE.toImage(""));
        //departureModel.renderAll();
        GL11.glPopMatrix();
*/
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
        if(marks == null || marks.isEmpty())
        {
            font.drawString("準    備    中", 0, 0, 0xFFFFFF);
            return;
        }

        MarkData[] mka = getNextMarkDataWithMinecraftDate(marks);
        if(mka != null)
        {
            //GL11.glColor3f(1F, 1F, 1F);
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
            /*
            GL11.glColor3f(1F, 1F, 1F);
            if(mka.length > 1 && mka[1] != null)
            {
                font.drawString(mka[1].type, 0, 18, mka[1].typeColor);
                font.drawString(toEm(mka[1].getTimeString()), 44, 18, mka[1].timeColor);
                font.drawString(mka[1].dest, 74, 18, mka[1].destColor);
            }
            */
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

    public MarkData[] getNextMarkDataWithMinecraftDate(List<MarkData> markDataList)
    {
        MinecraftDate md = new MinecraftDate(getWorld().getWorldTime());
        MarkData[] result = new MarkData[2];
        int resultIndex = 0;

        for(int i = 0; i < markDataList.size(); i++)
        {
            MarkData mkd = markDataList.get(i);
            if(mkd.hours >= md.getHours())
            {
                if((mkd.hours > md.getHours()) || (mkd.minutes >= md.getMinutes() && (mkd.hours >= md.getHours())))
                {
                    result[resultIndex] = mkd;
                    resultIndex++;
                }
            }

            if(resultIndex >= 2)
                break;
        }
        return result;
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
