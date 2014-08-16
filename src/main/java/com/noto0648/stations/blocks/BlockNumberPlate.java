package com.noto0648.stations.blocks;

import com.noto0648.stations.Stations;
import com.noto0648.stations.tile.TileEntityNumberPlate;
import com.noto0648.stations.client.render.TileEntityNumberPlateRender;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Noto on 14/08/04.
 */
public class BlockNumberPlate extends BlockContainer implements ISimpleBlockRenderingHandler
{

    public BlockNumberPlate()
    {
        super(Material.rock);
        setBlockName("NotoMod.numberPlates");
        setCreativeTab(Stations.tab);

    }

    public int getLightValue()
    {
        return 14;
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
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 6));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 8));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 10));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 12));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 14));
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double) (p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(l == 2 || l == 0)p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage(), 2);
        if(l == 1 || l == 3)p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage() + 1, 2);
    }

    @Override
    public int damageDropped(int p_149692_1_)
    {
        if(p_149692_1_ % 2 == 1)
            return p_149692_1_ - 1;
        return p_149692_1_;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityNumberPlate();
    }

    @Override
    public int getRenderType()
    {
        return Stations.instance.numberPlateId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        int value = metadata / 2;
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityNumberPlateRender.textures[value]);
        TileEntityNumberPlateRender.plate.renderAll();
        GL11.glPopMatrix();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderId()
    {
        return Stations.instance.numberPlateId;
    }
}
