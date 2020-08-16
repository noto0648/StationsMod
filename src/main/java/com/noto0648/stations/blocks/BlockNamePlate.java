package com.noto0648.stations.blocks;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tiles.TileEntityNamePlate;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockNamePlate extends BlockContainer
{
    public static final PropertyEnum<EnumRotating> ROTATING = PropertyEnum.create("direction", EnumRotating.class);
    public static final PropertyEnum<EnumPosition> POSITION = PropertyEnum.create("position", EnumPosition.class);

    public BlockNamePlate()
    {
        super(Material.ROCK);
        setCreativeTab(StationsMod.tab);
        setDefaultState(blockState.getBaseState().withProperty(ROTATING, EnumRotating.HORIZONTAL).withProperty(POSITION, EnumPosition.MIDDLE));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return false;
    }
    @Override
    public boolean isBlockNormalCube(IBlockState blockState)
    {
        return false;
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer p_149727_5_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_)
    {
        if(Utils.INSTANCE.haveWrench(p_149727_5_))
        {
            p_149727_5_.openGui(StationsMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        int meta = getMetaFromState(state);

        if(meta == 0) return new AxisAlignedBB(0.4F, 0F, -0.5F, 0.6F, 1F, 1.5F);
        else if(meta == 1) return new AxisAlignedBB(-0.5F, 0F, 0.4F, 1.5F, 1F, 0.6F);
        else if(meta == 5) return new AxisAlignedBB(0.0F, 0F, -0.5F, 0.2F, 1F, 1.5F);
        else if(meta == 4) return new AxisAlignedBB(0.8F, 0F, -0.5F, 1F, 1F, 1.5F);
        else if(meta == 2) return new AxisAlignedBB(-0.5F, 0F, 0.8F, 1.5F, 1F, 1F);
        else if(meta == 3) return new AxisAlignedBB(-0.5F, 0F, 0.0F, 1.5F, 1F, 0.2F);

        return super.getBoundingBox(state, world, pos);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess access, BlockPos pos)
    {
        TileEntity te =  access.getTileEntity(pos);
        if(te != null && te instanceof TileEntityNamePlate)
        {
            return ((TileEntityNamePlate)te).light ? 15 : 0;
        }
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{ROTATING, POSITION});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(POSITION, EnumPosition.values()[meta/2]).withProperty(ROTATING, EnumRotating.values()[meta%2]);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation p_withRotation_2_)
    {
        return state;
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror p_withMirror_2_)
    {
        return state;
    }

    @Override
    public IBlockState getStateForPlacement(World p_getStateForPlacement_1_, BlockPos p_getStateForPlacement_2_, EnumFacing p_getStateForPlacement_3_, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase p_getStateForPlacement_8_)
    {
        IBlockState state = getStateFromMeta(p_getStateForPlacement_7_);

        switch (p_getStateForPlacement_8_.getHorizontalFacing().getOpposite())
        {
            case EAST:
            case WEST:
                return state.withProperty(ROTATING, EnumRotating.VERTICAL);
        }
        return state.withProperty(ROTATING, EnumRotating.HORIZONTAL);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int j = 0;
        int i = 0;
        for(i = 0; i < EnumPosition.values().length; i++)
            if(EnumPosition.values()[i] == state.getValue(POSITION))
                break;
        for(j = 0; j < EnumRotating.values().length; j++)
            if(EnumRotating.values()[j] == state.getValue(ROTATING))
                break;
        return i * 2 + (1-j);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntityNamePlate();
    }

    public enum EnumRotating implements IStringSerializable
    {
        HORIZONTAL, //north, south
        VERTICAL;   //east, west

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }

    public enum EnumPosition implements IStringSerializable
    {
        MIDDLE,
        FRONT,
        BACK;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
