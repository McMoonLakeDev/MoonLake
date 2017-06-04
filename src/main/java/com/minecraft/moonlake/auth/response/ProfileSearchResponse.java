/*
 * Copyright (C) 2017 The MoonLake Authors
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


package com.minecraft.moonlake.auth.response;

import com.google.gson.*;
import com.minecraft.moonlake.auth.data.GameProfile;

import java.lang.reflect.Type;

/**
 * <h1>ProfileSearchResponse</h1>
 * 档案搜索响应类
 *
 * @version 1.0
 * @author Month_Light
 * @see MojangBaseResponse
 */
public class ProfileSearchResponse extends MojangBaseResponse {

    private GameProfile[] profiles;

    /**
     * 档案搜索响应类构造函数
     */
    public ProfileSearchResponse() {
    }

    /**
     * 获取此档案搜索响应的游戏档案数组
     *
     * @return 游戏档案数组
     */
    public GameProfile[] getProfiles() {
        return profiles;
    }

    /**
     * <h1>Serializer</h1>
     * 档案搜索响应序列化类
     *
     * @version 1.0
     * @author Month_Light
     * @see JsonDeserializer
     */
    public final static class Serializer implements JsonDeserializer<ProfileSearchResponse> {

        @Override
        public ProfileSearchResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            ProfileSearchResponse response = new ProfileSearchResponse();
            if(jsonElement instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                if(jsonObject.has("error"))
                    response.setError(jsonObject.get("error").getAsString());
                if(jsonObject.has("cause"))
                    response.setCause(jsonObject.get("cause").getAsString());
                if(jsonObject.has("errorMessage"))
                    response.setErrorMessage(jsonObject.get("errorMessage").getAsString());
            } else {
                response.profiles = jsonDeserializationContext.deserialize(jsonElement, GameProfile[].class);
            }
            return response;
        }
    }
}
