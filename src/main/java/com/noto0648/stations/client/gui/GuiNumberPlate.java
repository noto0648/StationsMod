package com.noto0648.stations.client.gui;


import com.noto0648.stations.client.gui.control.ControlButton;
import com.noto0648.stations.client.gui.control.ControlTextBox;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.packet.IPacketSender;
import com.noto0648.stations.tile.TileEntityNumberPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * Created by Noto on 14/08/19.
 */
public class GuiNumberPlate extends GuiScreenBase implements IPacketSender
{
    private TileEntityNumberPlate tile;

    private ControlTextBox textBox;
    private ControlButton button;
    private ControlTextBox colorCodeBox;
    private ControlTextBox strColorCodeBox;

    private IPacketSender instance;

    public GuiNumberPlate(TileEntityNumberPlate tileEntityNumberPlate)
    {
        tile = tileEntityNumberPlate;
        instance = this;

        strColorCodeBox = new ControlTextBox(this, (width - 200) / 2, (height - 20) / 2 - 80, 200, 20);
        strColorCodeBox.setText(tile.getStrColorCode());
        strColorCodeBox.setInputLimit("0123456789abcdefABCDEF");
        colorCodeBox = new ControlTextBox(this, (width - 200) / 2, (height - 20) / 2 - 40, 200, 20);
        colorCodeBox.setText(tile.getColorCode());
        colorCodeBox.setInputLimit("0123456789abcdefABCDEF");

        textBox = new ControlTextBox(this, (width - 200) / 2, (height - 20) / 2, 200, 20);
        textBox.setText(tile.getDrawStr());
        button = (new ControlButton(this, (width - 200) / 2, (height - 20) / 2 + 40, 200, 20, "Done")
        {
            @Override
            public void onButtonClick(int button)
            {
                if(button == 0)
                {
                    playClickSound();
                    Utils.INSTANCE.sendPacket(instance);
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
            }
        });
        controlList.add(textBox);
        controlList.add(button);
        controlList.add(colorCodeBox);
        controlList.add(strColorCodeBox);
    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {

    }

    @Override
    protected void resize()
    {
        strColorCodeBox.setLocation((width - 200) / 2, (height - 20) / 2 - 40);
        colorCodeBox.setLocation((width - 200) / 2, (height - 20) / 2 - 80);
        textBox.setLocation((width - 200) / 2, (height - 20) / 2);
        button.setLocation((width - 200) / 2, (height - 20) / 2 + 40);
    }

    @Override
    public TileEntity getTile()
    {
        return tile;
    }

    @Override
    public void setSendData(List<Object> list)
    {
        list.add((byte)0x03);
        list.add(textBox.getText());
        if(colorCodeBox.getText().length()==0)
        {
            colorCodeBox.setText(tile.getColorCode());
        }
        list.add(colorCodeBox.getText().toUpperCase());

        if(strColorCodeBox.getText().length()==0)
        {
            strColorCodeBox.setText(tile.getStrColorCode());
        }
        list.add(strColorCodeBox.getText().toUpperCase());
    }
}
