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

package com.mcmoonlake.api.service

import com.mcmoonlake.api.throwGiven
import java.util.concurrent.atomic.AtomicBoolean

abstract class ServiceAbstract : Service {

    /**
     * * If the service initialization error, then the service will not be cached and can not be initialized again.
     * * 如果服务初始化时错误，那么服务不会被缓存以及无法再次初始化.
     */
    private val initialized = AtomicBoolean(false)

    final override fun onInitialize() {
        if(initialized.compareAndSet(false, true)) try {
            onInitialized()
        } catch(e: Exception) {
            e.throwGiven { ServiceException(it.message, it.cause ?: it) }
        }
    }

    final override fun onUnload() {
        if(initialized.compareAndSet(true, false)) try {
            onUnloaded()
        } catch(e: Exception) {
            // e.printStackTrace() // ignore
        }
    }

    protected abstract fun onInitialized()

    protected abstract fun onUnloaded()
}
