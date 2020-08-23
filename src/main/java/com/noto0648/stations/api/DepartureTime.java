package com.noto0648.stations.api;

import java.util.Objects;

public class DepartureTime implements Comparable<DepartureTime>
{
    private int hours;
    private int minutes;

    public DepartureTime() {}

    public DepartureTime(int hours, int minutes)
    {
        setHours(hours);
        setMinutes(minutes);
    }

    public int getHours()
    {
        return hours;
    }

    public void setHours(int hours)
    {
        this.hours = Math.max(0, Math.min(23, hours));
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = Math.max(0, Math.min(59, minutes));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartureTime that = (DepartureTime) o;
        return hours == that.hours && minutes == that.minutes;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(hours, minutes);
    }

    @Override
    public String toString()
    {
        return "DepartureTime{" + "hours=" + hours + ", minutes=" + minutes + '}';
    }

    @Override
    public int compareTo(DepartureTime o)
    {
        if(this.hours > o.hours)
            return 1;

        return this.minutes - o.minutes;
    }
}
