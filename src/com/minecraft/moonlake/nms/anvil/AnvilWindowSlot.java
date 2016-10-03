package com.minecraft.moonlake.nms.anvil;

/**
 * Created by MoonLake on 2016/10/3.
 */
public enum AnvilWindowSlot {

    /**
     * 铁砧窗口左输入口
     */
    INPUT_LEFT(0),
    /**
     * 铁砧窗口右输入口
     */
    INPUT_RIGHT(1),
    /**
     * 铁砧窗口输出口
     */
    OUTPUT(2),
    ;

    private int slot;

    AnvilWindowSlot(int slot) {

        this.slot = slot;
    }

    public int getSlot() {

        return slot;
    }
}
