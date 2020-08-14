package com.noto0648.stations.client.gui;

import com.noto0648.stations.client.gui.control.Control;
import com.noto0648.stations.client.gui.control.ControlButton;
import com.noto0648.stations.client.gui.control.ControlCheckBox;
import com.noto0648.stations.client.gui.control.ControlTextBox;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.packet.IPacketSender;
import com.noto0648.stations.tiles.TileEntityNumberPlate;
import com.noto0648.stations.tiles.TileEntityStringSeal;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class GuiStringSeal extends GuiScreenBase implements IGui, IPacketSender
{
    private ControlButton button;

    private List<ControlTextBox> textBoxes;
    private List<ControlTextBox> colorBoxes;
    private List<ControlCheckBox> checkBoxes;

    private TileEntityStringSeal tile;
    public GuiStringSeal(TileEntityStringSeal tileEntity)
    {
        tile = tileEntity;
        textBoxes = new ArrayList<>(4);
        colorBoxes = new ArrayList<>(4);
        checkBoxes = new ArrayList<>(4);

        List<String> realColorCodes = new ArrayList<>();
        List<String> realTexts = new ArrayList<>();
        if(tile != null)
        {
            realColorCodes = tile.getColorCodes();
            realTexts = tile.getStrings();
        }

        for(int i = 0; i < 4; i++)
        {
            ControlCheckBox checkBox = new ControlCheckBox(this, width / 2-130, height / 2 - 60 + 30 * i,"");
            if(i < realColorCodes.size())
                checkBox.setCheck(true);
            checkBoxes.add(checkBox);
            controlList.add(checkBox);

            ControlTextBox colorCode = new ControlTextBox(this, width / 2-110, height / 2 - 60 + 30 * i, 100, 20);
            colorCode.setInputLimit("0123456789abcdefABCDEF");
            colorCode.setText("FFFFFF");
            if(i < realColorCodes.size())
            {
                colorCode.setText(realColorCodes.get(i));
            }
            colorBoxes.add(colorCode);
            controlList.add(colorCode);

            ControlTextBox textBox = new ControlTextBox(this, width / 2 + 10, height / 2 - 60 + 30 * i, 160, 20);
            if(i < realTexts.size())
            {
                textBox.setText(realTexts.get(i));
            }
            textBoxes.add(textBox);
            controlList.add(textBox);
        }

        button = (new ControlButton(this, (width - 200) / 2, (height - 20) / 2 + 80, 200, 20, "Done")
        {
            @Override
            public void onButtonClick(int button)
            {
                if(button == 0)
                {
                    playClickSound();
                    Utils.INSTANCE.sendPacket(GuiStringSeal.this);
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
            }
        });
        controlList.add(button);
    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {

    }

    @Override
    protected void resize()
    {
        for(int i = 0; i < 4; i++)
        {
            checkBoxes.get(i).setLocation(width / 2-130, height / 2 - 60 + 30 * i);
            colorBoxes.get(i).setLocation(width / 2-110, height / 2 - 60 + 30 * i);
            textBoxes.get(i).setLocation(width / 2 + 10, height / 2 - 60 + 30 * i);
        }

        button.setLocation((width - 200) / 2, (height - 20) / 2 + 80);
    }

    @Override
    public TileEntity getTile()
    {
        return tile;
    }

    @Override
    public void setSendData(List<Object> list)
    {
        list.add((byte)0x05);
        List<String> strs = new ArrayList<>();
        List<String> colors = new ArrayList<>();

        for(int i = 0; i < 4; i++)
        {
            if(!checkBoxes.get(i).getCheck())
                break;
            strs.add(textBoxes.get(i).getText());
            colors.add(colorBoxes.get(i).getText());
        }
        list.add((byte)strs.size());
        for(int i = 0; i < strs.size(); i++)
        {
            list.add(colors.get(i));
            list.add(strs.get(i));
        }
    }
}
