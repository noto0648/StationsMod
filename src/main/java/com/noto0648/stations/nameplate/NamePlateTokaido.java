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
public class NamePlateTokaido  extends NamePlateBase
{
    @Override
    public void render(Map<String, String> map, boolean rotate, int plateFace)
    {
        String nowStation = map.get("stationName");
        String englishStation = map.get("englishName");
        String nextStation = map.get("nextStation");
        String prevStation = map.get("prevStation");

        GL11.glTranslated(-0, 0, 0.1);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GlStateManager.color(0F, 0F, 0F);
        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glTranslated(-width / 2, 10, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glTranslated(width / 2, -10, 0);

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glTranslated(0, -10, 0);
            int kanjiWidth = NewFontRenderer.INSTANCE.drawString(map.get("kanjiName"), false);
            GL11.glTranslated(-kanjiWidth / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(map.get("kanjiName"));
            GL11.glPopMatrix();
        }

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glScalef(0.4F, 0.4F, 0.4F);
            GL11.glTranslated(0, -100, 0);
            int kanjiWidth = NewFontRenderer.INSTANCE.drawString("(" + map.get("address") + ")", false);
            GL11.glTranslated(-kanjiWidth / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString("(" + map.get("address") + ")");
            GL11.glPopMatrix();
        }

        String station = (!rotate) ? prevStation : nextStation;
        String eSta = (!rotate) ? map.get("prevEnglish") : map.get("nextEnglish");

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -40, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -48, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }

        station = rotate ? prevStation : nextStation;
        eSta = rotate ? map.get("prevEnglish") : map.get("nextEnglish");

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(107, -40, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            int w = NewFontRenderer.INSTANCE.drawString(station, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GlStateManager.color(0f,0f,0f);
            GL11.glPushMatrix();
            GL11.glTranslated(107, -48, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            int w = NewFontRenderer.INSTANCE.drawString(eSta, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GlStateManager.color(1F, 1F, 1F);
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
        list.add("englishName");
        list.add("kanjiName");
        list.add("nextStation");
        list.add("nextEnglish");
        list.add("prevStation");
        list.add("prevEnglish");
        list.add("address");
    }
/*
    @Override
    protected ModelResourceLocation getModelLocation()
    {
        return new ModelResourceLocation(StationsMod.MOD_ID+":nameplate_model/jr_central", "normal");
    }
*/
    @Override
    public void registerTextures(List<String> list)
    {
        //list.add(StationsMod.MOD_ID +":nameplates/jr_central");
    }

    @Override
    public String getModelName()
    {
        return "basic";
    }

    @Override
    public String getNamePlateId()
    {
        return "JR_Central";
    }

    @Override
    public String getDisplayName()
    {
        return I18n.format("nameplate.notomod.jr_central");
    }
}
