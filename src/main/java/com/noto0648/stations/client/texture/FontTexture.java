package com.noto0648.stations.client.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.awt.image.BufferedImageDevice;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Noto on 14/08/07.
 */
@SideOnly(Side.CLIENT)
public class FontTexture extends AbstractTexture
{
    private static final Logger logger = LogManager.getLogger();

    private BufferedImage buffer;

    public FontTexture(BufferedImage bImage)
    {
        buffer = bImage;
    }

    @Override
    public void loadTexture(IResourceManager manager) throws IOException
    {
        //this.deleteGlTexture();
        TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), buffer, false, false);
    }


}
