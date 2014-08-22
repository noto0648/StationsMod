package com.noto0648.stations.client.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.ITextureObject;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */
public class NewFontRenderer
{
    public static String defaultString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-,.あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをんがぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽっゃゅょぁぃぅぇぉ()";
    public static NewFontRenderer INSTANCE = new NewFontRenderer();



    private NewFontRenderer() { }

    private Map<Character, BufferedImage> images = new HashMap<Character, BufferedImage>();
    private Map<Character, Integer> widths = new HashMap<Character, Integer>();
    private Map<Character, FontTexture> textures = new HashMap<Character, FontTexture>();

    public void init()
    {
        for(int i = 0; i < defaultString.length(); i++)
        {
            char c = defaultString.charAt(i);
            Object[] objs = toImage(String.valueOf(c));
            images.put(c, (BufferedImage)objs[0]);
            widths.put(c, (Integer)objs[1]);
        }
    }

    @SideOnly(Side.CLIENT)
    public Object[] toImage(String str)
    {
        BufferedImage bImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D objGrh = (Graphics2D)bImage.getGraphics();
        objGrh.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 32);
        objGrh.setFont(f);
        objGrh.setColor(Color.WHITE);

        int width = objGrh.getFontMetrics().stringWidth(str);
        objGrh.drawString(str, 0, 28);
        //objGrh.fillRect(0, 0, 1024, 1024);
        objGrh.dispose();

        return new Object[]{bImage, width};
    }

    @SideOnly(Side.CLIENT)
    public BufferedImage toImageBuffer(String str)
    {
        return (BufferedImage)toImage(str)[0];
    }

    @SideOnly(Side.CLIENT)
    public int drawString(String str)
    {
        return drawString(0, 0, str, true);
    }

    @SideOnly(Side.CLIENT)
    public int drawString(String str, boolean draw)
    {
        return drawString(0, 0, str, draw);
    }

    @SideOnly(Side.CLIENT)
    public int drawString(int x, int y, String str)
    {
        return drawString(x, y, str, true);
    }


    @SideOnly(Side.CLIENT)
    public int drawString(int x, int y, String str, boolean draw)
    {

        if(str == null) return -1;

        int offsetX = x;
        int offsetY = y;

        if(draw)
            GL11.glEnable(GL11.GL_ALPHA_TEST);

        for(int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);

            if(!images.containsKey(c))
            {
                Object[] objs = toImage(String.valueOf(c));
                images.put(c, (BufferedImage)objs[0]);
                widths.put(c, (Integer)objs[1]);
            }

            if(draw)
            {
                bindTexture(images.get(c), c);

                GL11.glPushMatrix();
                GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
                GL11.glTexCoord2f(0F, 0F);
                GL11.glVertex3f(0F + offsetX, 0F + offsetY, 0F);
                GL11.glTexCoord2f(1F, 0F);
                GL11.glVertex3f(32F + offsetX, 0F + offsetY, 0F);
                GL11.glTexCoord2f(0F, -1F);
                GL11.glVertex3f(0F + offsetX, 32F + offsetY, 0F);
                GL11.glTexCoord2f(1F, -1F);
                GL11.glVertex3f(32F + offsetX, 32F + offsetY, 0F);

                GL11.glEnd();
                GL11.glPopMatrix();
            }
            offsetX += widths.get(c);
        }
        return offsetX - x;
    }

    @SideOnly(Side.CLIENT)
    public void bindTexture(BufferedImage bufferedImage, char c)
    {
        try
        {
            Object obj = null;
            if(!textures.containsKey(c))
            {
                FontTexture tex = new FontTexture(bufferedImage);
                (tex).loadTexture(null);
                textures.put(c, tex);
                obj = tex;
            }
            if(obj == null)
                obj = textures.get(c);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((ITextureObject) obj).getGlTextureId());
        }
        catch(Exception e)
        {

        }
    }
}
