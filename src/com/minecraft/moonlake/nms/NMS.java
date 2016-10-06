package com.minecraft.moonlake.nms;

import com.minecraft.moonlake.nms.anvil.*;
import org.bukkit.inventory.ItemStack;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class NMS {

    private NMS() {

        // Anvil Window Test Code
        AnvilWindow anvilWindow = new AnvilWindow(null /*Plugin Main*/);

        anvilWindow.setAllowMove(false);
        anvilWindow.onClick(new AnvilWindowEventHandler<AnvilWindowClickEvent>() {
            @Override
            public void onExecute(AnvilWindowClickEvent event) {
                // 监听铁砧窗口点击事件
                if(event.getSlot() == AnvilWindowSlot.OUTPUT) {
                    // 如果点击的槽位是输出口，就是铁砧最右边的 ? + ? = 这个槽位
                    ItemStack itemStack = event.getItemStack();
                    String displayName = itemStack.getItemMeta().getDisplayName();
                    // 获取输出口槽位的物品栈获取显示名称之后发送给玩家
                    event.getPlayer().sendMessage("value: " + displayName);
                }
            }
        });
        anvilWindow.onClose(new AnvilWindowEventHandler<AnvilWindowCloseEvent>() {
            @Override
            public void onExecute(AnvilWindowCloseEvent event) {
                // 监听铁砧窗口关闭事件
                // 释放铁砧窗口
                event.getAnvilWindow().dispose();
            }
        });
    }
}
