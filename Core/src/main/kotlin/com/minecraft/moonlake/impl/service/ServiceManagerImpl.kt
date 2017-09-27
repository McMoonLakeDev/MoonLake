/*
 * Copyright (C) 2016-2017 The MoonLake (mcmoonlake@hotmail.com)
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

package com.minecraft.moonlake.impl.service

import com.minecraft.moonlake.api.service.Service
import com.minecraft.moonlake.api.service.ServiceException
import com.minecraft.moonlake.api.service.ServiceManager
import com.minecraft.moonlake.api.service.ServiceRegistrable
import java.util.concurrent.ConcurrentHashMap

class ServiceManagerImpl : ServiceManager {

    private val services: MutableMap<Class<out Service>, Service> = ConcurrentHashMap()

    override fun <T : Service> registerService(clazz: Class<T>, service: T): Boolean {
        if((service is ServiceRegistrable && !service.registrable()) || services.containsKey(clazz))
            return false
        return (services.put(clazz, service) == null).also { if(it) service.onInitialize() }
    }

    override fun <T : Service> unregisterService(clazz: Class<T>): Boolean {
        if(ServiceCoreAbstract::class.java.isInstance(services[clazz]))
            throw ServiceException("待卸载的服务 $clazz 类是核心服务, 不可卸载.")
        return services.remove(clazz).also { service -> service?.onUnload() } != null
    }

    override fun <T : Service> getService(clazz: Class<T>): T = try {
        clazz.cast(services[clazz])
    } catch(e: Exception) {
        throw ServiceException(e)
    }

    override fun <T : Service> getServiceSafe(clazz: Class<T>): T? = try {
        getService(clazz)
    } catch(e: Exception) {
        null
    }

    override fun <T : Service> hasService(clazz: Class<T>): Boolean
            = getServiceSafe(clazz) != null
}
