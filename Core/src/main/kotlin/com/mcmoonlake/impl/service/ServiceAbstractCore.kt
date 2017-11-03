/*
 * Copyright (C) 2016-Present The MoonLake (mcmoonlake@hotmail.com)
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

package com.mcmoonlake.impl.service

import com.mcmoonlake.api.service.ServiceAbstract

/**
 * 如果继承此服务，那么此服务不可卸载。只有当插件被 disable 时才会释放。
 * 注：此服务用于 MoonLake 内的核心服务，请勿依赖此模块。随时将会改动。
 */
abstract class ServiceAbstractCore : ServiceAbstract() {
}
