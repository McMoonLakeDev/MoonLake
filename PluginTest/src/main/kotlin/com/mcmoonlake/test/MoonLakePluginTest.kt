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

package com.mcmoonlake.test

import com.mcmoonlake.api.*
import com.mcmoonlake.api.anvil.AnvilWindowSlot
import com.mcmoonlake.api.attribute.AttributeType
import com.mcmoonlake.api.attribute.Attributes
import com.mcmoonlake.api.attribute.Operation
import com.mcmoonlake.api.attribute.Slot
import com.mcmoonlake.api.block.Blocks
import com.mcmoonlake.api.chat.*
import com.mcmoonlake.api.chat.ChatColor
import com.mcmoonlake.api.depend.DependPlaceholderAPI
import com.mcmoonlake.api.depend.DependPlugins
import com.mcmoonlake.api.depend.DependVaultEconomy
import com.mcmoonlake.api.depend.DependWorldEdit
import com.mcmoonlake.api.effect.EffectBase
import com.mcmoonlake.api.effect.EffectCustom
import com.mcmoonlake.api.effect.EffectType
import com.mcmoonlake.api.event.MoonLakeListener
import com.mcmoonlake.api.item.*
import com.mcmoonlake.api.nbt.NBTFactory
import com.mcmoonlake.api.packet.*
import com.mcmoonlake.api.particle.Particle
import com.mcmoonlake.api.player.PlayerInfo
import com.mcmoonlake.api.task.MoonLakeRunnable
import org.bukkit.*
import org.bukkit.entity.Pig
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.URL
import java.util.*

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
                            .addAttribute(AttributeType.ATTACK_DAMAGE, Operation.ADD, Slot.MAIN_HAND, 10.0)
                            .addAttribute(AttributeType.ATTACK_SPEED, Operation.ADD, Slot.MAIN_HAND, 1.0)
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
                    anvilWindow.handleClick {
                        it.player.sendMessage("anvil click -> ${it.clickSlot}")
                        val itemRight = it.anvilWindow.getItem(AnvilWindowSlot.INPUT_RIGHT)
                        it.player.sendMessage(itemRight?.type?.name ?: "null")
                    }
                    anvilWindow.handleClose { println("如果处于打开状态服务器关闭的话看看我") }
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
                    val packetTitle = PacketOutTitle(PacketOutTitle.Action.TITLE, ChatComponentText("主标题"))
                    val packetSubTitle = PacketOutTitle(PacketOutTitle.Action.SUBTITLE, ChatComponentText("子标题"))
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
                    runTaskAsync {
                        val packet = PacketInChat("我没有，我不是")
                        packet.receive(event.player)
                    }
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
                if(event.message == "/packet chestopen") {
                    val block = event.player.toMoonLakePlayer().getTargetBlock(5)
                    if(block == null) {
                        event.player.sendMessage("方块为空")
                        return
                    }
                    Blocks.fakeActionChest(block, true)
                }
                if(event.message == "/region border") {
                    DependWorldEdit::class.java.useDependSafe {
                        val selection = it?.getSelection(event.player)
                        println(selection)
                        selection?.createWorldBorder()
                    }
                }
                if(event.message == "/region borderreset") {
                    event.player.world.worldBorder.reset()
                }
                if(event.message == "/packet fakeplayer") {
                    val info: MutableList<PlayerInfo> = ArrayList()
                    (0 until 10).forEach { info.add(PlayerInfo(UUID.randomUUID(), "FakePlayer-$it", null, GameMode.SURVIVAL, it * 100)) }
                    val packet = PacketOutPlayerInfo(PacketOutPlayerInfo.Action.ADD_PLAYER, info)
                    packet.sendToAllPlayer()
                }
                if(event.message == "/profile player") {
                    val profile = event.player.toMoonLakePlayer().profile
                    event.player.sendMessage(profile.toString())
                }
                if(event.message == "/items to") {
                    val itemStack = event.player.itemInHand
                    Items.toMojangson(itemStack).consumer { println(it) }
                    Items.toBase64(itemStack).consumer { println(it) }
                    Items.toCompoundFile(itemStack, File(dataFolder, "NBT.dat"))
                }
                if(event.message == "/items from") {
                    val origin = event.player.itemInHand
                    val json = Items.fromMojangson("{\"id\":\"minecraft:iron_sword\",\"Count\":1b,\"tag\":{\"Unbreakable\":1b,\"display\":{\"LocName\":\"tile.tnt.name\",\"Lore\":[\"标签属性\",\"标签属性\"]},\"CanPlaceOn\":[\"minecraft:stone\"],\"AttributeModifiers\":[{\"UUIDMost\":-5437538306879371484l,\"UUIDLeast\":-8190449683700789007l,\"Amount\":0.05d,\"Slot\":\"mainhand\",\"AttributeName\":\"generic.attackDamage\",\"Operation\":1,\"Name\":\"generic.attackDamage\"},{\"UUIDMost\":-2188973625266909354l,\"UUIDLeast\":-7798395392786142574l,\"Amount\":0.1d,\"Slot\":\"mainhand\",\"AttributeName\":\"generic.attackSpeed\",\"Operation\":1,\"Name\":\"generic.attackSpeed\"}],\"CanDestroy\":[\"minecraft:diamond_ore\"]},\"Damage\":0s}")
                    val base64 = Items.fromBase64("CgAACAACaWQAFG1pbmVjcmFmdDppcm9uX3N3b3JkAQAFQ291bnQBCgADdGFnAQALVW5icmVha2FibGUBCgAHZGlzcGxheQgAB0xvY05hbWUADXRpbGUudG50Lm5hbWUJAARMb3JlCAAAAAIADOagh+etvuWxnuaApwAM5qCH562+5bGe5oCnAAkACkNhblBsYWNlT24IAAAAAQAPbWluZWNyYWZ0OnN0b25lCQASQXR0cmlidXRlTW9kaWZpZXJzCgAAAAIEAAhVVUlETW9zdLSJ+7gd4EckBAAJVVVJRExlYXN0jlWtaFRN0PEGAAZBbW91bnQ/qZmZmZmZmggABFNsb3QACG1haW5oYW5kCAANQXR0cmlidXRlTmFtZQAUZ2VuZXJpYy5hdHRhY2tEYW1hZ2UDAAlPcGVyYXRpb24AAAABCAAETmFtZQAUZ2VuZXJpYy5hdHRhY2tEYW1hZ2UABAAIVVVJRE1vc3ThnzQV40RPVgQACVVVSURMZWFzdJPGiLU68GqSBgAGQW1vdW50P7mZmZmZmZoIAARTbG90AAhtYWluaGFuZAgADUF0dHJpYnV0ZU5hbWUAE2dlbmVyaWMuYXR0YWNrU3BlZWQDAAlPcGVyYXRpb24AAAABCAAETmFtZQATZ2VuZXJpYy5hdHRhY2tTcGVlZAAJAApDYW5EZXN0cm95CAAAAAEAFW1pbmVjcmFmdDpkaWFtb25kX29yZQACAAZEYW1hZ2UAAAA=")
                    val file = Items.fromCompoundFile(File(dataFolder, "NBT.dat"))

                    println("origin == json -> " + json.isSimilar(origin))
                    println("origin == base64 -> " + base64.isSimilar(origin))
                    println("origin == file -> " + file.isSimilar(origin))

                    json.givePlayer(event.player)
                }
                if(event.message == "/get attribute") {
                    val player = event.player.toMoonLakePlayer()
                    player.sendMessage("Attribute Attack Damage: ${player.getAttribute(AttributeType.ATTACK_DAMAGE).value}")
                    try {
                        // An exception is thrown if the server version is 1.8 or earlier
                        player.sendMessage("Attribute Attack Speed: ${player.getAttribute(AttributeType.ATTACK_SPEED).value}")
                    } catch(e: Exception) {
                        e.printStackTrace()
                    }
                }
                if(event.message == "/get attmodifiers") {
                    val player = event.player.toMoonLakePlayer()
                    player.sendMessage(player.getAttribute(AttributeType.ATTACK_DAMAGE).modifiers.joinToString())
                }
                if(event.message == "/cc null") {
                    val cct = ChatComponentText()
                    val cc1 = ChatComponent.NULL
                    val cc2 = ChatComponent.NULL
                    println(cct == cc1)
                    println(cc1 == cc2)
                }
                if(event.message == "/cc plus") {
                    val text = ChatComponentText("start")
                    val text2 = text + "&a&lAppend"
                    println(text2)
                }
                if(event.message == "/cc send") {
                    ChatComponentFancy("test")
                            .withBold()
                            .color(ChatColor.RED)
                            .build()
                            .send(event.player)

                    (ChatSerializer.fromRaw("2333") + "&6Append" + "&9Append")
                            .send(event.player)
                }
                if(event.message == "/att zombie") {
                    val zombie = Zombie::class.java.spawn(event.player.location)
                    val attribute = Attributes.getEntityAttribute(zombie, AttributeType.ZOMBIE_SPAWN_REINFORCEMENTS)
                    event.player.sendMessage("Current Value: ${attribute.value}")
                    attribute.baseValue = AttributeType.ZOMBIE_SPAWN_REINFORCEMENTS.max
                    event.player.sendMessage("Value: ${attribute.value}")
                }
                if(event.message == "/cc fshow") {
                    val itemStack = event.player.itemInHand
                    ItemShow(itemStack).build().send(event.player)
                }
