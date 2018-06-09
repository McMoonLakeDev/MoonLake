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

package com.mcmoonlake.impl.listener

import com.mcmoonlake.api.MoonLake
import com.mcmoonlake.api.anvil.AnvilWindows
import com.mcmoonlake.api.depend.DependPlugins
import com.mcmoonlake.api.event.MoonLakeListener
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.server.PluginDisableEvent

class GlobalListeners : MoonLakeListener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onPluginDisable(event: PluginDisableEvent) {
        if(event.plugin is MoonLake) {
            AnvilWindows.releaseAll()
            DependPlugins.unregisterAll()
        } else {
            DependPlugins.unregister(event.plugin.name)
        }
    }
}
