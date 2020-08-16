package com.noto0648.stations.blocks;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tiles.TileEntityStringSeal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockStringSeal extends BlockDirectional implements ITileEntityProvider
{
    private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0,0,0,1f,0.0625f,1f);
    private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0,1f - 0.0625f,0,1f,1f,1f);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0,0,0,0.0625f,1f,1f);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(1f - 0.0625f,0,0,1f,1f,1f);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0,0,0,1f,1f,0.0625f);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0,0,1f - 0.0625f,1f,1f,1f - 0.0625f);

    public BlockStringSeal()
    {
        super(Material.CLAY);
        setCreativeTab(StationsMod.tab);
        setHardness(0.3f);
        hasTileEntity = true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer p_149727_5_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_)
    {
        if(Utils.INSTANCE.haveWrench(p_149727_5_))
        {
            if(p_149727_5_.isSneaking())
            {
                TileEntity te = world.getTileEntity(pos);
                if(te != null && te instanceof TileEntityStringSeal)
                {
                    ((TileEntityStringSeal) te).setRotation((((TileEntityStringSeal) te).getRotation() + 1) % 4);
                    return true;
                }
            }

            p_149727_5_.openGui(StationsMod.instance, 5, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch(state.getActualState(world, pos).getValue(FACING))
        {
            case NORTH:
                return AABB_SOUTH;
            case SOUTH:
                return AABB_NORTH;
            case WEST:
                return AABB_EAST;
            case EAST:
                return AABB_WEST;
            case DOWN:
                return AABB_UP;
            case UP:
            default:
                return AABB_DOWN;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState p_isFullCube_1_) {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World p_canPlaceBlockOnSide_1_, BlockPos p_canPlaceBlockOnSide_2_, EnumFacing p_canPlaceBlockOnSide_3_)
    {
        return canPlaceBlock(p_canPlaceBlockOnSide_1_, p_canPlaceBlockOnSide_2_, p_canPlaceBlockOnSide_3_);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState p_getRenderType_1_)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public IBlockState getStateForPlacement(World p_getStateForPlacement_1_, BlockPos p_getStateForPlacement_2_, EnumFacing p_getStateForPlacement_3_, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase p_getStateForPlacement_8_)
    {
        return canPlaceBlock(p_getStateForPlacement_1_, p_getStateForPlacement_2_, p_getStateForPlacement_3_) ? this.getDefaultState().withProperty(FACING, p_getStateForPlacement_3_) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }

    protected static boolean canPlaceBlock(World p_canPlaceBlock_0_, BlockPos p_canPlaceBlock_1_, EnumFacing p_canPlaceBlock_2_)
    {
        BlockPos blockpos = p_canPlaceBlock_1_.offset(p_canPlaceBlock_2_.getOpposite());
        IBlockState iblockstate = p_canPlaceBlock_0_.getBlockState(blockpos);
        boolean flag = iblockstate.getBlockFaceShape(p_canPlaceBlock_0_, blockpos, p_canPlaceBlock_2_) == BlockFaceShape.SOLID;
        Block block = iblockstate.getBlock();
        if (p_canPlaceBlock_2_ == EnumFacing.UP) {
            return iblockstate.isTopSolid() || !isExceptionBlockForAttaching(block) && flag;
        } else {
            return !isExceptBlockForAttachWithPiston(block) && flag;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.values()[p_getStateFromMeta_1_ % 6]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState p_withRotation_1_, Rotation p_withRotation_2_)
    {
        return p_withRotation_1_.withProperty(FACING, p_withRotation_2_.rotate((EnumFacing)p_withRotation_1_.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState p_withMirror_1_, Mirror p_withMirror_2_)
    {
        return p_withMirror_1_.withRotation(p_withMirror_2_.toRotation((EnumFacing)p_withMirror_1_.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_getBlockFaceShape_1_, IBlockState p_getBlockFaceShape_2_, BlockPos p_getBlockFaceShape_3_, EnumFacing p_getBlockFaceShape_4_)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntityStringSeal();
    }
}
