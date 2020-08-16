package com.noto0648.stations.blocks;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.tiles.TileEntityShutter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockShutter extends BlockContainer
{
    public static final PropertyInteger PROGRESS = PropertyInteger.create("progress", 0, 7);
    public static final PropertyEnum<EnumAxis> FACING = PropertyEnum.create("facing", EnumAxis.class);

    public BlockShutter()
    {
        super(Material.IRON);
        setUnlocalizedName("notomod.shutter");
        setCreativeTab(StationsMod.tab);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumAxis.X).withProperty(PROGRESS, 0));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> p_getDrops_1_, IBlockAccess p_getDrops_2_, BlockPos p_getDrops_3_, IBlockState p_getDrops_4_, int p_getDrops_5_)
    {
        p_getDrops_1_.add(new ItemStack(StationsItems.blockMaterial1, 1, 14));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, PROGRESS);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex() * 8 + state.getValue(PROGRESS);
    }

    @Override
    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_)
    {
        return getDefaultState().withProperty(FACING, EnumAxis.values()[p_getStateFromMeta_1_/8]).withProperty(PROGRESS, p_getStateFromMeta_1_ % 8);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 1;
    }

    @Override
    public void onBlockHarvested(World p_onBlockHarvested_1_, BlockPos p_onBlockHarvested_2_, IBlockState p_onBlockHarvested_3_, EntityPlayer p_onBlockHarvested_4_)
    {
        endProcess(p_onBlockHarvested_1_, p_onBlockHarvested_2_.getX(), p_onBlockHarvested_2_.getY(), p_onBlockHarvested_2_.getZ());
    }

    private void endProcess(World p_149636_1_, int p_149636_3_, int p_149636_4_, int p_149636_5_)
    {
        for(int i = 1 ; ; i++)
        {
            final BlockPos targetPos = new BlockPos(p_149636_3_, p_149636_4_ + i, p_149636_5_);
            final IBlockState blockState = p_149636_1_.getBlockState(targetPos);
            final Block block = blockState.getBlock();
            //int meta = p_149636_1_.getBlockMetadata(p_149636_3_, p_149636_4_ + i, p_149636_5_);
            if(block == StationsItems.blockShutter)
            {
                p_149636_1_.setBlockToAir(targetPos);
                continue;
            }
            else if((block == StationsItems.blockMaterial1 && blockState.getValue(BlockStationMaterial.VARIANT) == BlockStationMaterial.EnumType.SHUTTER_CORE))
            {
                p_149636_1_.setBlockToAir(targetPos);
                break;
            }
            else
            {
                break;
            }
        }

        for(int i = 1; ; i++)
        {
            BlockPos targetPos = new BlockPos(p_149636_3_, p_149636_4_ - i, p_149636_5_);
            Block block = p_149636_1_.getBlockState(targetPos).getBlock();
            if(block == StationsItems.blockShutter)
                p_149636_1_.setBlockToAir(targetPos);
            else
                break;
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_)
    {
        return getAABB(p_getBoundingBox_1_, p_getBoundingBox_2_, p_getBoundingBox_3_);
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return getAABB(state, world, pos);
    }

    private AxisAlignedBB getAABB(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        final IBlockState actualState = state.getActualState(world, pos);
        final float height = actualState.getValue(PROGRESS) * 0.125f;
        final EnumAxis axis = actualState.getValue(FACING);

        if(axis == EnumAxis.Z)
            return new AxisAlignedBB(0F, height, 0.4F, 1F, 1F, 0.6F);
        else
            return new AxisAlignedBB(0.4F, height, 0F, 0.6F, 1F, 1F);
    }

    @Override
    public ItemStack getItem(World p_getItem_1_, BlockPos p_getItem_2_, IBlockState p_getItem_3_)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isFullBlock(IBlockState p_isFullBlock_1_)
    {
        return false;
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
    public void getSubBlocks(CreativeTabs p_getSubBlocks_1_, NonNullList<ItemStack> p_getSubBlocks_2_)
    {
        if(StationsMod.DEBUG_MODE)
            p_getSubBlocks_2_.add(new ItemStack(this, 1, 0));
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityShutter();
    }

    public static enum EnumAxis implements IStringSerializable
    {
        X("x", 0),
        Z("z", 1);
        private final String name;
        private final int index;

        EnumAxis(String label, int no)
        {
            name=label;
            index = no;
        }

        public int getIndex()
        {
            return index;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }
}
