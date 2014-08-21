package com.noto0648.stations.client.gui;

import com.noto0648.stations.container.ContainerTicketCase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/21.
 */
@SideOnly(Side.CLIENT)
public class GuiTicketCase extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("notomod", "textures/gui/ticket_case.png");

    public GuiTicketCase(EntityPlayer player)
    {
        super(new ContainerTicketCase(player.inventory, player.getCurrentEquippedItem(), player.worldObj));
        xSize = 212;
        ySize = 133;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString("TicketCase", 8, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
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
