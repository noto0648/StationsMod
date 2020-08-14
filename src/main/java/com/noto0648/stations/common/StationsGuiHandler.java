package com.noto0648.stations.common;

import com.noto0648.stations.client.gui.*;
import com.noto0648.stations.container.ContainerMARS;
import com.noto0648.stations.container.ContainerTicketCase;
import com.noto0648.stations.tiles.TileEntityNamePlate;
import com.noto0648.stations.tiles.TileEntityNumberPlate;
import com.noto0648.stations.tiles.TileEntityStringSeal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class StationsGuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        if(id == 6)
        {
            return new ContainerMARS(entityPlayer.inventory);
        }
        if(id == 30)
        {
            return new ContainerTicketCase(entityPlayer.inventory, entityPlayer.inventory.getCurrentItem(), world, entityPlayer);
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        if(id == 1)
        {
            return new GuiNamePlateNew((TileEntityNamePlate)world.getTileEntity(new BlockPos(x, y, z)));
        }
        if(id == 3)
        {
            return new GuiNumberPlate((TileEntityNumberPlate)world.getTileEntity(new BlockPos(x, y, z)));
        }
        if(id == 5)
        {
            return new GuiStringSeal((TileEntityStringSeal)world.getTileEntity(new BlockPos(x, y, z)));
        }
        if(id == 6)
        {
            return new GuiMARS(entityPlayer);
        }
        if(id == 30)
        {
            return new GuiTicketCase(entityPlayer);
        }
        if(id == 31)
        {
            return new GuiDiagramBook(entityPlayer);
        }
        return null;
    }
}
