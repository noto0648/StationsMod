package com.noto0648.stations.tiles;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.blocks.BlockShutter;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.packet.IPacketReceiver;
import com.noto0648.stations.packet.IPacketSender;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TileEntityShutter extends TileEntityBase implements ITickable, IPacketSender, IPacketReceiver
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
                    final BlockPos down = pos.down();
                    final IBlockState state = getWorld().getBlockState(down);
                    final Block bottomBlock = state.getBlock();
                    finish = true;
                    markDirty();
                    if(bottomBlock.isAir(state, getWorld(), down))
                    {
                        final BlockShutter.EnumAxis axis = mainState.getValue(BlockShutter.FACING);
                        getWorld().setBlockState(down, StationsItems.blockShutter.getDefaultState().withProperty(BlockShutter.FACING, axis).withProperty(BlockShutter.PROGRESS, 7));
                        TileEntityShutter s = (TileEntityShutter) getWorld().getTileEntity(down);
                        s.setUndoFlag(undoFlag);
                        //s.finish = finish;
                        return;
                    }
                }
                else
                {
                    getWorld().setBlockState(pos, mainState.withProperty(BlockShutter.PROGRESS, baseProgress - 1), 3);
                    TileEntityShutter tile = (TileEntityShutter)getWorld().getTileEntity(pos);
                    tile.setUndoFlag(undoFlag);
                    tile.finish = finish;
                    //updateBlockState(mainState.withProperty/(BlockShutter.PROGRESS, baseProgress - 1))
                }
            }
            if(undoFlag)
            {
                final IBlockState mainState = getWorld().getBlockState(pos);
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
//                    updateBlockState(mainState.withProperty(BlockShutter.PROGRESS, baseProgress + 1));
                    getWorld().setBlockState(pos, mainState.withProperty(BlockShutter.PROGRESS, baseProgress + 1), 3);

                    TileEntityShutter tile = (TileEntityShutter)getWorld().getTileEntity(pos);
                    tile.setUndoFlag(undoFlag);
                    tile.finish = finish;
                }
            }
        }
        markDirty();
    }

    private void updateBlockState(IBlockState state)
    {
        TileEntity tile = getWorld().getTileEntity(pos);
        getWorld().setBlockState(pos, state, 3);
        if(tile != null)
            getWorld().setTileEntity(pos, tile);
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
        if(undoFlag == par1)
            return;

        undoFlag = par1;
        if(!undoFlag)
        {
            finish = false;
        }
        if(!getWorld().isRemote)
            Utils.INSTANCE.sendToPlayers(this);
        markDirty();
    }

    public boolean isRollbackMode()
    {
        return undoFlag;
    }

    @Override
    public void receive(List<Object> data)
    {
        undoFlag = (boolean)data.get(0);
        finish = (boolean)data.get(1);
        markDirty();
    }

    @Override
    public TileEntity getTile()
    {
        return this;
    }

    @Override
    public void setSendData(List<Object> list)
    {
        list.add(undoFlag);
        list.add(finish);
    }
}
