package com.noto0648.stations.client.fontrenderer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class NewFontRenderer
{
    private static final String INITIALIZE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-,.{}#$%";
    public static final NewFontRenderer INSTANCE = new NewFontRenderer();

    private NewFontRenderer() { }

    private ArrayList<FontTexture> packedImages = new ArrayList<>();
    private Map<UTFCharacter, BufferedImage> images = new HashMap<>();
    private Map<UTFCharacter, FontTexturePosition> widths = new HashMap<>();
    private Map<UTFCharacter, FontTexture> textures = new HashMap<>();

    public void init()
    {
        for(int i = 0; i < INITIALIZE_CHARACTERS.length(); i++)
        {
            final char c = INITIALIZE_CHARACTERS.charAt(i);
            final UTFCharacter key = new UTFCharacter(c);
            Object[] objs = toImage(String.valueOf(c));
            images.put(key, (BufferedImage)objs[0]);
            widths.put(key, new FontTexturePosition((Integer)objs[1]));
        }
        packTexture();
    }

    private void packTexture()
    {
        BufferedImage bImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D)bImage.getGraphics();
        ArrayList<UTFCharacter> keys = new ArrayList<>(16*16);
        for(UTFCharacter key : images.keySet())
        {
            int len = keys.size();
            final int x = (len % 16) * 32;
            final int y = len / 16 * 32;
            graphics.drawImage(images.get(key), x, y, null);
            keys.add(key);
            widths.put(key, new FontTexturePosition(widths.get(key).getWidth(), x/512f, y/512f, (x+32f)/512f,(y+32f)/512f));
            if(keys.size() == 16*16)
            {
                break;
            }
        }
        graphics.dispose();
        FontTexture fontTex = null;

        try
        {
            fontTex = new FontTexture(bImage);
            fontTex.loadTexture(null);
            packedImages.add(fontTex);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        for(UTFCharacter c : keys)
        {
            images.remove(c);
            textures.put(c, fontTex);
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
    public int drawString(String str, int color, boolean draw)
    {
        return drawString(0, 0, str, color, draw);
    }

    @SideOnly(Side.CLIENT)
    public int drawString(int x, int y, String str)
    {
        return drawString(x, y, str, true);
    }

    @SideOnly(Side.CLIENT)
    public int drawString(int x, int y, String str, int color, boolean draw)
    {
        return drawString(x, y, str, draw, true, false, color);
    }

    @SideOnly(Side.CLIENT)
    public int drawString(int x, int y, String str, boolean draw)
    {
        return drawString(x, y, str, draw, false, false, 0);
    }

    @SideOnly(Side.CLIENT)
    public int drawStringVertical(int x, int y, String str, boolean draw)
    {
        return drawString(x, y, str, draw, true, false, 0);
    }

    @SideOnly(Side.CLIENT)
    public int drawString(int x, int y, String str, boolean draw, boolean vertical, boolean useColor, int color)
    {
        if(str == null) return -1;

        int offsetX = x;
        int offsetY = y;
        float fontScale = 1f;
        boolean surrogate = false;
        for(int i = 0; i < str.length(); i++)
        {
            final char c = vertical ? str.charAt(str.length() - 1 - i) : str.charAt(i);
            if(!surrogate && Character.isHighSurrogate(c))
            {
                surrogate = true;
                continue;
            }
            final UTFCharacter key = surrogate ? new UTFCharacter(str.charAt(i-1), c) : new UTFCharacter(c);

            if(!surrogate && c == '{')
            {
                boolean endBucket = false;
                String options = null;
                int endIndex = -1;
                for(int k = i+1; k < str.length(); k++)
                {
                    final char ch = str.charAt(k);
                    if(ch == '}' && i+1 < k)
                    {
                        endBucket = true;
                        options = str.substring(i+1, k);
                        endIndex = k;
                        break;

                    }
                }

                if(endBucket && options != null && options.length() > 1)
                {
                    if(options.startsWith("#"))
                    {
                        final String colCode = options.substring(1).toUpperCase();
                        final int colCodeInt = parseColorCode(colCode);
                        final int b = (colCodeInt >>> 0)& 0xFF;
                        final int g = (colCodeInt >>> 8) & 0xFF;
                        final int r = (colCodeInt >>> 16) & 0xFF;
                        GlStateManager.color(r / 255f, g / 255f, b / 255f);
                    }
                    else if(options.startsWith("%"))
                    {
                        final String scaleStr = options.substring(1);
                        fontScale = parseFloat(scaleStr);
                    }
                    if(i != -1)
                        i = endIndex;
                    continue;
                }
            }
            if(!widths.containsKey(key) || !widths.get(key).isGenerated())
            {
                Object[] objs = toImage(key.toString());
                images.put(key, (BufferedImage)objs[0]);
                widths.put(key, new FontTexturePosition((Integer)objs[1]));
            }
            FontTexturePosition fontPos = widths.get(key);
            if(draw)
            {
                bindCharTexture(key);

                GL11.glPushMatrix();
                GL11.glNormal3f(0f, 0f, -1f);
                GL11.glTranslatef(offsetX, offsetY, 0);
                GL11.glScalef(fontScale, fontScale, 1f);
                Tessellator tes = Tessellator.getInstance();
                if(useColor)
                {
                    buildVertexes(tes.getBuffer(), fontPos, color);
                }
                else
                {
                    buildVertexes(tes.getBuffer(), fontPos);
                }
                tes.draw();
                GL11.glPopMatrix();
            }
            if(!vertical)
            {
                offsetX += (int)(fontPos.getWidth() * fontScale);
            }
            else
            {
                offsetY += 32;
            }
            surrogate = false;
        }
        return vertical ? offsetY : offsetX - x;
    }

    @SideOnly(Side.CLIENT)
    public void drawCharacterPipeline(int offsetX, int offsetY, FontTexturePosition fontPos)
    {
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(fontPos.getX(), fontPos.getTextureHeight());
        GL11.glVertex3f(0F + offsetX, 0F + offsetY, 0F);
        GL11.glTexCoord2f(fontPos.getTextureWidth(), fontPos.getTextureHeight());
        GL11.glVertex3f(32F + offsetX, 0F + offsetY, 0F);
        GL11.glTexCoord2f(fontPos.getX(), fontPos.getY());
        GL11.glVertex3f(0F + offsetX, 32F + offsetY, 0F);
        GL11.glTexCoord2f(fontPos.getTextureWidth(), fontPos.getY());
        GL11.glVertex3f(32F + offsetX, 32F + offsetY, 0F);
        GL11.glEnd();
    }

    @SideOnly(Side.CLIENT)
    public void buildVertexes(BufferBuilder buffer, FontTexturePosition pos)
    {
        buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0,0,0).tex(pos.getX(), pos.getTextureHeight()).endVertex();
        buffer.pos(32,0,0).tex(pos.getTextureWidth(), pos.getTextureHeight()).endVertex();
        buffer.pos(0,32,0).tex(pos.getX(), pos.getY()).endVertex();
        buffer.pos(32,32,0).tex(pos.getTextureWidth(), pos.getY()).endVertex();
    }

    @SideOnly(Side.CLIENT)
    public void buildVertexes(BufferBuilder buffer, FontTexturePosition pos, int color)
    {
        final int r = (color >> 16 & 255);
        final int b = (color >> 8 & 255) ;
        final int g = (color & 255) ;
        final int a = (color >> 24 & 255);

        buffer.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(0,0,0).tex(pos.getX(), pos.getTextureHeight()).color(a, r, g, b).endVertex();
        buffer.pos(32,0,0).tex(pos.getTextureWidth(), pos.getTextureHeight()).color(a, r, g, b).endVertex();
        buffer.pos(0,32,0).tex(pos.getX(), pos.getY()).color(a, r, g, b).endVertex();
        buffer.pos(32,32,0).tex(pos.getTextureWidth(), pos.getY()).color(a, r, g, b).endVertex();
    }

    @SideOnly(Side.CLIENT)
    public void bindCharTexture(UTFCharacter c)
    {
        try
        {
            Object obj = null;
            if(!textures.containsKey(c))
            {
                FontTexture tex = new FontTexture(images.get(c));
                tex.loadTexture(null);
                textures.put(c, tex);
                obj = tex;
            }
            if(obj == null)
                obj = textures.get(c);

            GlStateManager.bindTexture(((ITextureObject) obj).getGlTextureId());
        }
        catch(Exception e)
        {

        }
    }

    private float parseFloat(final String scaleStr)
    {
        boolean fail = false;
        for(int l = 0; l < scaleStr.length(); l++)
        {
            final char cc = scaleStr.charAt(l);
            if(!((cc >= '0' && cc <= '9') || cc == '.'))
            {
                fail = true;
                break;
            }
        }
        if(!fail)
        {
            try
            {
                return Float.parseFloat(scaleStr);
            }
            catch (Exception e) {}
        }
        return 1f;
    }


    private int parseColorCode(final String colCode)
    {
        boolean fail = false;
        for(int l = 0; l < colCode.length(); l++)
        {
            final char cc = colCode.charAt(l);
            if(!((cc >= '0' && cc <= '9') || (cc >= 'A' && cc <= 'F')))
            {
                fail = true;
                break;
            }
        }
        if(!fail)
        {
            try
            {
                return Integer.parseInt(colCode, 16);
            }
            catch (Exception e) {}
        }
        return 0;
    }
}
