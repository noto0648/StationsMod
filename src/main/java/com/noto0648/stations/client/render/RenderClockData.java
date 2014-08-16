package com.noto0648.stations.client.render;

import com.noto0648.stations.common.MinecraftDate;
import com.noto0648.stations.Stations;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/15.
 */
public class RenderClockData
{
    private static Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void renderScreen(RenderGameOverlayEvent event)
    {
        if(event.type != RenderGameOverlayEvent.ElementType.CROSSHAIRS)
            return;

        if(mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() == Stations.instance.clock)
        {
            GL11.glPushMatrix();
            if(mc.thePlayer.getCurrentEquippedItem().getItemDamage() == 0)
            {
                mc.fontRenderer.drawStringWithShadow(new MinecraftDate(mc.theWorld.getWorldTime()).toString(), 1, 1, 0xFFFFFF);
            }
            else if(mc.thePlayer.getCurrentEquippedItem().getItemDamage() == 1)
            {
                GL11.glLineWidth(5);

                MinecraftDate md = new MinecraftDate(mc.theWorld);

                Tessellator tes = Tessellator.instance;
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glColor3f(0F, 1F, 0F);
                tes.startDrawing(GL11.GL_POLYGON);
                GL11.glColor4f(1F, 1F, 1F, 0.5F);
                fillCircle(tes, 20, 22, 22);
                tes.draw();


                GL11.glPointSize(4F);
                GL11.glColor3f(1F, 1F, 1F);
                GL11.glColor3f(0F, 0F, 0F);
                tes.startDrawing(GL11.GL_POINTS);
                for(int i = 0; i < 12; i++)
                {
                    float minute = (float)(30 * i * Math.PI / 180F);
                    tes.addVertex(22 + MathHelper.sin(minute) * 18, 22 + Math.cos(minute) * 18, 0);
                }
                tes.draw();

                GL11.glLineWidth(3);
                GL11.glColor3f(0F, 0F, 0F);
                tes.startDrawing(GL11.GL_LINES);
                tes.addVertex(22, 22, 0);
                float minute = (float)(((6F * (60 - md.getMinutes())) - 180) * Math.PI / 180F);
                tes.addVertex(22 + MathHelper.sin(minute) * 18, 22 + Math.cos(minute) * 18, 0);
                tes.draw();

                GL11.glLineWidth(5);
                tes.startDrawing(GL11.GL_LINES);
                tes.addVertex(22, 22, 0);
                float hours = (float)(((0.5F * (720 - (md.getHours() % 12 * 60 + md.getMinutes())) - 180) * Math.PI / 180F));
                tes.addVertex(22 + MathHelper.sin(hours) * 15, 22 + Math.cos(hours) * 15, 0);
                tes.draw();
                GL11.glColor3f(1F, 1F, 1F);
                GL11.glLineWidth(1);
                GL11.glPointSize(1F);
                /*
                tes.addVertex(10, 10, 0);
                tes.addVertex(100, 100, 0);
                */

                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }
            GL11.glPopMatrix();
        }
    }

    private void fillCircle(Tessellator tes, float r, int x, int y)
    {
        for(float th1 = 0F; th1 <= 360F; th1++)
        {
            float th2 = th1 + 10F;
            float th1_rad = th1 / 180F * (float)(Math.PI);
            float th2_rad = th2 / 180F * (float)(Math.PI);
            float x1 = r * MathHelper.cos(th1_rad);
            float y1 = r * MathHelper.sin(th1_rad);
            float x2 = r * MathHelper.cos(th2_rad);
            float y2 = r * MathHelper.sin(th2_rad);

            tes.addVertex((double)(x1 + x), (double)(y1 + y), 0);
            tes.addVertex((double)(x2 + x), (double)(y2 + y), 0);
        }
    }
}
