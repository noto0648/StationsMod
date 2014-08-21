package com.noto0648.stations.nameplate;

import com.noto0648.stations.client.texture.TextureImporter;
import cpw.mods.fml.common.Loader;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Noto on 14/08/08.
 */
public class NamePlateManager
{
    public static NamePlateManager INSTANCE = new NamePlateManager();

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
            else if(images[i].getPath().endsWith(".zip"))
            {
                zipPaths.add(images[i].getPath());
            }
        }



        for(int i = 0; i < zipPaths.size(); i++)
        {
            try
            {
                URLClassLoader loader = new URLClassLoader(new URL[]{ (new File(zipPaths.get(i)).toURI().toURL())});

                ZipFile zip = new ZipFile(zipPaths.get(i), Charset.forName("MS932"));
                for (Enumeration<? extends ZipEntry> e = zip.entries(); e.hasMoreElements();)
                {
                    ZipEntry entry = e.nextElement();
                    if(entry.getName().endsWith(".class"))
                    {
                        Class cls = Class.forName(entry.getName().replace(".class", "").replace("/", "."), true, loader);
                        if(cls != null)
                        {
                            Annotation[] as = cls.getDeclaredAnnotations();
                            for(int j = 0; j < as.length; j++)
                            {
                                Object obj = cls.newInstance();

                                    NamePlateManager.INSTANCE.registerNamePlate((NamePlateBase)cls.newInstance());

                            }
                        }

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
