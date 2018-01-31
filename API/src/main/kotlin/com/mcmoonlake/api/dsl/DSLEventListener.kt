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

@file:JvmName("DSLEventListener")

package com.mcmoonlake.api.dsl

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

data class Score(val plugin: Plugin) {

    inline fun <reified T: Event> event(priority: EventPriority = EventPriority.NORMAL, noinline block: T.() -> Unit)
            = plugin.server.pluginManager.registerEvent(T::class.java, object: Listener {}, priority, { _, event -> block(event as T) }, plugin)
}

fun buildEventListener(plugin: Plugin, block: Score.() -> Unit)
        = Score(plugin).block()
