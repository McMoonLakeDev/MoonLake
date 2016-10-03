package com.minecraft.moonlake.api.nbt;

import com.minecraft.moonlake.nbt.exception.NBTException;
import com.minecraft.moonlake.nbt.exception.NBTInitializeException;
import com.minecraft.moonlake.nms.packet.PacketFactory;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/10/2.
 */
class NBTBlockExpression implements NBTBlock {

    private Class<?> CLASS_WORLD;
    private Class<?> CLASS_CRAFTWORLD;
    private Class<?> CLASS_TILEENTITY;
    private Class<?> CLASS_BLOCKPOSITION;
    private Class<?> CLASS_NBTTAGCOMPOUND;
    private Method METHOD_GETHANDLE;
    private Method METHOD_GETTILEENTITY;
    private Method METHOD_GETUPDATEPACKET;
    private Method METHOD_A;
    private Method METHOD_SAVE;

    public NBTBlockExpression() throws NBTInitializeException {

        try {

            CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            CLASS_TILEENTITY = PackageType.MINECRAFT_SERVER.getClass("TileEntity");
            CLASS_BLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            CLASS_NBTTAGCOMPOUND = PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");

            METHOD_GETHANDLE = getMethod(CLASS_CRAFTWORLD, "getHandle");
            METHOD_GETTILEENTITY = getMethod(CLASS_WORLD, "getTileEntity", CLASS_BLOCKPOSITION);
            METHOD_GETUPDATEPACKET = getMethod(CLASS_TILEENTITY, "getUpdatePacket");
            METHOD_A = getMethod(CLASS_TILEENTITY, "a", CLASS_NBTTAGCOMPOUND);
            METHOD_SAVE = getMethod(CLASS_TILEENTITY, getServerVersionNumber() <= 8 ? "b" : "save", CLASS_NBTTAGCOMPOUND);
        }
        catch (Exception e) {

            throw new NBTInitializeException("The nbt block reflect raw initialize exception.", e);
        }
    }

    @Override
    public void readBlock(Block block, Object nbtTagCompound) throws NBTException {

        Validate.notNull(block, "The block object is null.");
        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        try {

            Object tileEntity = getTileEntity(block);

            if(tileEntity != null) {

                METHOD_A.invoke(tileEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The read block nbt tag exception.", e);
        }
    }

    @Override
    public void writeBlock(Block block, Object nbtTagCompound) throws NBTException {

        Validate.notNull(block, "The block object is null.");
        Validate.notNull(nbtTagCompound, "The nbt tag object is null.");

        try {

            Object tileEntity = getTileEntity(block);

            if(tileEntity != null) {

                METHOD_SAVE.invoke(tileEntity, nbtTagCompound);
            }
        }
        catch (Exception e) {

            throw new NBTException("The write block nbt tag exception.", e);
        }
    }

    @Override
    public void update(Block block) throws NBTException {

        if(block == null) return;

        try {

            Object tileEntity = getTileEntity(block);

            if(tileEntity != null) {

                Object packet = METHOD_GETUPDATEPACKET.invoke(tileEntity);

                if(packet == null) return;

                List<Player> target = new ArrayList<>();
                int maxDistance = Bukkit.getServer().getViewDistance() * 32;

                for(final Player player : block.getWorld().getPlayers()) {

                    if(player.getLocation().distance(block.getLocation()) < maxDistance) {

                        target.add(player);
                    }
                }
                if(target.size() > 0) {

                    PacketFactory.get().sendPacket(target.toArray(new Player[target.size()]), packet);
                }
            }
        }
        catch (Exception e) {

            throw new NBTException("The update block nbt tag exception.", e);
        }
    }

    @Override
    public Object getTileEntity(Block block) throws NBTException {

        Validate.notNull(block, "The block object is null.");

        try {

            Object nmsWorld = METHOD_GETHANDLE.invoke(block.getWorld());
            Object nmsBlockPosition = instantiateObject(CLASS_BLOCKPOSITION, block.getX(), block.getY(), block.getZ());

            return METHOD_GETTILEENTITY.invoke(nmsWorld, nmsBlockPosition);
        }
        catch (Exception e) {

            throw new NBTException("The get tile entity exception.", e);
        }
    }
}
