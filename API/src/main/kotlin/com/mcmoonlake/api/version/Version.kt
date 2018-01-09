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

package com.mcmoonlake.api.version

import com.mcmoonlake.api.util.ComparisonChain

open class Version(
        val major: Int,
        val minor: Int,
        val build: Int
) : Comparable<Version> {

    open fun getVersion(): String
            = "$major.$minor.$build"

    override fun compareTo(other: Version): Int {
        return ComparisonChain.start()
                .compare(major, other.major)
                .compare(minor, other.minor)
                .compare(build, other.build)
                .result
    }

    override fun equals(other: Any?): Boolean {
        if(other === this)
            return true
        if(other is Version)
            return major == other.major && minor == other.minor && build == other.build
        return false
    }

    override fun hashCode(): Int {
        var result = major
        result = 31 * result + minor
        result = 31 * result + build
        return result
    }

    override fun toString(): String {
        return "Version(major=$major, minor=$minor, build=$build)"
    }
}
