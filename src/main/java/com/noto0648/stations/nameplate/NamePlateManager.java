package com.noto0648.stations.nameplate;

import com.google.gson.Gson;
import com.noto0648.stations.client.texture.TextureImporter;
import cpw.mods.fml.common.Loader;

import javax.xml.stream.util.StreamReaderDelegate;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Noto on 14/08/08.
 */
public class NamePlateManager
{
    public static NamePlateManager INSTANCE = new NamePlateManager();
    private static Gson gson = new Gson();
    private List<NamePlateBase> plates = new ArrayList();
    public static List<String> platesImages = new ArrayList();

    private NamePlateManager() {}

    public void init()
    {
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateDefault());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateTokaido());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateMeitetsu());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateNagoyaSubway());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateToyohashiLine());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateAonamiLine());

        scanningNamePlate();
    }

    public void registerNamePlate(NamePlateBase plate)
    {
        plates.add(plate);
    }

    public void removeNamePlate(NamePlateBase plate)
    {
        plates.remove(plate);
    }

    private void scanningNamePlate()
    {
        File modsDir = new File(Loader.instance().getConfigDir().getParentFile(), "mods");
        File modDir = new File(modsDir, "stations_mod");
        File platesDir = new File(modDir, "name_plates");
        platesDir.mkdirs();

        File[] images = platesDir.listFiles();

        List<String> zipPaths = new ArrayList();
        for(int i = 0; i < images.length; i++)
        {
            if(images[i].getPath().endsWith(".png"))
            {
                platesImages.add(images[i].getPath());
                TextureImporter.INSTANCE.readTexture(images[i].getPath());
            }
            else if(images[i].getPath().endsWith(".json"))
            {
                try
                {
                    BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream(images[i])));
                    String line;
                    StringBuilder result = new StringBuilder();

                    while((line = stream.readLine()) != null)
                    {
                        result.append(line);
                    }
                    NamePlateJson namePlateData = gson.fromJson(result.toString(), NamePlateJson.class);
                    NamePlateManager.INSTANCE.registerNamePlate(new NamePlateJsonConverter(namePlateData, namePlateData.labels));
                    stream.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(images[i].getPath().endsWith(".zip"))
            {
                zipPaths.add(images[i].getPath());
            }
        }



        for(int i = 0; i < zipPaths.size(); i++)
        {
            try
            {

                ZipFile zip = new ZipFile(zipPaths.get(i), Charset.forName("MS932"));
                for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements();)
                {
                    ZipEntry entry = e.nextElement();
                    if(entry.getName().endsWith(".class"))
                    {


                    }
                    else if(entry.getName().endsWith(".json"))
                    {
                        BufferedReader stream = new BufferedReader(new InputStreamReader(zip.getInputStream(entry)));
                        String line;
                        StringBuilder result = new StringBuilder();

                        while((line = stream.readLine()) != null)
                        {
                            result.append(line);
                        }
                        NamePlateJson namePlateData = gson.fromJson(result.toString(), NamePlateJson.class);
                        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateJsonConverter(namePlateData, namePlateData.labels));
                        stream.close();
                    }
                    else if(entry.getName().endsWith(".png"))
                    {
                        String key = zipPaths.get(i) + "/" + entry.getName();
                        platesImages.add(key);
                        TextureImporter.INSTANCE.readTexture(key, zip.getInputStream(entry));
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<NamePlateBase> getNamePlates()
    {
        return plates;
    }
}
