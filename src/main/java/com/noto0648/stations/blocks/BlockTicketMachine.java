package com.noto0648.stations.blocks;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.tiles.TileEntityTicketGate;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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

import javax.annotation.Nullable;

public class BlockTicketMachine extends BlockHorizontal implements ITileEntityProvider
{
    private static final AxisAlignedBB FENCE_AABB = new AxisAlignedBB(0,0,0,1,1.5f,1);

    public static final PropertyEnum<BlockTicketMachine.EnumType> VARIANT = PropertyEnum.create("variant", BlockTicketMachine.EnumType.class);

    public BlockTicketMachine()
    {
        super(Material.ROCK);
        setCreativeTab(StationsMod.tab);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.TICKET_MACHINE).withProperty(FACING, EnumFacing.NORTH));
        setHardness(1.5F);
        hasTileEntity = true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return state.getValue(VARIANT) == EnumType.TICKET_GATE ? EnumBlockRenderType.INVISIBLE : EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer p_149727_5_, EnumHand p_onBlockActivated_5_, EnumFacing p_onBlockActivated_6_, float p_onBlockActivated_7_, float p_onBlockActivated_8_, float p_onBlockActivated_9_)
    {
        switch(state.getActualState(world, pos).getValue(VARIANT))
        {
            case STAFF_TICKET_MACHINE:
                p_149727_5_.openGui(StationsMod.instance, 6, world, pos.getX(), pos.getY(), pos.getZ());
                return true;
            case TICKET_GATE:
                ItemStack stack = p_149727_5_.getHeldItem(p_onBlockActivated_5_);
                ItemStack newStack = TileEntityTicketGate.cutTicket(p_149727_5_, stack);
                if(newStack != null)
                {
                    ((TileEntityTicketGate)world.getTileEntity(pos)).openGate(p_149727_5_.getEntityId());
                    world.playEvent((EntityPlayer)p_149727_5_, 1008, pos, 0);
                    p_149727_5_.setHeldItem(p_onBlockActivated_5_, newStack);
                    return true;
                }
                return false;
            case IC_TICKET_GATE:
                ItemStack heldItem = p_149727_5_.getHeldItem(p_onBlockActivated_5_);
                return TileEntityTicketGate.isICTicket(heldItem);
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
    public int damageDropped(IBlockState p_damageDropped_1_)
    {
        return getItem(null, BlockPos.ORIGIN, p_damageDropped_1_).getMetadata();
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch (state.getActualState(world, pos).getValue(VARIANT))
        {
            case TICKET_GATE:
                TileEntity te = world.getTileEntity(pos);
                if(te instanceof TileEntityTicketGate && ((TileEntityTicketGate)te).isGateOpen())
                {
                    return NULL_AABB;
                }
                return FENCE_AABB;
            case IC_TICKET_GATE:
                return FENCE_AABB;
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        switch(state.getActualState(world, pos).getValue(VARIANT))
        {
            case TICKET_MACHINE:
            default:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockTicketMachine), 1, 0);
            case TICKET_GATE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockTicketMachine), 1, 4);
            case STAFF_TICKET_MACHINE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockTicketMachine), 1, 8);
            case IC_TICKET_GATE:
                return new ItemStack(Item.getItemFromBlock(StationsItems.blockTicketMachine), 1, 12);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        switch (meta) {
            case 0:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_MACHINE).withProperty(FACING, EnumFacing.NORTH);
            case 1:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_MACHINE).withProperty(FACING, EnumFacing.SOUTH);
            case 2:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_MACHINE).withProperty(FACING, EnumFacing.WEST);
            case 3:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_MACHINE).withProperty(FACING, EnumFacing.EAST);
            case 4:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_GATE).withProperty(FACING, EnumFacing.NORTH);
            case 5:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_GATE).withProperty(FACING, EnumFacing.SOUTH);
            case 6:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_GATE).withProperty(FACING, EnumFacing.WEST);
            case 7:
                return getDefaultState().withProperty(VARIANT, EnumType.TICKET_GATE).withProperty(FACING, EnumFacing.EAST);
            case 8:
                return getDefaultState().withProperty(VARIANT, EnumType.STAFF_TICKET_MACHINE).withProperty(FACING, EnumFacing.NORTH);
            case 9:
                return getDefaultState().withProperty(VARIANT, EnumType.STAFF_TICKET_MACHINE).withProperty(FACING, EnumFacing.SOUTH);
            case 10:
                return getDefaultState().withProperty(VARIANT, EnumType.STAFF_TICKET_MACHINE).withProperty(FACING, EnumFacing.WEST);
            case 11:
                return getDefaultState().withProperty(VARIANT, EnumType.STAFF_TICKET_MACHINE).withProperty(FACING, EnumFacing.EAST);
            case 12:
                return getDefaultState().withProperty(VARIANT, EnumType.IC_TICKET_GATE).withProperty(FACING, EnumFacing.NORTH);
            case 13:
                return getDefaultState().withProperty(VARIANT, EnumType.IC_TICKET_GATE).withProperty(FACING, EnumFacing.SOUTH);
            case 14:
                return getDefaultState().withProperty(VARIANT, EnumType.IC_TICKET_GATE).withProperty(FACING, EnumFacing.WEST);
            case 15:
                return getDefaultState().withProperty(VARIANT, EnumType.IC_TICKET_GATE).withProperty(FACING, EnumFacing.EAST);
        }
        return getDefaultState().withProperty(VARIANT, EnumType.TICKET_MACHINE).withProperty(FACING, EnumFacing.NORTH);
    }


    private int getMetaFacing(EnumFacing face)
    {
        return Math.max(0, face.getIndex() - 2);
    }


    @Override
    public int getMetaFromState(IBlockState state)
    {
        switch (state.getValue(VARIANT))
        {
            case TICKET_MACHINE:
                return getMetaFacing(state.getValue(FACING));
            case TICKET_GATE:
                return 4 + getMetaFacing(state.getValue(FACING));
            case STAFF_TICKET_MACHINE:
                return 8 + getMetaFacing(state.getValue(FACING));
            case IC_TICKET_GATE:
                return 12 + getMetaFacing(state.getValue(FACING));
        }
        return 0;
    }


    @Override
    public IBlockState withRotation(IBlockState state, Rotation p_withRotation_2_)
    {
        return state.withProperty(FACING, p_withRotation_2_.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror p_withMirror_2_)
    {
        return state.withRotation(p_withMirror_2_.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateForPlacement(World p_getStateForPlacement_1_, BlockPos p_getStateForPlacement_2_, EnumFacing p_getStateForPlacement_3_, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase p_getStateForPlacement_8_)
    {
        IBlockState state = getStateFromMeta(p_getStateForPlacement_7_);
        return state.withProperty(FACING, p_getStateForPlacement_8_.getHorizontalFacing().getOpposite());
    }

    @Override
    public void getSubBlocks(CreativeTabs p_getSubBlocks_1_, NonNullList<ItemStack> p_getSubBlocks_2_)
    {
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 0));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 4));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 8));
        p_getSubBlocks_2_.add(new ItemStack(this, 1, 12));
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
        return getStateFromMeta(i).getValue(VARIANT) == EnumType.TICKET_GATE ? new TileEntityTicketGate() : null;
    }


    enum EnumType implements IStringSerializable
    {
        TICKET_MACHINE("ticket_machine"),
        STAFF_TICKET_MACHINE("staff_ticket_machine"),

        TICKET_GATE("ticket_gate"),
        IC_TICKET_GATE("ic_ticket_gate");
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
