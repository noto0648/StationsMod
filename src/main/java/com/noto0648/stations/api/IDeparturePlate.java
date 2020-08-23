package com.noto0648.stations.api;

import java.util.List;

public interface IDeparturePlate
{
    DeparturePlateMode getDisplayMode();

    List<DepartureData> getDepartureData();

    DepartureData getNextDepartureData(DepartureTime time);
    DepartureData getNextDepartureData();

    void addDepartureData(DepartureData data);
}
