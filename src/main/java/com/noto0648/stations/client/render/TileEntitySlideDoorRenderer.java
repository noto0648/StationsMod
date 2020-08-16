package com.noto0648.stations.client.render;

import com.noto0648.stations.tiles.TileEntitySlideDoor;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class TileEntitySlideDoorRenderer extends TileEntitySpecialRenderer<TileEntitySlideDoor>
{
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

    @Override
    public void render(TileEntitySlideDoor tile, double x, double y, double z, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        if(getWorld() == null)
            return;
        IBlockState state = getWorld().getBlockState(tile.getPos()).getActualState(getWorld(), tile.getPos()).withProperty(BlockDoor.OPEN, false).withProperty(BlockDoor.POWERED, false);
        BlockPos pos = tile.getPos();

        int coolTime = 0;
        IBlockState actualState = getWorld().getBlockState(pos).getActualState(getWorld(), tile.getPos());

        EnumFacing facing = state.getValue(BlockDoor.FACING);
        boolean isOpen = actualState.getValue(BlockDoor.OPEN);
        if(actualState.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            TileEntity t =getWorld().getTileEntity(pos.down());
            if(t != null && t instanceof TileEntitySlideDoor)
            {
                coolTime = ((TileEntitySlideDoor) t).getCoolTime();
                facing = getWorld().getBlockState (pos.down()).getActualState(getWorld(), pos.down()).getValue(BlockDoor.FACING);
                isOpen = getWorld().getBlockState(pos.down()).getActualState(getWorld(), pos.down()).getValue(BlockDoor.OPEN);
            }
        }
        else
        {
            coolTime = tile.getCoolTime();
        }

        EnumFacing f = facing.rotateYCCW();
        float cool = coolTime / 10f;
        if(isOpen) cool = 1f-cool;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        Vec3i v = actualState.getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT ?  f.getOpposite().getDirectionVec() : f.getDirectionVec();

        GlStateManager.translate(-pos.getX() + v.getX() * cool, -pos.getY(), -pos.getZ() + v.getZ() * cool);
        IBakedModel model = blockRenderer.getModelForState(state);
        Tessellator tessellator = Tessellator.getInstance();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        blockRenderer.getBlockModelRenderer().renderModel(getWorld(), model, state, tile.getPos(), bufferBuilder, true);
        tessellator.draw();

        GlStateManager.popMatrix();

    }
}