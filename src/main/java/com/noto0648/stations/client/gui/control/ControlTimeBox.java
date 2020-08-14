package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.client.gui.IGui;

public class ControlTimeBox extends ControlTextBox
{
    private boolean showDetail;

    public ControlTimeBox(IGui gui)
    {
        super(gui);
    }

    public ControlTimeBox(IGui gui, int x, int y, int w, int h)
    {
        super(gui, x, y, w, h);

        buttonEdit = (new ControlButton(getGui(), locationX + width - height, locationY, height, height, "E")
        {
            @Override
            public void onButtonClick(int button)
            {
                if(button == 0)
                {
                    playClickSound();
                    showDetail = true;
                }
            }
        });
    }


    @Override
    public void draw(int mouseX, int mouseY)
    {
        super.draw(mouseX, mouseY);
    }

    @Override
    public void drawTopLayer(int mouseX, int mouseY)
    {

    }

}
