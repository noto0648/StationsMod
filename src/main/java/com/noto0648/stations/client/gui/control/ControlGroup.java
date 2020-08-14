package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

import java.util.ArrayList;
import java.util.List;

public class ControlGroup extends Control
{
    private List<Control> children;

    public ControlGroup(IGui gui, int x, int y)
    {
        super(gui);
        locationX = x;
        locationY = y;
        children = new ArrayList<>();
    }

    @Override
    public void setEnabled(boolean par1)
    {
        isEnable = par1;
        for(Control c : children)
        {
            c.setEnabled(par1);
        }
    }

    public void add(Control control)
    {
        children.add(control);
    }

    @Override
    public void initGui()
    {
        for(Control c : children)
        {
            c.initGui();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        for(Control c : children)
        {
            c.draw(mouseX, mouseY);
        }
    }

    @Override
    public void update()
    {
        for(Control c : children)
        {
            c.update();
        }
    }

    @Override
    public void drawTopLayer(int mouseX, int mouseY)
    {
        for(Control c : children)
        {
            c.drawTopLayer(mouseX, mouseY);
        }
    }

    @Override
    public void draw3D(int mouseX, int mouseY)
    {
        for(Control c : children)
        {
            c.draw3D(mouseX, mouseY);
        }
    }

    @Override
    public void mouseScroll(int mouseScroll)
    {
        for(Control c : children)
        {
            c.mouseScroll(mouseScroll);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        for(Control c : children)
        {
            c.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        for(Control c : children)
        {
            c.mouseClickMove(mouseX, mouseY, button, time);
        }
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode)
    {
        for(Control c : children)
        {
            c.mouseMovedOrUp(mouseX, mouseY, mode);
        }
    }

    @Override
    public void keyTyped(char par1, int par2)
    {
        for(Control c : children)
        {
            c.keyTyped(par1, par2);
        }
    }

    @Override
    public void onGuiClosed()
    {
        for(Control c : children)
        {
            c.onGuiClosed();
        }
    }

    @Override
    public void setSize(int w, int h)
    {
        width = w;
        height = h;
        for(Control c : children)
        {
            c.setSize(w, h);
        }
        initGui();
    }

    @Override
    public void setLocation(int x, int y)
    {
        locationX = x;
        locationY = y;
        for(Control c : children)
        {
            c.setLocation(x, y);
        }
        initGui();
    }
}
