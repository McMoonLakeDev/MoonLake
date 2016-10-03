package com.minecraft.moonlake.nms.anvil;

import com.minecraft.moonlake.nms.exception.NMSException;
import org.bukkit.entity.Player;

import static com.minecraft.moonlake.reflect.Reflect.*;

/**
 * Created by MoonLake on 2016/10/3.
 */
class AnvilWindowReflect {

    private Class<?> CLASS_ANVILWINDOW;

    private AnvilWindowReflect() throws NMSException {

        try {

            CLASS_ANVILWINDOW = Class.forName(AnvilWindow.class.getName() + "_" + getServerVersion());
        }
        catch (Exception e) {

            throw new NMSException("The nms anvil window reflect raw initialize exception.", e);
        }
    }

    public void openAnvil(Player player) {

    }
}
