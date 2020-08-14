package com.noto0648.stations.blocks;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.tiles.TileEntitySlideDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockSlideDoor extends BlockDoor implements ITileEntityProvider
{
    protected static final AxisAlignedBB HORIZONTAL_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB VERTICAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);

    public BlockSlideDoor()
    {
        super(Material.ROCK);
        //setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false).withProperty(HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER));
        disableStats();
        setHardness(1.8F);

        hasTileEntity = true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{HALF, FACING, OPEN, HINGE, POWERED});
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState p_getRenderType_1_)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState p_onBlockActivated_3_, EntityPlayer p_onBlockActivated_4_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_)
    {
        BlockPos blockpos = p_onBlockActivated_3_.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down();
        IBlockState iblockstate = pos.equals(blockpos) ? p_onBlockActivated_3_ : world.getBlockState(blockpos);
        if (iblockstate.getBlock() != this) {
            return false;
        } else {
            p_onBlockActivated_3_ = iblockstate.cycleProperty(OPEN);
            world.setBlockState(blockpos, p_onBlockActivated_3_, 10);
            world.markBlockRangeForRenderUpdate(blockpos, pos);

            TileEntity te = world.getTileEntity(blockpos);
            if(te != null && te instanceof TileEntitySlideDoor)
            {
                ((TileEntitySlideDoor)te).setCoolTime(10);
            }
            //p_onBlockActivated_1_.playEvent(p_onBlockActivated_4_, (Boolean)p_onBlockActivated_3_.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), p_onBlockActivated_2_, 0);
            return true;
        }

    }

    public void toggleDoor(World p_toggleDoor_1_, BlockPos p_toggleDoor_2_, boolean p_toggleDoor_3_) {
        IBlockState iblockstate = p_toggleDoor_1_.getBlockState(p_toggleDoor_2_);
        if (iblockstate.getBlock() == this) {
            BlockPos blockpos = iblockstate.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER ? p_toggleDoor_2_ : p_toggleDoor_2_.down();
            IBlockState iblockstate1 = p_toggleDoor_2_ == blockpos ? iblockstate : p_toggleDoor_1_.getBlockState(blockpos);
            if (iblockstate1.getBlock() == this && (Boolean)iblockstate1.getValue(OPEN) != p_toggleDoor_3_) {
                p_toggleDoor_1_.setBlockState(blockpos, iblockstate1.withProperty(OPEN, p_toggleDoor_3_), 10);
                p_toggleDoor_1_.markBlockRangeForRenderUpdate(blockpos, p_toggleDoor_2_);
                //p_toggleDoor_1_.playEvent((EntityPlayer)null, p_toggleDoor_3_ ? this.getOpenSound() : this.getCloseSound(), p_toggleDoor_2_, 0);
                TileEntity te = p_toggleDoor_1_.getTileEntity(blockpos);
                if(te != null && te instanceof TileEntitySlideDoor)
                {
                    ((TileEntitySlideDoor)te).setCoolTime(10);
                }

            }
        }

    }

    @Override
    public void neighborChanged(IBlockState p_neighborChanged_1_, World p_neighborChanged_2_, BlockPos p_neighborChanged_3_, Block p_neighborChanged_4_, BlockPos p_neighborChanged_5_) {
        if (p_neighborChanged_1_.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
            BlockPos blockpos = p_neighborChanged_3_.down();
            IBlockState iblockstate = p_neighborChanged_2_.getBlockState(blockpos);
            if (iblockstate.getBlock() != this) {
                p_neighborChanged_2_.setBlockToAir(p_neighborChanged_3_);
            } else if (p_neighborChanged_4_ != this) {
                iblockstate.neighborChanged(p_neighborChanged_2_, blockpos, p_neighborChanged_4_, p_neighborChanged_5_);
            }
        } else {
            boolean flag1 = false;
            BlockPos blockpos1 = p_neighborChanged_3_.up();
            IBlockState iblockstate1 = p_neighborChanged_2_.getBlockState(blockpos1);
            if (iblockstate1.getBlock() != this) {
                p_neighborChanged_2_.setBlockToAir(p_neighborChanged_3_);
                flag1 = true;
            }

            if (!p_neighborChanged_2_.getBlockState(p_neighborChanged_3_.down()).isSideSolid(p_neighborChanged_2_, p_neighborChanged_3_.down(), EnumFacing.UP)) {
                p_neighborChanged_2_.setBlockToAir(p_neighborChanged_3_);
                flag1 = true;
                if (iblockstate1.getBlock() == this) {
                    p_neighborChanged_2_.setBlockToAir(blockpos1);
                }
            }

            if (flag1)
            {
                if (!p_neighborChanged_2_.isRemote) {
                    this.dropBlockAsItem(p_neighborChanged_2_, p_neighborChanged_3_, p_neighborChanged_1_, 0);
                }
            }
            else
                {
                boolean flag = p_neighborChanged_2_.isBlockPowered(p_neighborChanged_3_) || p_neighborChanged_2_.isBlockPowered(blockpos1);
                if (p_neighborChanged_4_ != this && (flag || p_neighborChanged_4_.getDefaultState().canProvidePower()) && flag != (Boolean)iblockstate1.getValue(POWERED)) {
                    p_neighborChanged_2_.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, flag), 2);
                    if (flag != p_neighborChanged_1_.getValue(OPEN))
                    {
                        p_neighborChanged_2_.setBlockState(p_neighborChanged_3_, p_neighborChanged_1_.withProperty(OPEN, flag), 2);
                        p_neighborChanged_2_.markBlockRangeForRenderUpdate(p_neighborChanged_3_, p_neighborChanged_3_);

                        TileEntity te = p_neighborChanged_2_.getTileEntity(p_neighborChanged_3_);
                        if(te != null && te instanceof TileEntitySlideDoor)
                        {
                            ((TileEntitySlideDoor)te).setCoolTime(10);
                        }

                        //p_neighborChanged_2_.playEvent((EntityPlayer)null, flag ? this.getOpenSound() : this.getCloseSound(), p_neighborChanged_3_, 0);
                    }
                }
            }
        }

    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        boolean isOpen = state.getActualState(world, pos).getValue(OPEN);
        if(isOpen)
            return NULL_AABB;
        //return getBoundingBox(state, world, pos);
        return this.getBoundingBox(state, world, pos);
    }

    @Override
    public ItemStack getItem(World p_getItem_1_, BlockPos p_getItem_2_, IBlockState p_getItem_3_)
    {
        return new ItemStack(StationsItems.itemSlideDoor);
    }

    @Override
    public Item getItemDropped(IBlockState p_getItemDropped_1_, Random p_getItemDropped_2_, int p_getItemDropped_3_)
    {
        return p_getItemDropped_1_.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : StationsItems.itemSlideDoor;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        state = state.getActualState(world, pos);
        EnumFacing enumfacing = state.getValue(FACING);
        boolean flag = !state.getValue(OPEN);
        boolean flag1 = state.getValue(HINGE) == BlockDoor.EnumHingePosition.RIGHT;
        switch(enumfacing) {
            /*
            case EAST:
            default:
                return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
            case SOUTH:
                return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
            case WEST:
                return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
            case NORTH:
                return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);*/
            case EAST:
            case WEST:
                return HORIZONTAL_AABB;
            default:
                return VERTICAL_AABB;
        }
    }

    @Override
    public void breakBlock(World p_breakBlock_1_, BlockPos p_breakBlock_2_, IBlockState p_breakBlock_3_)
    {
        super.breakBlock(p_breakBlock_1_, p_breakBlock_2_, p_breakBlock_3_);
        p_breakBlock_1_.removeTileEntity(p_breakBlock_2_);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntitySlideDoor();
    }
}
