package com.noto0648.stations.client.gui;

import com.noto0648.stations.*;
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

    private GuiTextField field;
    private TileEntityNamePlate tile;
    private Map<String, String> strMaps = new HashMap<String, String>();

    private List<String> textures = new ArrayList<String>();

    private int textureIndex;

    public GuiNamePlate(TileEntityNamePlate tileEntityNamePlate)
    {
        tile = tileEntityNamePlate;

        //controlList.add(new ControlButton(this, 220, 100, 200, 20, "Done"));
        controlList.add(new ControlTextBox(this, 220, 100, 200, 20));

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

    }


    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        if(field == null)
        {
            field = new GuiTextField(this.fontRendererObj, width / 2 + 10, 60, 200, 20);
            field.setEnabled(false);
        }
        else
        {
            field = new GuiTextField(this.fontRendererObj, width / 2 + 10, 60, 200, 20);
        }
        buttonList.add(new GuiButton(0, width / 2 + 10, 10, 200, 20, "TEX:" + new File(textures.get(textureIndex)).getName())
        {
            @Override
            public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
            {
                if(this.enabled && this.visible && p_146116_2_ >= this.xPosition && p_146116_3_ >= this.yPosition && p_146116_2_ < this.xPosition + this.width && p_146116_3_ < this.yPosition + this.height)
                {
                    return true;
                }
                return false;
            }
        });
        buttonList.add(new GuiButton(1, width / 2 + 10, height / 2 + 30, 200, 20, "Done"));

        plateList.setSize(width / 2 -20, height / 2 - 15);
        textList.locationY = height / 2 + 10;
        textList.setSize(width / 2 - 20, height / 2 - 15);
    }


    @Override
    public void updateScreen()
    {
        super.updateScreen();
        //plateList.width = width / 2 - 20;
        //plateList.height = height / 2 - 15;



        plateList.update();
        textList.update();
    }

    @Override
    protected void paint(int mouseX, int mouseY) {

    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        plateList.draw(p_73863_1_, p_73863_2_);
        textList.draw(p_73863_1_, p_73863_2_);
        field.drawTextBox();
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            textureIndex++;
            textureIndex = textureIndex % textures.size();

            button.displayString = "TEX:" +  new File(textures.get(textureIndex)).getName();
        }
        if(button.id == 1)
        {
            Stations.packetDispatcher.sendToServer(new PacketSendPlate(tile.xCoord, tile.yCoord, tile.zCoord, plateList.getText(), strMaps, textures.get(textureIndex)));
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }


    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
    {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        plateList.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        textList.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_)
    {
        super.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        plateList.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        textList.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        plateList.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        textList.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        field.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        super.keyTyped(p_73869_1_, p_73869_2_);
        field.textboxKeyTyped(p_73869_1_, p_73869_2_);
        List<String> strs = new ArrayList();
        if(plateList.selectedIndex != -1 && textList.selectedIndex != -1)
        {
            NamePlateRegister.INSTANCE.getNamePlates().get(plateList.selectedIndex).init(strs);
            strMaps.put(strs.get(textList.selectedIndex), field.getText());
        }
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
