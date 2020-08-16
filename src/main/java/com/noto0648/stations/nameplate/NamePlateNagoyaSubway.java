package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.NewFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
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
        final String nowStation = map.get("kanjiName");
        final String englishStation = map.get("englishName");
        final String nextStation = map.get("nextStation");
        final String prevStation = map.get("prevStation");

        GL11.glNormal3f(-1.0F, 0.0F, 0.0F);

        GL11.glTranslated(-0, 0, 0.1);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GlStateManager.color(1F, 1F, 1F);
        GL11.glNormal3f(-1.0F, 0.0F, 0.0F);

        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glScalef(0.6F, 0.6F, 0.6F);

        GL11.glPushMatrix();
        GL11.glTranslatef(0,30f,0);
        GL11.glScalef(1.2F, 1.2F, 0F);
        GL11.glTranslated(-width / 2, 0, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glPopMatrix();

        {
            GlStateManager.color(1F, 1F, 1F);
            GL11.glPushMatrix();
            GL11.glTranslated(0, 0, 0);
            GL11.glScaled(0.7F, 0.7F, 0.7F);
            int w = NewFontRenderer.INSTANCE.drawString(englishStation, false);
            GL11.glTranslated(-w / 2, 15, 0);
            NewFontRenderer.INSTANCE.drawString(englishStation);
            GL11.glPopMatrix();
        }

        GlStateManager.color(0F, 0F, 0F);
        String station = (!rotate) ? prevStation : nextStation;
        String eSta = (!rotate) ? map.get("prevEnglish") : map.get("nextEnglish");
        GL11.glScalef(1.66667F, 1.66667F, 1.66667F);
        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -39, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -47, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }

        station = rotate ? prevStation : nextStation;
        eSta = rotate ? map.get("prevEnglish") : map.get("nextEnglish");

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(107, -39, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            int w = NewFontRenderer.INSTANCE.drawString(station, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(107, -47, 0);
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
    public String getNamePlateId()
    {
        return "builtin_nagoya_subway";
    }

    @Override
    public String getDisplayName()
    {
        return I18n.format("nameplate.notomod.nagoya_subway");
    }
}
