package com.noto0648.stations.client.gui;

import com.noto0648.stations.client.gui.control.Control;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/16.
 */
public abstract class GuiScreenBase extends GuiScreen implements IGui
{
    protected List<Control> controlList = new ArrayList();

    protected abstract void paint(int mouseX, int mouseY);
    protected abstract void resize();

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        super.initGui();
        resize();
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).initGui();
        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

        if(doesDrawDarkScreen()) this.drawDefaultBackground();

        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).draw(p_73863_1_, p_73863_2_);
        }
        paint(p_73863_1_, p_73863_2_);
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).focusCheck(p_73864_1_, p_73864_2_, p_73864_3_);
            controlList.get(i).mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        }
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        super.keyTyped(p_73869_1_, p_73869_2_);
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).keyTyped(p_73869_1_, p_73869_2_);
        }
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
    {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }

    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_)
    {
        super.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).update();
        }
    }

    public Control getFocusControl()
    {
        for(int i = 0; i < controlList.size(); i++)
        {
            boolean focus = controlList.get(i).isFocus;
            if(focus)
            {
                return controlList.get(i);
            }
        }
        return null;
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).onGuiClosed();
        }
    }

    public boolean doesDrawDarkScreen()
    {
        return true;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    @Deprecated
    protected void actionPerformed(GuiButton button) {}

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
