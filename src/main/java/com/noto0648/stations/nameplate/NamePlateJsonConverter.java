package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.NewFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/22.
 */
@NamePlateAnnotation
public class NamePlateJsonConverter extends NamePlateBase
{
    private final List<NamePlateJson.LabelData> labels;
    private final String name;
    private final NamePlateJson plateData;

    public NamePlateJsonConverter(NamePlateJson plateJson, List<NamePlateJson.LabelData> list)
    {
        labels = list;
        name = plateJson.name;
        plateData = plateJson;

        HashMap<String, String> displayNames = new HashMap<>();
        for(String key : plateData.displayNames.keySet())
        {
            displayNames.put(key.toLowerCase(), plateData.displayNames.get(key));
        }
        plateJson.displayNames = displayNames;
    }

    @Override
    public void render(Map<String, String> map, boolean rotate, int plateFace)
    {
        if(labels != null)
        {
            if(plateData != null)
            {
                //if(plateData.enableDepthMask) GL11.glDepthMask(false);
                //if(plateData.enableNormal) GL11.glNormal3f(0.0F, 0.0F, -1.0F);
            }

            if(plateFace != 0)
            {
                if(plateData.modelId == 1)
                {
                    if(plateFace == 1) GL11.glTranslatef(0F, 0.25F, 0F);
                    if(plateFace == 2) GL11.glTranslatef(0F, -0.25F, 0F);
                }
                if(plateData.modelId == 2)
                {

                }
            }

            GL11.glTranslated(-0, 0, 0.1);
            GL11.glScalef(0.01F, 0.01F, 0.01F);
            GL11.glTranslated(-110, 20, 0);
            //WIDTH = 220
            for(int i = 0; i < labels.size(); i++)
            {
                NamePlateJson.LabelData label = labels.get(i);
                if(!map.containsKey(label.label))
                {
                    continue;
                }
                String drawStr = map.get(label.label);

                if(label.enableReverse && rotate) drawStr = map.get(label.reverseLabel);

                GL11.glPushMatrix();
                GlStateManager.color(label.R, label.G, label.B);
                GL11.glTranslated(label.x, -label.y, 0);
                GL11.glScaled(label.fontScale, label.fontScale, label.fontScale);

                int width = NewFontRenderer.INSTANCE.drawString(drawStr, false);

                if(label.justification == NamePlateJson.Justification.CENTER)
                {
                    GL11.glTranslated(-width / 2, 0, 0);
                }
                if(label.justification == NamePlateJson.Justification.RIGHT)
                {
                    GL11.glTranslated(-width, 0, 0);
                }

                NewFontRenderer.INSTANCE.drawString(0, 0, drawStr);
                GL11.glPopMatrix();
            }
        }

        if(plateData != null)
        {
            //if(plateData.enableDepthMask) GL11.glDepthMask(true);
        }
        GlStateManager.color(1F, 1F, 1F);
    }

    @Override
    public void init(List<String> list)
    {
        if(labels == null)
        {
            list.add("JSON_ERROR");
        }
        else
        {
            for(int i = 0; i < labels.size(); i++)
                list.add(labels.get(i).label);
        }
    }

    @Override
    public String getNamePlateId()
    {
        return name == null ? "NAME IS NULL" : name;
    }

    @Override
    public boolean isUserRender()
    {
        return plateData == null ? false : plateData.modelId != 0;
    }

    @Override
    public void userRender(int plateFace)
    {
        if(plateData.modelId == 1)
        {
            if(plateFace == 1) GL11.glTranslatef(0F, 0.5F, 0F);
            if(plateFace == 2) GL11.glTranslatef(0F, -0.5F, 0F);
            //TileEntityNamePlateRender.subwayModel.renderAll();
        }
        if(plateData.modelId == 2)
        {
            if(plateFace == 1) GL11.glTranslatef(0F, 0.5F, 0F);
            if(plateFace == 2) GL11.glTranslatef(0F, -0.5F, 0F);

            GL11.glScalef(1.5F, 1.5F, 1.5F);
            //TileEntityNamePlateRender.kokutetuModel.renderAll();
        }
    }

    @Override
    public String getDisplayName()
    {
        final String lang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode().toLowerCase();
        if(plateData.displayNames.containsKey(lang))
            return plateData.displayNames.get(lang);

        if(plateData.displayNames.containsKey("en_us"))
            return plateData.displayNames.get("en_us");

        return getNamePlateId();
    }
}
