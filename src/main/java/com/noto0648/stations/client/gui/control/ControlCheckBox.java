package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

/**
 * Created by Noto on 14/08/19.
 */
public class ControlCheckBox extends Control
{
    public String text;
    private boolean check;

    public ControlCheckBox(IGui gui)
    {
        super(gui);
    }

    public ControlCheckBox(IGui gui, int x, int y, String str)
    {
        super(gui);
        locationX = x;
        locationY = y;
        height = 16;
        text = str;
    }

    @Override
    public void initGui()
    {
        width = 17 + getFont().getStringWidth(text);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        drawRect(locationX, locationY, locationX + height, locationY + height, 0xFF000000);
        drawRect(locationX, locationY, locationX + height, locationY + 1, 0xFFC0C0C0);
        drawRect(locationX, locationY, locationX + 1, locationY + height, 0xFFC0C0C0);
        drawRect(locationX + height - 1, locationY, locationX + height, locationY + height, 0xFFC0C0C0);
        drawRect(locationX, locationY + height - 1, locationX + height, locationY + height, 0xFFC0C0C0);

        if(check)
            getFont().drawString("✓", locationX + 4, locationY + 4, 0xFFFFFFFF);

        if(onTheMouse(mouseX, mouseY)) getFont().drawStringWithShadow(text, locationX + 18, locationY + 4, 16777120);
        else getFont().drawStringWithShadow(text, locationX + 18, locationY + 4, 14737632);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(onTheMouse(mouseX, mouseY) && button == 0)
        {
            playClickSound();
            check = !check;
        }
    }

    public boolean getCheck()
    {
        return check;
    }

    public void setCheck(boolean par1)
    {
        check = par1;
    }
}
