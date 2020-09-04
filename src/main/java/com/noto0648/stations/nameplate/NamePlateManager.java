package com.noto0648.stations.nameplate;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.ModLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL20;

import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Noto on 14/08/08.
 */
public class NamePlateManager
{
    public static final NamePlateManager INSTANCE = new NamePlateManager();
    private final NamePlateBase DEFAULT;
    private static final Gson gson = new Gson();
    private List<NamePlateBase> plates = new ArrayList();

    private List<String> textures;
    private Map<String, String> models;
    private Map<String, TextureAtlasSprite> verticalNameplateTextures;
    private List<String> verticalTextures;

    private int namePlateShader;
    private boolean initialized = false;

    private NamePlateManager()
    {
        DEFAULT = new NamePlateDefault();
    }

    public void init()
    {
        if(initialized)
            return;

        models = new HashMap<>();
        models.put("basic", "block/name_plate.obj");

        textures = new ArrayList<>();

        NamePlateManager.INSTANCE.registerNamePlate(DEFAULT);
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateTokaido());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateMeitetsu());
        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateNagoyaSubway());

        verticalNameplateTextures = new HashMap<>();
        scanJarMyself();
        sortNamePlates();

        for(NamePlateBase plate : plates)
        {
            plate.registerTextures(textures);
        }

        textures = textures.stream().distinct().sorted().collect(Collectors.toList());

        //loadShader();
        initialized = true;
    }


    private String loadShaderContent(InputStream is) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null)
        {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }

    private void loadShader()
    {
        final ResourceLocation fragmentLocation = new ResourceLocation(StationsMod.MOD_ID, "shaders/program/nameplate_color_change.fsh");
        final ResourceLocation vertexLocation = new ResourceLocation(StationsMod.MOD_ID, "shaders/program/nameplate_color_change.vsh");

        try
        {
            final IResource vertRes = Minecraft.getMinecraft().getResourceManager().getResource(vertexLocation);

            int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
            GL20.glShaderSource(vertexShader, loadShaderContent(vertRes.getInputStream()));
            GL20.glCompileShader(vertexShader);

            final IResource fragRes = Minecraft.getMinecraft().getResourceManager().getResource(fragmentLocation);
            int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
            GL20.glShaderSource(fragmentShader, loadShaderContent(fragRes.getInputStream()));
            GL20.glCompileShader(fragmentShader);

            namePlateShader = GL20.glCreateProgram();
            GL20.glBindAttribLocation(namePlateShader, 0, "position");
            GL20.glAttachShader(namePlateShader, vertexShader);
            //GL20.glAttachShader(namePlateShader, fragmentShader);

            GL20.glDeleteShader(vertexShader);
            GL20.glDeleteShader(fragmentShader);

            GL20.glLinkProgram(namePlateShader);
            ModLog.getLog().info("Shader registered {}", namePlateShader);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerTextures(TextureMap textureMap)
    {
        if(!initialized) init();
        for(String key : textures)
        {
            textureMap.registerSprite(new ResourceLocation(key));
        }

        verticalNameplateTextures = new HashMap<>();
        for(String key : verticalTextures)
        {
            verticalNameplateTextures.put(key, textureMap.registerSprite(new ResourceLocation(key)));
            ModLog.getLog().info(key);
        }
        verticalTextures = null;
    }

    @SideOnly(Side.CLIENT)
    public void loadRetexturedModels(IRegistry<ModelResourceLocation, IBakedModel> registry)
    {
        if(!initialized) init();
        try
        {
            for(String modelKey : models.keySet())
            {
                String modelName = models.get(modelKey);
                OBJModel model = (OBJModel)ModelLoaderRegistry.getModel(new ResourceLocation(StationsMod.MOD_ID, modelName)).process(ImmutableMap.of("flip-v", "true"));

                for(String textureName : textures)
                {
                    IModel remodel = model.retexture(ImmutableMap.of("#stations_mod:nameplates/name_plate", textureName));
                    String val = textureName.replace(StationsMod.MOD_ID + ":nameplates/", "");
                    IBakedModel bakedModel = remodel.bake(model.getDefaultState(), DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter());
                    String resourceName = StationsMod.MOD_ID + ":nameplate_model/" + modelKey  + "/"+ val;
                    registry.putObject(new ModelResourceLocation(resourceName, "normal"), bakedModel);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void registerNamePlate(NamePlateBase plate)
    {
        plates.add(plate);
    }

    public void removeNamePlate(NamePlateBase plate)
    {
        plates.remove(plate);
        sortNamePlates();
    }

    private void scanJarMyself()
    {
        try
        {
            final FileSystem fs = FileSystems.newFileSystem(getClass().getResource("/assets/"  + StationsMod.MOD_ID).toURI(), Collections.emptyMap());
            Files.walk(fs.getPath("/assets/" + StationsMod.MOD_ID + "/labelpatterns"), 1).forEach(f -> {
                if(!f.toString().contains("json"))
                    return;
                try
                {
                    final BufferedReader br = Files.newBufferedReader(f);
                    String line;
                    final StringBuilder result = new StringBuilder();
                    while((line = br.readLine()) != null)
                    {
                        result.append(line);
                    }
                    try
                    {
                        final NamePlateJson namePlate = gson.fromJson(result.toString(), NamePlateJson.class);
                        if (namePlate.name != null && !namePlate.name.startsWith("__"))
                        {
                            NamePlateManager.INSTANCE.registerNamePlate(new NamePlateJsonConverter(namePlate, namePlate.labels));
                            ModLog.getLog().info("Registered label pattern {}", namePlate.name);
                        }
                    }
                    catch (Exception e)
                    {
                        ModLog.getLog().error("JSON format is not validate in {} !", f.toString());
                        e.printStackTrace();
                    }
                    br.close();
                }
                catch (IOException e)
                {
                }
            });

            Files.walk(fs.getPath("/assets/" + StationsMod.MOD_ID + "/textures/nameplates"), 1).forEach(f ->
            {
                if(f == null || !f.toString().contains("png"))
                    return;
                final String resourceName = StationsMod.MOD_ID +":nameplates/" + f.getFileName().toString().replace(".png", "");
                textures.add(resourceName);
                //ModLog.getLog().info("Register Texture {}", resourceName);
            });

            verticalTextures = new ArrayList<>();
            Files.walk(fs.getPath("/assets/" + StationsMod.MOD_ID + "/textures/vertical_nameplates"), 1).forEach(f ->
            {
                if(f == null || !f.toString().contains("png"))
                    return;
                final String resourceName = StationsMod.MOD_ID +":vertical_nameplates/" + f.getFileName().toString().replace(".png", "");
                verticalTextures.add(resourceName);
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void scanningNamePlate()
    {
//        File modsDir = new File(Loader.instance().getConfigDir().getParentFile(), "mods");
        File modsDir = new File(".", "mods");

        File modDir = new File(modsDir, "stations_mod");
        File platesDir = new File(modDir, "name_plates");
        platesDir.mkdirs();

        File[] images = platesDir.listFiles();

        List<String> zipPaths = new ArrayList();
        for(int i = 0; i < images.length; i++)
        {
            if(images[i].getPath().endsWith(".png"))
            {
                //platesImages.add(images[i].getPath());
                //Stations.proxy.readTexture(images[i].getPath());
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
                    if(namePlateData.name != null && !namePlateData.name.startsWith("__"))
                    {
                        NamePlateManager.INSTANCE.registerNamePlate(new NamePlateJsonConverter(namePlateData, namePlateData.labels));
                        System.out.println("Registered Nameplate " + namePlateData.name);
                    }
                    stream.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(images[i].getPath().endsWith(".zip") || images[i].getPath().endsWith(".jar"))
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
                        if(entry.getName().contains("$"))
                        {
                            continue;
                        }
                        ClassLoader clsLoader = this.getClass().getClassLoader();
                        Class cls = clsLoader.loadClass(entry.getName().replace(".class", "").replace("/", "."));

                        for(int k = 0; k < cls.getAnnotations().length; k++)
                        {
                            Annotation annotation = cls.getAnnotations()[k];
                            if(annotation instanceof NamePlateAnnotation)
                            {
                                Object inst = cls.newInstance();
                                NamePlateManager.INSTANCE.registerNamePlate((NamePlateBase)inst);
                            }
                        }
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
                        //platesImages.add(key);
                        //Stations.proxy.readTexture(key, zip.getInputStream(entry));
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void sortNamePlates()
    {
        plates.sort((s,t) -> s.getNamePlateId().compareToIgnoreCase(t.getNamePlateId()));
    }

    public NamePlateBase getNamePlateFromName(String str)
    {
        int low = 0;
        int high = plates.size() - 1;
        while(low <= high)
        {
            int mid = (low + high) / 2;
            NamePlateBase plate = plates.get(mid);
            int lvl = str.compareToIgnoreCase(plate.getNamePlateId());
            if(lvl == 0)
                return plate;
            if(0 < lvl)
            {
                low = mid + 1;
            }
            else
            {
                high = mid -1;
            }
        }
        return DEFAULT;
    }


    public List<NamePlateBase> getNamePlates()
    {
        return plates;
    }

    public List<String> getTextures()
    {
        return textures;
    }

    public int getNamePlateShader()
    {
        return namePlateShader;
    }

    public TextureAtlasSprite getVerticalTexture(String key)
    {
        final String newKey = StationsMod.MOD_ID + ":vertical_nameplates/" + key;
        if(verticalNameplateTextures.containsKey(newKey))
            return verticalNameplateTextures.get(newKey);

        return verticalNameplateTextures.get(StationsMod.MOD_ID + ":vertical_nameplates/kokutetsu");
    }
}
