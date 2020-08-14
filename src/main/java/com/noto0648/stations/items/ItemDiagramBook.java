package com.noto0648.stations.items;

import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.MarkData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemDiagramBook extends Item
{
    public ItemDiagramBook()
    {
        super();
        setMaxStackSize(1);
        setCreativeTab(StationsMod.tab);
        setUnlocalizedName("notomod.diagram_book");
        //this.setHasSubtypes(true);
        //this.setMaxDamage(0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand p_onItemRightClick_3_)
    {
        if(player.isSneaking())
        {
            //return super.onItemRightClick(world, player, p_onItemRightClick_3_);
            return new ActionResult(EnumActionResult.PASS, player.getHeldItem(p_onItemRightClick_3_));
        }
        player.openGui(StationsMod.instance, 31, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(p_onItemRightClick_3_));

    }

    public static List<MarkData> readFromNBT(NBTTagCompound p_145839_1_, List<MarkData> markDataList)
    {
        if(markDataList == null)
            markDataList = new ArrayList();
        markDataList.clear();
        NBTTagList tags = (NBTTagList)p_145839_1_.getTag("marks");
        for(int i = 0; i < tags.tagCount(); i++)
        {
            NBTTagCompound compound = tags.getCompoundTagAt(i);
            MarkData mkd = new MarkData();
            mkd.readFromNBTTag(compound);
            markDataList.add(mkd);
        }
        return markDataList;
    }

    public static List<MarkData> readFromNBT(ItemStack itemStack, List<MarkData> markDataList)
    {
        if(!itemStack.hasTagCompound())
        {
            return markDataList;
        }
        return readFromNBT(itemStack.getTagCompound(), markDataList);
    }

    public static void writeToNBT(ItemStack itemStack, List<MarkData> markDataList)
    {
        if(!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());
        writeToNBT(itemStack.getTagCompound(), markDataList);
    }

    public static void writeToNBT(NBTTagCompound tag, List<MarkData> markDataList)
    {
        NBTTagList tags = new NBTTagList();
        for(int i = 0; i < markDataList.size(); i++)
        {
            NBTTagCompound compound = new NBTTagCompound();
            markDataList.get(i).writeToNBTTag(compound);
            tags.appendTag(compound);
        }

        tag.setTag("marks", tags);
    }
}
