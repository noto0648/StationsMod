package com.noto0648.stations.packet;

import java.util.List;

/**
 * Created by Noto on 14/08/19.
 */
public interface IPacketReceiver
{
    void receive(List<Object> data);
}
