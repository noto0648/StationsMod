package com.noto0648.stations.common;

import com.noto0648.stations.StationsMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModLog
{
    private final Logger log = LogManager.getLogger(StationsMod.MOD_ID);

    private static final ModLog INSTANCE = new ModLog();
    private ModLog(){}

    public static Logger getLog()
    {
        return INSTANCE.log;
    }

}
