package com.noto0648.stations.client.gui;

import com.noto0648.stations.*;
import com.noto0648.stations.client.gui.control.ControlListBox;
import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.common.MarkDataComparator;
import com.noto0648.stations.common.MinecraftDate;
import com.noto0648.stations.packet.PacketSendMarkData;
import com.noto0648.stations.tile.TileEntityMarkMachine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Noto on 14/08/06.
 */
public class GuiMarkMachine extends GuiScreen implements IGui
{

    public List<MarkData> markDataList = new ArrayList();
    private World mainWorld;
    private TileEntityMarkMachine tile;
    private GuiTextField timeText;
    private GuiTextField typeText;
    private GuiTextField whereText;

    private ControlListBox listBox;

    public GuiMarkMachine(TileEntityMarkMachine tileEntityMarkMachine)
    {
        listBox = new ControlListBox(this, 10, 10, width / 2 - 20, height - 20);
        tile = tileEntityMarkMachine;
        mainWorld = tileEntityMarkMachine.getWorldObj();
        markDataList = tileEntityMarkMachine.getMarkDataList();

        for(int i = 0; i < markDataList.size(); i++)
            listBox.items.add(markDataList.get(i).toString());
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        buttonList.add(new GuiButton(0, width / 2 + 10, 10, 100, 20, "Remove"));
        buttonList.add(new GuiButton(1, width / 2 + 110, 10, 100, 20, "Add"));

        timeText = new GuiTextField(this.fontRendererObj, width / 2 + 70, 60, 140, 20);
        typeText = new GuiTextField(this.fontRendererObj, width / 2 + 70, 90, 140, 20);
        whereText = new GuiTextField(this.fontRendererObj, width / 2 +70, 120, 140, 20);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        listBox.width = width / 2 - 20;
        listBox.height = height - 20;
        listBox.update();
    }

    @Override
    protected void actionPerformed(GuiButton btn)
    {
        if(btn.id == 0)
        {
            if(listBox.selectedIndex > -1 && listBox.selectedIndex < markDataList.size())
            {
                markDataList.remove(listBox.selectedIndex);
                listBox.items.remove(listBox.selectedIndex);
                listReload();
                Stations.packetDispatcher.sendToServer(new PacketSendMarkData(markDataList, tile.xCoord, tile.yCoord, tile.zCoord));
            }
        }
        if(btn.id == 1)
        {
            String time_text = timeText.getText();
            if(time_text.contains(":") && time_text.split(":").length == 2 && whereText.getText() != null && whereText.getText().length() != 0 && typeText.getText() != null && typeText.getText().length() != 0)
            {
                String[] splits = time_text.split(":");
                int hour = Integer.valueOf(splits[0]);
                int mins = Integer.valueOf(splits[1]);
                MarkData md = new MarkData(hour, mins, whereText.getText(), typeText.getText());
                markDataList.add(md);
                listReload();
                Stations.packetDispatcher.sendToServer(new PacketSendMarkData(markDataList, tile.xCoord, tile.yCoord, tile.zCoord));
                timeText.setText("");
                typeText.setText("");
                whereText.setText("");
            }
        }
    }

    private void listReload()
    {
        listBox.items.clear();
        Collections.sort(markDataList, new MarkDataComparator());
        for(int i = 0; i < markDataList.size(); i++)
            listBox.items.add(markDataList.get(i).toString());
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        //Stations.packetDispatcher.sendToServer(new PacketSendMarkData(markDataList, tile.xCoord, tile.yCoord, tile.zCoord));
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
    {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        listBox.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_)
    {
        super.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        listBox.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        listBox.draw(p_73863_1_, p_73863_2_);

        timeText.drawTextBox();
        typeText.drawTextBox();
        whereText.drawTextBox();

        fontRendererObj.drawString("Time", width / 2 + 10, 60, 0xFFFFFF);
        fontRendererObj.drawString("TrainType", width / 2 + 10, 90, 0xFFFFFF);
        fontRendererObj.drawString("Dest", width / 2 + 10, 120, 0xFFFFFF);

        fontRendererObj.drawString(new MinecraftDate(mainWorld.getWorldTime()).toString(), width - fontRendererObj.getStringWidth(new MinecraftDate(mainWorld.getWorldTime()).toString()), height - 12, 0xFFFFFF);

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        listBox.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

        timeText.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        typeText.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        whereText.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        super.keyTyped(p_73869_1_, p_73869_2_);
        if((p_73869_1_ >= '0' && p_73869_1_ <= '9') || p_73869_1_ == ':' || p_73869_1_ == '\b' )
        {
            timeText.textboxKeyTyped(p_73869_1_, p_73869_2_);
        }
        typeText.textboxKeyTyped(p_73869_1_, p_73869_2_);
        whereText.textboxKeyTyped(p_73869_1_, p_73869_2_);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawRect(int x, int y, int x2, int y2, int color, int color2)
    {
        drawGradientRect(x, y, x2, y2, color, color2);
    }

    @Override
    public FontRenderer getFontRenderer()
    {
        return this.fontRendererObj;
    }
}
