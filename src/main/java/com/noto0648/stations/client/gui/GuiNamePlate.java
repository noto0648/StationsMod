package com.noto0648.stations.client.gui;

import com.noto0648.stations.*;
import com.noto0648.stations.client.gui.control.ControlButton;
import com.noto0648.stations.client.gui.control.ControlCheckBox;
import com.noto0648.stations.client.gui.control.ControlListBox;
import com.noto0648.stations.client.gui.control.ControlTextBox;
import com.noto0648.stations.client.texture.NewFontRenderer;
import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateRegister;
import com.noto0648.stations.packet.PacketSendPlate;
import com.noto0648.stations.tile.TileEntityNamePlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */
public class GuiNamePlate extends GuiScreenBase implements IGui
{
    private ControlListBox plateList;
    private ControlListBox textList;
    private ControlTextBox field;
    private ControlButton textureButton;
    private ControlButton doneButton;
    private ControlCheckBox lightCheck;

    private TileEntityNamePlate tile;
    private Map<String, String> strMaps = new HashMap<String, String>();

    private List<String> textures = new ArrayList<String>();

    private int textureIndex;

    public GuiNamePlate(TileEntityNamePlate tileEntityNamePlate)
    {
        tile = tileEntityNamePlate;

        field = (new ControlTextBox(this, 220, 100, 200, 20)
        {
            @Override
            public void textChanged()
            {
                List<String> strs = new ArrayList();
                if(plateList.selectedIndex != -1 && textList.selectedIndex != -1)
                {
                    NamePlateRegister.INSTANCE.getNamePlates().get(plateList.selectedIndex).init(strs);
                    strMaps.put(strs.get(textList.selectedIndex), field.getText());
                }
            }
        }
        );
        field.setEnabled(false);
        controlList.add(field);

        plateList = (new ControlListBox(this, 10, 10, width / 2 - 20, height / 2 - 15)
        {
            @Override
            public void selectChanged()
            {
                textList.items.clear();
                textList.selectedIndex = -1;
                strMaps.clear();
                if(selectedIndex != -1)
                {
                    List<String> strs = new ArrayList();
                    NamePlateRegister.INSTANCE.getNamePlates().get(selectedIndex).init(strs);
                    for(int i = 0; i < strs.size(); i++)
                    {
                        textList.items.add(strs.get(i));
                        if(!strMaps.containsKey(strs.get(i)))
                        {
                            strMaps.put(strs.get(i), "");
                        }

                    }
                }
            }
        });
        controlList.add(plateList);
        textList = (new ControlListBox(this, 10, height / 2 + 10, width / 2 - 20, height / 2 - 15)
        {
            @Override
            public void selectChanged()
            {
                if(selectedIndex != -1)
                {
                    field.setEnabled(true);
                    List<String> strs = new ArrayList();
                    NamePlateRegister.INSTANCE.getNamePlates().get(plateList.selectedIndex).init(strs);

                    field.setText(strMaps.get(strs.get(selectedIndex)));
                }
                else
                {
                    field.setEnabled(false);
                }
            }
        });
        controlList.add(textList);
        List<NamePlateBase> plates = NamePlateRegister.INSTANCE.getNamePlates();
        for(int i = 0; i < plates.size(); i++)
        {
            String plateName = plates.get(i).getName();
            plateList.items.add(plateName);

            if(plateName.equalsIgnoreCase(tile.currentType))
            {

                List<String> strs = new ArrayList();
                plates.get(i).init(strs);

                plateList.setSelectedIndex(i);
                for(int j = 0; j < tile.stringList.size(); j++)
                {
                    strMaps.put(tile.keyList.get(j), tile.stringList.get(j));
                }
            }
        }

        textures.add("DefaultTexture");
        for(int i = 0; i < NewFontRenderer.platesImages.size(); i++)
        {
            textures.add(NewFontRenderer.platesImages.get(i));
        }

        for(int i = 0; i < textures.size(); i++)
        {
            if(textures.get(i).equalsIgnoreCase(tile.texture))
            {
                textureIndex = i;
            }
        }

        textureButton = (new ControlButton(this, width / 2 + 10, 10, 200, 20, "TEX:" + new File(textures.get(textureIndex)).getName())
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                if(button == 0)
                {
                    textureIndex++;
                    textureIndex = textureIndex % textures.size();

                }
                else if(button == 1)
                {
                    textureIndex += (textures.size() - 1);
                    textureIndex = textureIndex % textures.size();
                }
                textureButton.setText("TEX:" +  new File(textures.get(textureIndex)).getName());
            }
        });
        controlList.add(textureButton);
        doneButton = (new ControlButton(this, width / 2 + 10, height / 2 + 60, 200, 20, "Done")
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                Stations.packetDispatcher.sendToServer(new PacketSendPlate(tile.xCoord, tile.yCoord, tile.zCoord, plateList.getText(), strMaps, textures.get(textureIndex), lightCheck.getCheck()));
                mc.displayGuiScreen((GuiScreen)null);
            }
        });
        controlList.add(doneButton);

        lightCheck = (new ControlCheckBox(this, width / 2 + 10, height / 2 + 10, "Shining"));
        lightCheck.setCheck(tile.light);
        controlList.add(lightCheck);
    }


    @Override
    protected void resize()
    {
        field.setLocation(width / 2 + 10, 60);
        textureButton.setLocation(width / 2 + 10, 10);
        doneButton.setLocation(width / 2 + 10, height / 2 + 60);
        plateList.setSize(width / 2 -20, height / 2 - 15);
        textList.locationY = height / 2 + 10;
        textList.setSize(width / 2 - 20, height / 2 - 15);
        lightCheck.setLocation(width / 2 + 10, height / 2 + 10);
    }

    @Override
    protected void paint(int mouseX, int mouseY) {}



}
