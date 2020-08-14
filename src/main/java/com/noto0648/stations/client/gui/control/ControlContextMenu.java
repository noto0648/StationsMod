package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.GuiScreenBase;
import com.noto0648.stations.client.gui.IGui;

/**
 * Created by Noto on 14/09/05.
 */
public class ControlContextMenu extends Control
{
    private boolean isShow;
    private String[] data;

    private int selectData = -1;

    public ControlContextMenu(IGui gui)
    {
        super(gui);
    }

    public ControlContextMenu(IGui gui, String[] str)
    {
        super(gui);
        data = str;
        height = 12 * data.length;
    }

    @Override
    public void draw(int mouseX, int mouseY) {}

    @Override
    public void initGui()
    {
        int maxWidth = 0;
        for(int i = 0; i < data.length; i++)
        {
            maxWidth = Math.max(getFont().getStringWidth(data[i]), maxWidth);
        }
        width = maxWidth + 4;
    }

    @Override
    public void drawTopLayer(int mouseX, int mouseY)
    {
        if(isEnable && isShow)
        {
            drawRect(locationX, locationY, locationX + width, locationY + height, 0xEEB0B0B0);

            if(onTheMouse(mouseX, mouseY))
            {
                selectData = (mouseY - locationY) / 12;
                selectData = Math.min(selectData, data.length - 1);
            }
            else
            {
                selectData = -1;
            }

            if(selectData != -1)
            {
                drawRect(locationX, locationY + selectData * 12, locationX + width, locationY + selectData * 12 + 12, 0xEE1111FE);
            }

            for(int i = 0; i < data.length; i++)
            {
                getFont().drawStringWithShadow(data[i], locationX + 2, locationY + 2 + i * 12, 0xFFFFFF);
            }
        }
    }

    @Override
    public void update()
    {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(isShow && isEnable)
        {
            if(onTheMouse(mouseX, mouseY))
            {
                int select = (mouseY - locationY) / 12;
                dataClick(select);
                hide();
            }
            else
            {
                hide();
            }
        }
    }

    public void show(int x, int y, int ctrlId)
    {
        isShow = true;
        locationX = x;
        locationY = y;
        if(getGui() instanceof GuiScreenBase)
        {
            ((GuiScreenBase) getGui()).openRightClickMenu(true, ctrlId);
        }
    }

    public void hide()
    {
        if(getGui() instanceof GuiScreenBase)
        {
            ((GuiScreenBase) getGui()).openRightClickMenu(false, -1);
        }
        isShow = false;
    }

    public void dataClick(int id)
    {

    }
}
