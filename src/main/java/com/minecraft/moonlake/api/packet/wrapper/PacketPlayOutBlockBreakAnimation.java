/*
 * Copyright (C) 2016 The MoonLake Authors
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

import com.minecraft.moonlake.api.packet.exception.PacketInitializeException;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.property.IntegerProperty;
import com.minecraft.moonlake.property.SimpleIntegerProperty;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * <h1>PacketPlayOutBlockBreakAnimation</h1>
 * 数据包输出方块破坏动画（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutBlockBreakAnimation extends PacketPlayOutBukkitAbstract {

    private final static Class<?> CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION;

    static {

        try {

            CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutBlockBreakAnimation");
        }
        catch (Exception e) {

            throw new PacketInitializeException("The net.minecraft.server packet play out block break animation reflect raw initialize exception.", e);
        }
    }

    private IntegerProperty entityId;
    private BlockPosition.BlockPositionProperty blockPosition;
    private IntegerProperty value;

    public PacketPlayOutBlockBreakAnimation() {

        this(0, BlockPosition.ZERO, 0);
    }

    public PacketPlayOutBlockBreakAnimation(Player player, BlockPosition blockPosition, int value) {

        this(player.getEntityId(), blockPosition, value);
    }

    public PacketPlayOutBlockBreakAnimation(MoonLakePlayer player, BlockPosition blockPosition, int value) {

        this(player.getBukkitPlayer().getEntityId(), blockPosition, value);
    }

    public PacketPlayOutBlockBreakAnimation(int entityId, BlockPosition blockPosition, int value) {

        this.entityId = new SimpleIntegerProperty(entityId);
        this.blockPosition = new BlockPosition.BlockPositionProperty(blockPosition);
        this.value = new SimpleIntegerProperty(value);
    }

    public IntegerProperty entityIdProperty() {

        return entityId;
    }

    public BlockPosition.BlockPositionProperty blockPositionProperty() {

        return blockPosition;
    }

    public IntegerProperty valueProperty() {

        return value;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        BlockPosition blockPosition = blockPositionProperty().get();
        Validate.notNull(blockPosition, "The block position object is null.");

        try {
            // 先用调用 NMS 的 PacketPlayOutBlockBreakAnimation 构造函数, 参数 int, BlockPosition, int
            // 进行反射实例发送
            Object packet = instantiateObject(CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION, entityId.get(), blockPosition.asNMS(), value.get());
            sendPacket(players, packet);
            return true;

        } catch (Exception e) {
            // 如果异常了说明 NMS 的 PacketPlayOutBlockBreakAnimation 构造函数不存在这个参数类型
            // 那么用反射直接设置字段值方式来发送
            try {
                // 判断字段数量等于 3 个的话就是有此方式
                // 这字段分别对应 int, BlockPosition, int 属性
                Object packet = instantiateObject(CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION);
                Object[] values = { entityId.get(), blockPosition.asNMS(), value.get() };
                setFieldAccessibleAndValueSend(players, 3, CLASS_PACKETPLAYOUTBLOCKBREAKANIMATION, packet, values);
                return true;

            } catch (Exception e1) {

            }
        }
        // 否则前面的方式均不支持则返回 false 并抛出不支持运算异常
        return false;
    }
}
