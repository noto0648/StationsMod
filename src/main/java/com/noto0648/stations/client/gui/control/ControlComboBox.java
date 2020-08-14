package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/26.
 */
public class ControlComboBox extends Control
{
    private ControlListBox listBox;

    public List<String> items = new ArrayList();
    public int selectedIndex = -1;

    private boolean showListBox;

    public ControlComboBox(IGui gui)
    {
        super(gui);
    }

    public ControlComboBox(IGui gui, int x, int y, int w, int h)
    {
        super(gui);
        locationX = x;
        locationY = y;
        width = w;
        height = h;

        listBox = (new ControlListBox(gui, x, y + h, w, h * 4)
        {
            @Override
            public void selectChanged()
            {
                showListBox = false;
                ControlComboBox.this.selectedIndex = this.selectedIndex;
            }

        });
        listBox.isEnable = false;
    }

    @Override
    public void initGui()
    {
        listBox.setLocation(locationX, locationY + height);
        listBox.setSize(width, height * 4);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
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

        if(items.get(selectedIndex) != null)
        {
            getFont().drawStringWithShadow(items.get(selectedIndex), width / 2 - getFont().getStringWidth(items.get(selectedIndex)) / 2 + locationX, height / 2 - 4 + locationY, fontColor);
        }

        if(showListBox)
        {
            listBox.draw(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(onTheMouse(mouseX, mouseY) && isEnable)
        {
            showListBox = !showListBox;
            listBox.isEnable = showListBox;
            if(showListBox)
            {
                listBox.items.clear();
                listBox.items.addAll(items);
                listBox.selectedIndex = (selectedIndex);
            }
        }

        if(!(listBox.onTheMouse(mouseX, mouseY) || onTheMouse(mouseX, mouseY)))
        {
            showListBox = false;
            listBox.isEnable = showListBox;
        }

        if(showListBox)
        {
            listBox.mouseClicked(mouseX, mouseY, button);
        }

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        if(showListBox)
            listBox.mouseClickMove(mouseX, mouseY, button, time);
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode)
    {
        if(showListBox)
            listBox.mouseMovedOrUp(mouseX, mouseY, mode);
    }

    public void setSelectedIndex(int par1)
    {
        selectedIndex = par1;

    }
}
