package com.noto0648.stations.api;

public enum DeparturePlateMode
{
    PREPARATION(0),
    NORMAL(1),
    REALTIME(2),
    FIXED(3),
    CUSTOM(4),
    UNDISPLAYED(5);

    private final int id;
    DeparturePlateMode(int id)
    {
        this.id = id;
    }
}
