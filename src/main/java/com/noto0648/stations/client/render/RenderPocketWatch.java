package com.noto0648.stations.client.render;
import com.noto0648.stations.StationsItems;
import com.noto0648.stations.common.MinecraftDate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RenderPocketWatch
{
    private static Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void renderScreen(RenderGameOverlayEvent.Post event)
    {
        final ScaledResolution scale = new ScaledResolution(mc);
        final int offsetX = scale.getScaledWidth() / 12;
        final int offsetY = scale.getScaledHeight() - 26;

        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            if(mc.player.inventory.getCurrentItem() != ItemStack.EMPTY && mc.player.inventory.getCurrentItem().getItem() == StationsItems.itemPocketWatch)
            {
                if(mc.player.inventory.getCurrentItem().getItemDamage() == 0)
                {
                    GlStateManager.color(1F, 1F, 1F);
                    mc.fontRenderer.drawStringWithShadow(new MinecraftDate(mc.world.getWorldTime()).toString(), offsetX, offsetY, 0xFFFFFFFF);
                }
            }
            return;
        }

        if(mc.player.inventory.getCurrentItem() != ItemStack.EMPTY && mc.player.inventory.getCurrentItem().getItem() == StationsItems.itemPocketWatch)
        {
            GlStateManager.pushMatrix();
            GlStateManager.resetColor();
            GlStateManager.disableNormalize();
            GlStateManager.disableRescaleNormal();
            if(mc.player.inventory.getCurrentItem().getItemDamage() == 1)
            {

                final int radius = 18;
                final MinecraftDate md = new MinecraftDate(mc.world);

                GlStateManager.disableTexture2D();
                GlStateManager.disableCull();

                GL11.glEnable(GL11.GL_LINE_SMOOTH);

                GlStateManager.color(0.5f,0.5f,0.5f,0.7f);
                renderFilledCircle(radius + 4, offsetX, offsetY);
                GlStateManager.color(1f,1f,1f,1f);
                renderFilledCircleWhite(radius + 2, offsetX, offsetY);

                GlStateManager.glLineWidth(5f);
                GL11.glPointSize(4F);
                GlStateManager.color(0f,0f,0f,1f);
                Tessellator tes = Tessellator.getInstance();
                BufferBuilder builder = tes.getBuffer();
                builder.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION);
                for(int i = 0; i < 12; i++)
                {
                    float minute = (float)(30 * i * Math.PI / 180F);
                    builder.pos(offsetX + MathHelper.sin(minute) * radius, offsetY + Math.cos(minute) * radius, 0).endVertex();
                }
                tes.draw();

                final float minute = (float)(((6F * (60 - md.getMinutes())) - 180) * Math.PI / 180F);
                final float hours = (float)(((0.5F * (720 - (md.getHours() % 12 * 60 + md.getMinutes())) - 180) * Math.PI / 180F));

                GL11.glLineWidth(3);
                GlStateManager.color(0F, 0F, 0F);
                builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
                builder.pos(offsetX, offsetY, 0).endVertex();
                builder.pos(offsetX + MathHelper.sin(minute) * radius, offsetY + Math.cos(minute) * radius, 0).endVertex();;
                builder.pos(offsetX, offsetY, 0).endVertex();
                builder.pos(offsetX + MathHelper.sin(hours) * (radius - 5), offsetY + Math.cos(hours) * (radius - 5), 0).endVertex();
                tes.draw();

                GlStateManager.glLineWidth(1);
                GL11.glPointSize(1F);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GlStateManager.enableCull();
                GlStateManager.enableTexture2D();
            }
            GlStateManager.enableNormalize();
            GlStateManager.enableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.resetColor();
        }
    }

    private void renderFilledCircle(float r, int x, int y)
    {
        Tessellator tes = Tessellator.getInstance();
        tes.getBuffer().begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        tes.getBuffer().pos(x, y, 0).endVertex();
        for(float th1 = 0F; th1 <= 360F; th1 += 20F)
        {
            float rad = th1 / 180F * (float)Math.PI;
            float x1 = r * MathHelper.cos(rad);
            float y1 = r * MathHelper.sin(rad);

            tes.getBuffer().pos((double)(x1 + x), (double)(y1 + y), 0).endVertex();
        }
        tes.draw();
    }

    private void renderFilledCircleWhite(float r, int x, int y)
    {
        Tessellator tes = Tessellator.getInstance();
        tes.getBuffer().begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        tes.getBuffer().pos(x, y, 0).color(0F,0F,0F,0F).endVertex();
        for(float th1 = 0F; th1 <= 360F; th1 += 20F)
        {
            float rad = th1 / 180F * (float)Math.PI;
            float x1 = r * MathHelper.cos(rad);
            float y1 = r * MathHelper.sin(rad);

            tes.getBuffer().pos((double)(x1 + x), (double)(y1 + y), 0).color(0.84f,0.84f,0.84f,1f).endVertex();
        }
        tes.draw();
    }
}
