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
import com.minecraft.moonlake.auth.data.ProfileHistory;

import java.lang.reflect.Type;

/**
 * <h1>ProfileHistoryResponse</h1>
 * 档案历史记录响应类
 *
 * @version 1.0
 * @author Month_Light
 * @see MojangBaseResponse
 */
public class ProfileHistoryResponse extends MojangBaseResponse {

    private ProfileHistory[] histories;

    /**
     * 档案历史记录响应类构造函数
     *
     * @param histories 档案历史数组
     */
    public ProfileHistoryResponse(ProfileHistory[] histories) {
        this.histories = histories;
    }

    /**
     * 获取此档案历史记录响应的档案历史记录数组
     *
     * @return 档案历史记录数组
     */
    public ProfileHistory[] getHistories() {
        return histories;
    }

    /**
     * <h1>Serializer</h1>
     * 档案历史记录响应序列化类
     *
     * @version 1.0
     * @author Month_Light
     * @see JsonDeserializer
     */
    public final static class Serializer implements JsonDeserializer<ProfileHistoryResponse> {

        @Override
        public ProfileHistoryResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if(jsonElement instanceof JsonArray) {
                JsonArray jsonArray = (JsonArray) jsonElement;
                ProfileHistory[] histories = new ProfileHistory[jsonArray.size()];
                for(int i = 0; i < histories.length; i++) {
                    JsonObject history = jsonArray.get(i).getAsJsonObject();
                    String name = history.get("name").getAsString();
                    long changeToAt = history.has("changedToAt") ? history.get("changedToAt").getAsLong() : -1L;
                    histories[i] = new ProfileHistory(name, changeToAt);
                }
                return new ProfileHistoryResponse(histories);
            }
            return null;
        }
    }
}
