/*
 * Copyright (C) 2016 The MoonLake Authors
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
 
 
package com.minecraft.moonlake.api.anvil;

import com.minecraft.moonlake.api.VersionAdapter;
import com.minecraft.moonlake.api.utility.MinecraftBukkitVersion;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * <h1>AnvilWindowExpression_v1_8_R3</h1>
 * 铁砧窗口 v1.8-R3 版容器类
 *
 * @version 2.0
 * @author Month_Light
 */
class AnvilWindowExpression_v1_8_R3 extends ContainerAnvil implements VersionAdapter.MinecraftBukkit {

    private final Player owner;
    private final AnvilWindowExpression anvilWindow;

    /**
     * 铁砧窗口 v1.8-R3 版容器类构造函数
     *
     * @param owner 拥有者
     * @param anvilWindow 铁砧窗口
     */
    public AnvilWindowExpression_v1_8_R3(Player owner, AnvilWindowExpression anvilWindow) {

        super(((CraftPlayer) owner).getHandle().inventory, ((CraftPlayer) owner).getHandle().world, BlockPosition.ZERO, ((CraftPlayer) owner).getHandle());

        this.owner = owner;
        this.anvilWindow = anvilWindow;
    }

    @Override
    public boolean a(EntityHuman entityhuman) {

        return true;
    }

    @Override
    public void a(String s) {

        String input = AnvilWindowReflect.get().callAnvilInputEvent(owner, anvilWindow, s);
        if(input != null)
            super.a(input);
    }

    @Override
    public MinecraftBukkitVersion mcBukkitVersion() {

        return MinecraftBukkitVersion.V1_8_R3;
    }
}
