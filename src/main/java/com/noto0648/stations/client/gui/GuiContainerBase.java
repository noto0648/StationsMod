package com.noto0648.stations.client.gui;

import com.noto0648.stations.client.gui.control.Control;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GuiContainerBase extends GuiContainer implements IGui
{
    protected List<Control> controlList = new ArrayList();

    private boolean isShowContextMenu = false;
    private int contextMenuId = -1;

    public GuiContainerBase(Container p_i1072_1_)
    {
        super(p_i1072_1_);
    }

    protected abstract void paint(int mouseX, int mouseY);
    protected abstract void resize();

    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        super.initGui();
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).controlId = i;
        }
        resize();
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).initGui();
        }
    }
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        if(doesDrawDarkScreen())
        {
            this.drawDefaultBackground();
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        renderHoveredToolTip(p_73863_1_, p_73863_2_);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int x, int y)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawGuiSlots();

        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).draw(x, y);
        }
        paint(x, y);

        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).drawTopLayer(x, y);
        }

    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        if(!isShowContextMenu)
        {
            for(int i = 0; i < controlList.size(); i++)
            {
                controlList.get(i).focusCheck(p_73864_1_, p_73864_2_, p_73864_3_);
                controlList.get(i).mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
            }
        }
        else
        {
            controlList.get(contextMenuId).focusCheck(p_73864_1_, p_73864_2_, p_73864_3_);
            controlList.get(contextMenuId).mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        }
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException
    {
//        /super.keyTyped(p_73869_1_, p_73869_2_);

        if (p_73869_2_ == 1 || (this.mc.gameSettings.keyBindInventory.isActiveAndMatches(p_73869_2_) && closeInventoryKey()) ) {
            this.mc.player.closeScreen();
        }

        this.checkHotbarKeys(p_73869_2_);
        if (this.getSlotUnderMouse() != null && this.getSlotUnderMouse().getHasStack()) {
            if (this.mc.gameSettings.keyBindPickBlock.isActiveAndMatches(p_73869_2_)) {
                this.handleMouseClick(this.getSlotUnderMouse(), this.getSlotUnderMouse().slotNumber, 0, ClickType.CLONE);
            } else if (this.mc.gameSettings.keyBindDrop.isActiveAndMatches(p_73869_2_)) {
                this.handleMouseClick(this.getSlotUnderMouse(), this.getSlotUnderMouse().slotNumber, isCtrlKeyDown() ? 1 : 0, ClickType.THROW);
            }
        }

        if(!isShowContextMenu)
        {
            for(int i = 0; i < controlList.size(); i++)
            {
                controlList.get(i).keyTyped(p_73869_1_, p_73869_2_);
            }
        }
        else
        {
            controlList.get(contextMenuId).keyTyped(p_73869_1_, p_73869_2_);
        }
    }

    protected boolean closeInventoryKey()
    {
        return true;
    }

    //mouseMovedOrUp
    @Override
    protected void mouseReleased(int p_146286_1_, int p_146286_2_, int p_146286_3_)
    {
        super.mouseReleased(p_146286_1_, p_146286_2_, p_146286_3_);
        if(!isShowContextMenu)
        {
            for(int i = 0; i < controlList.size(); i++)
            {
                controlList.get(i).mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
            }
        }
        else
        {
            controlList.get(contextMenuId).mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }

    @Override
    protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_)
    {
        super.mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        if(!isShowContextMenu)
        {
            for(int i = 0; i < controlList.size(); i++)
            {
                controlList.get(i).mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
            }
        }
        else
        {
            controlList.get(contextMenuId).mouseClickMove(p_146273_1_, p_146273_2_, p_146273_3_, p_146273_4_);
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        if(!isShowContextMenu)
        {
            for(int i = 0; i < controlList.size(); i++)
            {
                controlList.get(i).update();
            }
        }
        else
        {
            controlList.get(contextMenuId).update();
        }
    }

    public Control getFocusControl()
    {
        for(int i = 0; i < controlList.size(); i++)
        {
            boolean focus = controlList.get(i).isFocus;
            if(focus)
            {
                return controlList.get(i);
            }
        }
        return null;
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        for(int i = 0; i < controlList.size(); i++)
        {
            controlList.get(i).onGuiClosed();
        }
    }

    public boolean doesDrawDarkScreen()
    {
        return true;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    @Deprecated
    protected void actionPerformed(GuiButton button) {}

    @Override
    public void drawRect(int x, int y, int x2, int y2, int color, int color2)
    {
        drawGradientRect(x, y, x2, y2, color, color2);
    }

    @Override
    public FontRenderer getFontRenderer()
    {
        return this.fontRenderer;
    }

    public void openRightClickMenu(boolean par1, int par2)
    {
        isShowContextMenu = par1;
        contextMenuId = par2;
    }

    protected void drawGuiSlots()
    {
        if(inventorySlots == null)
            return;
        //GlStateManager.colorMask(true, true, true, true);
        //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        /*
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableRescaleNormal();
        */
        GlStateManager.translate((float)guiLeft, (float)guiTop, 0f);
        //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for(Slot s : inventorySlots.inventorySlots)
        {
            drawRect(s.xPos-1, s.yPos-1, s.xPos + 18, s.yPos + 18, 0xFF8C8C8C,0xFF8C8C8C);
            drawRect(s.xPos-1, s.yPos-1, s.xPos + 17, s.yPos + 17, 0xFF383838,0xFF383838);
            drawRect(s.xPos, s.yPos, s.xPos + 17, s.yPos + 17, 0xFFFFFFFF,0xFFFFFFFF);
            drawRect(s.xPos, s.yPos, s.xPos + 16,  s.yPos + 16, 0xFF8C8C8C,0xFF8C8C8C);
        }
        GlStateManager.popMatrix();
        //GlStateManager.disableAlpha();
    }

}
