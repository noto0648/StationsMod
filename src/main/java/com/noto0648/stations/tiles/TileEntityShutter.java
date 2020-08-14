package com.noto0648.stations.tiles;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.blocks.BlockShutter;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityShutter extends TileEntityBase implements ITickable
{
    private int count;
    private boolean undoFlag;
    private boolean finish;

    @Override
    public void update()
    {
        count++;
        if(count == 5)
        {
            count = 0;
            if(!undoFlag && !finish)
            {
                IBlockState mainState = getWorld().getBlockState(pos);
                final int baseProgress = mainState.getValue(BlockShutter.PROGRESS);
                if(baseProgress == 0)
                {
                    BlockShutter.EnumAxis axis = mainState.getValue(BlockShutter.FACING);
                    IBlockState state = getWorld().getBlockState(pos.down());
                    //Block bottomBlock = getWorld().getBlock(xCoord, yCoord - 1, zCoord);
                    Block bottomBlock = state.getBlock();
                    finish = true;
                    if(bottomBlock.isAir(state, getWorld(), pos.down()))
                    {
                        getWorld().setBlockState(pos.down(), StationsItems.blockShutter.getDefaultState().withProperty(BlockShutter.FACING, axis).withProperty(BlockShutter.PROGRESS, 7));
                        return;
                    }
                }
                else
                {
                    //getWorld().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() - 1, 2);
                    //getWorld().setBlockState(pos.down(), StationsItems.blockShutter.getDefaultState().withProperty(BlockShutter.FACING, axis).withProperty(BlockShutter.PROGRESS, 7));
                    getWorld().setBlockState(pos, mainState.withProperty(BlockShutter.PROGRESS, baseProgress-1));
                }
            }
            else if(undoFlag)
            {
                IBlockState mainState = getWorld().getBlockState(pos);
                final int baseProgress = mainState.getValue(BlockShutter.PROGRESS);

                if(baseProgress == 7)
                {
                    IBlockState bottomState = getWorld().getBlockState(pos.up());
                    TileEntity te = getWorld().getTileEntity(pos.up());
                    if(bottomState.getBlock() == StationsItems.blockShutter && te != null && te instanceof TileEntityShutter)
                    {
                        ((TileEntityShutter)te).setUndoFlag(true);
                    }
                    getWorld().setBlockToAir(pos);
                    getWorld().removeTileEntity(pos);
                }
                else
                {
//                    getWorld().setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() + 1, 2);
                    getWorld().setBlockState(pos, mainState.withProperty(BlockShutter.PROGRESS, baseProgress + 1));

                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        count = p_145839_1_.getInteger("counter");
        undoFlag = p_145839_1_.getBoolean("undo");
        finish = p_145839_1_.getBoolean("fin");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("counter", count);
        p_145841_1_.setBoolean("undo", undoFlag);
        p_145841_1_.setBoolean("fin", finish);
        return p_145841_1_;
    }

    public void setUndoFlag(boolean par1)
    {
        undoFlag = par1;
        if(!undoFlag)
        {
            finish = false;
        }
    }

}
