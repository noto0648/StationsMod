package com.noto0648.stations.common;

import java.util.Comparator;

public class LowerBoundComparator<T extends Comparable<? super T>> implements Comparator<T>
{
    @Override
    public int compare(T o1, T o2)
    {
        return (o1.compareTo(o2) >= 0) ? 1 : -1;
    }
}
