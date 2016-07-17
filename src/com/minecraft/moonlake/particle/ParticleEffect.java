package com.minecraft.moonlake.particle;

import com.minecraft.moonlake.reflect.Reflect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by MoonLake on 2016/6/8.
 */
public enum  ParticleEffect {

    EXPLOSION_NORMAL("explode", 0, -1),
    EXPLOSION_LARGE("largeexplode", 1, -1),
    EXPLOSION_HUGE("hugeexplosion", 2, -1),
    FIREWORKS_SPARK("fireworksSpark", 3, -1),
    WATER_BUBBLE("bubble", 4, -1, false, true),
    WATER_SPLASH("splash", 5, -1),
    WATER_WAKE("wake", 6, 7),
    SUSPENDED("suspended", 7, -1, false, true),
    SUSPENDED_DEPTH("depthSuspend", 8, -1),
    CRIT("crit", 9, -1),
    CRIT_MAGIC("magicCrit", 10, -1),
    SMOKE_NORMAL("smoke", 11, -1),
    SMOKE_LARGE("largesmoke", 12, -1),
    SPELL("spell", 13, -1),
    SPELL_INSTANT("instantSpell", 14, -1),
    SPELL_MOB("mobSpell", 15, -1),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1),
    SPELL_WITCH("witchMagic", 17, -1),
    DRIP_WATER("dripWater", 18, -1),
    DRIP_LAVA("dripLava", 19, -1),
    VILLAGER_ANGRY("angryVillager", 20, -1),
    VILLAGER_HAPPY("happyVillager", 21, -1),
    TOWN_AURA("townaura", 22, -1),
    NOTE("note", 23, -1),
    PORTAL("portal", 24, -1),
    ENCHANTMENT_TABLE("enchantmenttable", 25, -1),
    FLAME("flame", 26, -1),
    LAVA("lava", 27, -1),
    FOOTSTEP("footstep", 28, -1),
    CLOUD("cloud", 29, -1),
    REDSTONE("reddust", 30, -1),
    SNOWBALL("snowballpoof", 31, -1),
    SNOW_SHOVEL("snowshovel", 32, -1),
    SLIME("slime", 33, -1),
    HEART("heart", 34, -1),
    BARRIER("barrier", 35, 8),
    ITEM_CRACK("iconcrack", 36, -1, true),
    BLOCK_CRACK("blockcrack", 37, -1, true),
    BLOCK_DUST("blockdust", 38, 7, true),
    WATER_DROP("droplet", 39, 8),
    ITEM_TAKE("take", 40, 8),
    MOB_APPEARANCE("mobappearance", 41, 8),
    DRAGON_BREATH("dragonbreath", 42, 9),
    END_ROD("endRod", 43, 9),
    DAMAGE_INDICATOR("damageIndicator", 44, 9),
    SWEEP_ATTACK("sweepAttack", 45, 9),
    FALLING_DUST("fallingdust", 46, 10, true),
    ;

    private final String name;
    private final int id;
    private final int requiredVersion;
    private final boolean requiresData;
    private final boolean requiresWater;
    private final static Map<String, ParticleEffect> NAME_MAP;
    private final static Map<Integer, ParticleEffect> ID_MAP;

    static {

        NAME_MAP = new HashMap<>();
        ID_MAP = new HashMap<>();

        for(ParticleEffect particleEffect : ParticleEffect.values()) {

            NAME_MAP.put(particleEffect.name, particleEffect);
            ID_MAP.put(particleEffect.id, particleEffect);
        }
    }

    ParticleEffect(String name, int id, int requiredVersion, boolean requiresData, boolean requiresWater) {

        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.requiresData = requiresData;
        this.requiresWater = requiresWater;
    }

    ParticleEffect(String name, int id, int requiredVersion, boolean requiresData) {

        this(name, id, requiredVersion, requiresData, false);
    }

    ParticleEffect(String name, int id, int requiredVersion) {

        this(name, id, requiredVersion, false);
    }

    public String getName() {

        return name;
    }

    public int getId() {

        return id;
    }

    public int getRequiredVersion() {

        return requiredVersion;
    }

    public boolean isRequiresData() {

        return requiresData;
    }

    public boolean isRequiresWater() {

        return requiresWater;
    }

    public boolean isSupported() {

        return requiredVersion == -1 ? true : ParticlePacket.version >= requiredVersion;
    }

    public ParticleData getData(Material material, Byte blockData) {

        ParticleData data = null;

        if(blockData == null) {

            blockData = Byte.valueOf((byte)0);
        }
        if((this == BLOCK_CRACK || this == ITEM_CRACK || this == BLOCK_DUST) && material != null && material != Material.AIR) {

            if(this == ITEM_CRACK) {

                data = new ItemData(material, blockData.byteValue());
            }
            else {

                data = new BlockData(material, blockData.byteValue());
            }
        }
        return data;
    }

    public static ParticleEffect fromName(String name) {

        return NAME_MAP.containsKey(name) ? NAME_MAP.get(name) : null;
    }

    public static ParticleEffect fromId(int id) {

        return ID_MAP.containsKey(id) ? ID_MAP.get(id) : null;
    }

    private static boolean isWater(Location location) {

        Material marterial = location.getBlock().getType();
        return marterial == Material.WATER || marterial == Material.STATIONARY_WATER;
    }

    private static boolean isLongDistance(Location location, List<Player> players) {

        for(Player player : players) {

            if(!location.getWorld().getName().equals(player.getWorld().getName()) || player.getLocation().distanceSquared(location) <= 65536d) {

                continue;
            }
            return true;
        }
        return false;
    }

    public static boolean isDataCorrect(ParticleEffect effect, ParticleData data) {

        return (effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData || effect == ITEM_CRACK && data instanceof ItemData;
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(isRequiresWater() && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        else {

            new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 16d, null).sendTo(center, range);
        }
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center,List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(isRequiresWater() && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        else {

            new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null).sendTo(center, players);
        }
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleException {

        display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(Vector direction, float speed, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(isRequiresWater() && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        else {

            new ParticlePacket(this, direction, speed, range > 16d, null).sendTo(center, range);
        }
    }

    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(isRequiresWater() && !isWater(center)) {

            throw new ParticleException("这个粒子效果没有水方块在中心位置.");
        }
        else {

            new ParticlePacket(this, direction, speed, isLongDistance(center, players), null).sendTo(center, players);
        }
    }

    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleException {

        display(direction, speed, center, Arrays.asList(players));
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据类型不正确: " + data + " 从 " + this);
        }
        else {

            new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 16d, data).sendTo(center, range);
        }
    }

    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据类型不正确: " + data + " 从 " + this);
        }
        else {

            new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data).sendTo(center, players);
        }
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据类型不正确: " + data + " 从 " + this);
        }
        else {

            new ParticlePacket(this, direction, speed, range > 16d, data).sendTo(center, range);
        }
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleException {

        if(!isSupported()) {

            throw new ParticleException("这个粒子效果 " + this + " 不支持您的服务端版本.");
        }
        else if(isRequiresData()) {

            throw new ParticleException("这个粒子效果 " + this + " 要求添加效果数据.");
        }
        else if(!isDataCorrect(this, data)) {

            throw new ParticleException("粒子效果数据类型不正确: " + data + " 从 " + this);
        }
        else {

            new ParticlePacket(this, direction, speed, isLongDistance(center, players), data).sendTo(center, players);
        }
    }

    public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleException {

        display(data, direction, speed, center, Arrays.asList(players));
    }

    public void display(Location center, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        display(offsetX, offsetY, offsetZ, speed, amount, center, range);
    }

    public void display(Location center, double range) throws ParticleException {

        display(0f, 0f, 0f, 0f, 1, center, range);
    }

    public void display(ParticleData data, Location center, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        if(isRequiresData()) {

            display(data, offsetX, offsetY, offsetZ, speed, amount, center, range);
        }
        else {

            display(offsetX, offsetY, offsetZ, speed, amount, center, range);
        }
    }

    public void display(ParticleData data, Location center, Color color, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount) throws ParticleException {

        if(color != null && (this == REDSTONE || this == SPELL_MOB || this == SPELL_MOB_AMBIENT)) {

            amount = 0;

            if(speed == 0.0f) {

                speed = 1.0f;
            }
            offsetX = (float)color.getRed() / 255f;
            offsetY = (float)color.getGreen() / 255f;
            offsetZ = (float)color.getBlue() / 255f;

            if(offsetX < 1.17549435E-38f) {

                offsetX = 1.17549435E-38f;
            }
        }
        if(isRequiresData()) {

            display(data, offsetX, offsetY, offsetZ, speed, amount, center, range);
        }
        else {

            display(offsetX, offsetY, offsetZ, speed, amount, center, range);
        }
    }

    public final static class ParticlePacket {

        private static int version;
        private static Class<?> enumParticle;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffect effect;
        private final float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleData data;
        private Object packet;

        public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleData data) throws ParticleException {

            initialize();

            if(speed < 0.0f) {

                throw new ParticleException("粒子效果速度不能小于 0");
            }
            else if(amount < 0) {

                throw new ParticleException("粒子效果数量不能小于 0");
            }
            else {

                this.effect = effect;
                this.offsetX = offsetX;
                this.offsetY = offsetY;
                this.offsetZ = offsetZ;
                this.speed = speed;
                this.amount = amount;
                this.longDistance = longDistance;
                this.data = data;
            }
        }

        public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleData data) throws ParticleException {

            initialize();

            if(speed < 0.0f) {

                throw new ParticleException("粒子效果速度不能小于 0");
            }
            else {

                this.effect = effect;
                this.offsetX = (float)direction.getX();
                this.offsetY = (float)direction.getY();
                this.offsetZ = (float)direction.getZ();
                this.speed = speed;
                this.amount = 0;
                this.longDistance = longDistance;
                this.data = data;
            }
        }

        public static void initialize() throws ParticleException {

            if(!isInitialized()) {

                try {

                    version = Reflect.getServerVersionNumber();

                    if(version > 7) {

                        enumParticle = Reflect.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                    }
                    Class<?> exception = Reflect.PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
                    packetConstructor = Reflect.getConstructor(exception);
                    getHandle = Reflect.getMethod("CraftPlayer", Reflect.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
                    playerConnection = Reflect.getField("EntityPlayer", Reflect.PackageType.MINECRAFT_SERVER, false, "playerConnection");
                    sendPacket = Reflect.getMethod(playerConnection.getType(), "sendPacket", Reflect.PackageType.MINECRAFT_SERVER.getClass("Packet"));
                }
                catch (Exception e) {

                    throw new ParticleException("粒子效果初始化时异常: " + e.getMessage());
                }
                initialized = true;
            }
        }

        public static int getVersion() {

            return version;
        }

        public static boolean isInitialized() {

            return initialized;
        }

        public void sendTo(Location center, Player player) {

            sendToWithBukkit(center, player);
        }

        public void sendTo(Location center, List<Player> players) {

            if(players == null || players.isEmpty()) {

                throw new ParticleException("粒子效果数据包的玩家列表为空");
            }
            else {

                Iterator<Player> i$ = players.iterator();

                while (i$.hasNext()) {

                    sendTo(center, i$.next());
                }
            }
        }

        public void sendTo(Location center, double range) {

            if(range < 1.0d) {

                throw new ParticleException("粒子效果数据包范围小于 1");
            }
            else {

                String worldName = center.getWorld().getName();
                double squared = range * range;
                Iterator<? extends Player> i$ = Bukkit.getOnlinePlayers().iterator();

                while (i$.hasNext()) {

                    Player player = i$.next();

                    if(player.getWorld().getName().equals(worldName) && player.getLocation().distanceSquared(center) <= squared) {

                        sendTo(center, player);
                    }
                }
            }
        }

        private void sendToWithBukkit(Location center, Player player) {

            if(packet == null) {

                try {

                    packet = packetConstructor.newInstance();

                    if(version < 8) {

                        String name = effect.getName() + (data == null ? "" : data.getPacketDataString());
                        Reflect.setValue(packet, true, "a", name);
                    }
                    else {

                        Reflect.setValue(packet, true, "a", enumParticle.getEnumConstants()[effect.getId()]);
                        Reflect.setValue(packet, true, "j", longDistance);

                        if(data != null) {

                            int[] packetData = data.getPacketData();
                            Reflect.setValue(packet, true, "k", effect == ITEM_CRACK ? packetData : new int[] { packetData[0] | (packetData[1] << 12) });
                        }
                    }
                    Reflect.setValue(packet, true, "b", (float)center.getX());
                    Reflect.setValue(packet, true, "c", (float)center.getY());
                    Reflect.setValue(packet, true, "d", (float)center.getZ());
                    Reflect.setValue(packet, true, "e", offsetX);
                    Reflect.setValue(packet, true, "f", offsetY);
                    Reflect.setValue(packet, true, "g", offsetZ);
                    Reflect.setValue(packet, true, "h", speed);
                    Reflect.setValue(packet, true, "i", amount);
                }
                catch (Exception e) {

                    throw new ParticleException("粒子效果数据包实例字段时异常: " + e.getMessage());
                }
            }
            try {

                sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), packet);
            }
            catch (Exception e) {

                throw new ParticleException("粒子效果数据包发送时异常: " + e.getMessage());
            }
        }
    }

    public abstract static class ParticleData {

        private final Material material;
        private final byte data;
        private final int[] packetData;

        public ParticleData(Material material, byte data) {

            this.material = material;
            this.data = data;
            this.packetData = new int[] { data << 12 | material.getId() & 4095 };
        }

        public Material getMaterial() {

            return material;
        }

        public byte getData() {

            return data;
        }

        public int[] getPacketData() {

            return packetData;
        }

        public String getPacketDataString() {

            return ("_" + packetData[0] + "_" + packetData[1]);
        }
    }

    public final static class BlockData extends ParticleData {

        public BlockData(Material material, byte data) throws IllegalArgumentException {

            super(material, data);

            if(!material.isBlock()) {

                throw new IllegalArgumentException("粒子效果物品类型数据不是方块类型");
            }
        }
    }

    public final static class ItemData extends ParticleData {

        public ItemData(Material material, byte data) {

            super(material, data);
        }
    }
}
