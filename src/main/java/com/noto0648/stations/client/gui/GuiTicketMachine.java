package com.noto0648.stations.client.gui;

import com.noto0648.stations.packet.PacketSendTicket;
import com.noto0648.stations.Stations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/09.
 */
public class GuiTicketMachine extends GuiScreen implements IGui
{
    String[] oneLine = new String[20];
    int[] twoLine = new int[20];

    public GuiTicketMachine()
    {

        oneLine[0] = "片道きっぷ";
        twoLine[0] = 160;

        oneLine[1] = "往復券";
        twoLine[1] = 320;

        oneLine[2] = "回数券";
        twoLine[2] = 160 * 10;

        oneLine[3] = "入場券";
        twoLine[3] = 140;
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();

        drawRect(50, 10, width - 50, height - 50, 0xFF000000, 0xFF000000);

        for(int x = 0; x < 5; x++)
        {
            if(oneLine[x] != null)
            {
                GL11.glPushMatrix();
                //drawRect(60 + x * 60, 15, (60 + x * 60) + 10, 15 + 10, 0xFFFFFFFF, 0xFFFFFFFF);
                drawRect(60 + x * 60, 15, (60 + x * 60) + 50, 15 + 30, 0xFFC0C0C0, 0xFFC0C0C0);
                GL11.glTexCoord2d(60 + x * 60, 15);
                GL11.glColor3f(0F, 0F, 0F);

                getFontRenderer().drawString(oneLine[x], 85 + x * 60 - (getFontRenderer().getStringWidth(oneLine[x]) / 2), 15 + 4, 0);
                getFontRenderer().drawString(twoLine[x] + "", 85 + x * 60 - (getFontRenderer().getStringWidth(twoLine[x] + "") / 2), 15 + 16, 0);

                GL11.glPopMatrix();
            }
        }

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        for(int x = 0; x < 5; x++)
        {
            if(oneLine[x] != null)
            {
                if(60 + x * 60 <= p_73864_1_ && 15 <= p_73864_2_ && (60 + x * 60) + 50 >= p_73864_1_ && 15 + 30 >= p_73864_2_)
                {
                    Stations.packetDispatcher.sendToServer(new PacketSendTicket(x));
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                }
            }
        }

        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawRect(int x, int y, int x2, int y2, int color, int color2)
    {
        drawGradientRect(x, y, x2, y2, color, color2);
    }

    @Override
    public FontRenderer getFontRenderer()
    {
        return this.fontRendererObj;
    }
}
