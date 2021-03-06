package com.noto0648.stations.tile;

import com.noto0648.stations.common.Utils;
import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.packet.PacketSendPlate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 14/08/07.
 */
public class TileEntityNamePlate extends TileBase
{
    public String currentType;
    public String texture;
    public List<String> stringList;
    public List<String> keyList;
    public boolean light;

    public int pasteFace;

    public TileEntityNamePlate()
    {
        currentType = "default";
        texture = "DefaultTexture";
        stringList = new ArrayList();
        keyList = new ArrayList();

        stringList.add("まいくら");
        stringList.add("たくみとうげ");
        stringList.add("えんだ");
        stringList.add("Minecraft");

        keyList.add("stationName");
        keyList.add("nextStation");
        keyList.add("prevStation");
        keyList.add("englishName");
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        currentType = p_145839_1_.getString("currentType");
        texture = p_145839_1_.getString("texture");
        light = p_145839_1_.getBoolean("light");
        pasteFace = p_145839_1_.getInteger("pasteFace");

        NBTTagList tagList = (NBTTagList)p_145839_1_.getTag("stringList");
        stringList.clear();
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            stringList.add(tagList.getStringTagAt(i));
        }

        NBTTagList tagKeys = (NBTTagList)p_145839_1_.getTag("keyList");
        keyList.clear();
        for(int i = 0; i < tagKeys.tagCount(); i++)
        {
            keyList.add(tagKeys.getStringTagAt(i));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setString("currentType", currentType);
        p_145841_1_.setString("texture", texture);
        p_145841_1_.setBoolean("light", light);
        p_145841_1_.setInteger("pasteFace", pasteFace);

        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < stringList.size(); i++)
        {
            tagList.appendTag(new NBTTagString(stringList.get(i)));
        }
        p_145841_1_.setTag("stringList", tagList);

        NBTTagList tagKeys = new NBTTagList();
        for(int i = 0; i < keyList.size(); i++)
        {
            tagKeys.appendTag(new NBTTagString(keyList.get(i)));
        }
        p_145841_1_.setTag("keyList", tagKeys);
    }


    public Map<String, String> getHashMap()
    {
        NamePlateBase renderPlate = null;

        for(int i = 0; i < NamePlateManager.INSTANCE.getNamePlates().size(); i++)
        {
            String name = NamePlateManager.INSTANCE.getNamePlates().get(i).getName();
            if(name.equalsIgnoreCase(currentType))
            {
                renderPlate = NamePlateManager.INSTANCE.getNamePlates().get(i);
            }
        }
        if(renderPlate != null)
        {
            List<String> strs = new ArrayList();
            renderPlate.init(strs);
            if(strs.size() == stringList.size() && strs.size() == keyList.size())
            {
                Map<String, String> result = new HashMap<String, String>(strs.size());
                for(int i = 0; i < strs.size(); i++)
                {
                    String key = keyList.get(i);
                    String value = stringList.get(i);
                    result.put(key, value);
                }
                return result;
            }
        }
        return null;
    }

    public void reload()
    {
        //worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata(), 2);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void setNamePlateData(String type, String tex, boolean l, List<String> r, List<String> k)
    {
        currentType = type;
        texture = tex;
        light = l;
        stringList = r;
        keyList = k;
        Map<String, String> ms = new HashMap();

        for(int i = 0; i < stringList.size(); i++)
        {
            ms.put(keyList.get(i), stringList.get(i));
        }

        Utils.INSTANCE.sendToPlayers(new PacketSendPlate(xCoord, yCoord, zCoord, currentType, ms, texture, light), this);
    }
}
