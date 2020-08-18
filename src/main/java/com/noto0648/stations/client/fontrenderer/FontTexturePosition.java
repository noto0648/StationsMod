package com.noto0648.stations.client.fontrenderer;

public class FontTexturePosition
{
    private boolean generated;
    private final float x;
    private final float y;
    private final int width;
    private final float textureWidth;
    private final float textureHeight;

    public FontTexturePosition(int width)
    {
        this.x = 0f;
        this.y = 0f;
        this.width = width;
        this.textureWidth = 1f;
        this.textureHeight = 1f;
        this.generated = true;
    }

    public FontTexturePosition(int width, float x, float y, float textureWidth, float textureHeight)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.generated = true;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public float getTextureWidth()
    {
        return textureWidth;
    }

    public float getTextureHeight()
    {
        return textureHeight;
    }

    public boolean isGenerated()
    {
        return generated;
    }

    public void setGenerated(boolean generated)
    {
        this.generated = generated;
    }
}
