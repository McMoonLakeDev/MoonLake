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


package com.minecraft.moonlake.nms.packet;

import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.nms.packet.exception.PacketException;
import com.minecraft.moonlake.nms.packet.wrapped.PacketDataSerializer;
import com.minecraft.moonlake.property.BooleanProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleBooleanProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>PacketPlayOutBookOpen</h1>
 * 数据包输出书本打开（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 */
public class PacketPlayOutBookOpen extends PacketAbstract<PacketPlayOutBookOpen> {

    private final static String CHANNEL = "MC|BOpen";
    private ObjectProperty<ItemStack> book;
    private BooleanProperty onlyPacket;

    /**
     * 数据包输出书本打开构造函数
     *
     * @deprecated 已过时, 请使用 {@link #PacketPlayOutBookOpen(ItemStack, boolean)}
     */
    @Deprecated
    public PacketPlayOutBookOpen() {

        this(null, false);
    }

    /**
     * 数据包输出书本打开构造函数
     *
     * @param itemStack 书本物品栈
     */
    public PacketPlayOutBookOpen(ItemStack itemStack) {

        this(itemStack, false);
    }

    /**
     * 数据包输出书本打开构造函数
     *
     * @param itemStack 书本物品栈
     * @param onlyPacket 是否仅数据包
     */
    public PacketPlayOutBookOpen(ItemStack itemStack, boolean onlyPacket) {

        this.book = new SimpleObjectProperty<>(itemStack);
        this.onlyPacket = new SimpleBooleanProperty(onlyPacket);
    }

    /**
     * 获取此数据包输出书本打开的书本物品栈
     *
     * @return 书本物品栈
     */
    public ObjectProperty<ItemStack> getBook() {

        return book;
    }

    /**
     * 获取此数据包输出书本打开是否仅数据包
     *
     * @return 是否仅为数据包
     */
    public BooleanProperty getOnlyPacket() {

        return onlyPacket;
    }

    @Override
    public void send(Player... players) throws PacketException {

        if(fireEvent(this, players)) return;

        try {

            for(MoonLakePlayer mplayer : PlayerManager.adapter(players)) {

                ItemStack handItemStack = mplayer.getItemInHand();
                mplayer.setItemInHand(book.get());
                // 发送数据包
                new PacketPlayOutCustomPayload(CHANNEL, handlerEnumHand()).send(mplayer);
                // 判断是否需要恢复原来的手中物品
                if(onlyPacket.get())
                    mplayer.setItemInHand(handItemStack);
                // 更新玩家的背包
                mplayer.updateInventory();
            }
        }
        catch (Exception e) {

            throw new PacketException("The nms packet play out book open send exception.", e);
        }
    }

    private static PacketDataSerializer handlerEnumHand() {

        if(Reflect.getServerVersionNumber() <= 8)
            // 1.8 版本不需要这个 EnumHand 的
            return new PacketDataSerializer();

        // 处理 1.9+ 版本打开书本需要枚举手的数据
        // 0 为 MAIN_HAND, 1 为 OFF_HAND
        PacketDataSerializer data = new PacketDataSerializer();
        writeEnumHand(data.getByteBuf(), 0);
        return data;
    }

    private static void writeEnumHand(ByteBuf byteBuf, int i) {
        // 此代码摘自 PacketDataSerializer 的 d(int i) 函数
        while((i & 0xffffff80) != 0) {
            byteBuf.writeByte(i & 0x7f | 0x80);
            i >>>= 7;
        }
        byteBuf.writeByte(i);
    }
}
