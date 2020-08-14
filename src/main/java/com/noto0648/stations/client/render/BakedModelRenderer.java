package com.noto0648.stations.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.List;

public class BakedModelRenderer implements IBakedModel
{
    private final IBakedModel parent;
    public BakedModelRenderer(IBakedModel model)
    {
        parent = model;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState iBlockState, @Nullable EnumFacing enumFacing, long l)
    {
        return parent.getQuads(iBlockState, enumFacing, l);
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return parent.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return parent.getOverrides();
    }
}
