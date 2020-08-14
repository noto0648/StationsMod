package com.noto0648.stations.tiles;

import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.items.ItemDiagramBook;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class TileEntityDeparturePlate extends TileEntityBase
{
    public static final int MODE_PREPARATION = 0;
    public static final int MODE_NORMAL = 1;
    public static final int MODE_NORMAL_REALTIME = 2;
    public static final int MODE_FIXED = 3;

    private final List<MarkData> markDataList;
    private int mode;

    public TileEntityDeparturePlate()
    {
        markDataList = new ArrayList<>();
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);

        if(!p_145839_1_.hasKey("marks"))
            return;

        mode = p_145839_1_.getByte("mode");
        ItemDiagramBook.readFromNBT(p_145839_1_, markDataList);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setByte("mode", (byte) mode);
        ItemDiagramBook.writeToNBT(tag, markDataList);
        return tag;
    }

    public List<MarkData> getMarkDataList()
    {
        return markDataList;
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }
}
