package com.noto0648.stations.plugins;

import com.noto0648.stations.common.ITicketItem;
import com.noto0648.stations.tiles.TileEntityTicketGate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Method;

public class PluginRTM implements ITicketItem
{
    public static final PluginRTM INSTANCE = new PluginRTM();

    private final String CLASS_BLOCK_TURNSTILE = "jp.ngt.rtm.block.BlockTurnstile";

    private Class classBlockTurnstile;
    private Method blockTurnstileOpenGate;

    public PluginRTM() {}

    public void construct()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void initializeClazz()
    {
        try
        {
            classBlockTurnstile = Class.forName(CLASS_BLOCK_TURNSTILE);
            blockTurnstileOpenGate = classBlockTurnstile.getMethod("openGate", World.class, int.class, int.class, int.class, EntityPlayer.class);
        }
        catch (Exception e)
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
        final String name = stack.getUnlocalizedName();
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
        final Block block = world.getBlockState(blockPos).getBlock();
        ItemStack stack = TileEntityTicketGate.cutTicket(entityPlayer, entityPlayer.getHeldItem(hand));

        if(stack == null)
            return EnumActionResult.FAIL;

        if(classBlockTurnstile != null && classBlockTurnstile.isInstance(block))
        {
            try
            {
                blockTurnstileOpenGate.invoke(block, world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), entityPlayer);
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

    @SubscribeEvent
    public void handlePlayerInteract(PlayerInteractEvent.RightClickBlock evt)
    {
        final IBlockState blockState = evt.getWorld().getBlockState(evt.getPos());
        final Block block = blockState.getBlock();
        if(block.getUnlocalizedName().equals("tile.rtm:turnstile"))
        {
            handleTurnstile(evt.getEntityPlayer(), evt.getWorld(), evt.getPos(), evt.getHand());
        }
    }

}