//                Fix
//                if(event.message == "/cc md_5-issues") {
//                    val component = TextComponent("test")
//                    component.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("testa").create())
//                    val json = ComponentSerializer.toString(component)
//                    println(json)
//                    val me = ChatSerializer.fromJson(json)
//                    println(me.toJson())
//                    me.send(event.player)
//                }
                if(event.message == "/forgehs mods") {
                    val mods = event.player.toMoonLakePlayer().getForgeMods()
                    event.player.sendMessage("client mods -> ${mods?.joinToString(separator = " | ") ?: "empty"}")
                }
                if(event.message == "/packet listener") {
                    PacketListeners.registerListener(object: PacketListenerAnyAdapter(this@MoonLakePluginTest) {
                        override fun onSending(event: PacketEvent) {
                            println(event.packet)
                        }
                        override fun onReceiving(event: PacketEvent) {
                            println(event.packet)
                        }
                    })
                }
                if(event.message == "/lightning") {
                    event.player
                            .toMoonLakePlayer()
                            .getTargetBlock(100)
                            ?.world?.strikeLightning(event.player.location)
                }
                if(event.message == "/packet s-listener") {
                    val packets = arrayOf(
                            PacketOutSpawnEntity::class.java,
                            PacketOutSpawnExperienceOrb::class.java,
                            PacketOutSpawnPainting::class.java,
                            PacketOutSpawnWeather::class.java,
                            PacketOutStatistic::class.java
                    )
                    PacketListeners.registerListener(object: PacketListenerAdapter(this@MoonLakePluginTest, *packets) {
                        override fun onSending(event: PacketEvent) {
                            println(event.packet)
                        }
                    })
                }
                if(event.message == "/packet recipe-listener") {
                    val packets = arrayOf(
                            PacketInAutoRecipe::class.java,
                            PacketOutAutoRecipe::class.java)
                    PacketListeners.registerListener(object: PacketListenerAdapter(this@MoonLakePluginTest, *packets) {
                        override fun onSending(event: PacketEvent) {
                            println(event.packet)
                        }
                        override fun onReceiving(event: PacketEvent) {
                            println(event.packet)
                        }
                    })
                }
                if(event.message == "/packet recipe") {
                    val packets = arrayOf(
                            PacketInRecipeDisplayed::class.java,
                            PacketOutRecipes::class.java)
                    PacketListeners.registerListener(object: PacketListenerAdapter(this@MoonLakePluginTest, *packets) {
                        override fun onSending(event: PacketEvent) {
                            println(event.packet)
                        }
                        override fun onReceiving(event: PacketEvent) {
                            println(event.packet)
                        }
                    })
                }
                if(event.message == "/packet 2018-1-2") {
                    val packets = arrayOf(
                            PacketInAdvancements::class.java,
                            PacketOutSpawnPosition::class.java)
                    PacketListeners.registerListener(object: PacketListenerAdapter(this@MoonLakePluginTest, *packets) {
                        override fun onSending(event: PacketEvent) {
                            println(event.packet)
                        }
                        override fun onReceiving(event: PacketEvent) {
                            println(event.packet)
                        }
                    })
                }
                if(event.message == "/effectest") {
                    val effectCustom = EffectCustom(EffectType.SPEED, 30 * 20, 4)
                    val effectBase = EffectBase.WATER_AWKWARD

                    ItemBuilder.of(Material.POTION)
                            .setPotionBase(effectBase)
                            .addPotionEffect(effectCustom)
                            .build().givePlayer(event.player)

                    effectCustom.apply(event.player)
                            .consumer { event.player.sendMessage("apply effect -> $it") }
                }
                if(event.message == "/task sync-future") {
                    // If the future is synchronized, then the server thread will be blocked until the future work is completed
                    callTaskSyncFuture {
                        URL("https://github.com").readBytes().size +
                        URL("https://github.com/lgou2w").readBytes().size
                    }.whenComplete { value, ex ->
                        if(ex != null)
                            event.player.sendMessage("Sync future ex: ${ex.message}")
                        else
                            event.player.sendMessage("Sync future done. value size: $value")
                    }
                }
                if(event.message == "/task async-future") {
                    // Asynchronous future does not block server threads
                    callTaskAsyncFuture {
                        URL("https://github.com").readBytes()
                    }.whenComplete { value, ex ->
                        if(ex != null)
                            event.player.sendMessage("Async future ex: ${ex.message}")
                        else
                            event.player.sendMessage("Async future value size: ${value.size}")
                    }
                }
                if(event.message == "/task sync-later-future") {
                    callTaskLaterSyncFuture(5 * 20L) { // Start synchronization after 5 seconds delay
                        URL("https://github.com").readBytes().size +
                        URL("https://github.com/lgou2w").readBytes().size
                    }.whenComplete { value, ex ->
                        if(ex != null)
                            event.player.sendMessage("Sync later future ex: ${ex.message}")
                        else
                            event.player.sendMessage("Sync later future value size: ${value}")
                    }
                }
            }
        }.registerEvent(this)
    }

    class ItemShow(itemStack: ItemStack) : ChatComponentFancy("[") {
        init {
            color(ChatColor.WHITE)
            then(itemStack.itemMeta.displayName ?: itemStack.type.name)
            color(ChatColor.AQUA)
            withBold()
            tooltipItem(itemStack)
            then("]")
            color(ChatColor.WHITE)
        }
    }

    override fun onDisable() {
    }
}
