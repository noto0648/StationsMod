package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.NewFontRenderer;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */
@NamePlateAnnotation
public class NamePlateDefault extends NamePlateBase
{

    @Override
    public void render(Map<String, String> map, boolean rotate)
    {
        String nowStation = map.get("stationName");
        String englishStation = map.get("englishName");
        String nextStation = map.get("nextStation");
        String prevStation = map.get("prevStation");


        GL11.glTranslated(-0, 0, 0.1);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GL11.glColor3f(0F, 0F, 0F);
        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glTranslated(-width / 2, 0, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glTranslated(width / 2, 0, 0);

        String station = (!rotate) ? prevStation : nextStation;

        {
            GL11.glPushMatrix();
            GL11.glTranslated(-110, -43, 0);
            GL11.glScaled(0.5F, 0.5F, 0.5F);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        station = rotate ? prevStation : nextStation;

        {
            GL11.glPushMatrix();
            GL11.glTranslated(110, -43, 0);
            GL11.glScaled(0.5F, 0.5F, 0.5F);
            int w = NewFontRenderer.INSTANCE.drawString(station, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glColor3f(1F, 1F, 1F);
            GL11.glTranslated(0, -25, 0);
            GL11.glScaled(0.45F, 0.45F, 0.45F);
            int w = NewFontRenderer.INSTANCE.drawString(englishStation, false);
            GL11.glTranslated(-w / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(englishStation);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void init(List<String> list)
    {
        list.add("stationName");
        list.add("nextStation");
        list.add("prevStation");
        list.add("englishName");
    }

    @Override
    public String getName()
    {
        return "Default";
    }
}
