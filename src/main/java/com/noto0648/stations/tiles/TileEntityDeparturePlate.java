package com.noto0648.stations.tiles;

import com.google.common.collect.ImmutableList;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.api.DepartureData;
import com.noto0648.stations.api.DeparturePlateMode;
import com.noto0648.stations.api.DepartureTime;
import com.noto0648.stations.api.IDeparturePlate;
import com.noto0648.stations.common.LowerBoundComparator;
import com.noto0648.stations.common.MarkData;
import com.noto0648.stations.common.MarkDataComparator;
import com.noto0648.stations.common.MinecraftDate;
import com.noto0648.stations.items.ItemDiagramBook;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public class TileEntityDeparturePlate extends TileEntityBase implements IDeparturePlate
{
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

    @Override
    public DeparturePlateMode getDisplayMode()
    {
        if(StationsMod.DEBUG_MODE)
            return DeparturePlateMode.FIXED;

        if(markDataList == null || markDataList.isEmpty())
            return DeparturePlateMode.PREPARATION;
        return DeparturePlateMode.values()[mode];
    }

    @Override
    public List<DepartureData> getDepartureData()
    {
        ImmutableList.Builder<DepartureData> result = new ImmutableList.Builder<>();
        for(MarkData md : markDataList)
        {
            result.add(md.toDepartureData());
        }
        return result.build();
    }

    @Override
    public DepartureData getNextDepartureData(DepartureTime time)
    {
        List<DepartureTime> times = new ArrayList<>(markDataList.size());
        for(int i = 0; i < markDataList.size(); i++)
        {
            times.add(markDataList.get(i).getDepartureTime());
        }

        final int binaryIndex = Collections.binarySearch(times, time, new LowerBoundComparator<DepartureTime>());
        final int index = (binaryIndex >= 0) ? binaryIndex : ~binaryIndex;
        if(index >= 0 && index < times.size())
            return getDepartureData().get(index);
        return null;
    }

    @Override
    public DepartureData getNextDepartureData()
    {
        if(getDisplayMode() == DeparturePlateMode.REALTIME)
        {
            return getNextDepartureData(new DepartureTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE)));
        }
        final MinecraftDate date = new MinecraftDate(getWorld());
        return getNextDepartureData(date.toDepartureTime());
    }

    @Override
    public void addDepartureData(DepartureData data)
    {
        markDataList.add(new MarkData(data.getDate().getHours(), data.getDate().getMinutes(), data.getType(), data.getDestination()));
        Collections.sort(markDataList, new MarkDataComparator());
    }

    public MarkData[] getNextMarkDataWithMinecraftDate()
    {
        return getNextMarkDataWithMinecraftDate(markDataList);
    }

    private MarkData[] getNextMarkDataWithMinecraftDate(List<MarkData> markDataList)
    {
        final MinecraftDate md = new MinecraftDate(getWorld().getWorldTime());
        final MarkData[] result = new MarkData[2];
        int resultIndex = 0;

        final int binaryIndex = Collections.binarySearch(markDataList, MarkData.of(md), new LowerBoundComparator<MarkData>());

        final int index = (binaryIndex >= 0) ? binaryIndex : ~binaryIndex;
        if(index < 0 || index >= markDataList.size())
            return result;

        for(int i = index; i < Math.min(index + 2, markDataList.size()); i++)
        {
            result[resultIndex] = markDataList.get(i);
            resultIndex++;
        }
        return result;
    }
}
