package com.minecraft.moonlake.nms.anvil;

/**
 * <h1>AnvilWindowSlot</h1>
 * 铁砧窗口槽位类型
 *
 * @version 1.0
 * @author Month_Light
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

    /**
     * 铁砧创建槽位类型构造函数
     *
     * @param slot 槽位索引
     */
    AnvilWindowSlot(int slot) {

        this.slot = slot;
    }

    public int getSlot() {

        return slot;
    }

    public static AnvilWindowSlot fromRawSlot(int rawSlot) {

        switch (rawSlot) {

            case 0:
                return INPUT_LEFT;
            case 1:
                return INPUT_RIGHT;
            case 2:
                return OUTPUT;
            default:
                return null;
        }
    }
}
