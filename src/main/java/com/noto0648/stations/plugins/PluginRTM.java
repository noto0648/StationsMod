package com.noto0648.stations.plugins;

import com.noto0648.stations.common.ITicketItem;
import com.noto0648.stations.common.ModLog;
import com.noto0648.stations.tiles.TileEntityTicketGate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Method;

public class PluginRTM implements ITicketItem
{
    public static final PluginRTM INSTANCE = new PluginRTM();

    private final String CLASS_TILE_ENTITY_TURNSTILE = "jp.ngt.rtm.block.tileentity.TileEntityTurnstile";
    private final String CLASS_TILE_ENTITY_MACHINE_BASE = "jp.ngt.rtm.block.tileentity.TileEntityMachineBase";

    private Class classTileEntityTurnstile;
    private Class classTileEntityMachineBase;

    public PluginRTM() {}

    private void initializeClazz()
    {
        try
        {
            classTileEntityTurnstile = Class.forName(CLASS_TILE_ENTITY_TURNSTILE);
            classTileEntityMachineBase = Class.forName(CLASS_TILE_ENTITY_MACHINE_BASE);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    public void postInit()
    {
        TileEntityTicketGate.addITicketItemHandler(this);
        initializeClazz();
    }


    @Override
    public boolean validItem(ItemStack itemStack)
    {
        if(itemStack == ItemStack.EMPTY)
            return false;
        String name = itemStack.getUnlocalizedName();
        return name.equals("item.rtm:ticket") || name.equals("item.rtm:ticketbook") || name.equals("item.rtm:ic_card");
    }

    @Override
    public ItemStack cutTicket(EntityLivingBase ep, ItemStack stack)
    {
        String name = stack.getUnlocalizedName();
        if(name.equals("item.rtm:ticket") || name.equals("item.rtm:ticketbook"))
        {
            if(!stack.hasTagCompound())
                stack.setTagCompound(new NBTTagCompound());

            NBTTagCompound comp = stack.getTagCompound();

            if(comp.hasKey("Entered"))
            {
                if(stack.getItemDamage() == 0)
                {
                    return ItemStack.EMPTY;
                }
                //stack = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
                //ep.setCurrentItemOrArmor(0, stack);
                return new ItemStack(stack.getItem(), 1, stack.getItemDamage());
            }
            else
            {
                ItemStack itemStack2 = new ItemStack(stack.getItem(), 1, stack.getItemDamage() - 1);
                NBTTagCompound tag = new NBTTagCompound();
                tag.setBoolean("Entered", true);
                itemStack2.setTagCompound(tag);
                //stack = itemStack2;
                //ep.setCurrentItemOrArmor(0, stack);
                return itemStack2;
            }
        }
        else if(name.equals("item.rtm:ic_card"))
        {
            return stack;
        }
        return stack;
    }

    @Override
    public boolean canStoreTicketCase(ItemStack itemStack)
    {
        if(itemStack == ItemStack.EMPTY)
            return false;
        return !itemStack.getUnlocalizedName().equals("item.rtm:ic_card");
    }

    @Override
    public boolean isICTicket(ItemStack itemStack)
    {
        return !canStoreTicketCase(itemStack);
    }

    public EnumActionResult handleTurnstile(EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumHand hand)
    {
        ItemStack stack = TileEntityTicketGate.cutTicket(entityPlayer, entityPlayer.getHeldItem(hand));

        if(stack == null)
            return EnumActionResult.FAIL;

        final TileEntity tile = world.getTileEntity(blockPos);

        if(classTileEntityTurnstile != null && classTileEntityTurnstile.isInstance(tile))
        {
            try
            {
                final Method m1 = classTileEntityTurnstile.getMethod("setCount", int.class);
                m1.invoke(tile, 30);
                if(classTileEntityMachineBase != null)
                {
                    final Method m2 = classTileEntityMachineBase.getMethod("onActivate");
                    m2.invoke(tile);
                }
                entityPlayer.setHeldItem(hand, stack);
                return EnumActionResult.SUCCESS;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return EnumActionResult.FAIL;
    }

}
