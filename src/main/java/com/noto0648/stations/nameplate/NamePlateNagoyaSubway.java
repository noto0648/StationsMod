package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.NewFontRenderer;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/08.
 */
@NamePlateAnnotation
public class NamePlateNagoyaSubway extends NamePlateBase
{
    @Override
    public void render(Map<String, String> map, boolean rotate, int plateFace)
    {
        String nowStation = map.get("kanjiName");
        String englishStation = map.get("englishName");
        String nextStation = map.get("nextStation");
        String prevStation = map.get("prevStation");

        GL11.glNormal3f(-1.0F, 0.0F, 0.0F);

        GL11.glTranslated(-0, 0, 0.1);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GL11.glColor3f(1F, 1F, 1F);
        GL11.glNormal3f(-1.0F, 0.0F, 0.0F);

        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glTranslated(-width / 2, 30, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glTranslated(width / 2, -30, 0);


        {
            GL11.glPushMatrix();
            GL11.glTranslated(0, 0, 0);
            GL11.glScaled(0.7F, 0.7F, 0.7F);
            int w = NewFontRenderer.INSTANCE.drawString(englishStation, false);
            GL11.glTranslated(-w / 2, 15, 0);
            NewFontRenderer.INSTANCE.drawString(englishStation);
            GL11.glPopMatrix();
        }

        GL11.glColor3f(0F, 0F, 0F);
        String station = (!rotate) ? prevStation : nextStation;
        String eSta = (!rotate) ? map.get("prevEnglish") : map.get("nextEnglish");
        GL11.glScalef(1.66667F, 1.66667F, 1.66667F);
        {
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -24, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -32, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }

        station = rotate ? prevStation : nextStation;
        eSta = rotate ? map.get("prevEnglish") : map.get("nextEnglish");

        {
            GL11.glPushMatrix();
            GL11.glTranslated(107, -24, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            int w = NewFontRenderer.INSTANCE.drawString(station, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslated(107, -32, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            int w = NewFontRenderer.INSTANCE.drawString(eSta, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void init(List<String> list)
    {
        list.add("kanjiName");
        list.add("englishName");

        list.add("nextStation");
        list.add("nextEnglish");
        list.add("prevStation");
        list.add("prevEnglish");
    }

    @Override
    public String getName()
    {
        return "Nagoya_Subway";
    }
}
