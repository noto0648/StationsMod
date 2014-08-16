package com.noto0648.stations.items;

import com.noto0648.stations.Stations;
import com.noto0648.stations.tile.TileEntityMark;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * Created by Noto on 14/08/07.
 */
public class ItemLanSetter extends Item
{
    public ItemLanSetter()
    {
        super();
        setCreativeTab(Stations.tab);
        setTextureName("notomod:lan_setter");
        setUnlocalizedName("NotoMod.lanSetter");
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if(item.getItem() == this)
        {
            Block blk = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if(blk == Stations.instance.pillarBlock && meta == 1)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger("parentX", x);
                compound.setInteger("parentY", y);
                compound.setInteger("parentZ", z);
                item.setTagCompound(compound);
                if(world.isRemote)
                    player.addChatMessage(new ChatComponentText("Register parent!"));
            }
            else if(blk == Stations.instance.stationFence && (meta == 15 || meta == 14))
            {
                if(item.hasTagCompound())
                {
                    int _x = item.getTagCompound().getInteger("parentX");
                    int _y = item.getTagCompound().getInteger("parentY");
                    int _z = item.getTagCompound().getInteger("parentZ");
                    if(world.getBlock(_x, _y, _z) == Stations.instance.pillarBlock && world.getBlockMetadata(_x, _y, _z) == 1)
                    {
                        TileEntity te = world.getTileEntity(x, y, z);
                        if(te != null && te instanceof TileEntityMark)
                        {
                            if(((TileEntityMark)te).setParent(_x, _y, _z))
                            {
                                if(world.isRemote)
                                    player.addChatMessage(new ChatComponentText("Successful registration"));
                            }

                        }
                    }
                    else
                    {
                        if(world.isRemote)
                            player.addChatMessage(new ChatComponentText("Parent is not found"));
                    }
                }
                else
                {
                    if(world.isRemote)
                        player.addChatMessage(new ChatComponentText("Parent is not registered"));
                }
            }

        }
        return true;
    }
}
