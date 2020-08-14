package com.noto0648.stations.blocks;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tiles.TileEntityNumberPlate;
import com.noto0648.stations.tiles.TileEntityStationFence;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockPillar extends BlockContainer
{
    private static final AxisAlignedBB MAT_AABB = new AxisAlignedBB(0,0,0,1f,0.0625f,1f);
    private static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.4f,0,0.4f,0.6f,1f,0.6f);

    public static final PropertyDirection FACING;

    public static final PropertyEnum<BlockPillar.EnumType> VARIANT = PropertyEnum.create("variant", BlockPillar.EnumType.class);

    public BlockPillar()
    {
        super(Material.ROCK);
        setCreativeTab(StationsMod.tab);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.PILLAR_WHITE).withProperty(FACING, EnumFacing.NORTH));
        setHardness(1.2f);
        //hasTileEntity = true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{VARIANT, FACING});
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
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return state.getValue(VARIANT).getRenderType();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        if(i == 7 || i == 8)
            return new TileEntityNumberPlate();
        return null;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch(state.getActualState(world, pos).getValue(VARIANT))
        {
            case PILLAR_BLACK:
            case PILLAR_WHITE:
                return PILLAR_AABB;
            case WHITE_LINE:
            case YELLOW_LINE:
                return MAT_AABB;
        }

        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer p_149727_5_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_) {
        //public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
        EnumType et = state.getValue(VARIANT);
        /*
        if(et ==  && Utils.INSTANCE.haveWrench(p_149727_5_))
        {
            p_149727_5_.openGui(Stations.instance, 0, world, p_149727_2_, p_149727_3_, p_149727_4_);
            return true;
        }
        */
        if(et == EnumType.NUMBER_PLATE && Utils.INSTANCE.haveWrench(p_149727_5_))
        {
            p_149727_5_.openGui(StationsMod.instance, 3, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }


    @Override
    public ItemStack getItem(World p_getItem_1_, BlockPos p_getItem_2_, IBlockState p_getItem_3_)
    {
        switch (p_getItem_3_.getValue(VARIANT))
        {
            case PILLAR_WHITE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockPillar), 1, 0);
            case YELLOW_LINE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockPillar), 1, 2);
            case PILLAR_BLACK:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockPillar), 1, 6);
            case NUMBER_PLATE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockPillar), 1, 7);
            case WHITE_LINE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockPillar), 1, 9);
        }
        return new ItemStack(Item.getItemFromBlock(StationsItems.blockPillar), 1, 0);
    }

    @Override
    public float getBlockHardness(IBlockState p_getBlockHardness_1_, World p_getBlockHardness_2_, BlockPos p_getBlockHardness_3_)
    {
        IBlockState state = p_getBlockHardness_1_.getActualState(p_getBlockHardness_2_, p_getBlockHardness_3_);
        switch(state.getValue(VARIANT))
        {
            case YELLOW_LINE:
            case WHITE_LINE:
                return 0.1f;
        }
        return this.blockHardness;
    }

    @Override
    public int damageDropped(IBlockState p_damageDropped_1_)
    {
        return getItem(null, BlockPos.ORIGIN, p_damageDropped_1_).getMetadata();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        switch (meta) {
            case 0:
                return getDefaultState().withProperty(VARIANT, BlockPillar.EnumType.PILLAR_WHITE).withProperty(FACING, EnumFacing.NORTH);
            case 1:
                return getDefaultState().withProperty(VARIANT, EnumType.MARK_MACHINE).withProperty(FACING, EnumFacing.NORTH);
            case 2:
                return getDefaultState().withProperty(VARIANT, EnumType.YELLOW_LINE).withProperty(FACING, EnumFacing.NORTH);
            case 3:
                return getDefaultState().withProperty(VARIANT, BlockPillar.EnumType.YELLOW_LINE).withProperty(FACING, EnumFacing.SOUTH);
            case 4:
                return getDefaultState().withProperty(VARIANT, BlockPillar.EnumType.YELLOW_LINE).withProperty(FACING, EnumFacing.WEST);
            case 5:
                return getDefaultState().withProperty(VARIANT, BlockPillar.EnumType.YELLOW_LINE).withProperty(FACING, EnumFacing.EAST);
            case 6:
                return getDefaultState().withProperty(VARIANT, EnumType.PILLAR_BLACK).withProperty(FACING, EnumFacing.NORTH);
            case 7:
                return getDefaultState().withProperty(VARIANT, EnumType.NUMBER_PLATE).withProperty(FACING, EnumFacing.NORTH);
            case 8:
                return getDefaultState().withProperty(VARIANT, EnumType.NUMBER_PLATE).withProperty(FACING, EnumFacing.WEST);
            case 9:
                return getDefaultState().withProperty(VARIANT, EnumType.WHITE_LINE).withProperty(FACING, EnumFacing.NORTH);
            case 10:
                return getDefaultState().withProperty(VARIANT, EnumType.WHITE_LINE).withProperty(FACING, EnumFacing.SOUTH);
            case 11:
                return getDefaultState().withProperty(VARIANT, BlockPillar.EnumType.WHITE_LINE).withProperty(FACING, EnumFacing.WEST);
            case 12:
                return getDefaultState().withProperty(VARIANT, BlockPillar.EnumType.WHITE_LINE).withProperty(FACING, EnumFacing.EAST);
        }
        return getDefaultState().withProperty(VARIANT, EnumType.PILLAR_WHITE).withProperty(FACING, EnumFacing.NORTH);
    }


    private int getMetaFacing(EnumFacing face)
    {
        return Math.max(0, face.getIndex() - 2);
    }


    private int getMetaFacing2(EnumFacing face)
    {
        return getMetaFacing(face) / 2;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        switch (state.getValue(VARIANT))
        {
            case PILLAR_WHITE:
                return 0;
            case MARK_MACHINE:
                return 1;
            case YELLOW_LINE:
                return 2 + getMetaFacing(state.getValue(FACING));
            case PILLAR_BLACK:
                return 6;
            case NUMBER_PLATE:
                return 7 + getMetaFacing2(state.getValue(FACING));
            case WHITE_LINE:
                return 9 + getMetaFacing(state.getValue(FACING));
        }
        return 0;
    }


    @Override
    public IBlockState withRotation(IBlockState state, Rotation p_withRotation_2_)
    {
        if(state.getValue(VARIANT).isAllowDirection())
            return state.withProperty(FACING, p_withRotation_2_.rotate((EnumFacing)state.getValue(FACING)));
        return state;
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror p_withMirror_2_)
    {
        if(state.getValue(VARIANT).isAllowDirection())
            return state.withRotation(p_withMirror_2_.toRotation((EnumFacing)state.getValue(FACING)));
        return state;
    }

    @Override
    public IBlockState getStateForPlacement(World p_getStateForPlacement_1_, BlockPos p_getStateForPlacement_2_, EnumFacing p_getStateForPlacement_3_, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase p_getStateForPlacement_8_)
    {
        IBlockState state = getStateFromMeta(p_getStateForPlacement_7_);
        if(state.getValue(VARIANT).isAllowDirection())
            return state.withProperty(FACING, p_getStateForPlacement_8_.getHorizontalFacing().getOpposite());

        return state;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void getSubBlocks(CreativeTabs p_getSubBlocks_1_, NonNullList<ItemStack> p_getSubBlocks_2_)
    {
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 0));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 6));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 2));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 9));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 7));

        //p_getSubBlocks_2_.add(new ItemStack(this, 1, 5));
        //p_getSubBlocks_2_.add(new ItemStack(this, 1, 9));

    }

    static
    {
        FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    }

    enum EnumType implements IStringSerializable
    {
        PILLAR_WHITE("pillar_white", false, EnumBlockRenderType.MODEL),
        MARK_MACHINE("mark_machine", false, EnumBlockRenderType.MODEL),
        YELLOW_LINE("yellow_line", true, EnumBlockRenderType.MODEL),
        PILLAR_BLACK("pillar_black", false, EnumBlockRenderType.MODEL),
        NUMBER_PLATE("number_plate", true, EnumBlockRenderType.MODEL),
        WHITE_LINE("white_line", true, EnumBlockRenderType.MODEL);


        private final String name;
        private final boolean allowDirection;
        private final EnumBlockRenderType renderType;

        EnumType(String name, boolean allowDirection, EnumBlockRenderType renderType)
        {
            this.name = name;
            this.allowDirection = allowDirection;
            this.renderType = renderType;
        }
        @Override
        public String getName()
        {
            return name;
        }

        public boolean isAllowDirection()
        {
            return allowDirection;
        }

        public EnumBlockRenderType getRenderType()
        {
            return renderType;
        }
    }
}
