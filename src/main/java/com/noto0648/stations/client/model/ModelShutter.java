package com.noto0648.stations.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Noto on 14/08/13.
 */
public class ModelShutter extends ModelBase
{
    private ModelRenderer shape;

    public ModelShutter()
    {
        textureWidth = 64;
        textureHeight = 32;

        shape = new ModelRenderer(this, 0, 0);
        shape.addBox(0F, 0F, 0F, 16, 16, 1);
        shape.setRotationPoint(0F, 0F, 0F);
        shape.setTextureSize(64, 32);
        shape.mirror = true;
        setRotation(shape, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        shape.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

    public void renderAll(float f5)
    {
        shape.render(f5);
    }
}
