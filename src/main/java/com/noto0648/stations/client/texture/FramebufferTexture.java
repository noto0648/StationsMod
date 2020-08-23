package com.noto0648.stations.client.texture;

import com.noto0648.stations.client.fontrenderer.NewFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FramebufferTexture extends AbstractTexture
{
    private int frameBuffer = -1;
    private ByteBuffer buf;
    public FramebufferTexture()
    {
    }

    public void init()
    {
        if(frameBuffer != -1)
            return;

        buf = ByteBuffer.allocateDirect(512*512*3);

        frameBuffer = OpenGlHelper.glGenFramebuffers();
        OpenGlHelper.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        getGlTextureId();
        GlStateManager.bindTexture(glTextureId);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 512, 512,  0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buf);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

        OpenGlHelper.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D , getGlTextureId(), 0);
       /*
        OpenGlHelper.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GlStateManager.viewport(0, 0, 512, 512);
        GL11.glPushMatrix();
        //GL11.glLoadIdentity();
        GL11.glScalef(20f,20f,20f);
        GL11.glColor3f(1f,0f,1f);
        NewFontRenderer.INSTANCE.drawString("aaaaaaaaaaaaaaaaaaa");
        GL11.glFlush();
        GL11.glPopMatrix();
        */

        OpenGlHelper.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GlStateManager.viewport(0, 0, 512, 512);
        GL11.glClearColor(1f,1f,1f,1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-1.0, 1.0, -1.0, 1.0, 1.0, -1.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glScalef(0.5f, 0.5f, 1f);
        GL11.glPushMatrix();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1f,0f,0f);
        GL11.glVertex2d(0,0);
        GL11.glColor3f(1f,1f,0f);
        GL11.glVertex2d(1,0);
        GL11.glColor3f(0f,1f,0f);
        GL11.glVertex2d(1,1);
        GL11.glColor3f(0f,1f,1f);
        GL11.glVertex2d(0,1);
        GL11.glEnd();
        GL11.glPopMatrix();
        //GL11.glScalef(20f,20f,20f);
        GL11.glColor3f(1f,0f,1f);
        //GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        //GL11.glEnableClientState(GL11.GL_INDEX_ARRAY);
        //GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glScalef(1/32f, 1/32f, 1f);
        NewFontRenderer.INSTANCE.drawString("aaaaaaaaaaaaaaaaaaa");
        GL11.glPopMatrix();
        GL11.glFlush();
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);



    }

    public void testDraw()
    {


        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);

        GlStateManager.disableCull();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, getGlTextureId());
        Tessellator tes = Tessellator.getInstance();
        GL11.glColor3f(1f,1f,1f);
        tes.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        tes.getBuffer().pos(0,0,0).tex(0,0).endVertex();
        tes.getBuffer().pos(1,0,0).tex(1,0).endVertex();
        tes.getBuffer().pos(1,1,0).tex(1,1).endVertex();
        tes.getBuffer().pos(0,1,0).tex(0,1).endVertex();
        tes.draw();
        GlStateManager.enableCull();
    }

    @Override
    public void loadTexture(IResourceManager iResourceManager) throws IOException
    {

    }
}
