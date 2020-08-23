package com.noto0648.stations.api;

import java.util.Objects;

public class DepartureData
{
    private DepartureTime date;
    private String type;
    private String destination;

    public DepartureData()
    {
        date = new DepartureTime();
        type = "";
        destination = "";
    }

    public DepartureData(String type, String destination)
    {
        date = new DepartureTime();
        this.type = type;
        this.destination = destination;
    }

    public DepartureData(DepartureTime date, String type,String destination)
    {
        this.date = date;
        this.type = type;
        this.destination = destination;
    }

    public DepartureTime getDate()
    {
        return date;
    }

    public void setDate(DepartureTime date)
    {
        this.date = date;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartureData that = (DepartureData) o;
        return Objects.equals(date, that.date) && Objects.equals(type, that.type) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(date, type, destination);
    }
}
