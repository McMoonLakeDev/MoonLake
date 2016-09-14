package com.minecraft.moonlake.player;

import com.minecraft.moonlake.property.ReadOnlyIntegerProperty;

/**
 * Created by MoonLake on 2016/9/14.
 */
public class PlayerExpressionWrapped extends PlayerExpression {

    private final NMSPlayerExpression nmsPlayerExpression;

    public PlayerExpressionWrapped() {

        this.nmsPlayerExpression = new NMSPlayerExpression();
    }

    @Override
    public ReadOnlyIntegerProperty getPing(String player) {

        return nmsPlayerExpression.getPing(player);
    }
}
