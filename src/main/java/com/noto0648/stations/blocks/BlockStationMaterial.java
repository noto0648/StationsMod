package com.noto0648.stations.blocks;

import com.google.common.base.Predicate;
import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockStationMaterial extends Block
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        @Override
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN;
        }
    });
    public static final PropertyEnum<BlockStationMaterial.EnumType> VARIANT = PropertyEnum.create("variant", BlockStationMaterial.EnumType.class);


    public BlockStationMaterial()
    {
        super(Material.ROCK);
        setCreativeTab(StationsMod.tab);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.PLATFORM_WHITE).withProperty(FACING, EnumFacing.UP));
        setHardness(1.5F);
        setResistance(10.0F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{FACING, VARIANT});
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
    public IBlockState getStateForPlacement(World p_getStateForPlacement_1_, BlockPos p_getStateForPlacement_2_, EnumFacing p_getStateForPlacement_3_, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase p_getStateForPlacement_8_)
    {
        if(p_getStateForPlacement_7_ == 1 || p_getStateForPlacement_7_ == 5)
            return getStateFromMeta(p_getStateForPlacement_7_).withProperty(FACING, p_getStateForPlacement_8_.getHorizontalFacing().getOpposite());

        return getStateFromMeta(p_getStateForPlacement_7_);
    }

    @Override
    public int damageDropped(IBlockState p_damageDropped_1_)
    {
        return getItem(null, BlockPos.ORIGIN, p_damageDropped_1_).getMetadata();
    }

    @Override
    public ItemStack getItem(World p_getItem_1_, BlockPos p_getItem_2_, IBlockState p_getItem_3_)
    {
        switch(p_getItem_3_.getValue(VARIANT))
        {
            case PLATFORM_WHITE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockMaterial1), 1, 0);
            case GRAY_PLATFORM:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockMaterial1), 1, 1);
            case LINED_PLATFORM_WHITE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockMaterial1), 1, 5);
            case ASPHALT:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockMaterial1), 1, 9);
            case OLD_PLATFORM_BRICK:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockMaterial1), 1, 10);
        }
        return new ItemStack(Item.getItemFromBlock(StationsItems.blockMaterial1));
    }

    @Override
    public void getSubBlocks(CreativeTabs p_getSubBlocks_1_, NonNullList<ItemStack> p_getSubBlocks_2_)
    {
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 0));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 1));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 5));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 9));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 10));

    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        switch(meta)
        {
            case 0:
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_WHITE).withProperty(FACING, EnumFacing.UP);
            case 1:
                return getDefaultState().withProperty(VARIANT, EnumType.GRAY_PLATFORM).withProperty(FACING, EnumFacing.NORTH);
            case 2:
                return getDefaultState().withProperty(VARIANT, EnumType.GRAY_PLATFORM).withProperty(FACING, EnumFacing.SOUTH);
            case 3:
                return getDefaultState().withProperty(VARIANT, EnumType.GRAY_PLATFORM).withProperty(FACING, EnumFacing.WEST);
            case 4:
                return getDefaultState().withProperty(VARIANT, EnumType.GRAY_PLATFORM).withProperty(FACING, EnumFacing.EAST);
            case 5:
                return getDefaultState().withProperty(VARIANT, EnumType.LINED_PLATFORM_WHITE).withProperty(FACING, EnumFacing.NORTH);
            case 6:
                return getDefaultState().withProperty(VARIANT, EnumType.LINED_PLATFORM_WHITE).withProperty(FACING, EnumFacing.SOUTH);
            case 7:
                return getDefaultState().withProperty(VARIANT, EnumType.LINED_PLATFORM_WHITE).withProperty(FACING, EnumFacing.WEST);
            case 8:
                return getDefaultState().withProperty(VARIANT, EnumType.LINED_PLATFORM_WHITE).withProperty(FACING, EnumFacing.EAST);
            case 9:
                return getDefaultState().withProperty(VARIANT, EnumType.ASPHALT).withProperty(FACING, EnumFacing.UP);
            case 10:
                return getDefaultState().withProperty(VARIANT, EnumType.OLD_PLATFORM_BRICK).withProperty(FACING, EnumFacing.UP);
            default:
        }
        return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_WHITE).withProperty(FACING, EnumFacing.UP);
    }

    private int getMetaFacing(EnumFacing face)
    {
        return Math.max(0, face.getIndex() - 2);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        switch(state.getValue(VARIANT))
        {
            case PLATFORM_WHITE:
                return 0;
            case GRAY_PLATFORM:
                return 1 + getMetaFacing(state.getValue(FACING));
            case LINED_PLATFORM_WHITE:
                return 5 + getMetaFacing(state.getValue(FACING));
            case ASPHALT:
                return 9;
            case OLD_PLATFORM_BRICK:
                return 10;
            default:
                return 0;
        }
    }

    enum EnumType implements IStringSerializable
    {
        PLATFORM_WHITE("whitebrick_platform"),
        GRAY_PLATFORM("gray_platform"),
        LINED_PLATFORM_WHITE("lined_whitebrick_platform"),
        ASPHALT("asphalt"),
        OLD_PLATFORM_BRICK("old_platform_brick");

        private String name;
        EnumType(String name)
        {
            this.name = name;
        }

        @Override
        public String getName()
        {
            return name;
        }
    }
}
