package com.minecraft.moonlake.nms;

/**
 * Created by MoonLake on 2016/6/23.
 */
public class NMS {

    private NMS() {

        /*
        AnvilWindow anvilWindow = new AnvilWindow();
        anvilWindow.onClick(new AnvilWindowEventHandle<AnvilWindowClickEvent>() {
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
        anvilWindow.onClose(new AnvilWindowEventHandle<AnvilWindowCloseEvent>() {
            @Override
            public void onExecute(AnvilWindowCloseEvent event) {
                // 监听铁砧窗口关闭事件
                // 释放铁砧窗口
                event.getAnvilWindow().dispose();
            }
        });
        */
    }
}
