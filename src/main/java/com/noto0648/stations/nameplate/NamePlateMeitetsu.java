package com.noto0648.stations.nameplate;

import com.google.common.collect.ImmutableList;
import com.noto0648.stations.client.fontrenderer.NewFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/08.
 */
@NamePlateAnnotation
public class NamePlateMeitetsu extends NamePlateBase
{
    @Override
    public void render(Map<String, String> map, boolean rotate, int plateFace)
    {
        String nowStation = map.get("kanjiName");
        String englishStation = map.get("englishName");
        String nextStation = map.get("nextStation");
        String prevStation = map.get("prevStation");

        GL11.glTranslated(-0, 0, 0.1);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GlStateManager.color(0F, 0F, 0F);

        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        GL11.glTranslated(-width / 2, 10, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glTranslated(width / 2, -10, 0);

        {
            GL11.glPushMatrix();
            GL11.glScalef(0.4F, 0.4F, 0.4F);
            GL11.glTranslated(0, -10, 0);
            int kanjiWidth = NewFontRenderer.INSTANCE.drawString(map.get("hiraganaName"), false);
            GL11.glTranslated(-kanjiWidth / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(map.get("hiraganaName"));
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            //GL11.glColor3f(1F, 1F, 1F);
            GL11.glTranslated(0, -18, 0);
            GL11.glScaled(0.45F, 0.45F, 0.45F);
            int w = NewFontRenderer.INSTANCE.drawString(englishStation, false);
            GL11.glTranslated(-w / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(englishStation);
            GL11.glPopMatrix();
        }

        GlStateManager.color(0F, 0F, 0F);
        String station = (!rotate) ? prevStation : nextStation;
        String eSta = (!rotate) ? map.get("prevEnglish") : map.get("nextEnglish");
        GL11.glScalef(1.25F, 1.25F, 1.25F);
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
    }

    @Override
    public void init(List<String> list)
    {
        list.add("kanjiName");
        list.add("hiraganaName");
        list.add("englishName");

        list.add("nextStation");
        list.add("nextEnglish");
        list.add("prevStation");
        list.add("prevEnglish");
    }

    @Override
    protected List<Pair<String, String>> getPairLabels()
    {
        return ImmutableList.of(Pair.of("nextStation", "prevStation"), Pair.of("nextEnglish", "prevEnglish"));
    }

/*
    @Override
    protected ModelResourceLocation getModelLocation()
    {
        return new ModelResourceLocation(StationsMod.MOD_ID+":nameplate_model/meitetsu", "normal");
    }
    */

    @Override
    public void registerTextures(List<String> list)
    {
        //list.add(StationsMod.MOD_ID +":nameplates/meitetsu");
    }

    @Override
    public String getNamePlateId()
    {
        return "Meitetsu";
    }

    @Override
    public String getDisplayName()
    {
        return I18n.format("nameplate.notomod.meitetsu");
    }
}
