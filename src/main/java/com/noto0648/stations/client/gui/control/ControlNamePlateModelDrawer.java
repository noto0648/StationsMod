package com.noto0648.stations.client.gui.control;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.client.gui.IGui;
import com.noto0648.stations.nameplate.NamePlateBase;
import com.noto0648.stations.nameplate.NamePlateManager;
import com.noto0648.stations.tiles.TileEntityNamePlate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControlNamePlateModelDrawer extends Control
{
    private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

    private ControlVerticalScrollBar scrollBar;

    private int selected;
    private final TileEntityNamePlate tile;

    private Map<String, String> stringData;
    private String currentLabelMap;

    private List<String> items;
    public ControlNamePlateModelDrawer(IGui gui, TileEntityNamePlate te)
    {
        super(gui);
        this.tile = te;
        items = new ArrayList<>();
        //items.add("DefaultTexture");
        for(String s : NamePlateManager.INSTANCE.getTextures())
        {
            items.add(s.replace(StationsMod.MOD_ID + ":nameplates/", ""));
        }
        int current = items.indexOf(/*StationsMod.MOD_ID + ":nameplates/" + */this.tile.texture);

        if(this.tile.texture.equals("DefaultTexture"))
        {
            current = items.indexOf("name_plate");
        }
        selected = current;

        scrollBar = new ControlVerticalScrollBar(gui);
        scrollBar.setPageSize(2);
        scrollBar.setMaxScroll(items.size() % 3 == 0 ? items.size() / 3 : (items.size() / 3 + 1));
    }

    @Override
    public void initGui()
    {
        scrollBar.initGui();
        scrollBar.setSize(12, height);
        scrollBar.setLocation(locationX + width - 12, locationY);
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if(!isEnable)
            return;

        scrollBar.draw(mouseX, mouseY);
        int onCursor = -1;
        for(int i = scrollBar.getCurrentScroll() * 3; i < Math.min(items.size(), scrollBar.getCurrentScroll() * 3 + 6); i++)
        {
            int ci = i - scrollBar.getCurrentScroll() * 3;
            int x1 = (width-12)/3*(ci%3) + locationX + 1;
            int y1 = (height/2) * (ci/3) + locationY+ 1;
            int x2 = (width-12)/3*((ci%3)+1)+locationX - 1;
            int y2 = (height/2) * (ci/3) + height / 2 + locationY - 1;
            if(selected == i)
            {
                drawRect(x1, y1, x2, y2, 0x66AAAAFF);
            }
            else
            {
                drawRect(x1, y1, x2, y2, 0x66EEEEEE);
            }

            if(x1 <= mouseX && mouseX <= x2 && y1 <= mouseY && mouseY <= y2)
            {
                onCursor = i;
            }

        }

        draw3DPlates(mouseX, mouseY, onCursor);

        GlStateManager.disableDepth();
        if(onCursor != -1)
        {
            int oc = onCursor - scrollBar.getCurrentScroll() * 3;
            int sx1 = (width-12)/3*(oc%3) + locationX + 1;
            int sy1 = (height/2) * (oc/3) + locationY+ 1;
            int sx2 = (width-12)/3*((oc%3)+1)+locationX - 1;
            int sy2 = (height/2) * (oc/3) + height / 2 + locationY - 1;
            GlStateManager.enableAlpha();
            drawRect(sx1, sy1, sx2, sy2, 0x66FFFFFF);
            GlStateManager.disableAlpha();
        }

    }

    public void draw3DPlates(int mouseX, int mouseY, int onCursor)
    {
        if(!isEnable || currentLabelMap == null)
            return;

        final int modIdLength = StationsMod.MOD_ID.length() + 12;
        final NamePlateBase renderPlate = NamePlateManager.INSTANCE.getNamePlateFromName(currentLabelMap);
        final IBlockState state = StationsItems.blockNamePlate.getDefaultState();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)0, (float)0, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


        for(int i = scrollBar.getCurrentScroll() * 3; i < Math.min(items.size(), scrollBar.getCurrentScroll() * 3 + 6); i++)
        {
            final String texture = items.get(i);
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)((width-12) / 3) * (i % 3) + 10 + locationX + ((width-12)/24), (float)(height/2) * (i / 3 - scrollBar.getCurrentScroll()) + locationY + height/3, 100.0F + 250f);
            GlStateManager.translate(8.0F, 8.0F, 0.0F);
            GlStateManager.scale(1.0F, -1.0F, 1.0F);
            GlStateManager.scale(48.0F, 48.0F, 48.0F);

            GlStateManager.rotate(90,0,1,0);
            final IBakedModel model = renderPlate.getModel(texture);

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            blockRenderer.getBlockModelRenderer().renderModelBrightnessColor(model, 1f, 1f, 1f, 1f);


            GlStateManager.pushMatrix();
            GlStateManager.translate(0.5f, 0.5f, 0.5f);
            for(int j = 0; j < 2; j++)
            {
                GlStateManager.pushMatrix();
                GL11.glRotatef(-180F * j + 90f, 0, 1, 0);
                if(renderPlate != null && stringData != null)
                {
                    renderPlate.render(stringData, j == 0,0);
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();

        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();

    }


    @Override
    public void update()
    {
        if(!isEnable)
            return;
        scrollBar.update();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if(!isEnable)
            return;
        scrollBar.mouseClicked(mouseX, mouseY, button);

        if(button == 0 && locationX <= mouseX && locationY <= mouseY && locationX + width - 12 >= mouseX && locationY + height >= mouseY)
        {
            int sel = ((mouseY - locationY) / (height / 2)) * 3 + ((mouseX - locationX) / ((width-12)/3)) + scrollBar.getCurrentScroll() * 3;
            if(sel > -1 && sel < items.size())
            {
                playClickSound();
                if(selected != sel)
                {
                    selected = sel;
                    selectedChanged();
                }
            }
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int button, long time)
    {
        if(!isEnable)
            return;
        scrollBar.mouseClickMove(mouseX, mouseY, button, time);
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int mode)
    {
        if(!isEnable)
            return;
        scrollBar.mouseMovedOrUp(mouseX, mouseY, mode);
        //scrollBarClicked = false;
    }

    @Override
    public void mouseScroll(int mouseScroll)
    {
        if(!isEnable)
            return;
        scrollBar.mouseScroll(mouseScroll);
    }


    public int getSelectedIndex()
    {
        return selected;
    }

    public String getSelected()
    {
        if(selected == -1)
            return "";

        return items.get(selected);
    }

    public void setPreviewStringMap(Map<String, String> map)
    {
        stringData = map;
    }

    public void setCurrentLabelMap(String str)
    {
        currentLabelMap = str;
    }

    public void selectedChanged() {}

}
