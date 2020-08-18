package com.noto0648.stations.client.fontrenderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class FontTexture extends AbstractTexture
{
    private final BufferedImage buffer;

    public FontTexture(BufferedImage bImage)
    {
        buffer = bImage;
    }

    @Override
    public void loadTexture(IResourceManager manager) throws IOException
    {
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), buffer, false, false);
    }
}
