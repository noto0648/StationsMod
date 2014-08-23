package com.noto0648.stations.blocks;

import com.noto0648.stations.*;
import com.noto0648.stations.client.render.TileEntityTicketGateRender;
import com.noto0648.stations.client.render.TileEntityTicketMachineRender;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tile.TileEntityTicketGate;
import com.noto0648.stations.tile.TileEntityTicketMachine;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Noto on 14/08/09.
 */
public class BlockTicketMachine extends BlockContainer implements ISimpleBlockRenderingHandler
{
    public BlockTicketMachine()
    {
        super(Material.rock);
        setBlockName("NotoMod.ticketMachine");
        setCreativeTab(Stations.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int damageDropped(int p_149692_1_)
    {
        if(p_149692_1_ < 4)
            return 0;

        if(p_149692_1_ < 8)
            return 4;

        return 0;
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        int meta = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        if(meta < 4 && Utils.INSTANCE.haveWrench(p_149727_5_) && Stations.instance.isLoadedEconomy)
        {
            p_149727_5_.openGui(Stations.instance, 40, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
            return true;
        }
        else if(meta < 4 && Stations.instance.isLoadedEconomy)
        {
            p_149727_5_.openGui(Stations.instance, 2, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
            return true;
        }
        else if(meta < 8 && Utils.INSTANCE.haveTicket(p_149727_5_))
        {
            TileEntity te = p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
            if(te != null && te instanceof TileEntityTicketGate)
            {
                ((TileEntityTicketGate)te).openGate(p_149727_5_.getEntityId());
                p_149727_5_.setCurrentItemOrArmor(0, TileEntityTicketGate.cutTicket(p_149727_5_.getCurrentEquippedItem()));

                p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double) (p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(l == 2) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage() + 0, 2);
        if(l == 1) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage() + 1, 2);
        if(l == 0) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage() + 2, 2);
        if(l == 3) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage() + 3, 2);

    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int par1, int par2, int par3)
    {
        int meta = p_149668_1_.getBlockMetadata(par1, par2, par3);
        if(meta >= 4 && meta <= 7)
        {
            TileEntity te = p_149668_1_.getTileEntity(par1, par2, par3);
            if(te != null && te instanceof TileEntityTicketGate)
            {
                if(((TileEntityTicketGate)te).isGateOpen())
                {
                    return null;
                }
            }
            return AxisAlignedBB.getBoundingBox(par1, par2, par3, par1 + 1.0, par2 + 1.5, par3 + 1.0);
        }

        return AxisAlignedBB.getBoundingBox(par1, par2, par3, par1 + 1.0, par2 + 1.0, par3 + 1.0);
    }


    @Override
    public int getRenderType()
    {
        return Stations.instance.tickerMachineRenderId;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        if(p_149915_2_ < 4)
        {
            return new TileEntityTicketMachine();
        }
        else if(p_149915_2_ < 8)
        {
            return new TileEntityTicketGate();
        }
        return null;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if(modelId == getRenderId())
        {
            if(metadata < 4)
            {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityTicketMachineRender.texture);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                TileEntityTicketMachineRender.model.renderAll();
                GL11.glPopMatrix();
            }
            else
            {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityTicketGateRender.texture);
                GL11.glTranslatef(0F, 0F, 0.5F);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                TileEntityTicketGateRender.model.renderPart("machine_machine_cube");
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return Stations.instance.tickerMachineRenderId;
    }
}
