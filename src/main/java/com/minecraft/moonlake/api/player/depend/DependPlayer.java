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


package com.minecraft.moonlake.api.player.depend;

/**
 * <h1>DependPlayer</h1>
 * 依赖玩家接口类（详细doc待补充...）
 *
 * @version 1.0
 * @author Month_Light
 * @see EconomyPlayer
 * @see EconomyVaultPlayer
 * @see PermissionsExPlayer
 * @see WorldEditPlayer
 */
public interface DependPlayer extends EconomyPlayer, EconomyVaultPlayer, PermissionsExPlayer, WorldEditPlayer {
}
