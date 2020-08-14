package com.noto0648.stations.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockBase extends ItemBlock
{
    //private Block
    public ItemBlockBase(Block p_i45328_1_)
    {
        super(p_i45328_1_);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.block.getUnlocalizedName() + p_77667_1_.getItemDamage();
    }

    @Override
    public int getMetadata(int p_77647_1_)
    {
        return p_77647_1_;
    }

    @SideOnly(Side.CLIENT)
    public ItemBlockBase setRenderer(net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer t)
    {
        setTileEntityItemStackRenderer(t);
        return this;
    }

}
