package com.noto0648.stations.client.render;

import com.noto0648.stations.client.fontrenderer.NewFontRenderer;
import com.noto0648.stations.entity.EntityVerticalNamePlate;
import com.noto0648.stations.nameplate.NamePlateManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderVerticalNamePlate extends Render<EntityVerticalNamePlate>
{
    public RenderVerticalNamePlate(RenderManager p_i46179_1_)
    {
        super(p_i46179_1_);
    }

    @Override
    public void doRender(EntityVerticalNamePlate p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_6_, float p_doRender_8_, float p_doRender_9_)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(p_doRender_2_, p_doRender_4_, p_doRender_6_);

        GlStateManager.rotate(180.0F - p_doRender_1_.rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5f, -0.5f, 0);
        GlStateManager.translate(0f,0f, p_doRender_1_.getBlockOffset());
        GlStateManager.translate(0f,1f,0f);
        GlStateManager.scale(1f,-1f,1f);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite sprite = NamePlateManager.INSTANCE.getVerticalTexture(p_doRender_1_.getTexture());
        GL11.glNormal3f(0f,0f, 1f);
        Tessellator tes = Tessellator.getInstance();
        tes.getBuffer().begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX_COLOR);
        tes.getBuffer().pos(1,1,0).tex(sprite.getMaxU(), sprite.getMaxV()).color(1f,1f,1f,1f).endVertex();
        tes.getBuffer().pos(0,1,0).tex(sprite.getMinU(), sprite.getMaxV()).color(1f,1f,1f,1f).endVertex();
        tes.getBuffer().pos(1,0,0).tex(sprite.getMaxU(), sprite.getMinV()).color(1f,1f,1f,1f).endVertex();
        tes.getBuffer().pos(0,0,0).tex(sprite.getMinU(), sprite.getMinV()).color(1f,1f,1f,1f).endVertex();
        tes.draw();

        GlStateManager.pushMatrix();
        GlStateManager.scale(1f,-1f,1f);
        GlStateManager.translate(1f,0f,-0.02);
        GlStateManager.rotate(180f, 0f,1f, 0f);
        GlStateManager.scale(1/32f, 1/32f, 1);
        GlStateManager.scale(1/4f, 1/4f, 1);
        final String label = p_doRender_1_.getLabel("stationName");

        int height = NewFontRenderer.INSTANCE.drawStringVertical(48, 4, label, false);
        if(height > 120)
        {
            final float sc = 120f/height;
            GlStateManager.translate(0f, -120f,0f);
            GlStateManager.scale(1f, sc,1f);
        }
        else
        {
            GlStateManager.translate(0, -height, 0);
        }
        //GlStateManager.depthMask(true);
        NewFontRenderer.INSTANCE.drawStringVertical(48, 4, label, true);
        //GlStateManager.depthMask(false);

        GlStateManager.popMatrix();

        GlStateManager.popMatrix();

        super.doRender(p_doRender_1_, p_doRender_2_, p_doRender_4_, p_doRender_6_, p_doRender_8_, p_doRender_9_);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityVerticalNamePlate entityVerticalNamePlate)
    {
        return null;
    }
}
