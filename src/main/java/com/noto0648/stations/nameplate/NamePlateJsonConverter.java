package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.NewFontRenderer;
import org.lwjgl.opengl.GL11;

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

    public NamePlateJsonConverter(String str, List<NamePlateJson.LabelData> list)
    {
        labels = list;
        name = str;
    }

    @Override
    public void render(Map<String, String> map, boolean rotate)
    {
        if(labels != null)
        {
            GL11.glTranslated(-0, 0, 0.1);
            GL11.glScalef(0.01F, 0.01F, 0.01F);
            GL11.glTranslated(-110, 20, 0);
            //WIDTH = 220
            for(int i = 0; i < labels.size(); i++)
            {
                NamePlateJson.LabelData label = labels.get(i);
                String drawStr = map.get(label.label);

                if(label.enableReverse && rotate) drawStr = map.get(label.reverseLabel);

                GL11.glPushMatrix();
                GL11.glColor3d(label.R, label.G, label.B);
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
    public String getName()
    {
        return name == null ? "NAME IS NULL" : name;
    }
}
