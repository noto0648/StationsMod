package com.noto0648.stations.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by Noto on 14/08/13.
 */
public class TileEntityTicketGateRender extends TileEntitySpecialRenderer
{
    public static IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("notomod", "objs/ticket_gate.obj"));
    public static ResourceLocation texture = new ResourceLocation("notomod", "textures/models/ticket_gate_main.png");
    public static ResourceLocation textureDoor = new ResourceLocation("notomod", "textures/models/ticket_gate_door.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double par2, double par3, double par4, float p_147500_8_)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(par2 + 0.5, par3 + 0.68, par4 + 0.5);

        /*
            door_right_door_right_cube
            door_left_door_left_cube
            machine_machine_cube
         */

        GL11.glScalef(0.5F, 0.6F, 0.5F);
        GL11.glRotatef(-90F, 0, 1, 0);

        if(tile.getBlockMetadata() == 5) GL11.glRotatef(90F, 0, 1, 0);
        if(tile.getBlockMetadata() == 6) GL11.glRotatef(180F, 0, 1, 0);
        if(tile.getBlockMetadata() == 7) GL11.glRotatef(270F, 0, 1, 0);

        bindTexture(texture);
        model.renderPart("machine_machine_cube");
        /*
        bindTexture(textureDoor);
        GL11.glRotatef(180F, 0F, 1F, 0F);
        GL11.glTranslatef(0F, 0F, -1F);
        model.renderPart("door_right_door_right_cube");
*/
        //model.renderPart("door_left_door_left_cube");
        //model.renderAll();
        GL11.glPopMatrix();
    }
}
