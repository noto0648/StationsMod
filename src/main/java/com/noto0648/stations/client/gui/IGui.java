package com.noto0648.stations.client.gui;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Noto on 14/08/06.
 */
public interface IGui
{
    public void drawRect(int x, int y, int x2, int y2, int color, int color2);

    public FontRenderer getFontRenderer();
}
