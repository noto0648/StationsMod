package com.noto0648.stations.client.render;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.blocks.BlockNamePlate;
import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.tiles.TileEntityNamePlate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Map;


public class TileEntityNamePlateRenderer extends TileEntitySpecialRenderer<TileEntityNamePlate>
{
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

    @Override
    public void render(TileEntityNamePlate p_147500_1_, double x, double y, double z, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        super.render(p_147500_1_, x, y, z, p_render_8_, p_render_9_, p_render_10_);

        final NamePlateBase renderPlate = NamePlateManager.INSTANCE.getNamePlateFromName(p_147500_1_.currentType);
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        renderModel(getWorld(), p_147500_1_, renderPlate);
        //GlStateManager.disableRescaleNormal();
        renderSub(renderPlate, p_147500_1_, x, y, z, p_render_8_, p_render_9_, p_render_10_);
        //GlStateManager.enableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    @Override
    public void renderTileEntityFast(TileEntityNamePlate p_renderTileEntityFast_1_, double x, double y, double z, float p_renderTileEntityFast_8_, int p_renderTileEntityFast_9_, float p_renderTileEntityFast_10_, BufferBuilder p_renderTileEntityFast_11_)
    {
    }

    public void renderSub(NamePlateBase renderPlate, TileEntityNamePlate p_147500_1_, double par2, double par3, double par4, float p_render_8_, int p_render_9_, float p_render_10_)
    {
        if(!p_147500_1_.hasWorld())
            return;
        final IBlockState blockState = getWorld().getBlockState(p_147500_1_.getPos());
        final BlockNamePlate.EnumPosition tilePos = blockState.getValue(BlockNamePlate.POSITION);
        final BlockNamePlate.EnumRotating rotate = blockState.getValue(BlockNamePlate.ROTATING);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5f, 0.5f, 0.5f);
        if(rotate == BlockNamePlate.EnumRotating.HORIZONTAL)
            GL11.glRotatef(-90F, 0, 1, 0);

        GlStateManager.translate(-tilePos.getOffset(),0, 0);

        Map<String, String> strMap = (p_147500_1_).getHashMap();


        for(int i = 0; i < 2; i++)
        {
            GlStateManager.pushMatrix();
            GL11.glRotatef(-180F * i + 90f, 0, 1, 0);
            if(renderPlate != null && strMap != null)
            {
                renderPlate.render(strMap, i == 0, p_147500_1_.pasteFace);
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();

    }

    public void renderSub2(TileEntityNamePlate p_147500_1_, double par2, double par3, double par4, float p_render_8_, int p_render_9_, float p_render_10_)
    {

        if(!p_147500_1_.hasWorld())
            return;

        int meta = p_147500_1_.getBlockMetadata();

        IBlockState blockState = getWorld().getBlockState(p_147500_1_.getPos());
        BlockNamePlate.EnumPosition tilePos = blockState.getValue(BlockNamePlate.POSITION);
        BlockNamePlate.EnumRotating rotate = blockState.getValue(BlockNamePlate.ROTATING);

        NamePlateBase renderPlate = null;

        for(int i = 0; i < NamePlateManager.INSTANCE.getNamePlates().size(); i++)
        {
            String name = NamePlateManager.INSTANCE.getNamePlates().get(i).getNamePlateId();
            if(name.equalsIgnoreCase(((TileEntityNamePlate)p_147500_1_).currentType))
            {
                renderPlate = NamePlateManager.INSTANCE.getNamePlates().get(i);
            }
        }

        GL11.glPushMatrix();

        //GL11.glTranslatef((float) par2 + 0.5F, (float) par3 + 0.5F, (float) par4 + 0.5F);
        GlStateManager.translate(0.5f, 0.5f, 0.5f);

        /*
        if(tilePos == BlockNamePlate.EnumPosition.BACK)
            GL11.glTranslatef(-0.5F, 0F, 0F);
        if(tilePos == BlockNamePlate.EnumPosition.FRONT)
            GL11.glTranslatef(0.5F, 0F, 0F);
        */
/*
        if(meta == 5) GL11.glTranslatef(-0.5F, 0F, 0F);
        if(meta == 4) GL11.glTranslatef(0.5F, 0F, 0F);
        if(meta == 2)
        {
            GL11.glTranslatef(0F, 0F, 0.5F);
            GL11.glRotatef(-90F, 0, 1, 0);
        }
        if(meta == 3)
        {
            GL11.glTranslatef(0F, 0F, -0.5F);
            GL11.glRotatef(-90F, 0, 1, 0);
        }
*/
        if(rotate == BlockNamePlate.EnumRotating.HORIZONTAL)
            GL11.glRotatef(-90F, 0, 1, 0);

        //GL11.glPushMatrix();
        //GL11.glScaled(0.5F, 0.5F, 0.5F);




        String tex = ((TileEntityNamePlate)p_147500_1_).texture;
        if(tex.equalsIgnoreCase("DefaultTexture"))
        {
            //bindCharTexture(texture);
            //IBakedModel bakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(modelResourceLocation);
        }
        else
        {
            /*
            if(!TextureImporter.INSTANCE.bindCharTexture(tex))
            {
                bindCharTexture(texture);
            }
            */
        }
        if(renderPlate != null && renderPlate.isUserRender())
        {
            renderPlate.userRender(((TileEntityNamePlate)p_147500_1_).pasteFace);
        }
        else
        {
            //model.renderAll();
            //renderModel(getWorld(), p_147500_1_);
        }
        //GL11.glPopMatrix();


        Map<String, String> strMap = ((TileEntityNamePlate)p_147500_1_).getHashMap();

        for(int i = 0; i < 2; i++)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(-180F * i + 90f, 0, 1, 0);

            /*
            if(meta == 0 || meta == 3 || meta == 2 || meta == 5 || meta == 4)
            {
                if(i == 0) GL11.glRotatef(-90F, 0, 1, 0);
                if(i == 1) GL11.glRotatef(-270F, 0, 1, 0);
            }
            else
            {
                if(i == 0) GL11.glRotatef(-0F, 0, 1, 0);
                if(i == 1) GL11.glRotatef(-180F, 0, 1, 0);
            }
*/
            if(renderPlate != null && strMap != null)
            {
                renderPlate.render(strMap, i == 0, ((TileEntityNamePlate)p_147500_1_).pasteFace);
            }

            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();

    }

    private  void renderModel(World world, TileEntityNamePlate te, NamePlateBase plate)
    {
        GlStateManager.pushMatrix();
        //RenderHelper.disableStandardItemLighting();
        //this.bindCharTexture(TEST_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }


        final IBlockState actualState = getWorld().getBlockState(te.getPos());
        final BlockNamePlate.EnumRotating rotate = actualState.getValue(BlockNamePlate.ROTATING);
        final BlockNamePlate.EnumPosition pos = actualState.getValue(BlockNamePlate.POSITION);

        if(rotate == BlockNamePlate.EnumRotating.HORIZONTAL)
        {
            GlStateManager.translate(0.5f,0,0.5f);
            GlStateManager.rotate(90f, 0f, 1f, 0);
            GlStateManager.translate(-0.5f,0,-0.5f);

            GlStateManager.translate(pos.getOffset(),0, 0);
        }
        else
        {
            GlStateManager.translate(-pos.getOffset(),0, 0);
        }
        //GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        IBlockState state = StationsItems.blockNamePlate.getDefaultState();
        //IBakedModel model = blockRenderer.getModelForState(state);
        IBakedModel model = plate.getModel(te.texture);

        Tessellator tessellator = Tessellator.getInstance();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        //GlStateManager.disableLighting();
        /*
        GL11.glColor3f(1f,1f,1f);
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
       b lockRenderer.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        tessellator.draw();
        */
        blockRenderer.getBlockModelRenderer().renderModelBrightnessColor(model, 1f, 1f, 1f, 1f);

        //GlStateManager.enableLighting();
        //RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}