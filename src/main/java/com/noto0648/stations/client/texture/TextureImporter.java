package com.noto0648.stations.client.texture;

import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;

/**
 * Created by Noto on 14/08/08.
 */
public class TextureImporter
{
    public static TextureImporter INSTANCE = new TextureImporter();

    private Map<String, FontTexture> textures = new HashMap<String, FontTexture>();

    private TextureImporter() {}

    public boolean readTexture(String path)
    {
        File f = new File(path);
        if(f.exists())
        {
            try
            {
                BufferedImage bufferedImage = ImageIO.read(f);
                FontTexture ft = new FontTexture(bufferedImage);
                ft.loadTexture(null);
                textures.put(path, ft);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean readTexture(String key, InputStream stream)
    {
        try
        {
            BufferedImage bufferedImage = ImageIO.read(stream);
            FontTexture ft = new FontTexture(bufferedImage);
            ft.loadTexture(null);
            textures.put(key, ft);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }


    public boolean bindTexture(String path)
    {
        if(!textures.containsKey(path))
        {
            if(readTexture(path))
                return false;
        }

        if(textures.get(path) != null)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, (textures.get(path).getGlTextureId()));
            return true;
        }
        return false;
    }
}
