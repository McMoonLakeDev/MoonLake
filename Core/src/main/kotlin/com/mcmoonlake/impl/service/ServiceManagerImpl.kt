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

import com.mcmoonlake.api.service.*
import java.util.concurrent.ConcurrentHashMap

class ServiceManagerImpl : ServiceManager {

    private val services: MutableMap<Class<out Service>, Service> = ConcurrentHashMap()

    override fun <T: Service> registerService(clazz: Class<T>, service: T): Boolean {
        if((service is ServiceRegistrable && !service.registrable()) || services.containsKey(clazz))
            return false
        return try {
            service.onInitialize()
            services.put(clazz, service) == null
        } catch(e: Exception) {
            e.printStackTrace() // print
            false
        }
    }

    override fun <T: Service> unregisterService(clazz: Class<T>): Boolean {
        if(ServiceAbstractCore::class.java.isInstance(services[clazz]))
            throw ServiceException("待卸载的服务 $clazz 类是核心服务, 不可卸载.")
        return services.remove(clazz).also { service -> service?.onUnload() } != null
    }

    override fun <T: Service> getService(clazz: Class<T>): T = try {
        val service = services[clazz] ?: throw ServiceNotFoundException("获取服务类 $clazz 不存在的服务实现.")
        clazz.cast(service)
    } catch(e: ClassCastException) {
        throw ServiceException(e)
    }

    override fun <T: Service> getServiceSafe(clazz: Class<T>): T? = try {
        getService(clazz)
    } catch(e: Exception) {
        null
    }

    override fun <T: Service> hasService(clazz: Class<T>): Boolean
            = getServiceSafe(clazz) != null

    fun shutdown() {
        services.values.forEach { it.onUnload() }
        services.clear()
    }
}
