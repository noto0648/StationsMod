package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.entity.EntityVerticalNamePlate;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemVerticalNamePlate extends Item
{
    public ItemVerticalNamePlate()
    {
        super();
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.vertical_nameplate");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer p_onItemUse_1_, World p_onItemUse_2_, BlockPos p_onItemUse_3_, EnumHand p_onItemUse_4_, EnumFacing p_onItemUse_5_, float p_onItemUse_6_, float p_onItemUse_7_, float p_onItemUse_8_)
    {
        final ItemStack lvt_9_1_ = p_onItemUse_1_.getHeldItem(p_onItemUse_4_);
        final BlockPos lvt_10_1_ = p_onItemUse_3_.offset(p_onItemUse_5_);
        if (p_onItemUse_5_ != EnumFacing.DOWN && p_onItemUse_5_ != EnumFacing.UP && p_onItemUse_1_.canPlayerEdit(lvt_10_1_, p_onItemUse_5_, lvt_9_1_))
        {
            final EntityHanging lvt_11_1_ = this.createEntity(p_onItemUse_2_, lvt_10_1_, p_onItemUse_5_);
            if (lvt_11_1_ != null && lvt_11_1_.onValidSurface())
            {
                if (!p_onItemUse_2_.isRemote)
                {
                    lvt_11_1_.playPlaceSound();
                    p_onItemUse_2_.spawnEntity(lvt_11_1_);
                }
                lvt_9_1_.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    private EntityHanging createEntity(World p_createEntity_1_, BlockPos p_createEntity_2_, EnumFacing p_createEntity_3_)
    {
        return new EntityVerticalNamePlate(p_createEntity_1_, p_createEntity_2_, p_createEntity_3_);
    }


}
