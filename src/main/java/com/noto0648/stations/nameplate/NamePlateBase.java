package com.noto0648.stations.nameplate;

import com.google.common.collect.ImmutableList;
import com.noto0648.stations.StationsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */

public abstract class NamePlateBase
{
    public abstract void render(Map<String, String> map, boolean rotate, int plateFace);

    public abstract void init(List<String> list);

    public abstract String getNamePlateId();

    public boolean isUserRender()
    {
        return false;
    }

    public void userRender(int plateFace) {}

    public void registerTextures(List<String> list) {}

    public String getDisplayName()
    {
        return getNamePlateId();
    }

    public IBakedModel getModel(String texture)
    {
        if(texture.equals("DefaultTexture"))
        {
            texture = "name_plate";
        }
        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(getModelLocation(texture));
    }

    protected ModelResourceLocation getModelLocation(String texture)
    {
        return new ModelResourceLocation(StationsMod.MOD_ID+ ":nameplate_model/" + getModelName() + "/" + texture, "normal");
    }

    public String getModelName()
    {
        return "basic";
    }

    protected List<Pair<String, String>> getPairLabels()
    {
        return ImmutableList.of();
    }

    public void reverseLabels(Map<String, String> str)
    {
        final List<Pair<String, String>> pairs = getPairLabels();
        if(pairs == null || pairs.isEmpty())
            return;

        final Map<String, Boolean> changed = new HashMap<>();
        for(final String key : str.keySet())
            changed.put(key, false);
        for(final Pair<String, String> p : pairs)
        {
            if(str.containsKey(p.getLeft()) && str.containsKey(p.getRight()) && !changed.get(p.getLeft()) && !changed.get(p.getRight()))
            {
                final String first = str.get(p.getLeft());
                str.put(p.getLeft(), str.get(p.getRight()));
                str.put(p.getRight(), first);
                changed.put(p.getRight(), true);
                changed.put(p.getLeft(), true);
            }
        }
    }

    @Override
    public int hashCode()
    {
        return getNamePlateId().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof NamePlateBase)
            return getNamePlateId().equals(((NamePlateBase) obj).getNamePlateId());
        return false;
    }


}
