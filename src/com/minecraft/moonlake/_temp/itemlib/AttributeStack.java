package com.minecraft.moonlake._temp.itemlib;

import com.minecraft.moonlake._temp.itemlib.Itemlib.AttributeType;

/**
 * Created by MoonLake on 2016/5/1.
 * @version 1.0
 * @author Month_Light
 */
public class AttributeStack {

    private AttributeType type;
    private AttributeType.Slot slot;
    private double amount;
    private int operation;

    public AttributeStack(AttributeType type, double amount, int operation) {
        this(type, null, amount, operation);
    }

    public AttributeStack(AttributeType type, AttributeType.Slot slot, double amount, int operation) {
        this.type = type;
        this.slot = slot;
    }

    public AttributeType getType() {
        return type;
    }

    public AttributeType.Slot getSlot() {
        return slot;
    }

    public double getAmount() {
        return amount;
    }

    public int getOperation() {
        return operation;
    }

    /**
     * 获取此特殊属性是否百分比形式
     *
     * @return 百分比
     */
    public boolean isPercent() {
        return getOperation() == 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof AttributeStack) {

            AttributeStack attributeStack = (AttributeStack)obj;

            return attributeStack.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 29 * type.hashCode() + Double.hashCode(amount) + Integer.hashCode(operation);
        return result;
    }
}
