package com.noto0648.stations.client.gui;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.client.gui.control.ControlButton;
import com.noto0648.stations.client.gui.control.ControlListBox;
import com.noto0648.stations.client.gui.control.ControlTextBox;
import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.common.MarkDataComparator;
import com.noto0648.stations.common.MinecraftDate;
import com.noto0648.stations.items.ItemDiagramBook;
import com.noto0648.stations.packet.PacketDiagramBook;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiDiagramBook extends GuiScreenBase
{
    public List<MarkData> markDataList;
    private final World mainWorld;
    //private TileEntityMarkMachine tile;

    private final EntityPlayer entityPlayer;
    private final ControlTextBox timeText;
    private final ControlTextBox typeText;
    private final ControlTextBox whereText;
    private final ControlListBox listBox;
    private final ControlButton removeButton;
    private final ControlButton addButton;
    private final ControlButton doneButton;

    private final ItemStack diagramBook;

    private boolean isAddMode;
    private boolean isEditMode;

    public GuiDiagramBook(EntityPlayer player)
    {
        listBox = new ControlListBox(this, 10, 22, width / 2 - 20, height - 20)
        {
            @Override
            public void selectChanged()
            {
                setEditMode(false);
                if(selectedIndex < 0 || selectedIndex >= items.size())
                    return;

                final MarkData md = markDataList.get(selectedIndex);
                timeText.setText(md.getTimeString());
                whereText.setText(md.dest);
                typeText.setText(md.type);
                setEditMode(true);

            }
        };

        //tile = tileEntityMarkMachine;
        entityPlayer = player;
        mainWorld = player.getEntityWorld();

        if(entityPlayer.getHeldItemMainhand().getItem() instanceof ItemDiagramBook)
        {
            diagramBook = entityPlayer.getHeldItemMainhand();
        }
        else
        {
            diagramBook = entityPlayer.getHeldItemOffhand();
        }

        markDataList = new ArrayList<>();

        //System.out.println(diagramBook);
        if(diagramBook != null || diagramBook != ItemStack.EMPTY)
        {
            ItemDiagramBook.readFromNBT(diagramBook, markDataList);
        }
        //markDataList = tileEntityMarkMachine.getMarkDataList();
/*
        for(int i = 0; i < markDataList.size(); i++)
            listBox.items.add(markDataList.get(i).toString());
        */
        listReload();

        timeText = new ControlTextBox(this, width / 2 + 70, 60, 140, 20);
        timeText.setInputLimit("0123456789:");
        typeText = new ControlTextBox(this, width / 2 + 70, 90, 140, 20);
        whereText = new ControlTextBox(this, width / 2 + 70, 120, 140, 20);
        timeText.setEnabled(false);
        typeText.setEnabled(false);
        whereText.setEnabled(false);

        doneButton = (new ControlButton(this, width / 2 + 10, 10, 100, 20, I18n.format("gui.done"))
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();

                if(!isAddMode && !isEditMode)
                    return;

                String time_text = timeText.getText();
                if(time_text.contains(":") && time_text.split(":").length == 2 && whereText.getText() != null && whereText.getText().length() != 0 && typeText.getText() != null && typeText.getText().length() != 0)
                {
                    String[] splits = time_text.split(":");
                    int hour = Integer.valueOf(splits[0]);
                    int mins = Integer.valueOf(splits[1]);

                    if(isAddMode)
                    {
                        final MarkData md = new MarkData(hour, mins, whereText.getText(), typeText.getText());
                        markDataList.add(md);
                    }
                    if(isEditMode)
                    {
                        final MarkData md = markDataList.get(listBox.getSelectedIndex());
                        md.dest = whereText.getText();
                        md.hours = hour;
                        md.minutes = mins;
                        md.type = typeText.getText();
                    }

                    listReload();
                    timeText.setText("");
                    typeText.setText("");
                    whereText.setText("");
                    setAddMode(false);
                    setEditMode(false);
                }

            }

        });
        doneButton.setEnabled(false);

        removeButton = (new ControlButton(this, width / 2 + 10, 10, 100, 20, I18n.format("gui.notomod.remove"))
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                if(listBox.selectedIndex > -1 && listBox.selectedIndex < markDataList.size())
                {
                    markDataList.remove(listBox.selectedIndex);
                    listBox.items.remove(listBox.selectedIndex);
                    if(isEditMode)
                        setEditMode(false);
                    listReload();
                }
            }
        });

        addButton = (new ControlButton(this, width / 2 + 110, 10, 100, 20, I18n.format("gui.notomod.new"))
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();

                if(!isAddMode)
                {
                    setEditMode(false);
                    setAddMode(true);
                    timeText.setText("");
                    whereText.setText("");
                    typeText.setText("");
                    return;
                }

                if(isAddMode || isEnable)
                {
                    setAddMode(false);
                    setEditMode(false);
                    return;
                }

            }
        });

        controlList.add(listBox);
        controlList.add(timeText);
        controlList.add(typeText);
        controlList.add(whereText);
        controlList.add(removeButton);
        controlList.add(addButton);
        controlList.add(doneButton);

    }

    private void setAddMode(boolean mode)
    {
        isAddMode = mode;
        addButton.setText(mode ? I18n.format("gui.cancel") : I18n.format("gui.notomod.new"));
        doneButton.setEnabled(mode);
        timeText.setEnabled(mode);
        whereText.setEnabled(mode);
        typeText.setEnabled(mode);
    }

    private void setEditMode(boolean mode)
    {
        isEditMode = mode;
        addButton.setText(mode ? I18n.format("gui.cancel") : I18n.format("gui.notomod.new"));
        doneButton.setEnabled(mode);
        timeText.setEnabled(mode);
        whereText.setEnabled(mode);
        typeText.setEnabled(mode);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        //Stations.packetDispatcher.sendToServer(new PacketSendMarkData(markDataList, tile.xCoord, tile.yCoord, tile.zCoord));

        if(diagramBook != null && diagramBook != ItemStack.EMPTY)
        {
            ItemDiagramBook.writeToNBT(diagramBook, markDataList);
            //System.out.println("diagram written");

            StationsMod.PACKET_DISPATCHER.sendToServer(new PacketDiagramBook(diagramBook));
        }
    }

    @Override
    protected void resize()
    {
        timeText.setLocation(width / 2 + 70, 60);
        typeText.setLocation(width / 2 + 70, 90);
        whereText.setLocation(width / 2 + 70, 120);
        addButton.setLocation(width / 2 + 10, 10);
        removeButton.setLocation(width / 2 + 110, 10);
        listBox.setSize(width / 2 - 20, height - 32);
        doneButton.setLocation(width / 2 + 35, 150);

    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {
        getFontRenderer().drawString(I18n.format("gui.notomod.trains") + ":", 10, 10, 0xFFFFFF);

        if(isAddMode || isEditMode)
        {
            getFontRenderer().drawString(I18n.format("gui.notomod.time"), width / 2 + 10, 60, 0xFFFFFF);
            getFontRenderer().drawString(I18n.format("gui.notomod.traintype"), width / 2 + 10, 90, 0xFFFFFF);
            getFontRenderer().drawString(I18n.format("gui.notomod.destination"), width / 2 + 10, 120, 0xFFFFFF);
        }

        getFontRenderer().drawString(new MinecraftDate(mainWorld.getWorldTime()).toString(), width - getFontRenderer().getStringWidth(new MinecraftDate(mainWorld.getWorldTime()).toString()), height - 12, 0xFFFFFF);
    }

    private void listReload()
    {
        listBox.items.clear();
        listBox.selectedIndex = -1;
        Collections.sort(markDataList, new MarkDataComparator());
        for(int i = 0; i < markDataList.size(); i++)
            listBox.items.add(markDataList.get(i).toString());
    }


}
