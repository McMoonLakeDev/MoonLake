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

import com.minecraft.moonlake.MoonLakeAPI;
import com.minecraft.moonlake.api.player.MoonLakePlayer;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.property.BooleanProperty;
import com.minecraft.moonlake.property.ObjectProperty;
import com.minecraft.moonlake.property.SimpleBooleanProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.reflect.Reflect;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * <h1>PacketPlayOutBookOpen</h1>
 * 数据包输出书本打开（详细doc待补充...）
 *
 * @version 2.0
 * @author Month_Light
 */
public class PacketPlayOutBookOpen extends PacketPlayOutBukkitAbstract {

    private final static String CHANNEL = "MC|BOpen";
    private ObjectProperty<ItemStack> book;
    private BooleanProperty onlyPacket;

    public PacketPlayOutBookOpen() {

        this(null, false);
    }

    public PacketPlayOutBookOpen(ItemStack itemStack) {

        this(itemStack, false);
    }

    public PacketPlayOutBookOpen(ItemStack itemStack, boolean onlyPacket) {

        this.book = new SimpleObjectProperty<>(itemStack);
        this.onlyPacket = new SimpleBooleanProperty(onlyPacket);
    }

    public ObjectProperty<ItemStack> bookProperty() {

        return book;
    }

    public BooleanProperty onlyPacketProperty() {

        return onlyPacket;
    }

    @Override
    protected boolean sendPacket(Player... players) throws Exception {

        ItemStack bookItemStack = bookProperty().get();
        Validate.notNull(bookItemStack, "The itemstack object is null.");
        Validate.isTrue(MoonLakeAPI.getItemLibrary().isWrittenBook(bookItemStack), "The itemstack type not is 'WRITTEN_BOOK'.");

        PacketPlayOutCustomPayload ppocp = new PacketPlayOutCustomPayload();
        ppocp.channelProperty().set(CHANNEL);

        if(Reflect.getServerVersionNumber() >= 9)
            // 处理 1.9+ 版本打开书本需要枚举手的数据
            // 0 为 MAIN_HAND, 1 为 OFF_HAND
            ppocp.dataProperty().byteBufProperty().get().writeByte(0);

        for(MoonLakePlayer moonLakePlayer : PlayerManager.adapter(players)) {
            // 将需要发送的玩家转换成 MoonLakePlayer 以更好的操作
            ItemStack handItemStack = moonLakePlayer.getItemInHand();
            moonLakePlayer.setItemInHand(bookItemStack);
            // 发送数据包
            ppocp.send(moonLakePlayer.getBukkitPlayer());
            // 判断是否需要恢复原来的手中物品
            if(onlyPacket.get())
                moonLakePlayer.setItemInHand(handItemStack);
            // 更新玩家的背包
            moonLakePlayer.updateInventory();
        }

        return true;
    }
}
