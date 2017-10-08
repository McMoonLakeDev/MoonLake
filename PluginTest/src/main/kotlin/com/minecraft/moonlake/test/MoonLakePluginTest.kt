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

package com.minecraft.moonlake.test

import com.minecraft.moonlake.api.*
import com.minecraft.moonlake.api.anvil.AnvilWindowSlot
import com.minecraft.moonlake.api.attribute.AttributeType
import com.minecraft.moonlake.api.attribute.Operation
import com.minecraft.moonlake.api.attribute.Slot
import com.minecraft.moonlake.api.chat.*
import com.minecraft.moonlake.api.depend.DependPlaceholderAPI
import com.minecraft.moonlake.api.depend.DependPlugins
import com.minecraft.moonlake.api.depend.DependVaultEconomy
import com.minecraft.moonlake.api.depend.DependWorldEdit
import com.minecraft.moonlake.api.effect.EffectBase
import com.minecraft.moonlake.api.effect.EffectType
import com.minecraft.moonlake.api.event.MoonLakeListener
import com.minecraft.moonlake.api.item.Enchantment
import com.minecraft.moonlake.api.item.ItemBuilder
import com.minecraft.moonlake.api.item.ItemCooldowns
import com.minecraft.moonlake.api.item.Pattern
import com.minecraft.moonlake.api.nbt.NBTFactory
import com.minecraft.moonlake.api.packet.*
import com.minecraft.moonlake.api.particle.Particle
import com.minecraft.moonlake.api.task.MoonLakeRunnable
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.entity.Pig
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class MoonLakePluginTest : JavaPlugin() {

    override fun onLoad() {
    }

    override fun onEnable() {
        this.logger.info("月色之湖核心 API 测试插件 v${description.version} 成功加载.")

        object: MoonLakeListener {
            @EventHandler
            fun onCommand(event: PlayerCommandPreprocessEvent) {
                if(event.message == "/nbt") {
                    val itemStack = event.player.itemInHand
                    val tag = NBTFactory.readStackTag(itemStack)
                    event.player.sendMessage(tag.toString())
                }
                if(event.message == "/nbt w") {
                    val itemStack = ItemStack(Material.DIAMOND_SWORD)
                    val tag = NBTFactory.readStackTagSafe(itemStack)
                    tag.putBoolean("Unbreakable", true) // 不可破坏
                    NBTFactory.writeStackTag(itemStack, tag)
                    event.player.itemInHand = itemStack
                }
                if(event.message == "/ib") {
                    var itemStack = ItemBuilder.of(Material.IRON_SWORD)
                            .setLocalizedName("tile.tnt.name") // 本地化名称
                            .setCanDestroy(Material.DIAMOND_ORE) // 只能破坏钻石矿
                            .setCanPlaceOn(Material.STONE) // 只能放置在石头上
                            .addEnchant(Enchantment.DAMAGE, 5) // 附魔锋利5
                            .setUnbreakable(true) // 不可破坏
                            .addAttribute(AttributeType.ATTACK_DAMAGE, Operation.MULTIPLY, Slot.MAIN_HAND, .05) // 攻击增加 5%
                            .addAttribute(AttributeType.ATTACK_SPEED, Operation.MULTIPLY, Slot.MAIN_HAND, .1) // 攻击速度增加 10%
                            .addLore("标签属性", "标签属性") // 标签属性
                            .clearEnchant()
                            .addFlag(ItemFlag.HIDE_UNBREAKABLE) // 隐藏不可破坏属性
                            .build()

                    itemStack = ItemBuilder.of(itemStack)
                            .removeFlag(ItemFlag.HIDE_UNBREAKABLE)
                            .build()

                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/ib potion") {
                    val itemStack = ItemBuilder.of(Material.POTION)
                            .addPotionEffect(EffectType.SPEED, 100, 0)
                            .addPotionEffect(EffectType.JUMP, 100, 0)
                            .setPotionColor(Color.fromRGB(0, 0, 0))
                            .addLore("标签属性")
                            .setDisplayName("233")
                            .addEnchant(Enchantment.DAMAGE, 1)
                            .build()
                    event.player.inventory.addItem(itemStack)
                    event.player.toMoonLakePlayer().send(ChatComponentFancy("物品展示: ").then("[ITEM]").tooltipItem(itemStack))
                }
                if(event.message == "/ib firework") {
                    val itemStack = ItemBuilder.of(Material.FIREWORK)
                            .addFireworkEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).build())
                            .addLore("233")
                            .build()
                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/dp we") {
                    val region = DependPlugins.of(DependWorldEdit::class.java).getSelection(event.player)
                    if(region == null)
                        event.player.sendMessage("未使用 WorldEdit 选中区域.")
                    else
                        event.player.sendMessage(region.toString())
                }
                if(event.message == "/dp pipa") {
                    val value = DependPlugins.of(DependPlaceholderAPI::class.java).setRelationalPlaceholders(event.player, Bukkit.getPlayer("Month_Light"), "233")
                    event.player.sendMessage(value)
                }
                if(event.message == "/dp eco") {
                    DependVaultEconomy::class.java.useDependSafe {
                        if(it == null) {
                            event.player.sendMessage("依赖插件不存在, 无法使用功能.")
                            return@useDependSafe
                        }
                        event.player.sendMessage("当前金币 ${it.format(it.getBalance(event.player))} 金币.")
                        event.player.sendMessage("并扣掉你 ${it.format(10.0)} 金币: ${it.withdraw(event.player, 10.0)}")
                    }
                }
                if(event.message == "/nbt rc") {

                    Material.IRON_SWORD.newItemBuilder()
                            .setDisplayName("显示名称")
                            .addEnchant(Enchantment.DAMAGE, 5)
                            .build()
                            .readTagSafe { event.player.sendMessage(it.toString()) }
                }
                if(event.message == "/nbt player") {
                    event.player.readTag {
                        event.player.sendMessage(it.toString())
                        it.putShort("Health", 10) // 修改生命值为 10
                        event.player.writeTag(it)
                    }
                }
                if(event.message == "/nbt etarget") {
                    val pig = event.player.toMoonLakePlayer().getLivingTarget(Pig::class.java, 5.0)
                    if(pig == null)
                        event.player.sendMessage("范围 5.0 内未找到猪.")
                    else
                        pig.readTag { event.player.sendMessage(it.toString()) }
                }
                if(event.message == "/ib segg") {
                    val zombie = event.player.world.spawn(event.player.location, Zombie::class.java)
                    zombie.customName = "Zombie"
                    zombie.isCustomNameVisible = true
                    zombie.equipment.itemInHand = ItemStack(Material.DIAMOND_SWORD)
                    zombie.equipment.helmet = ItemStack(Material.IRON_HELMET)
                    zombie.remove()

                    val spawnEgg = Material.MONSTER_EGG.newItemBuilder()
                            .setSpawnEggType(zombie)
                            .build()
                    event.player.inventory.addItem(spawnEgg)
                }
                if(event.message == "/ib potionbase") {
                    val itemStack = Material.POTION.newItemBuilder()
                            .setPotionBase(EffectBase.瞬间治疗药水增强版)
                            .setDisplayName("&6治疗药剂".toColor())
                            .build()
                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/anvil") {
                    val anvilWindow = this@MoonLakePluginTest.newAnvilWindow()
                    anvilWindow.handleOpen { it.player.sendMessage("anvil open") }
                    anvilWindow.handleInput { it.player.sendMessage("anvil input -> ${it.input}") }
                    anvilWindow.handleClick { it.player.sendMessage("anvil click -> ${it.clickSlot}") }
                    anvilWindow.handleClose { it.player.sendMessage("anvil close") }
                    anvilWindow.isAllowMove = true
                    anvilWindow.open(event.player)
                    anvilWindow.setItem(AnvilWindowSlot.INPUT_LEFT, ItemStack(Material.NAME_TAG))
                }
                if(event.message == "/packet chat") {
                    val packet = PacketOutChat(ChatComponentFancy("聊天花式组件").color(ChatColor.RED).withUnderlined().withBold().build())
                    packet.action = ChatAction.CHAT
                    packet.send(event.player)
                    println(packet)
                }
                if(event.message == "/packet particle") {
                    runTaskTimerAsync(object: MoonLakeRunnable() {
                        var life = 0
                        override fun run() {
                            if(++life / 20 >= 10) {
                                cancel()
                                return
                            }
                            var dir = event.player.location.direction
                            dir = dir.add(dir.multiply(0.1)).setY(dir.y)
                            Particle.FLAME.display(dir, 0f, event.player.location, 32.0)
                        }
                    }, 0L, 1L)
                }
                if(event.message == "/packet bopen") {
                    val itemStack = Material.WRITTEN_BOOK.newItemStack()
                    val packet = PacketOutPayload("MC|BOpen", PacketBuffer().writeByte(0))
                    event.player.itemInHand = itemStack
                    packet.send(event.player)
                }
                if(event.message == "/packet title") {
                    val packetTitle = PacketOutTitle(PacketOutTitle.Action.主标题, ChatComponentText("主标题"))
                    val packetSubTitle = PacketOutTitle(PacketOutTitle.Action.子标题, ChatComponentText("子标题"))
                    val packetTimes = PacketOutTitle(30, 60, 30)
                    packetTimes.send(event.player)
                    packetSubTitle.send(event.player)
                    packetTitle.send(event.player)
                }
                if(event.message == "/cs raw") {
                    val component = ChatSerializer.fromRaw("&a&n你好&6&n世界&f!!!")
                    val xd = ChatComponentText(" XD ")
                    xd.style.setHoverEvent(ChatHoverEvent(ChatHoverEvent.Action.SHOW_TEXT, ChatComponentText("你知道的太多了2333")))
                    component.append(xd)
                    event.player.toMoonLakePlayer().send(component)
                    println(component)
                }
                if(event.message == "/packetin chat") {
                    val packet = PacketInChat("我没有，我不是")
                    packet.receive(event.player)
                }
                if(event.message == "/ib skull") {
                    val itemStack = Material.SKULL_ITEM.newItemBuilder(1, 3)
                            .setSkullTexture("eyJ0aW1lc3RhbXAiOjE1MDY5MzExMzczNTEsInByb2ZpbGVJZCI6ImU5NDhmMGIzYzliZTQ5MDlhMTc2ZjEzNzIwZDNiZTRjIiwicHJvZmlsZU5hbWUiOiJNb250aF9MaWdodCIsInRleHR1cmVzIjp7IlNLSU4iOnsibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn0sInVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM5MThhNzk5YTI4ZjQ0ZGZhOWQ0OWNiYzdiM2I2M2ZjMDE5MmYzYzU1ZDAyYzI1ODIwMzk2YTUwYjRkZmM0In19fQ==")
                            .getSkullTexture { _, value -> event.player.sendMessage("value -> $value") }
                            .build()
                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/nbt stream") {
                    val file = File(dataFolder.parentFile, "player.dat")
                    event.player.readTag {
                        event.player.sendMessage(it.toString())
                        NBTFactory.writeDataCompoundFile(it, file)
                        NBTFactory.writeDataCompoundFile(it, File(file.parentFile, "player-un.dat"), false) // uncompressed
                    }
                    val nbt = NBTFactory.readDataCompoundFile(file)
                    println(nbt)
                }
                if(event.message == "/ib banner") {
                    val itemStack = Material.BANNER.newItemBuilder(1, 15)
                            .addBannerPattern(Pattern(Pattern.Color.PINK, Pattern.Type.CIRCLE_MIDDLE))
                            .addBannerPattern(Pattern(Pattern.Color.WHITE, Pattern.Type.FLOWER))
                            .addBannerPattern(Pattern(Pattern.Color.PINK, Pattern.Type.TRIANGLE_TOP))
                            .addBannerPattern(Pattern(Pattern.Color.WHITE, Pattern.Type.CROSS))
                            .addBannerPattern(Pattern(Pattern.Color.PINK, Pattern.Type.CURLY_BORDER))
                            .addBannerPattern(Pattern(Pattern.Color.WHITE, Pattern.Type.TRIANGLES_BOTTOM))
                            .build()
                    event.player.inventory.addItem(itemStack)
                }
                if(event.message == "/ic set") {
                    val itemStack = event.player.itemInHand
                    ItemCooldowns.set(event.player, itemStack.type, 10 * 20)
                }
                if(event.message == "/ic get") {
                    val itemStack = event.player.itemInHand
                    val ticks = ItemCooldowns.get(event.player, itemStack.type)
                    event.player.sendMessage("冷却时间: $ticks")
                }
                if(event.message == "/ic has") {
                    val itemStack = event.player.itemInHand
                    val result = ItemCooldowns.has(event.player, itemStack.type)
                    event.player.sendMessage("是否拥有冷却时间: $result")
                }
                if(event.message == "/nbt streamb64") {
                    val value = event.player.readTagLet { NBTFactory.writeDataBase64(it) }
                    event.player.sendMessage(value)
                    val nbt = NBTFactory.readDataBase64Compound(value)
                    println(nbt)
                }
                if(event.message == "/ib attitem") {
                    Material.IRON_SWORD.newItemBuilder()
                            .addAttribute(AttributeType.ATTACK_DAMAGE, "1", Operation.ADD, Slot.MAIN_HAND, 5.0)
                            .addAttribute(AttributeType.ATTACK_DAMAGE, "2", Operation.MULTIPLY, Slot.MAIN_HAND, .5)
                            .build().givePlayer(event.player)
                }
            }
        }.registerEvent(this)

        PacketListeners.registerListener(object: PacketListenerAdapter(this, PacketInUseEntity::class.java) {
            override fun onReceiving(event: PacketEvent) {
                val packet = event.packet as PacketInUseEntity
                val entity = packet.getEntity(event.player?.world)
                if(entity != null)
                    event.player?.sendMessage("你交互了实体 -> ${entity.type}")
            }
        })
    }

    override fun onDisable() {
    }
}
