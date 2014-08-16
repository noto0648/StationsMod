package com.noto0648.stations.nameplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/08.
 */
public class NamePlateRegister
{
    public static NamePlateRegister INSTANCE = new NamePlateRegister();

    private List<NamePlateBase> plates = new ArrayList();

    private NamePlateRegister() {}

    public void registerNamePlate(NamePlateBase plate)
    {
        plates.add(plate);
    }

    public List<NamePlateBase> getNamePlates()
    {
        return plates;
    }
}
