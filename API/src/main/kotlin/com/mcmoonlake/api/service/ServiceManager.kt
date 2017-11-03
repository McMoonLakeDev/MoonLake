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

interface ServiceManager {

    fun <T: Service> registerService(clazz: Class<T>, service: T): Boolean

    /**
     * @throws ServiceException If the service is the core.
     */
    @Throws(ServiceException::class)
    fun <T: Service> unregisterService(clazz: Class<T>): Boolean

    /**
     * @throws ServiceException If the service is not available or not registered.
     */
    @Throws(ServiceException::class)
    fun <T: Service> getService(clazz: Class<T>): T

    fun <T: Service> getServiceSafe(clazz: Class<T>): T?

    fun <T: Service> hasService(clazz: Class<T>): Boolean

    /**
     * Called when the [com.mcmoonlake.api.MoonLake] plugin is disabled.
     * This function will unload all services, including the core, do not call.
     */
    fun shutdown()
}
