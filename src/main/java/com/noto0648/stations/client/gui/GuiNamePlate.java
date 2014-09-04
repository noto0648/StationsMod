package com.noto0648.stations.client.gui;

import com.noto0648.stations.*;
import com.noto0648.stations.client.gui.control.*;
import com.noto0648.stations.client.texture.NewFontRenderer;
import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.packet.PacketSendPlate;
import com.noto0648.stations.tile.TileEntityNamePlate;
import net.minecraft.client.gui.GuiScreen;

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
    private ControlComboBox textureComboBox;
    private ControlButton doneButton;
    private ControlCheckBox lightCheck;

    private TileEntityNamePlate tile;
    private Map<String, String> strMaps = new HashMap<String, String>();

    private List<String> textures = new ArrayList<String>();


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
                    NamePlateManager.INSTANCE.getNamePlates().get(plateList.selectedIndex).init(strs);
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
                    NamePlateManager.INSTANCE.getNamePlates().get(selectedIndex).init(strs);
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
                    NamePlateManager.INSTANCE.getNamePlates().get(plateList.selectedIndex).init(strs);

                    field.setText(strMaps.get(strs.get(selectedIndex)));
                }
                else
                {
                    field.setEnabled(false);
                }
            }
        });
        controlList.add(textList);
        List<NamePlateBase> plates = NamePlateManager.INSTANCE.getNamePlates();
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
        for(int i = 0; i < NamePlateManager.platesImages.size(); i++)
        {
            textures.add(NamePlateManager.platesImages.get(i));
        }

        textureComboBox = (new ControlComboBox(this, width / 2 + 10, 10, 200, 20)
        {
            /*
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
                textureButton.setText(new File(textures.get(textureIndex)).getName());
            }*/
        });
        for(int i = 0; i < textures.size(); i++)
            textureComboBox.items.add(new File(textures.get(i)).getName());

        textureComboBox.setSelectedIndex(0);

        for(int i = 0; i < textures.size(); i++)
        {
            if(textures.get(i).equalsIgnoreCase(tile.texture))
            {
                textureComboBox.setSelectedIndex(i);
            }
        }

        doneButton = (new ControlButton(this, width / 2 + 10, height / 2 + 60, 200, 20, "Done")
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();

                tile.currentType = plateList.getText();
                tile.texture = textures.get(textureComboBox.selectedIndex);
                tile.light = lightCheck.getCheck();

                List<String> result = new ArrayList();
                List<String> keyMap = new ArrayList();
                String[] keys = strMaps.keySet().toArray(new String[0]);
                for(int i = 0; i < keys.length; i++)
                {
                    keyMap.add(keys[i]);
                    result.add(strMaps.get(keys[i]));
                }
                tile.stringList = result;
                tile.keyList = keyMap;

                Stations.packetDispatcher.sendToServer(new PacketSendPlate(tile.xCoord, tile.yCoord, tile.zCoord, plateList.getText(), strMaps, textures.get(textureComboBox.selectedIndex), lightCheck.getCheck()));
                mc.displayGuiScreen((GuiScreen)null);
            }
        });
        controlList.add(doneButton);

        lightCheck = (new ControlCheckBox(this, width / 2 + 10, height / 2 + 10, "Shining"));
        lightCheck.setCheck(tile.light);
        controlList.add(lightCheck);

        controlList.add(textureComboBox);
    }


    @Override
    protected void resize()
    {
        field.setLocation(width / 2 + 10, height / 2 + 10);
        textureComboBox.setLocation(width / 2 + 10, 10);
        doneButton.setLocation(width / 2 + 10, height / 2 + 60);
        plateList.setSize(width / 2 -20, height / 2 - 15);
        textList.locationY = height / 2 + 10;
        textList.setSize(width / 2 - 20, height / 2 - 15);
        lightCheck.setLocation(width / 2 + 10, height / 2 + 40);
    }

    @Override
    protected void paint(int mouseX, int mouseY) {}



}
