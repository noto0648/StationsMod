package com.noto0648.stations.common;

import com.noto0648.stations.Stations;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Noto on 14/08/14.
 */
public class Utils
{
    public static Utils INSTANCE = new Utils();
    private Utils() {}

    public boolean haveWrench(EntityPlayer ep)
    {
        return ep.getCurrentEquippedItem() != null && ep.getCurrentEquippedItem().getItem() == Stations.instance.torqueWrench;
    }
}
