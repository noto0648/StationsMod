package com.noto0648.stations.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Noto on 14/08/04.
 */
public class ModelFence extends ModelBase
{
    ModelRenderer box1;
    ModelRenderer box2;
    ModelRenderer box3;
    ModelRenderer box4;
    ModelRenderer box5;
    ModelRenderer box6;
    ModelRenderer box7;
    ModelRenderer box8;
    ModelRenderer box9;
    ModelRenderer box10;
    ModelRenderer box11;
    ModelRenderer box12;
    ModelRenderer box13;

    public ModelFence()
    {
        textureWidth = 64;
        textureHeight = 32;

        box1 = new ModelRenderer(this, 0, 0);
        box1.addBox(0F, -16F, 0F, 1, 16, 2);
        box1.setRotationPoint(-8F, 24F, 0F);
        box1.setTextureSize(64, 32);
        box1.mirror = true;
        setRotation(box1, 0F, 0F, 0F);
        box2 = new ModelRenderer(this, 0, 0);
        box2.addBox(0F, -16F, 0F, 1, 16, 2);
        box2.setRotationPoint(7F, 24F, 0F);
        box2.setTextureSize(64, 32);
        box2.mirror = true;
        setRotation(box2, 0F, 0F, 0F);
        box3 = new ModelRenderer(this, 8, 0);
        box3.addBox(0F, 0F, 0F, 14, 1, 1);
        box3.setRotationPoint(-7F, 22F, 0F);
        box3.setTextureSize(64, 32);
        box3.mirror = true;
        setRotation(box3, 0F, 0F, 0F);
        box4 = new ModelRenderer(this, 8, 0);
        box4.addBox(0F, 0F, 0F, 14, 1, 1);
        box4.setRotationPoint(-7F, 9F, 0F);
        box4.setTextureSize(64, 32);
        box4.mirror = true;
        setRotation(box4, 0F, 0F, 0F);
        box5 = new ModelRenderer(this, 12, 19);
        box5.addBox(0F, 0F, 0F, 1, 12, 1);
        box5.setRotationPoint(5F, 10F, 0F);
        box5.setTextureSize(64, 32);
        box5.mirror = true;
        setRotation(box5, 0F, 0F, 0F);
        box6 = new ModelRenderer(this, 12, 19);
        box6.addBox(0F, 0F, 0F, 1, 12, 1);
        box6.setRotationPoint(3F, 10F, 0F);
        box6.setTextureSize(64, 32);
        box6.mirror = true;
        setRotation(box6, 0F, 0F, 0F);
        box7 = new ModelRenderer(this, 12, 19);
        box7.addBox(0F, 0F, 0F, 1, 12, 1);
        box7.setRotationPoint(1F, 10F, 0F);
        box7.setTextureSize(64, 32);
        box7.mirror = true;
        setRotation(box7, 0F, 0F, 0F);
        box8 = new ModelRenderer(this, 12, 19);
        box8.addBox(0F, 0F, 0F, 1, 12, 1);
        box8.setRotationPoint(-2F, 10F, 0F);
        box8.setTextureSize(64, 32);
        box8.mirror = true;
        setRotation(box8, 0F, 0F, 0F);
        box9 = new ModelRenderer(this, 12, 19);
        box9.addBox(0F, 0F, 0F, 1, 12, 1);
        box9.setRotationPoint(-4F, 10F, 0F);
        box9.setTextureSize(64, 32);
        box9.mirror = true;
        setRotation(box9, 0F, 0F, 0F);
        box10 = new ModelRenderer(this, 12, 19);
        box10.addBox(0F, 0F, 0F, 1, 12, 1);
        box10.setRotationPoint(-6F, 10F, 0F);
        box10.setTextureSize(64, 32);
        box10.mirror = true;
        setRotation(box10, 0F, 0F, 0F);
        box11 = new ModelRenderer(this, 8, 4);
        box11.addBox(0F, 0F, 0F, 16, 1, 1);
        box11.setRotationPoint(-8F, 5F, 1F);
        box11.setTextureSize(64, 32);
        box11.mirror = true;
        setRotation(box11, 0F, 0F, 0F);
        box12 = new ModelRenderer(this, 0, 20);
        box12.addBox(0F, 0F, 0F, 1, 3, 1);
        box12.setRotationPoint(7F, 5F, 0F);
        box12.setTextureSize(64, 32);
        box12.mirror = true;
        setRotation(box12, 0F, 0F, 0F);
        box13 = new ModelRenderer(this, 0, 20);
        box13.addBox(0F, 0F, 0F, 1, 3, 1);
        box13.setRotationPoint(-8F, 5F, 0F);
        box13.setTextureSize(64, 32);
        box13.mirror = true;
        setRotation(box13, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        box1.render(f5);
        box2.render(f5);
        box3.render(f5);
        box4.render(f5);
        box5.render(f5);
        box6.render(f5);
        box7.render(f5);
        box8.render(f5);
        box9.render(f5);
        box10.render(f5);
        box11.render(f5);
        box12.render(f5);
        box13.render(f5);
    }

    public void allRender(float f5)
    {
        box1.render(f5);
        box2.render(f5);
        box3.render(f5);
        box4.render(f5);
        box5.render(f5);
        box6.render(f5);
        box7.render(f5);
        box8.render(f5);
        box9.render(f5);
        box10.render(f5);
        box11.render(f5);
        box12.render(f5);
        box13.render(f5);
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
