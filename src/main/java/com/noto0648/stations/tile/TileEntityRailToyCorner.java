package com.noto0648.stations.tile;

import com.noto0648.stations.tile.TileEntityRailToy;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Noto on 14/09/07.
 */
public class TileEntityRailToyCorner extends TileEntityRailToy
{
    private static ForgeDirection[][] cornerDirections = new ForgeDirection[][]{ {ForgeDirection.EAST, ForgeDirection.SOUTH}, {ForgeDirection.WEST, ForgeDirection.SOUTH}, {ForgeDirection.NORTH, ForgeDirection.WEST},  {ForgeDirection.EAST, ForgeDirection.NORTH}};

    @Override
    public ForgeDirection[] getConnectDirection()
    {
        return cornerDirections[rotate];
    }
}
