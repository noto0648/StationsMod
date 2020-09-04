package com.noto0648.stations.entity;

import com.noto0648.stations.StationsItems;
import com.noto0648.stations.StationsMod;
import com.noto0648.stations.common.Utils;
import com.noto0648.stations.packet.IPacketReceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class EntityVerticalNamePlate extends EntityHanging implements IEntityAdditionalSpawnData, IPacketReceiver
{
    private static final DataParameter<String> TEXTURE = EntityDataManager.createKey(EntityVerticalNamePlate.class, DataSerializers.STRING);
    private static final DataParameter<NBTTagCompound> LABELS = EntityDataManager.createKey(EntityVerticalNamePlate.class, DataSerializers.COMPOUND_TAG);
    private static final DataParameter<Float> DEPTH = EntityDataManager.createKey(EntityVerticalNamePlate.class, DataSerializers.FLOAT);
    private static final DataParameter<String> LABEL_PATTERN = EntityDataManager.createKey(EntityVerticalNamePlate.class, DataSerializers.STRING);

    public EntityVerticalNamePlate(World p_i1588_1_)
    {
        super(p_i1588_1_);
    }

    public EntityVerticalNamePlate(World p_i45852_1_, BlockPos p_i45852_2_, EnumFacing p_i45852_3_)
    {
        super(p_i45852_1_, p_i45852_2_);
        this.updateFacingWithBoundingBox(p_i45852_3_);
    }

    @Override
    protected void entityInit()
    {
        this.getDataManager().register(TEXTURE, "kokutetsu");
        this.getDataManager().register(LABELS, new NBTTagCompound());
        this.getDataManager().register(DEPTH, 0f);
        this.getDataManager().register(LABEL_PATTERN, "builtin_default");

        setLabel("stationName", "おといねっぷ");
    }

    @Override
    public int getWidthPixels()
    {
        return 6;
    }

    @Override
    public int getHeightPixels()
    {
        return 16;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_isInRangeToRenderDist_1_)
    {
        double d0 = 16.0D;
        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return p_isInRangeToRenderDist_1_ < d0 * d0;
    }

    @Override
    public void onBroken(@Nullable Entity entity)
    {
        this.playSound(SoundEvents.ENTITY_ITEMFRAME_BREAK, 1.0F, 1.0F);
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer lvt_2_1_ = (EntityPlayer)entity;
                if (lvt_2_1_.capabilities.isCreativeMode) {
                    return;
                }
            }
            this.entityDropItem(new ItemStack(StationsItems.itemVerticalNamePlate), 0.0F);
        }
    }

    @Override
    public void playPlaceSound()
    {
        this.playSound(SoundEvents.ENTITY_ITEMFRAME_PLACE, 1.0F, 1.0F);
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound p_writeEntityToNBT_1_)
    {
        super.writeEntityToNBT(p_writeEntityToNBT_1_);
        if(getTexture().length() != 0)
            p_writeEntityToNBT_1_.setString("texture", getTexture());

        p_writeEntityToNBT_1_.setTag("labels", getLabels());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound p_readEntityFromNBT_1_)
    {
        super.readEntityFromNBT(p_readEntityFromNBT_1_);
        if(p_readEntityFromNBT_1_.hasKey("texture"))
        {
            setTexture(p_readEntityFromNBT_1_.getString("texture"));
        }

        if(p_readEntityFromNBT_1_.hasKey("labels"))
        {
            setLabels(p_readEntityFromNBT_1_.getCompoundTag("labels"));
        }
    }

    @Override
    protected void updateBoundingBox()
    {
        if (this.facingDirection == null)
            return;

        double d0 = (double)this.hangingPosition.getX() + 0.5D;
        double d1 = (double)this.hangingPosition.getY() + 0.5D;
        double d2 = (double)this.hangingPosition.getZ() + 0.5D;
        double d3 = 0.46875D;
        double d4 = this.offs(this.getWidthPixels());
        double d5 = this.offs(this.getHeightPixels());
        d0 -= (double)this.facingDirection.getFrontOffsetX() * d3;
        d2 -= (double)this.facingDirection.getFrontOffsetZ() * d3;
        d1 += d5;
        EnumFacing enumfacing = this.facingDirection.rotateYCCW();
        d0 += d4 * (double)enumfacing.getFrontOffsetX();
        d2 += d4 * (double)enumfacing.getFrontOffsetZ();
        this.posX = d0;
        this.posY = d1;
        this.posZ = d2;
        double d6 = (double)this.getWidthPixels();
        double d7 = (double)this.getHeightPixels();
        double d8 = d6;
        if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
            d8 = 1.0D;
        } else {
            d6 = 1.0D;
        }

        d6 /= 32.0D;
        d7 /= 32.0D;
        d8 /= 32.0D;

        double ofsx = -getBlockOffset() * this.facingDirection.getFrontOffsetX();
        double ofsz = -getBlockOffset() * this.facingDirection.getFrontOffsetZ();

        this.setEntityBoundingBox(new AxisAlignedBB(d0 - d6 + ofsx, d1 - d7, d2 - d8 + ofsz, d0 + d6 + ofsx, d1 + d7, d2 + d8 + ofsz));

    }


    private double offs(int p_offs_1_)
    {
        return p_offs_1_ % 32 == 0 ? 0.5D : 0.0D;
    }

    public float getBlockOffset()
    {
        if(getEntityWorld() == null)
            return getDepth();

        final BlockPos pos = hangingPosition.add(-facingDirection.getFrontOffsetX(), 0, -facingDirection.getFrontOffsetZ());

        if(!getEntityWorld().isBlockLoaded(pos))
            return getDepth();
        final Block block = getEntityWorld().getBlockState(pos).getBlock();
        //System.out.println(block);
        if(block instanceof BlockFence)
        {
            return 0.4f;
        }
        return 0f;
    }

    @Override
    public void writeSpawnData(ByteBuf byteBuf)
    {
        byteBuf.writeInt(hangingPosition.getX());
        byteBuf.writeInt(hangingPosition.getY());
        byteBuf.writeInt(hangingPosition.getZ());
        byteBuf.writeInt(facingDirection.getIndex());
        byteBuf.writeFloat(getDepth());
    }

    @Override
    public void readSpawnData(ByteBuf byteBuf)
    {
        int x =  byteBuf.readInt();
        int y =  byteBuf.readInt();
        int z =  byteBuf.readInt();
        int d =  byteBuf.readInt();
        float f =  byteBuf.readFloat();

        hangingPosition = new BlockPos(x, y, z);
        setDepth(f);
        updateFacingWithBoundingBox(EnumFacing.values()[d]);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer p_processInitialInteract_1_, EnumHand p_processInitialInteract_2_)
    {
        if(Utils.INSTANCE.haveWrench(p_processInitialInteract_1_))
        {
            p_processInitialInteract_1_.openGui(StationsMod.instance, 40, p_processInitialInteract_1_.world, hangingPosition.getX(), hangingPosition.getY(), hangingPosition.getZ());
            return true;
        }
        return false;
    }

    public String getTexture()
    {
        return getDataManager().get(TEXTURE);
    }

    public void setTexture(String str)
    {
        getDataManager().set(TEXTURE, str);
    }

    public NBTTagCompound getLabels()
    {
        return getDataManager().get(LABELS);
    }

    public void setLabels(NBTTagCompound compound)
    {
        getDataManager().set(LABELS, compound);
    }

    public String getLabel(String key)
    {
        if(getLabels().hasKey(key))
            return getLabels().getString(key);
        return "";
    }

    public void setLabel(String key, String val)
    {
        getLabels().setString(key, val);
    }

    public Set<String> getLabelKeySet()
    {
        return getLabels().getKeySet();
    }

    public float getDepth()
    {
        return getDataManager().get(DEPTH);
    }

    public void setDepth(float depth)
    {
        getDataManager().set(DEPTH, depth);
    }

    @Override
    public void receive(List<Object> data)
    {
        int mapSize = (int)data.get(1);
        int j = 2;
        NBTTagCompound newTag = new NBTTagCompound();
        for(int i = 0; i < mapSize; i++)
        {
            String key = (String)data.get(j);
            j++;
            String val = (String)data.get(j);
            j++;
            newTag.setString(key, val);
        }
        setLabels(newTag);
    }
}
