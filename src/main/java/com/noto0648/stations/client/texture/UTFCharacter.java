package com.noto0648.stations.client.texture;

import java.util.Objects;

public class UTFCharacter
{
    private char high = 0;
    private char low = 0;
    private final boolean isSurrogatePair;

    public UTFCharacter(char high, char low)
    {
        this.high = high;
        this.low = low;
        isSurrogatePair = true;
    }

    public UTFCharacter(char c)
    {
        this.high = c;
        isSurrogatePair = false;
    }

    public String toString()
    {
        if(!isSurrogatePair)
            return String.valueOf(high);

        return new String(new char[]{high, low});
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(isSurrogatePair, high, low);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof UTFCharacter))
            return false;

        final UTFCharacter utf = (UTFCharacter)obj;
        return utf.isSurrogatePair == isSurrogatePair && high == utf.high && low == utf.low;
    }
}
