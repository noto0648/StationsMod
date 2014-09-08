package com.noto0648.stations.tile;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Noto on 14/09/06.
 */
public class TileEntityRailToy extends TileBase
{
    private static ForgeDirection[][] normalConnection = new ForgeDirection[][]{ {ForgeDirection.NORTH, ForgeDirection.SOUTH}, {ForgeDirection.EAST, ForgeDirection.WEST}};
    protected int rotate;

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        rotate = p_145839_1_.getInteger("rotate");
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("rotate", rotate);
    }

    public int getRotate()
    {
        return rotate;
    }

    public void setRotate(int par1)
    {
        rotate = par1;
    }

    public ForgeDirection[] getConnectDirection()
    {
        if(rotate == 0 || rotate == 2)
            return normalConnection[0];

        if(rotate == 1 || rotate == 3)
            return normalConnection[1];

        return null;
    }
}
