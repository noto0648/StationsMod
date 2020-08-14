package com.noto0648.stations.blocks;

import com.google.common.base.Predicate;
import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tiles.TileEntityDeparturePlate;
import com.noto0648.stations.tiles.TileEntityStationFence;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockStationFence extends BlockContainer
{
    private static final AxisAlignedBB FENCE_AABB = new AxisAlignedBB(0,0,0,1,1.5f,1);

    public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        @Override
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.DOWN;
        }
    });
    public static final PropertyEnum<BlockStationFence.EnumType> VARIANT = PropertyEnum.create("variant", BlockStationFence.EnumType.class);


    public BlockStationFence()
    {
        super(Material.IRON);
        setCreativeTab(StationsMod.tab);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.PLATFORM_FENCE).withProperty(FACING, EnumFacing.NORTH));
        this.hasTileEntity = false;
        setHardness(1.4f);
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer p_onBlockActivated_4_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_)
    {
        ItemStack stack = Utils.INSTANCE.getHasDiagram(p_onBlockActivated_4_);
        if(stack == ItemStack.EMPTY || !stack.hasTagCompound())
            return false;
        IBlockState actual = state.getActualState(world, pos);
        if(actual.getValue(VARIANT) == EnumType.PLATFORM_DISPLAY)
        {
            TileEntity te = world.getTileEntity(pos);
            if(te == null || !(te instanceof TileEntityDeparturePlate))
                return false;

            ((TileEntityDeparturePlate)te).getMarkDataList().clear();
            NBTTagList marks = stack.getTagCompound().getTagList("marks", 10);
            for(int i = 0; i < marks.tagCount(); i++)
            {
                MarkData md = new MarkData();
                md.readFromNBTTag( marks.getCompoundTagAt(i) );
                ((TileEntityDeparturePlate)te).getMarkDataList().add(md);
            }
            return true;
        }
        return false;
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
        if(i == 14 || i == 15)
        {
            return new TileEntityDeparturePlate();
        }
        return null;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch(state.getActualState(world, pos).getValue(VARIANT))
        {
            case PLATFORM_FENCE:
            case RAIL_FENCE:
                return FENCE_AABB;
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public ItemStack getItem(World p_getItem_1_, BlockPos p_getItem_2_, IBlockState p_getItem_3_)
    {
        switch(p_getItem_3_.getValue(VARIANT))
        {
            case PLATFORM_FENCE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockFence), 1, 0);
            case RAIL_FENCE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockFence), 1, 4);
            case PLATFORM_DISPLAY:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockFence), 1, 14);
        }
        return new ItemStack(Item.getItemFromBlock(StationsItems.blockFence), 1, 0);
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
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_FENCE).withProperty(FACING, EnumFacing.NORTH);
            case 1:
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_FENCE).withProperty(FACING, EnumFacing.SOUTH);
            case 2:
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_FENCE).withProperty(FACING, EnumFacing.WEST);
            case 3:
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_FENCE).withProperty(FACING, EnumFacing.EAST);
            case 4:
                return getDefaultState().withProperty(VARIANT, EnumType.RAIL_FENCE).withProperty(FACING, EnumFacing.NORTH);
            case 5:
                return getDefaultState().withProperty(VARIANT, EnumType.RAIL_FENCE).withProperty(FACING, EnumFacing.WEST);
            case 14:
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_DISPLAY).withProperty(FACING, EnumFacing.NORTH);
            case 15:
                return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_DISPLAY).withProperty(FACING, EnumFacing.WEST);
        }
        return getDefaultState().withProperty(VARIANT, EnumType.PLATFORM_FENCE).withProperty(FACING, EnumFacing.NORTH);
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
            case PLATFORM_FENCE:
                return getMetaFacing(state.getValue(FACING));
            case RAIL_FENCE:
                return 4 + getMetaFacing2(state.getValue(FACING));
            case PLATFORM_DISPLAY:
                return 14 + getMetaFacing2(state.getValue(FACING));
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
    public void getSubBlocks(CreativeTabs p_getSubBlocks_1_, NonNullList<ItemStack> p_getSubBlocks_2_)
    {
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 0));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 4));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 14));

        //p_getSubBlocks_2_.add(new ItemStack(this, 1, 5));
        //p_getSubBlocks_2_.add(new ItemStack(this, 1, 9));

    }

    enum EnumType implements IStringSerializable
    {
        PLATFORM_FENCE("platform_fence", true, EnumBlockRenderType.MODEL),
        RAIL_FENCE("rail_fence", true, EnumBlockRenderType.MODEL),
        PLATFORM_DISPLAY("platform_display", true, EnumBlockRenderType.MODEL);


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
