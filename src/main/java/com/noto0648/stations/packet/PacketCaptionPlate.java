package com.noto0648.stations.packet;

import com.noto0648.stations.tile.TileEntityNamePlate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 14/08/08.
 */
public class PacketCaptionPlate implements IMessageHandler<PacketSendPlate, IMessage>
{
    @Override
    public IMessage onMessage(PacketSendPlate message, MessageContext ctx)
    {
        int x = message.x;
        int y = message.y;
        int z = message.z;

        World world = ctx.getServerHandler().playerEntity.worldObj;
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileEntityNamePlate)
        {
            List<String> result = new ArrayList();
            List<String> keyMap = new ArrayList();
            String[] keys = message.strMap.keySet().toArray(new String[0]);
            for(int i = 0; i < keys.length; i++)
            {
                keyMap.add(keys[i]);
                result.add(message.strMap.get(keys[i]));
            }

            ((TileEntityNamePlate)te).setNamePlateData(message.currentType, message.texture, message.light, result, keyMap);
            ((TileEntityNamePlate)te).reload();
        }
        return null;
    }
}
