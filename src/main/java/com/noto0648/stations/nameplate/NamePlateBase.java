package com.noto0648.stations.nameplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */

public abstract class NamePlateBase
{
    public abstract void render(Map<String, String> map, boolean rotate);

    public abstract void init(List<String> list);

    public abstract String getName();

    public boolean isUserRender()
    {
        return false;
    }

    public void userRender() {}
}
