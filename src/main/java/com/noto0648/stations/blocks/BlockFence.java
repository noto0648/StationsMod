package com.noto0648.stations.blocks;

import com.noto0648.stations.*;
import com.noto0648.stations.client.model.ModelFence;
import com.noto0648.stations.client.render.TileEntityMarkRender;
import com.noto0648.stations.tile.TileEntityFence;
import com.noto0648.stations.tile.TileEntityMark;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Noto on 14/08/04.
 */
public class BlockFence extends BlockContainer implements ISimpleBlockRenderingHandler
{
    private static ModelFence model = new ModelFence();
    private static ResourceLocation texture = new ResourceLocation("notomod", "textures/models/fence_texture.png");
    public BlockFence()
    {
        super(Material.iron);
        setBlockName("NotoMod.stationFence");
        setCreativeTab(Stations.tab);
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
    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
    {
        return false;
    }

    @Override
    public int damageDropped(int p_149692_1_)
    {
        if(p_149692_1_ <= 3 && p_149692_1_ >= 0)
            return 0;

        if(p_149692_1_ <= 14 && p_149692_1_ >= 14)
            return 15;

        return p_149692_1_;
    }

    @Override
    public int getRenderType()
    {
        return Stations.instance.fenceRendererId;
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double) (p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if(p_149689_6_.getItemDamage() == 0)
        {
            if (l == 0) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0, 2);
            if (l == 1) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 1, 2);
            if (l == 2) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
            if (l == 3) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }
        else if(p_149689_6_.getItemDamage() >= 14)
        {
            if(l == 2 || l == 0)
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 14, 2);
            else
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 15, 2);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int par1, int par2, int par3)
    {
        return AxisAlignedBB.getBoundingBox(par1, par2, par3, par1 + 1.0, par2 + 1.5, par3 + 1.0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 15));
    }


    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if(block == Stations.instance.stationFence)
        {
            if(modelId == getRenderId())
            {
                if(metadata == 0)
                {
                    GL11.glPushMatrix();
                    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                    GL11.glTranslatef(0.5F, 1.4F, 0.5F);
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    model.allRender(0.065F);
                    GL11.glPopMatrix();
                }
                else if(metadata >= 14)
                {
                    GL11.glPushMatrix();
                    GL11.glScalef(0.4F, 0.4F, 0.4F);
                    Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMarkRender.departureTexture);
                    TileEntityMarkRender.departureModel.renderAll();
                    GL11.glPopMatrix();
                }
            }
        }
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
        return getRenderId() == modelId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderId()
    {
        return Stations.instance.fenceRendererId;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        if(p_149915_2_ >= 14)
        {
            return new TileEntityMark();
        }
        return new TileEntityFence();
    }
}
