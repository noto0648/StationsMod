package com.noto0648.stations.client.gui;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.container.ContainerTicketCase;
import jdk.nashorn.internal.ir.annotations.Ignore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiTicketCase extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation(StationsMod.MOD_ID, "textures/gui/ticket_case.png");

    public GuiTicketCase(EntityPlayer player)
    {
        super(new ContainerTicketCase(player.inventory, player.inventory.getCurrentItem(), player.world, player));
        xSize = 212;
        ySize = 133;
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_)
    {
        this.drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        this.renderHoveredToolTip(p_drawScreen_1_, p_drawScreen_2_);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRenderer.drawString(I18n.format("container.notomod.ticketcase"), 8, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

    }
}
