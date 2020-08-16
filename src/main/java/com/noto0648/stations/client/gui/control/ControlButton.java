package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

/**
 * Created by Noto on 14/08/16.
 */
public class ControlButton extends Control
{
    private String text;

    public ControlButton(IGui gui)
    {
        super(gui);
    }

    public ControlButton(IGui gui, int x, int y, int w, int h, String tx)
    {
        super(gui);
        locationX = x;
        locationY = y;
        width = w;
        height = h;
        text = tx;
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if(!isEnable)
            return;
        int centerColor = 0xFF6F6F6F;
        int topColor = 0xFFAAAAAA;
        int bottomColor = 0xFF565656;
        int fontColor = 14737632;

        if(onTheMouse(mouseX, mouseY))
        {
            centerColor = 0xFF7E88BF;
            bottomColor = 0xFF5B659C;
            topColor = 0xFFBEC7FF;
            fontColor = 16777120;
        }

        drawRect(locationX, locationY, locationX + width, locationY + height, centerColor);
        drawRect(locationX, locationY, locationX + width, locationY + 1, topColor);
        drawRect(locationX, locationY, locationX + 1, locationY + height, topColor);
        drawRect(locationX + 1, locationY + height - 1, locationX + width, locationY + height, bottomColor);
        drawRect(locationX + width - 1, locationY, locationX + width, locationY + height, bottomColor);

        getFont().drawStringWithShadow(text, width / 2 - getFont().getStringWidth(text) / 2 + locationX, height / 2 - 4 + locationY, fontColor);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(onTheMouse(mouseX, mouseY) && isEnable)
        {
            onButtonClick(button);
        }
    }

    /**
     *
     * @param button 0 = left, 1 = right
     */
    public void onButtonClick(int button) {}

    public void setText(String par1)
    {
        text = par1;
    }
}
