package com.noto0648.stations.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Noto on 14/08/04.
 */
public class ItemBlockBase extends ItemBlock
{
    public ItemBlockBase(Block p_i45328_1_)
    {
        super(p_i45328_1_);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.field_150939_a.getUnlocalizedName() + p_77667_1_.getItemDamage();
    }

    @Override
    public int getMetadata(int p_77647_1_)
    {
        return p_77647_1_;
    }
}
