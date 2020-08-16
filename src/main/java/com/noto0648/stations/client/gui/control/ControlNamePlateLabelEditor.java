package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

import java.util.*;

public class ControlNamePlateLabelEditor extends Control
{
    private ControlVerticalScrollBar scrollBar;
    private Map<String, ControlTextBox> map;
    private List<String> keys;

    public ControlNamePlateLabelEditor(IGui gui)
    {
        super(gui);
        scrollBar = new ControlVerticalScrollBar(gui)
        {
            @Override
            public void scrollChanged()
            {
                changeScrollEvent();
            }
        };
        map = new HashMap<>();
        keys = new ArrayList<>();
    }

    protected void changeScrollEvent()
    {
        for(ControlTextBox  val : map.values())
        {
            val.setEnabled(false);
        }
    }

    @Override
    public void initGui()
    {
        scrollBar.initGui();
        scrollBar.setSize(12, height);
        scrollBar.setLocation(locationX + width - 12, locationY);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if(!isEnable)
            return;
        final int baseWidth = (width - 13);

        drawRect(locationX, locationY, locationX + baseWidth, locationY + height, 0x80808080);
        scrollBar.draw(mouseX, mouseY);

        final int itemHeight = 22;
        final int maxItems = height / itemHeight;
        scrollBar.setPageSize(Math.min(map.size(), maxItems));
        int count = 0;
        int index = 0;
        for(String key : keys)
        {
            if(index < scrollBar.getCurrentScroll())
            {
                index++;
                continue;
            }

            final int baseY = locationY + (count) * itemHeight + 1;
            getFont().drawStringWithShadow(key, locationX + 2, baseY + 6, 0xFFFFFFFF);

            final ControlTextBox textBox = map.get(key);
            textBox.setEnabled(true);
            textBox.setLocation(locationX + baseWidth / 2, baseY);
            textBox.setSize(baseWidth / 2, 20);
            textBox.draw(mouseX, mouseY);
            count++;
            index++;
            if(count >= maxItems)
                break;
        }

    }

    @Override
    public void update()
    {
        if(!isEnable)
            return;
        scrollBar.update();
        for(String key : map.keySet())
        {
            final ControlTextBox textBox = map.get(key);
            textBox.update();
        }
    }

    public void clear()
    {
        map.clear();
        keys.clear();
        scrollBar.setMaxScroll(map.size());
    }

    public String getText(String key)
    {
        return map.get(key).getText();
    }

    public void setText(String key, String text)
    {
        if(map.containsKey(key))
        {
            map.get(key).setText(text);
            return;
        }
        ControlTextBox textBox = new ControlTextBox(getGui(),0,0,1,1);
        textBox.setEnabled(false);
        textBox.setText(text);
        map.put(key, textBox);
        keys.add(key);
        scrollBar.setMaxScroll(map.size());
        //initGui();
    }

    public void endSettingKeys()
    {
        Collections.sort(keys);
    }

    public Map<String, String> getKeyMap()
    {
        Map<String, String> dat = new HashMap<>();
        for(String key : map.keySet())
        {
            dat.put(key, map.get(key).getText());
        }
        return dat;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(!isEnable)
            return;
        scrollBar.mouseClicked(mouseX, mouseY, button);
        for(ControlTextBox  val : map.values())
        {
            val.focusCheck(mouseX, mouseY, button);
            val.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        if(!isEnable)
            return;
        scrollBar.mouseClickMove(mouseX, mouseY, button, time);
        for(ControlTextBox  val : map.values())
        {
            val.mouseClickMove(mouseX, mouseY, button, time);
        }
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode)
    {
        if(!isEnable)
            return;
        scrollBar.mouseMovedOrUp(mouseX, mouseY, mode);
        for(ControlTextBox  val : map.values())
        {
            val.mouseMovedOrUp(mouseX, mouseY, mode);
        }
    }

    @Override
    public void mouseScroll(int mouseScroll)
    {
        if(!isEnable)
            return;
        scrollBar.mouseScroll(mouseScroll);
        for(ControlTextBox  val : map.values())
        {
            val.mouseScroll(mouseScroll);
        }
    }

    @Override
    public void keyTyped(char par1, int par2)
    {
        if(!isEnable)
            return;
        scrollBar.keyTyped(par1, par2);
        for(ControlTextBox  val : map.values())
        {
            val.keyTyped(par1, par2);
        }
    }
}
