package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Noto on 14/08/06.
 */
public abstract class Control
{
    private IGui gui;

    public int locationX;
    public int locationY;
    public int width;
    public int height;

    public boolean isFocus;
    protected boolean isEnable = true;

    public int controlId;

    public Control(IGui gui)
    {
        this.gui = gui;
    }

    public abstract void draw(int mouseX, int mouseY);

    public void drawTopLayer(int mouseX, int mouseY) {}

    public abstract void mouseClicked(int mouseX, int mouseY, int button);
    public void update() {}

    public void initGui() {}

    /**
     * draw a rectangle
     * @param x leftTop x position
     * @param y leftTop y position
     * @param x2 rightBottom x Position
     * @param y2 rightBottom y position
     * @param color ARGB
     */
    protected void drawRect(int x, int y, int x2, int y2, int color)
    {
        gui.drawRect(x, y, x2, y2, color, color);
    }

    protected void drawRect(int x, int y, int x2, int y2, int color, int color2)
    {
        gui.drawRect(x, y, x2, y2, color, color2);
    }

    protected FontRenderer getFont()
    {
        return gui.getFontRenderer();
    }

    public void mouseClickMove(int mouseX, int mouseY, int button, long time) {}
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode) {}
    public void keyTyped(char par1, int par2) {}
    public void onGuiClosed() {}

    public void focusCheck(int mouseX, int mouseY, int button)
    {
        if(onTheMouse(mouseX, mouseY))
        {
            isFocus = true;
        }
        else
        {
            isFocus = false;
        }
    }

    public void setSize(int w, int h)
    {
        width = w;
        height = h;
        initGui();
    }

    public void setLocation(int x, int y)
    {
        locationX = x;
        locationY = y;
        initGui();
    }

    public boolean onTheMouse(int mouseX, int mouseY)
    {
        return mouseX >= locationX && mouseY >= locationY && locationX + width >= mouseX && locationY + height >= mouseY;
    }

    protected IGui getGui()
    {
        return  gui;
    }

    public void setEnabled(boolean par1)
    {
        isEnable = par1;
    }

    public void playClickSound()
    {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }
}
