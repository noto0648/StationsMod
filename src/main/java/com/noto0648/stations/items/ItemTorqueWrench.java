package com.noto0648.stations.items;

import com.noto0648.stations.Stations;
import com.noto0648.stations.tile.TileEntityNamePlate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/14.
 */
public class ItemTorqueWrench extends Item
{
    public ItemTorqueWrench()
    {
        setCreativeTab(Stations.tab);
        setTextureName("notomod:torque_wrench");
        setUnlocalizedName("NotoMod.torqueWrench");
        setMaxStackSize(1);

    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
        {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null && te instanceof TileEntityNamePlate)
            {
                ((TileEntityNamePlate)te).pasteFace++;
                ((TileEntityNamePlate)te).pasteFace %= 3;
            }
            return true;
        }
        return false;
    }

}
