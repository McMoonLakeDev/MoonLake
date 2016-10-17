/*
 * Copyright (C) 2016 The MoonLake Authors
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
 
 
package com.minecraft.moonlake.api.fancy;

import com.google.gson.stream.JsonWriter;
import com.minecraft.moonlake.json.JsonRepresentedObject;
import com.minecraft.moonlake.manager.ItemManager;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.minecraft.moonlake.api.fancy.TextualComponent.rawText;

/**
 * <h1>FancyMessage Implement Class</h1>
 * 花式消息接口实现类
 *
 * @version 1.0
 * @author Month_Light
 */
class FancyMessageExpression implements FancyMessage {

    private BooleanProperty dirty;
    private StringProperty jsonString;
    private ObjectProperty<List<FancyMessagePart>> partList;

    /**
     * 花式消息实现类构造函数
     *
     * @deprecated 已过时，请使用 {@link #FancyMessageExpression(String)} 或 {@link #FancyMessageExpression(TextualComponent)}
     * @see #FancyMessageExpression(String)
     * @see #FancyMessageExpression(TextualComponent)
     */
    @Deprecated
    public FancyMessageExpression() {

        this((TextualComponent) null);
    }

    /**
     * 花式消息实现类构造函数
     *
     * @param text 内容
     */
    public FancyMessageExpression(String text) {

        this(rawText(text));
    }

    /**
     * 花式消息实现类构造函数
     *
     * @param text 文本组件
     */
    public FancyMessageExpression(TextualComponent text) {

        this.partList = new SimpleObjectProperty<>(new ArrayList<>());
        this.partList.get().add(new FancyMessagePart(text));
        this.jsonString = new SimpleStringProperty(null);
        this.dirty = new SimpleBooleanProperty(false);
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {

        if (partList.get().size() == 1) {

            getLaster().writeJson(jsonWriter);
        }
        else {

            jsonWriter.beginObject().name("text").value("").name("extra").beginArray();

            for (final FancyMessagePart part : this) {

                part.writeJson(jsonWriter);
            }
            jsonWriter.endArray().endObject();
        }
    }

    @Override
    public Iterator<FancyMessagePart> iterator() {

        return partList.get().iterator();
    }

    @Override
    public FancyMessage text(String text) {

        return text(rawText(text));
    }

    @Override
    public FancyMessage text(TextualComponent text) {

        FancyMessagePart laster = getLaster();

        laster.text.set(text);

        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage color(ChatColor color) {

        Validate.isTrue(color.isColor(), "The fancy message chatcolor type not is color.");

        getLaster().color.set(color);

        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage style(FancyMessageStyle... style) {

        Validate.notNull(style, "The fancy message style object is null.");

        getLaster().styles.get().addAll(Arrays.asList(style));

        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage file(String path) {

        Validate.notNull(path, "The fancy message file path object is null.");

        onClick("open_file", path);

        return this;
    }

    @Override
    public FancyMessage link(String url) {

        Validate.notNull(url, "The fancy message url object is null.");

        onClick("open_url", url);

        return this;
    }

    @Override
    public FancyMessage suggest(String command) {

        Validate.notNull(command, "The fancy message suggest command object is null.");

        onClick("suggest_command", command);

        return this;
    }

    @Override
    public FancyMessage insert(String command) {

        Validate.notNull(command, "The fancy message insert command object is null.");

        getLaster().insertion.set(command);

        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage command(String command) {

        Validate.notNull(command, "The fancy message run command object is null.");

        onClick("run_command", command);

        return this;
    }

    @Override
    public FancyMessage tooltip(String text) {

        Validate.notNull(text, "The tooltip text object is null.");

        onHover("show_text", new FancyJsonString(text));

        return this;
    }

    @Override
    public FancyMessage tooltip(String... texts) {

        Validate.notNull(texts, "The tooltip text object is null.");

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < texts.length; i++) {

            builder.append(texts[i]);

            if (i != texts.length - 1) {

                builder.append('\n');
            }
        }
        tooltip(builder.toString());

        return this;
    }

    @Override
    public FancyMessage tooltip(ItemStack itemStack) {

        Validate.notNull(itemStack, "The tooltip itemstack object is null.");

        String tag = ItemManager.getTagString(itemStack);

        onHover("show_item", new FancyJsonString(

                StringUtil.format("{id:\"minecraft:%1$s\",Damage:%2$s,Count:%3$s,tag:%4$s}",
                                  itemStack.getType().name().toLowerCase(),
                                  itemStack.getDurability(),
                                  itemStack.getAmount(),
                                  tag == null ? "{}" : tag)));
        return this;
    }

    @Override
    public FancyMessage tooltipAchievement(String name) {

        Validate.notNull(name, "The tooltip achievement name object is null.");

        onHover("show_achievement", new FancyJsonString("achievement." + name));

        return this;
    }

    @Override
    public FancyMessage translationReplacements(String... replacements) {

        Validate.notNull(replacements, "The translation replacements object is null.");

        for (final String str : replacements) {

            getLaster().translationReplacements.get().add(new FancyJsonString(str));
        }
        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage translationReplacements(FancyMessage... replacements) {

        Validate.notNull(replacements, "The translation replacements object is null.");

        for (final FancyMessage fancyMessage : replacements) {

            getLaster().translationReplacements.get().add(fancyMessage);
        }
        dirty.set(true);

        return this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public FancyMessage then() {

        Validate.isTrue(getLaster().hasText(), "The fancy message part laster not has text.");

        partList.get().add(new FancyMessagePart());
        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage then(String text) {

        return then(rawText(text));
    }

    @Override
    public FancyMessage then(TextualComponent text) {

        Validate.isTrue(getLaster().hasText(), "The fancy message part laster not has text.");

        partList.get().add(new FancyMessagePart(text));
        dirty.set(true);

        return this;
    }

    @Override
    public FancyMessage build() {

        toJsonString();

        return this;
    }

    @Override
    public String toJsonString() {

        if (!dirty.get() && jsonString.get() != null) {

            return jsonString.get();
        }
        StringWriter string = new StringWriter();
        JsonWriter json = new JsonWriter(string);

        try {

            writeJson(json);
            json.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        jsonString.set(string.toString());
        dirty.set(false);

        return jsonString.get();
    }

    @Override
    public void send(Player player) {

        Validate.notNull(player, "The player object is null.");

        if(jsonString.get() == null) {

            build();
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw " + player.getName() + " " + jsonString.get());
    }

    @Override
    public void send(String player) {

        send(PlayerManager.fromName(player));
    }

    @Override
    public void sendAll() {

        if(jsonString.get() == null) {

            build();
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw @a " + jsonString.get());    // @a = All Player
    }

    protected FancyMessagePart getLaster() {

        return partList.get().get(partList.get().size() - 1);
    }

    private void onClick(String name, String data) {

        FancyMessagePart laster = getLaster();

        laster.clickAction.set(name);
        laster.clickActionValue.set(data);

        dirty.set(true);
    }

    private void onHover(String name, JsonRepresentedObject data) {

        FancyMessagePart laster = getLaster();

        laster.hoverAction.set(name);
        laster.hoverActionValue.set(data);

        dirty.set(true);
    }
}
