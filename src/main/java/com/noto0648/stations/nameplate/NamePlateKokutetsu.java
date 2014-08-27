package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.NewFontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/27.
 */
@NamePlateAnnotation
public class NamePlateKokutetsu extends NamePlateBase
{
    public static IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("notomod", "objs/name_plate_kokutetsu.obj"));

    @Override
    public void render(Map<String, String> map, boolean rotate, int plateFace)
    {
        if(plateFace == 1) GL11.glTranslatef(0F, 0.25F, 0F);
        if(plateFace == 2) GL11.glTranslatef(0F, -0.25F, 0F);

        String nowStation = map.get("stationName");
        String englishStation = map.get("englishName");
        String nextStation = map.get("nextStation");
        String prevStation = map.get("prevStation");

        GL11.glTranslated(-0, 0, 0.1);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GL11.glColor3f(0F, 0F, 0F);

        GL11.glPushMatrix();
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glTranslated(-width / 2, 10, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glTranslated(width / 2, -10, 0);
        GL11.glPopMatrix();

        {
            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glTranslated(0, 0, 0);
            int kanjiWidth = NewFontRenderer.INSTANCE.drawString(map.get("kanjiName"), false);
            GL11.glTranslated(-kanjiWidth / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(map.get("kanjiName"));
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glScalef(0.3F, 0.3F, 0.3F);
            GL11.glTranslated(0, -110, 0);
            int kanjiWidth = NewFontRenderer.INSTANCE.drawString("(" + map.get("address") + ")", false);
            GL11.glTranslated(-kanjiWidth / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString("(" + map.get("address") + ")");
            GL11.glPopMatrix();
        }

        String station = (!rotate) ? prevStation : nextStation;
        String eSta = (!rotate) ? map.get("prevEnglish") : map.get("nextEnglish");

        {
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -60, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslated(-107, -68, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }

        station = rotate ? prevStation : nextStation;
        eSta = rotate ? map.get("prevEnglish") : map.get("nextEnglish");

        {
            GL11.glPushMatrix();
            GL11.glTranslated(107, -60, 0);
            GL11.glScaled(0.39F, 0.39F, 0.39F);
            int w = NewFontRenderer.INSTANCE.drawString(station, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(station);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslated(107, -68, 0);
            GL11.glScaled(0.28F, 0.28F, 0.28F);
            int w = NewFontRenderer.INSTANCE.drawString(eSta, false);
            GL11.glTranslated(-w, 0, 0);
            NewFontRenderer.INSTANCE.drawString(eSta);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();

            GL11.glTranslated(0, -20, 0);
            GL11.glScaled(0.6F, 0.6F, 0.6F);
            int w = NewFontRenderer.INSTANCE.drawString(englishStation, false);
            GL11.glTranslated(-w / 2, 0, 0);
            NewFontRenderer.INSTANCE.drawString(englishStation);
            GL11.glPopMatrix();
        }

        GL11.glColor3f(1F, 1F, 1F);
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

    @Override
    public String getName()
    {
        return "Japan_National_Railway";
    }

    @Override
    public boolean isUserRender()
    {
        return true;
    }

    @Override
    public void userRender(int plateFace)
    {
        if(plateFace == 1) GL11.glTranslatef(0F, 0.5F, 0F);
        if(plateFace == 2) GL11.glTranslatef(0F, -0.5F, 0F);

        GL11.glScalef(1.5F, 1.5F, 1.5F);
        model.renderAll();
    }
}
