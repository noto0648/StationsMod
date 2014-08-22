package com.noto0648.stations.nameplate;

import java.util.List;

/**
 * Created by Noto on 14/08/22.
 */
public class NamePlateJson
{
    public String name;
    public List<LabelData> labels;

    public String author;
    public String comment;

    public class LabelData
    {
        public int x;
        public int y;
        public Justification justification;

        public String label;
        public String reverseLabel;
        public boolean enableReverse;

        public double R;
        public double G;
        public double B;

        public double fontScale;

        public String author;
        public String comment;
    }

    public enum Justification
    {
        CENTER,
        RIGHT,
        LEFT
    }
}
