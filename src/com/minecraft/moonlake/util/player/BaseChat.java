package com.minecraft.moonlake.util.player;

import net.md_5.bungee.api.chat.BaseComponent;

/**
 * Created by MoonLake on 2016/4/29.
 */
public class BaseChat extends BaseComponent {

    public BaseChat() {

    }

    @Override
    public BaseComponent duplicate() {
        return new BaseChat();
    }
}
