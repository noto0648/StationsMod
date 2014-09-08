package com.noto0648.stations.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Noto on 14/09/06.
 */
public class ModelRailToy extends ModelBase
{
    ModelRenderer shape1;
    ModelRenderer shape2;
    ModelRenderer shape3;
    ModelRenderer shape4;
    ModelRenderer shape5;

    public ModelRailToy()
    {
        textureWidth = 64;
        textureHeight = 32;

        shape1 = new ModelRenderer(this, 0, 7);
        shape1.addBox(0F, 0F, 0F, 16, 1, 4);
        shape1.setRotationPoint(-8F, 23F, -2F);
        shape1.setTextureSize(64, 32);
        shape1.mirror = true;
        setRotation(shape1, 0F, 0F, 0F);
        shape2 = new ModelRenderer(this, 0, 0);
        shape2.addBox(0F, 0F, 0F, 16, 1, 1);
        shape2.setRotationPoint(-8F, 23F, 3F);
        shape2.setTextureSize(64, 32);
        shape2.mirror = true;
        setRotation(shape2, 0F, 0F, 0F);
        shape3 = new ModelRenderer(this, 0, 0);
        shape3.addBox(0F, 0F, 0F, 16, 1, 1);
        shape3.setRotationPoint(-8F, 23F, -4F);
        shape3.setTextureSize(64, 32);
        shape3.mirror = true;
        setRotation(shape3, 0F, 0F, 0F);
        shape4 = new ModelRenderer(this, 0, 4);
        shape4.addBox(0F, -0.5F, 0F, 16, 0, 1);
        shape4.setRotationPoint(-8F, 24F, -3F);
        shape4.setTextureSize(64, 32);
        shape4.mirror = true;
        setRotation(shape4, 0F, 0F, 0F);
        shape5 = new ModelRenderer(this, 0, 4);
        shape5.addBox(0F, -0.5F, 0F, 16, 0, 1);
        shape5.setRotationPoint(-8F, 24F, 2F);
        shape5.setTextureSize(64, 32);
        shape5.mirror = true;
        setRotation(shape5, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        shape1.render(f5);
        shape2.render(f5);
        shape3.render(f5);
        shape4.render(f5);
        shape5.render(f5);
    }

    public void allRender(float f5)
    {
        shape1.render(f5);
        shape2.render(f5);
        shape3.render(f5);
        shape4.render(f5);
        shape5.render(f5);
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

}
