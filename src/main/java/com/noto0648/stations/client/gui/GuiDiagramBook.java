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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiDiagramBook extends GuiScreenBase
{
    public List<MarkData> markDataList = new ArrayList();
    private final World mainWorld;
    //private TileEntityMarkMachine tile;

    private final EntityPlayer entityPlayer;
    private final ControlTextBox timeText;
    private final ControlTextBox typeText;
    private final ControlTextBox whereText;
    private final ControlListBox listBox;
    private final ControlButton removeButton;
    private final ControlButton addButton;

    private final ItemStack diagramBook;

    public GuiDiagramBook(EntityPlayer player)
    {
        listBox = new ControlListBox(this, 10, 10, width / 2 - 20, height - 20);
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

        for(int i = 0; i < markDataList.size(); i++)
            listBox.items.add(markDataList.get(i).toString());

        timeText = new ControlTextBox(this, width / 2 + 70, 60, 140, 20);
        timeText.setInputLimit("0123456789:");
        typeText = new ControlTextBox(this, width / 2 + 70, 90, 140, 20);
        whereText = new ControlTextBox(this, width / 2 + 70, 120, 140, 20);

        removeButton = (new ControlButton(this, width / 2 + 10, 10, 100, 20, "Remove")
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                if(listBox.selectedIndex > -1 && listBox.selectedIndex < markDataList.size())
                {
                    markDataList.remove(listBox.selectedIndex);
                    listBox.items.remove(listBox.selectedIndex);
                    listReload();
                }
            }
        });

        addButton = (new ControlButton(this, width / 2 + 110, 10, 100, 20, "Add")
        {
            @Override
            public void onButtonClick(int button)
            {
                playClickSound();
                String time_text = timeText.getText();
                if(time_text.contains(":") && time_text.split(":").length == 2 && whereText.getText() != null && whereText.getText().length() != 0 && typeText.getText() != null && typeText.getText().length() != 0)
                {
                    String[] splits = time_text.split(":");
                    int hour = Integer.valueOf(splits[0]);
                    int mins = Integer.valueOf(splits[1]);
                    MarkData md = new MarkData(hour, mins, whereText.getText(), typeText.getText());
                    markDataList.add(md);
                    listReload();
                    timeText.setText("");
                    typeText.setText("");
                    whereText.setText("");
                }
            }
        });

        controlList.add(listBox);
        controlList.add(timeText);
        controlList.add(typeText);
        controlList.add(whereText);
        controlList.add(removeButton);
        controlList.add(addButton);

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
        removeButton.setLocation(width / 2 + 10, 10);
        addButton.setLocation(width / 2 + 110, 10);
        listBox.setSize(width / 2 - 20, height - 20);
    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {
        getFontRenderer().drawString("Time", width / 2 + 10, 60, 0xFFFFFF);
        getFontRenderer().drawString("TrainType", width / 2 + 10, 90, 0xFFFFFF);
        getFontRenderer().drawString("Dest", width / 2 + 10, 120, 0xFFFFFF);

        getFontRenderer().drawString(new MinecraftDate(mainWorld.getWorldTime()).toString(), width - getFontRenderer().getStringWidth(new MinecraftDate(mainWorld.getWorldTime()).toString()), height - 12, 0xFFFFFF);
    }

    private void listReload()
    {
        listBox.items.clear();
        Collections.sort(markDataList, new MarkDataComparator());
        for(int i = 0; i < markDataList.size(); i++)
            listBox.items.add(markDataList.get(i).toString());
    }


}
