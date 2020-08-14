package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

public class ControlVerticalScrollBar extends Control
{
    private static final int COLOR_BACK = 0x80808080;
    private static final int COLOR_BAR = 0xC0C0C080;
    private static final int COLOR_BAR_BACK = 0x80C0C0C0;

    private static final int BUTTON_SIZE = 12;

    private int maxScroll = 10;
    private int currentScroll = 0;
    private int pageSize = 1;
    private boolean clicking = false;

    public ControlVerticalScrollBar(IGui gui)
    {
        super(gui);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if(!isEnable)
            return;

        if(maxScroll - pageSize + 1 == 0)
            return;

        drawRect(locationX, locationY, locationX + width, locationY + BUTTON_SIZE, COLOR_BAR);
        drawRect(locationX, locationY + BUTTON_SIZE, locationX + width, locationY + height - BUTTON_SIZE, COLOR_BAR_BACK);
        drawRect(locationX, locationY + height - BUTTON_SIZE, locationX + width, locationY + height, COLOR_BAR);

        int top = (locationY + BUTTON_SIZE + 2);
        int bottom = (locationY + height - BUTTON_SIZE - 2);
        int size = Math.max(1, (bottom - top) / (maxScroll - pageSize + 1));
        if(currentScroll == maxScroll - pageSize)
        {
            drawRect(locationX + 2, locationY + height - BUTTON_SIZE - size - 2, locationX + width - 2, locationY + height - BUTTON_SIZE - 2 , COLOR_BAR);
        }
        else
        {
            drawRect(locationX + 2, top + size * currentScroll, locationX + width - 2, top + size + size * currentScroll, COLOR_BAR);
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(!isEnable)
            return;

        if(maxScroll - pageSize + 1 == 0)
            return;


        if(locationX <= mouseX && locationY <= mouseY && locationX + width >= mouseX && locationY + BUTTON_SIZE >= mouseY)
        {
            currentScroll = Math.max(0, currentScroll - 1);
            scrollChanged();
        }
        else if(locationX <= mouseX && locationY + height - BUTTON_SIZE <= mouseY && locationX + width >= mouseX && locationY + height >= mouseY)
        {
            currentScroll = Math.min(maxScroll - pageSize, currentScroll + 1);
            scrollChanged();
        }
        else if(locationX <= mouseX && locationY + BUTTON_SIZE <= mouseY && locationX + width >= mouseX && locationY + height - BUTTON_SIZE >= mouseY)
        {
            int top = (locationY + BUTTON_SIZE);
            int bottom = (locationY + height - BUTTON_SIZE);
            int size = (bottom - top) / (maxScroll - pageSize + 1);
            int sel = (mouseY - locationY - 12) / size;
            currentScroll = sel;
            scrollChanged();
            clicking = true;
        }

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        if(button == 0 && clicking && isEnable)
        {
            if(maxScroll - pageSize + 1 == 0)
                return;

            int top = (locationY + BUTTON_SIZE);
            int bottom = (locationY + height - BUTTON_SIZE);
            int size = (bottom - top) / (maxScroll - pageSize + 1);
            int sel = (mouseY - locationY - BUTTON_SIZE) / size;
            currentScroll = Math.min(maxScroll - pageSize, Math.max(0, sel));
            scrollChanged();
        }
    }

    @Override
    public void mouseScroll(int mouseScroll)
    {
        currentScroll = (int)Math.max(Math.min(currentScroll + (float)(-1 * mouseScroll) / 120.0F, maxScroll - pageSize), 0);
        scrollChanged();
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode)
    {
        clicking = false;
    }

    public void scrollChanged() {}

    public int getMaxScroll()
    {
        return maxScroll;
    }

    public void setMaxScroll(int maxScroll)
    {
        this.maxScroll = maxScroll;
    }

    public int getCurrentScroll()
    {
        return currentScroll;
    }

    public void setCurrentScroll(int currentScroll)
    {
        this.currentScroll = currentScroll;
        scrollChanged();
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = Math.max(1, pageSize);
    }
}
