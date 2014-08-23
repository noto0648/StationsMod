package com.noto0648.stations.common;

import com.noto0648.stations.client.gui.*;
import com.noto0648.stations.container.ContainerTicketCase;
import com.noto0648.stations.tile.TileEntityMarkMachine;
import com.noto0648.stations.tile.TileEntityNamePlate;
import com.noto0648.stations.tile.TileEntityNumberPlate;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/06.
 */
public class StationsGuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == 30)
        {
            return new ContainerTicketCase(player.inventory, player.getCurrentEquippedItem(), world);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == 0)
        {
            return new GuiMarkMachine((TileEntityMarkMachine)world.getTileEntity(x, y, z));
        }
        if(ID == 1)
        {
            return new GuiNamePlate((TileEntityNamePlate)world.getTileEntity(x, y, z));
        }
        if(ID == 2)
        {
            return new GuiTicketMachine(x, y, z);
        }
        if(ID == 3)
        {
            return new GuiNumberPlate((TileEntityNumberPlate)world.getTileEntity(x, y, z));
        }
        if(ID == 30)
        {
            return new GuiTicketCase(player);
        }
        if(ID == 40)
        {
            return new GuiTetris();
        }
        return null;
    }
}
