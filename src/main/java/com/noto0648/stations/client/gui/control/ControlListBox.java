package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/06.
 */
public class ControlListBox extends Control
{
    public List<String> items = new ArrayList();
    public int selectedIndex = -1;

    private int scrollLevel;

    private boolean scrollBerClicked;

    public ControlListBox(IGui gui)
    {
        super(gui);
    }

    public ControlListBox(IGui gui, int x, int y, int w, int h)
    {
        super(gui);
        locationX = x;
        locationY = y;
        width = w;
        height = h;
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        drawRect(locationX, locationY, locationX + width, locationY + height, 0x80808080);

        int itemMax = height / 12;
        int itemCount = 0;

        if(items.size() > 0 && selectedIndex != -1 && scrollLevel <= selectedIndex && itemMax + scrollLevel > selectedIndex)
        {
            drawRect(locationX + 2 , locationY + selectedIndex * 12 - scrollLevel * 12 + 2, locationX + width - 14, locationY + selectedIndex * 12 - scrollLevel * 12 + 2 + 12, 0x80C0C0C0);
        }

        for(int i = scrollLevel; i < itemMax + scrollLevel; i++)
        {
            if(items.size() > i)
            {
                getFont().drawStringWithShadow(items.get(i), 4 + locationX, itemCount * 12 + locationY + 4, 0xFFFFFF);
                itemCount++;
            }
            else break;
        }

        if(height / 12 < items.size())
        {
            drawRect(locationX + width - 12, locationY, locationX + width, locationY + 12, 0xC0C0C080);
            drawRect(locationX + width - 12, locationY + 12, locationX + width, locationY + height - 12, 0x80C0C0C0);
            drawRect(locationX + width - 12, locationY + height - 12, locationX + width, locationY + height, 0xC0C0C080);
            int top = (locationY + 12 + 2);
            int bottom = (locationY + height - 12 - 2);
            int size = Math.max(1, (bottom - top) / (items.size() - itemMax + 1));
            if(scrollLevel == items.size() - itemMax)
            {
                drawRect(locationX + width - 10, locationY + height - 12 - size - 2, locationX + width - 2, locationY + height - 14 , 0xC0C0C080);
            }
            else
            {
                drawRect(locationX + width - 10, top + size * scrollLevel, locationX + width - 2, top + size + size * scrollLevel, 0xC0C0C080);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(button == 0 && items.size() > 0 && isEnable)
        {
            if(height / 12 < items.size())
            {
                if(locationX + width - 12 <= mouseX && locationY <= mouseY && locationX + width >= mouseX && locationY + 12 >= mouseY)
                {
                    scrollLevel = Math.max(0, scrollLevel - 1);
                }
                else if(locationX + width - 12 <= mouseX && locationY + height - 12 <= mouseY && locationX + width >= mouseX && locationY + height >= mouseY)
                {
                    scrollLevel = Math.min(items.size() - height / 12, scrollLevel + 1);
                }
                else if(locationX + width - 12 <= mouseX && locationY + 12 <= mouseY && locationX + width >= mouseX && locationY + height - 12 >= mouseY)
                {
                    int top = (locationY + 12);
                    int bottom = (locationY + height - 12);
                    int size = (bottom - top) / (items.size() - (height / 12) + 1);
                    int sel = (mouseY - locationY - 12) / size;
                    scrollLevel = sel;
                    scrollBerClicked = true;
                }
            }
            if(locationX <= mouseX && locationY <= mouseY && locationX + width - 12 >= mouseX && locationY + height >= mouseY)
            {
                int sel = (mouseY - locationY - 2) / 12 + scrollLevel;
                if(sel > -1 && sel < items.size())
                {
                    if(selectedIndex != sel)
                    {
                        selectedIndex = sel;
                        selectChanged();
                    }
                }
            }
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        if(button == 0 && scrollBerClicked && isEnable)
        {
            int top = (locationY + 12);
            int bottom = (locationY + height - 12);
            int size = (bottom - top) / (items.size() - (height / 12) + 1);
            int sel = (mouseY - locationY - 12) / size;
            scrollLevel = Math.min(items.size() - height / 12, Math.max(0, sel));
        }
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode)
    {
        scrollBerClicked = false;
    }

    public void selectChanged() {}

    public void setSelectedIndex(int par1)
    {
        selectedIndex = par1;
        selectChanged();
    }

    public String getText()
    {
        if(selectedIndex != -1 && selectedIndex < items.size())
        {
            String str = items.get(selectedIndex);
            return str == null ? "" : str;
        }
        return "";
    }

}
