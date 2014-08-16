package com.noto0648.stations.common;

import java.util.Comparator;

/**
 * Created by Noto on 14/08/07.
 */
public class MarkDataComparator implements Comparator<MarkData>
{
    @Override
    public int compare(MarkData o1, MarkData o2)
    {
        if(o1.hours == o2.hours && o1.minutes == o2.minutes)
            return 0;

        if(o1.hours > o2.hours)
        {
            return 1;
        }
        if(o1.hours >= o2.hours)
        {
            if(o1.minutes > o2.minutes)
                return 1;
            if(o1.minutes >= o2.minutes)
                return 1;
        }
        return -1;
    }
}
