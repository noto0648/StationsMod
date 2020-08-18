package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.fontrenderer.NewFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/25.
 */
@NamePlateAnnotation
public class NamePlateAonamiLine extends NamePlateBase
{
    @SideOnly(Side.CLIENT)



    @Override
    public void render(Map<String, String> map, boolean rotate, int plateFace)
    {
        String nowStation = map.get("stationName");
        String nowEnglish = map.get("englishName");

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glNormal3f(-1.0F, 0.0F, 0.0F);

        if(plateFace == 1) GL11.glTranslatef(0F, 0.25F, 0F);
        if(plateFace == 2) GL11.glTranslatef(0F, -0.25F, 0F);

        GL11.glTranslated(-0, 0, 0.07);
        GL11.glScalef(0.01F, 0.01F, 0.01F);
        GlStateManager.color(0.9999999999F, 0.9999999999F, 0.9999999999F);



        GL11.glPushMatrix();
        GL11.glScaled(0.4D, 0.4D, 0.4D);
        int width = NewFontRenderer.INSTANCE.drawString(nowStation, false);
        GL11.glTranslated(-width / 2, 10, 0);
        NewFontRenderer.INSTANCE.drawString(nowStation);
        GL11.glTranslated(width / 2, -10, 0);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(0, -20, 0);
        GL11.glScaled(0.4D, 0.4D, 0.4D);
        int engW = NewFontRenderer.INSTANCE.drawString(nowEnglish, false);
        GL11.glTranslated(-engW / 2, 10, 0);
        NewFontRenderer.INSTANCE.drawString(nowEnglish);
        GL11.glTranslated(width / 2, -10, 0);
        GL11.glPopMatrix();

        String nextStation = !rotate ? map.get("prevStation"): map.get("nextStation");
        String nextEnglish = !rotate ? map.get("prevEnglish"): map.get("nextEnglish");

        {
            GL11.glPushMatrix();
            GL11.glTranslated(-80, 10, 0);
            GL11.glScaled(0.15D, 0.15D, 0.15D);
            int strW = NewFontRenderer.INSTANCE.drawString(nextStation, false);
            NewFontRenderer.INSTANCE.drawString(nextStation);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslated(-80, 5, 0);
            GL11.glScaled(0.15D, 0.15D, 0.15D);
            int strW = NewFontRenderer.INSTANCE.drawString(nextEnglish, false);
            NewFontRenderer.INSTANCE.drawString(nextEnglish);
            GL11.glPopMatrix();
        }

        nextStation = rotate ? map.get("prevStation"): map.get("nextStation");
        nextEnglish = rotate ? map.get("prevEnglish"): map.get("nextEnglish");

        {
            GL11.glPushMatrix();
            GL11.glTranslated(70, 10, 0);
            GL11.glScaled(0.15D, 0.15D, 0.15D);
            int strW = NewFontRenderer.INSTANCE.drawString(nextStation, false);
            GL11.glTranslatef(-strW, 0, 0);
            NewFontRenderer.INSTANCE.drawString(nextStation);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslated(70, 5, 0);
            GL11.glScaled(0.15D, 0.15D, 0.15D);
            int strW = NewFontRenderer.INSTANCE.drawString(nextEnglish, false);
            GL11.glTranslatef(-strW, 0, 0);
            NewFontRenderer.INSTANCE.drawString(nextEnglish);
            GL11.glPopMatrix();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.color(1F, 1F, 1F);
    }

    @Override
    public void init(List<String> list)
    {
        list.add("stationName");
        list.add("englishName");
        list.add("nextStation");
        list.add("nextEnglish");
        list.add("prevStation");
        list.add("prevEnglish");
    }

    @Override
    public String getNamePlateId()
    {
        return "Aonami Line";
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

        //TileEntityNamePlateRender.subwayModel.renderAll();
    }

    @Override
    public String getDisplayName()
    {
        return I18n.format("nameplate.notomod.aonami");
    }
}
