package com.noto0648.stations.client.gui;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.client.gui.control.ControlTextBox;
import com.noto0648.stations.container.ContainerMARS;
import com.noto0648.stations.packet.PacketMARSTicket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GuiMARS extends GuiContainerBase  implements IContainerListener
{
    private final ControlTextBox src;
    private final ControlTextBox dest;

    private final InventoryPlayer playerInventory;
    public GuiMARS(EntityPlayer player)
    {
        super(new ContainerMARS(player.inventory));
        playerInventory = player.inventory;
        src = new ControlTextBox(this, (width - 120)/2 - 60, (height-20) / 2 - 60, 120,20 )
        {
            @Override
            public void textChanged()
            {
                textBoxChanged();
            }
        };
        dest = new ControlTextBox(this, (width - 120)/2 + 60, (height-20) / 2 - 60, 120,20 )
        {
            @Override
            public void textChanged()
            {
                textBoxChanged();
            }
        };

        controlList.add(src);
        controlList.add(dest);

    }

    @Override
    public void initGui()
    {
        super.initGui();
        inventorySlots.removeListener(this);
        inventorySlots.addListener(this);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        inventorySlots.removeListener(this);
    }

    private void textBoxChanged()
    {
        ((ContainerMARS)inventorySlots).updateTicket(src.getText(), dest.getText());
        StationsMod.PACKET_DISPATCHER.sendToServer(new PacketMARSTicket(src.getText(), dest.getText()));
    }

    @Override
    protected void paint(int mouseX, int mouseY)
    {
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_drawGuiContainerForegroundLayer_1_, int p_drawGuiContainerForegroundLayer_2_)
    {
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void resize()
    {
        src.setLocation((width - 120)/2 - 80, (height-20) / 2 - 60);
        dest.setLocation((width - 120)/2 - 80, (height-20) / 2 - 30);
    }

    @Override
    protected boolean closeInventoryKey()
    {
        return false;
    }

    @Override
    public void sendAllContents(Container container, NonNullList<ItemStack> nonNullList)
    {

    }

    @Override
    public void sendSlotContents(Container container, int i, ItemStack itemStack)
    {

    }

    @Override
    public void sendWindowProperty(Container container, int i, int i1) {}
    @Override
    public void sendAllWindowProperties(Container container, IInventory iInventory) {}
}
