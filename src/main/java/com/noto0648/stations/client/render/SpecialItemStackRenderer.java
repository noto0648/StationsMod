package com.noto0648.stations.client.render;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.tiles.TileEntityStationFence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SpecialItemStackRenderer extends TileEntityItemStackRenderer
{
    private TileEntityStationFence fence = new TileEntityStationFence();

    @Override
    public void renderByItem(ItemStack p_renderByItem_1_, float p_renderByItem_2_)
    {
        Item item = p_renderByItem_1_.getItem();
        //this.fence.setItemValues(p_renderByItem_1_);

        System.out.println("aaaa");
        TileEntityRendererDispatcher.instance.render(this.fence, 0.0D, 0.0D, 0.0D, 0.0F, p_renderByItem_2_);
    }
}
