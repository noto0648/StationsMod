package com.noto0648.stations.blocks;

import com.noto0648.stations.Stations;
import com.noto0648.stations.client.render.TileEntityNumberPlateRender;
import com.noto0648.stations.tile.TileEntityMarkMachine;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.tile.TileEntityNumberPlate;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/04.
 */
public class BlockPillar extends BlockContainer implements ISimpleBlockRenderingHandler
{
    public Map<String, IIcon> iconMap = new HashMap<String, IIcon>();

    public BlockPillar()
    {
        super(Material.rock);
        //setBlockBounds(0.4F, 0F, 0.4F, 0.6F, 1F, 0.6F);
        setBlockName("NotoMod.stationPillar");
        //setBlockTextureName("iron_block");
        setCreativeTab(Stations.tab);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != this)
        {
            return block.getLightValue(world, x, y, z);
        }
        int meta = world.getBlockMetadata(x, y, z);
        if(meta == 7 || meta == 8) return 14;
        return 0;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        super.setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);

        int meta = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);

        if(meta == 0 || meta == 6) setBlockBounds(0.4F, 0F, 0.4F, 0.6F, 1F, 0.6F);
        else if(meta == 1) setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
        else if(meta >= 2 && meta <= 5) setBlockBounds(0F, 0F, 0F, 1F, 0.0625F, 1F);
        else setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        int meta = p_149633_1_.getBlockMetadata(p_149633_2_, p_149633_3_, p_149633_4_);
        if(meta == 0 || meta == 6)
        {
            return AxisAlignedBB.getBoundingBox((double)p_149633_2_ + 0.4F, (double)p_149633_3_ + 0F, (double)p_149633_4_ + 0.4F, (double)p_149633_2_ + 0.6F, (double)p_149633_3_ + 1F, (double)p_149633_4_ + 0.6F);
        }
        if(meta >= 2 && meta <= 5)
        {
            return AxisAlignedBB.getBoundingBox((double)p_149633_2_ + 0F, (double)p_149633_3_ + 0F, (double)p_149633_4_ + 0F, (double)p_149633_2_ + 1F, (double)p_149633_3_ + 0.0625F, (double)p_149633_4_ + 1F);
        }
        return AxisAlignedBB.getBoundingBox((double)p_149633_2_ + this.minX, (double)p_149633_3_ + this.minY, (double)p_149633_4_ + this.minZ, (double)p_149633_2_ + this.maxX, (double)p_149633_3_ + this.maxY, (double)p_149633_4_ + this.maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        int meta = p_149633_1_.getBlockMetadata(p_149633_2_, p_149633_3_, p_149633_4_);
        if(meta == 0 || meta == 6)
        {
            return AxisAlignedBB.getBoundingBox((double)p_149633_2_ + 0.4F, (double)p_149633_3_ + 0F, (double)p_149633_4_ + 0.4F, (double)p_149633_2_ + 0.6F, (double)p_149633_3_ + 1F, (double)p_149633_4_ + 0.6F);
        }
        if(meta >= 2 && meta <= 5)
        {
            return AxisAlignedBB.getBoundingBox((double)p_149633_2_ + 0F, (double)p_149633_3_ + 0F, (double)p_149633_4_ + 0F, (double)p_149633_2_ + 1F, (double)p_149633_3_ + 0.0625F, (double)p_149633_4_ + 1F);
        }
        return AxisAlignedBB.getBoundingBox((double)p_149633_2_ + this.minX, (double)p_149633_3_ + this.minY, (double)p_149633_4_ + this.minZ, (double)p_149633_2_ + this.maxX, (double)p_149633_3_ + this.maxY, (double)p_149633_4_ + this.maxZ);
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double) (p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if(p_149689_6_.getItemDamage() >= 2 && p_149689_6_.getItemDamage() <= 5)
        {
            if(l == 1) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
            if(l == 0) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
            if(l == 2) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
            if(l == 3) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }
        else if(p_149689_6_.getItemDamage() == 7 || p_149689_6_.getItemDamage() == 8)
        {
            if(l == 2 || l == 0) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 7, 2);
            if(l == 1 || l == 3) p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 8, 2);
        }
        else
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_6_.getItemDamage(), 2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1)
    {
        iconMap.put("iron_block", par1.registerIcon("iron_block"));
        iconMap.put("pillar_dark", par1.registerIcon("notomod:pillar_dark"));
        iconMap.put("mark_machine", par1.registerIcon("notomod:mark_machine"));

        iconMap.put("yellow_line_left", par1.registerIcon("notomod:yellow_line_left"));
        iconMap.put("yellow_line_down", par1.registerIcon("notomod:yellow_line_down"));
        iconMap.put("yellow_line_up", par1.registerIcon("notomod:yellow_line_up"));
        iconMap.put("yellow_line_right", par1.registerIcon("notomod:yellow_line_right"));

        iconMap.put("line", par1.registerIcon("notomod:line_side"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if(meta == 0)
            return iconMap.get("iron_block");
        if(meta == 1)
            return iconMap.get("mark_machine");
        if(meta == 6)
            return iconMap.get("pillar_dark");

        if(meta >= 2 && meta <= 5 && side == 1)
        {
            if(meta == 2)
                return iconMap.get("yellow_line_left");
            if(meta == 3)
                return iconMap.get("yellow_line_down");
            if(meta == 4)
                return iconMap.get("yellow_line_up");
            if(meta == 5)
                return iconMap.get("yellow_line_right");
        }
        else if(meta >= 2 && meta <= 5)
        {
            return iconMap.get("line");
        }

        return null;
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
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 6));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 7));
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        int meta = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        if(meta == 1 && Utils.INSTANCE.haveWrench(p_149727_5_))
        {
            p_149727_5_.openGui(Stations.instance, 0, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
            return true;
        }
        if((meta == 7 || meta == 8) && Utils.INSTANCE.haveWrench(p_149727_5_))
        {
            p_149727_5_.openGui(Stations.instance, 3, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
            return true;
        }
        return false;
    }

    @Override
    public int damageDropped(int p_149692_1_)
    {
        if(p_149692_1_ <= 5 && p_149692_1_ >= 2)
            return 2;
        if(p_149692_1_ == 7 || p_149692_1_ == 8)
            return 7;
        return p_149692_1_;
    }

    @Override
    public int getRenderType()
    {
        return Stations.instance.pillarRenderId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if(modelId == getRenderId())
        {
            if(metadata == 7 || metadata == 8)
            {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityNumberPlateRender.newTexture);
                TileEntityNumberPlateRender.plate.renderAll();
                GL11.glPopMatrix();
                return;
            }
            Tessellator tessellator = Tessellator.instance;

            if(metadata == 0 || metadata == 6) renderer.setRenderBounds(0.4F, 0F, 0.4F, 0.6F, 1F, 0.6F);
            else if(metadata >= 2 && metadata <= 5) renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.0625F, 1F);
            else renderer.setRenderBounds(0F, 0F, 0F, 1F, 1F, 1F);

            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
            tessellator.draw();

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
            tessellator.draw();

            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            renderer.clearOverrideBlockTexture();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if(getRenderId() == modelId && (meta == 0 || meta == 6))
        {
            renderer.setOverrideBlockTexture(renderer.getBlockIconFromSideAndMetadata(this, 0, meta));
            renderer.setRenderBounds(0.4F, 0F, 0.4F, 0.6F, 1F, 0.6F);
            renderer.setRenderAllFaces(true);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.clearOverrideBlockTexture();
            renderer.setRenderAllFaces(false);
            return true;
        }
        else if(getRenderId() == modelId && meta >= 2 && meta <= 5)
        {
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(this.getMixedBrightnessForBlock(world, x, y, z));
            renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.0025F, 1F);
            renderer.renderStandardBlock(this, x, y, z);
                /*
            renderer.renderFaceYNeg(this, x, y, z, renderer.getBlockIconFromSideAndMetadata(this, 0, meta));
            renderer.renderFaceYPos(this, x, y, z, renderer.getBlockIconFromSideAndMetadata(this, 1, meta));
            renderer.renderFaceZNeg(this, x, y, z, renderer.getBlockIconFromSideAndMetadata(this, 2, meta));
            renderer.renderFaceZPos(this, x, y, z, renderer.getBlockIconFromSideAndMetadata(this, 3, meta));
            renderer.renderFaceXNeg(this, x, y, z, renderer.getBlockIconFromSideAndMetadata(this, 4, meta));
            renderer.renderFaceXPos(this, x, y, z, renderer.getBlockIconFromSideAndMetadata(this, 5, meta));
            */
            renderer.clearOverrideBlockTexture();
            return true;

        }
        else if(meta == 7 || meta == 8)
        {
            return false;
        }
        else if(getRenderId() == modelId)
        {
            renderer.setRenderBounds(0F, 0F, 0F, 1F, 1F, 1F);
            renderer.setOverrideBlockTexture(getIcon(-1, world.getBlockMetadata(x, y, z)));

            renderer.renderStandardBlock(block, x, y, z);
            renderer.clearOverrideBlockTexture();
            return true;
        }
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
        return Stations.instance.pillarRenderId;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int meta)
    {
        if(meta == 1)
        {
            return new TileEntityMarkMachine();
        }
        if(meta == 7 || meta == 8)
        {
            return new TileEntityNumberPlate();
        }
        return null;
    }
}
