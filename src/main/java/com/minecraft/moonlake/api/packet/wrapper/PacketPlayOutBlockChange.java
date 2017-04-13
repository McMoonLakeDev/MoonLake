/*
 * Copyright (C) 2017 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.api.packet.wrapper;

import com.minecraft.moonlake.api.packet.Packet;
import com.minecraft.moonlake.api.packet.PacketPlayOut;
import com.minecraft.moonlake.api.packet.PacketPlayOutBukkit;
import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.api.utility.MinecraftReflection;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutBlockChange</h1>
 * 数据包输出方块改变（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see Packet
 * @see PacketPlayOut
 * @see PacketPlayOutBukkit
 */
public class PacketPlayOutBlockChange extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTBLOCKCHANGE;
    private final static Class<?> CLASS_BLOCKPOSITION;
    private final static Class<?> CLASS_CRAFTWORLD;
    private final static Class<?> CLASS_WORLD;
    private final static Method METHOD_GETHANDLER;
    private final static Method METHOD_GETTYPE;

    static {

        try {

            CLASS_PACKETPLAYOUTBLOCKCHANGE = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutBlockChange");
            CLASS_BLOCKPOSITION = PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            CLASS_CRAFTWORLD = PackageType.CRAFTBUKKIT.getClass("CraftWorld");
            CLASS_WORLD = PackageType.MINECRAFT_SERVER.getClass("World");
            METHOD_GETHANDLER = getMethod(CLASS_CRAFTWORLD, "getHandle");
            METHOD_GETTYPE = getMethod(CLASS_WORLD, "getType", CLASS_BLOCKPOSITION);
        }
        catch (Exception e) {

            throw new PacketInitializeException("The nms packet play out block change reflect raw initialize exception.", e);
        }
    }

    private ObjectProperty<World> world;
    private BlockPosition.BlockPositionProperty blockPosition;

    /**
     * 数据包输出方块改变构造函数
     */
    public PacketPlayOutBlockChange() {

        this(null, BlockPosition.ZERO);
    }

    /**
     * 数据包输出方块改变构造函数
     *
     * @param world 世界
     * @param blockPosition 方块位置
     */
    public PacketPlayOutBlockChange(World world, BlockPosition blockPosition) {

        this.world = new SimpleObjectProperty<>(world);
        this.blockPosition = new BlockPosition.BlockPositionProperty(blockPosition);
    }

    @Override
    public Class<?> getPacketClass() {

        return CLASS_PACKETPLAYOUTBLOCKCHANGE;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        // 触发事件判断如果为 true 则阻止发送
        if(super.fireEvent(this, players))
            return true;

        Validate.notNull(world.get(), "The world object is null.");
        Validate.notNull(blockPosition.get(), "The block position object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutBlockChange 构造函数, 参数 World, BlockPosition
            // 进行反射实例发送
            Object nmsWorld = METHOD_GETHANDLER.invoke(world.get());
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTBLOCKCHANGE, nmsWorld, blockPosition.get().asNMS());
            MinecraftReflection.sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutBlockChange 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 2 个的话就是有此方式
                // 这两个字段分别对应 BlockPosition, IBlockData 的 2 个属性
                Object blockData = METHOD_GETTYPE.invoke(blockPosition.get());
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTBLOCKCHANGE);
                Object[] values = { blockPosition.get(), blockData };
                setFieldAccessibleAndValueSend(players, 2, CLASS_PACKETPLAYOUTBLOCKCHANGE, packet, values);
                return true;

            } catch (Exception e1) {
            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
