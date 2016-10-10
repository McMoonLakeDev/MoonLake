# MoonLake [![GitHub version](https://d25lcipzij17d.cloudfront.net/badge.svg?id=gh&type=6&v=1.8-r1&x2=0)](https://github.com/u2g/MoonLake) [![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://github.com/u2g/MoonLake) [![Open Source Love](https://badges.frapsoft.com/os/gpl/gpl.svg?v=102)](https://github.com/u2g/MoonLake)

Minecraft MoonLake Core API Plugin
By Month_Light Ver: 1.8-r1

## 简介
这个插件提供了大量的 API 功能，实现了一些 Bukkit 无法做到的 NMS 功能<br />
例如数据包封装、物品栈操作类、玩家操作类、NBT 操作类、消息源操作类等。

## 目前插件支持的服务端版本
* `Bukkit [1.8.x - 1.10.x]` <span style="color: red"><s>✔</s></span> 部分功能不支持
* `Spigot [1.8.x - 1.10.x]` <span style="color: rgb(85, 255, 85)">✔</span> 完完全全支持全功能
* `PaperSpigot [1.8.x - 1.10.x]` <span style="color: rgb(85, 255, 85)"><s>✔</s></span> 部分功能不支持
* `Cauldron | KCauldron [模组服务端]` <span style="color: red">✘</span> 完全不支持
 
## 目前已经实现的功能
* 玩家支持库 `{@link com.minecraft.moonlake.api.player.PlayerLibrary}`
* 物品栈支持库 `{@link com.minecraft.moonlake.api.item.ItemLibrary}`
* 数据库支持库 `{@link com.minecraft.moonlake.api.mysql.MySQLConnection}`
* 花式消息支持库 `{@link com.minecraft.moonlake.api.fancy.FancyMessage}`
* NMS 数据包发送 `{@link com.minecraft.moonlake.nms.packet.Packet}`
* NBT 操作支持库 `{@link com.minecraft.moonlake.api.nbt.NBTLibrary}`
 
更多功能开发中 _(:з」∠)_

## 使用方法
注意将您的插件内 `plugin.yml` 添加 `depend: [MoonLake]` 前置支持
```java
private MoonLake moonlake;

private boolean setupMoonLake() {
  
  Plugin plugin = this.getServer().getPluginManager().getPlugin("MoonLake");
  return plugin != null && plugin instanceof MoonLakePlugin && (this.moonLake = ((MoonLakePlugin)plugin).getInstance()) != null;
}
```
调用的话就在主类的 `onEnable` 函数里面
```java
@Override
public void onEnable() {
  
  if(!setupMoonLake()) {
    // 前置插件 MoonLake 加载失败
    return;
  }
  // 前置插件 MoonLake 加载成功
}
```

## 项目协议
此项目完全属于开源项目，如需修改和添加功能请到 <a href="https://github.com/u2g/MoonLake" target="_blank">GitHub</a> 进行 Fork 操作.<br/>
修改操作请您遵守 <a href="https://github.com/u2g/MoonLake/blob/master/LICENSE" target="_blank">GPLv3</a> 协议，您必须公开修改过的所有代码！

## 其他插件
* `MoonLakeKitPvP` 职业战争插件 :point_right:[GO](http://github.com/u2g/MoonLakeKitPvP "MoonLake KitPvP Plugin")
* `MoonLakeSkinme` 玩家皮肤披风操作插件 :point_right:[GO](http://github.com/u2g/MoonLakeSkinme "MoonLake Skinme Plugin")
* `MoonLakeEconomy` 基于 `MySQL` 的经济插件 :point_right:[GO](http://github.com/u2g/MoonLakeEconomy "MoonLake Economy Plugin")

Website: [MoonLake](http://www.mcyszh.com "MoonLake Website")<br />
Minecraft MoonLake Core API Plugin
By Month_Light
