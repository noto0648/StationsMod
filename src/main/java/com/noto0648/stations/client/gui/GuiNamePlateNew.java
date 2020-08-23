package com.noto0648.stations.client.gui;

import com.noto0648.stations.client.gui.control.*;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.packet.IPacketSender;
import com.noto0648.stations.tiles.TileEntityNamePlate;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class GuiNamePlateNew extends GuiScreenBase implements IGui, IPacketSender
{
    private ControlButton doneButton;
    private ControlNamePlateModelDrawer textureSelector;
    private ControlCheckBox lightCheck;
    private ControlListBox labelPatternList;
    private ControlNamePlateLabelEditor labelEditor;

    private ControlButton labelReverseButton;

    private ControlButton[] tabButtons;
    private ControlGroup[] tabControls;
    private int selectedTab = 0;

    private final TileEntityNamePlate tile;
    private Map<String, String> strMaps = new HashMap<String, String>();

    public GuiNamePlateNew(TileEntityNamePlate tileEntityNamePlate)
    {
        tile = tileEntityNamePlate;

        doneButton = (new ControlButton(this, width / 2 + 10, height / 2 + 90, 200, 20, I18n.format("gui.done"))
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();

                Utils.INSTANCE.sendPacket(GuiNamePlateNew.this);
                mc.displayGuiScreen((GuiScreen)null);
            }
        });
        controlList.add(doneButton);
        final String[] tabsLabels = {I18n.format("gui.notomod.texture"), I18n.format("gui.notomod.label"), I18n.format("gui.notomod.other")};

        tabButtons = new ControlButton[tabsLabels.length];
        tabButtons[0] = (new ControlButton(this, (width / 2 - 100) + 100 * 0, height / 2 - 100, 100, 20, tabsLabels[0])
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                changeTab(0);
            }
        });
        tabButtons[1] = (new ControlButton(this, (width / 2 - 100) + 100 * 1, height / 2 - 100, 100, 20, tabsLabels[1])
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                changeTab(1);
            }
        });
        tabButtons[2] = (new ControlButton(this, (width / 2 - 100) + 100 * 2, height / 2 - 100, 100, 20, tabsLabels[2])
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                changeTab(2);
            }
        });
        tabControls = new ControlGroup[tabButtons.length];
        for(int i = 0; i < tabButtons.length; i++)
        {

            controlList.add(tabButtons[i]);
            tabControls[i] = new ControlGroup(this, 0,0);
            tabControls[i].setEnabled(false);
            controlList.add(tabControls[i]);
        }
        textureSelector = new ControlNamePlateModelDrawer(this, tileEntityNamePlate);
        tabControls[0].add(textureSelector);


        labelPatternList = (new ControlListBox(this, 10, 10, width / 2 - 20, height / 2 - 15)
        {
            @Override
            public void selectChanged()
            {
                if(selectedIndex != -1)
                {
                    Map<String, String> newStrMap = new HashMap<>();

                    List<String> strs = new ArrayList();
                    NamePlateManager.INSTANCE.getNamePlates().get(selectedIndex).init(strs);
                    Collections.sort(strs);
                    for(int i = 0; i < strs.size(); i++)
                    {
                        if(newStrMap.containsKey(strs.get(i)))
                            continue;

                        if(strMaps.containsKey(strs.get(i)))
                        {
                            newStrMap.put(strs.get(i), strMaps.get(strs.get(i)));
                        }
                        else
                        {
                            newStrMap.put(strs.get(i), "");
                        }
                    }

                    strMaps = newStrMap;

                }
                setupLabelEditor();
            }
        });
        List<NamePlateBase> plates = NamePlateManager.INSTANCE.getNamePlates();
        for(int i = 0; i < plates.size(); i++)
        {
            final String plateId = plates.get(i).getNamePlateId();
            final String plateName = plates.get(i).getDisplayName();
            labelPatternList.items.add(plateName);

            if(plateId.equalsIgnoreCase(tile.currentType))
            {
                List<String> strs = new ArrayList();
                plates.get(i).init(strs);

                labelPatternList.setSelectedIndex(i);
                for(int j = 0; j < tile.stringList.size(); j++)
                {
                    strMaps.put(tile.keyList.get(j), tile.stringList.get(j));
                }
            }
        }

        labelPatternList.setEnabled(false);
        tabControls[1].add(labelPatternList);

        labelEditor = new ControlNamePlateLabelEditor(this);
        labelEditor.setEnabled(false);
        tabControls[1].add(labelEditor);
        setupLabelEditor();

        labelReverseButton = new ControlButton(this, 0,0,10,10, "R")
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                clickLabelReverse();
            }
        };
        labelReverseButton.setEnabled(false);
        tabControls[1].add(labelReverseButton);

        lightCheck = (new ControlCheckBox(this, width / 2 - 80, height / 2 - 30, "Shining"));
        lightCheck.setCheck(tile.light);
        lightCheck.setEnabled(false);
        tabControls[2].add(lightCheck);


        changeTab(0);
    }

    protected void setupLabelEditor()
    {
        if(labelEditor == null)
            return;
        labelEditor.clear();
        for(String key : strMaps.keySet())
        {
            labelEditor.setText(key, strMaps.get(key));
        }
        labelEditor.endSettingKeys();
    }

    protected void changeTab(int tabIndex)
    {
        tabControls[selectedTab].setEnabled(false);
        tabControls[tabIndex].setEnabled(true);
        selectedTab = tabIndex;

        if(tabIndex == 0)
        {
            if(labelPatternList.getSelectedIndex() != -1)
            {
                textureSelector.setCurrentLabelMap(NamePlateManager.INSTANCE.getNamePlates().get(labelPatternList.getSelectedIndex()).getNamePlateId());
            }
            textureSelector.setPreviewStringMap(labelEditor.getKeyMap());
        }
    }

    protected void clickLabelReverse()
    {
        final int sel = labelPatternList.getSelectedIndex();

        final Map<String,String> newMap = labelEditor.getKeyMap();
        NamePlateManager.INSTANCE.getNamePlates().get(sel).reverseLabels(newMap);

        for(final String key : newMap.keySet())
        {
            final String val = newMap.get(key);
            if(!val.equals(labelEditor.getText(key)))
                labelEditor.setText(key, val);
        }
    }

    @Override
    protected void resize()
    {
        doneButton.setLocation(width - 210, height  - 30);
        doneButton.setSize(200, 20);

        for(int i = 0; i < tabButtons.length; i++)
        {
            tabButtons[i].setLocation((width / 2 - 180) + 100 * i, height / 2 - 110);
            tabControls[i].setLocation(0, 0);
        }

        textureSelector.setLocation((width / 2 - 180), height / 2 - 80);
        textureSelector.setSize(360, 160);
        lightCheck.setLocation(width / 2 - 80, height / 2 - 30);

        labelPatternList.setLocation((width / 2 - 180), height / 2 - 70);
        labelPatternList.setSize((width / 2 - 15) - (width / 2 - 180), height / 2 - 30);

        labelEditor.setLocation((width / 2), height / 2 - 70);
        labelEditor.setSize((width - 15) - (width / 2), height - 35 - (height / 2 - 80) - 10);

        labelReverseButton.setLocation((width - 15) - 15, height / 2 - 70 - 12);

        //labelPatternList.locationY = height / 2 + 10;
        //labelPatternList.setSize(width / 2 - 20, height / 2 - 15);

    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {
        if(selectedTab == 1)
        {
            getFontRenderer().drawStringWithShadow(I18n.format("gui.notomod.labelpattern") + ":", width / 2 - 180, height / 2 -80, 0xFFFFFFFF);
        }
    }


    @Override
    public TileEntity getTile()
    {
        return tile;
    }

    @Override
    public void setSendData(List<Object> list)
    {
        list.add((byte)0x01);


        final int sel = labelPatternList.getSelectedIndex();
        if(sel == -1)
            list.add("");
        else
            list.add(NamePlateManager.INSTANCE.getNamePlates().get(sel).getNamePlateId());
        list.add(textureSelector.getSelected());
        list.add(lightCheck.getCheck());

        list.add(strMaps.size());
        for(String key : strMaps.keySet())
        {
            list.add(key);
            list.add(labelEditor.getText(key));
        }
    }
}
