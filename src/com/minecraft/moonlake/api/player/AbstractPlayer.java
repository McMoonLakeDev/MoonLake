package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.nms.packet.Packet;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.property.ReadOnlyObjectProperty;
import com.minecraft.moonlake.property.ReadOnlyStringProperty;
import com.minecraft.moonlake.property.SimpleObjectProperty;
import com.minecraft.moonlake.property.SimpleStringProperty;
import com.minecraft.moonlake.util.StringUtil;
import com.mojang.authlib.GameProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by MoonLake on 2016/7/16.
 */
public class AbstractPlayer implements MoonLakePlayer {

    private final ReadOnlyStringProperty nameProperty;
    private final ReadOnlyObjectProperty<Player> playerProperty;

    public AbstractPlayer(String name) {

        this.nameProperty = new SimpleStringProperty(name);
        this.playerProperty = new SimpleObjectProperty<>(Bukkit.getPlayer(name));
    }

    @Override
    public Player getBukkitPlayer() {

        return playerProperty.get();
    }

    @Override
    public String getName() {

        return nameProperty.get();
    }

    @Override
    public UUID getUniqueId() {

        return getBukkitPlayer().getUniqueId();
    }

    @Override
    public GameProfile getProfile() {

        return PlayerManager.getProfile(getBukkitPlayer());
    }

    @Override
    public String getDisplayName() {

        return getBukkitPlayer().getDisplayName();
    }

    @Override
    public void setDisplayName(String displayName) {

        getBukkitPlayer().setDisplayName(displayName);
    }

    @Override
    public String getListName() {

        return getBukkitPlayer().getPlayerListName();
    }

    @Override
    public void setListName(String listName) {

        getBukkitPlayer().setPlayerListName(listName);
    }

    @Override
    public void chat(String msg) {

        getBukkitPlayer().chat(msg);
    }

    @Override
    public void send(String msg) {

        getBukkitPlayer().sendMessage(StringUtil.toColor(msg).get());
    }

    @Override
    public void send(String[] msg) {

        getBukkitPlayer().sendMessage(StringUtil.toColor(msg).get());
    }

    @Override
    public void send(BaseComponent bc) {

        getBukkitPlayer().spigot().sendMessage(bc);
    }

    @Override
    public void send(BaseComponent... bcs) {

        getBukkitPlayer().spigot().sendMessage(bcs);
    }

    @Override
    public double getHealth() {

        return getBukkitPlayer().getHealth();
    }

    @Override
    public double getMaxHealth() {

        return getBukkitPlayer().getMaxHealth();
    }

    @Override
    public void giveHealth(double amount) {

        if(getHealth() + amount >= getMaxHealth()) {

            setHealth(getMaxHealth());
            return;
        }
        setHealth(getHealth() + amount);
    }

    @Override
    public void takeHealth(double amount) {

        if(getHealth() - amount <= 0d) {

            setHealth(0d);
            return;
        }
        setHealth(getHealth() - amount);
    }

    @Override
    public float getXp() {

        return getBukkitPlayer().getExp();
    }

    @Override
    public int getLevel() {

        return getBukkitPlayer().getLevel();
    }

    @Override
    public void setHealth(double health) {

        getBukkitPlayer().setHealth(health);
    }

    @Override
    public void setMaxHealth(double maxHealth) {

        getBukkitPlayer().setMaxHealth(maxHealth);
    }

    @Override
    public void giveXp(float xp) {

        setXp(getXp() + xp);
    }

    @Override
    public void setXp(float xp) {

        getBukkitPlayer().setExp(xp);
    }

    @Override
    public void setLevel(int level) {

        getBukkitPlayer().setLevel(level);
    }

    @Override
    public float getFlySpeed() {

        return getBukkitPlayer().getFlySpeed();
    }

    @Override
    public int getFoodLevel() {

        return getBukkitPlayer().getFoodLevel();
    }

    @Override
    public void setFlySpeed(float flySpeed) {

        getBukkitPlayer().setFlySpeed(flySpeed);
    }

    @Override
    public void setFoodLevel(int foodLevel) {

        getBukkitPlayer().setFoodLevel(foodLevel);
    }

    @Override
    public Scoreboard getScoreboard() {

        return getBukkitPlayer().getScoreboard();
    }

    @Override
    public void setScoreboard(Scoreboard scoreboard) {

        getBukkitPlayer().setScoreboard(scoreboard);
    }

    @Override
    public Entity getSpectatorTarget() {

        return getBukkitPlayer().getSpectatorTarget();
    }

    @Override
    public void setSpectatorTarget(Entity entity) {

        getBukkitPlayer().setSpectatorTarget(entity);
    }

    @Override
    public void damage(double damage) {

        getBukkitPlayer().damage(damage);
    }

    @Override
    public void damage(double damage, LivingEntity damager) {

        getBukkitPlayer().damage(damage, damager);
    }

    @Override
    public Location getLocation() {

        return getBukkitPlayer().getLocation();
    }

    @Override
    public PlayerInventory getInventory() {

        return getBukkitPlayer().getInventory();
    }

    @Override
    public boolean hasBeforePlayed() {

        return getBukkitPlayer().hasPlayedBefore();
    }

    @Override
    public boolean isOnGround() {

        return getBukkitPlayer().isOnGround();
    }

    @Override
    public boolean isFly() {

        return getBukkitPlayer().isFlying();
    }

    @Override
    public boolean isAllowFly() {

        return getBukkitPlayer().getAllowFlight();
    }

    @Override
    public void setAllowFly(boolean flag) {

        getBukkitPlayer().setAllowFlight(flag);
    }

    @Override
    public World getWorld() {

        return getBukkitPlayer().getWorld();
    }

    @Override
    public void updateInventory() {

        getBukkitPlayer().updateInventory();
    }

    @Override
    public void closeInventory() {

        getBukkitPlayer().closeInventory();
    }

    @Override
    public float getWalkSpeed() {

        return getBukkitPlayer().getWalkSpeed();
    }

    @Override
    public void setWalkSpeed(float speed) {

        getBukkitPlayer().setWalkSpeed(speed);
    }

    @Override
    public int getX() {

        return getLocation().getBlockX();
    }

    @Override
    public int getY() {

        return getLocation().getBlockY();
    }

    @Override
    public int getZ() {

        return getLocation().getBlockZ();
    }

    @Override
    public double getDoubleX() {

        return getLocation().getX();
    }

    @Override
    public double getDoubleY() {

        return getLocation().getY();
    }

    @Override
    public double getDoubleZ() {

        return getLocation().getZ();
    }

    @Override
    public void teleport(Entity entity) {

        getBukkitPlayer().teleport(entity);
    }

    @Override
    public void teleport(Location location) {

        getBukkitPlayer().teleport(location);
    }

    @Override
    public void teleport(int x, int y, int z) {

        teleport((double)x, (double)y, (double)z);
    }

    @Override
    public void teleport(double x, double y, double z) {

        getBukkitPlayer().teleport(new Location(getWorld(), x, y, z));
    }

    @Override
    public void teleport(World world, int x, int y, int z) {

        teleport(world, (double)x, (double)y, (double)z);
    }

    @Override
    public void teleport(World world, double x, double y, double z) {

        getBukkitPlayer().teleport(new Location(world, x, y, z));
    }

    @Override
    public Block getTargetBlock(int distance) {

        return getTargetBlock(null, distance);
    }

    @Override
    public void playSound(String sound, float volume, float pitch) {

        getBukkitPlayer().playSound(getLocation(), sound, volume, pitch);
    }

    @Override
    public void playSound(Sound sound, float volume, float pitch) {

        getBukkitPlayer().playSound(getLocation(), sound, volume, pitch);
    }

    @Override
    public void playNote(Instrument instrument, Note note) {

        getBukkitPlayer().playNote(getLocation(), instrument, note);
    }

    @Override
    public void stopSound(String sound) {

        getBukkitPlayer().stopSound(sound);
    }

    @Override
    public void stopSound(Sound sound) {

        getBukkitPlayer().stopSound(sound);
    }

    @Override
    public double distance(Location target) {

        return getLocation().distance(target);
    }

    @Override
    public ItemStack getItemInMainHand() {

        return getInventory().getItemInMainHand();
    }

    @Override
    public ItemStack getItemInOffHand() {

        return getInventory().getItemInOffHand();
    }

    @Override
    public ItemStack getItemOnCursor() {

        return getBukkitPlayer().getItemOnCursor();
    }

    @Override
    public void setItemInMainHand(ItemStack item) {

        getInventory().setItemInMainHand(item);
    }

    @Override
    public void setItemInOffHand(ItemStack item) {

        getInventory().setItemInOffHand(item);
    }

    @Override
    public void openInventory(Inventory inv) {

        getBukkitPlayer().openInventory(inv);
    }

    @Override
    public Inventory getTopInventory() {

        return getBukkitPlayer().getOpenInventory() != null ? getBukkitPlayer().getOpenInventory().getTopInventory() : null;
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time) {

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1));
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient) {

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient));
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles) {

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles));
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles, color));
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType type) {

        return getBukkitPlayer().hasPotionEffect(type);
    }

    @Override
    public void removePotionEffect(PotionEffectType type) {

        getBukkitPlayer().removePotionEffect(type);
    }

    @Override
    public Map<Integer, ItemStack> addItemStack(ItemStack... items) {

        return getInventory().addItem(items);
    }

    @Override
    public Map<Integer, ItemStack> removeItemStack(ItemStack... items) {

        return getInventory().removeItem(items);
    }

    @Override
    public boolean hasPermission(String permission) {

        return getBukkitPlayer().hasPermission(permission);
    }

    @Override
    public void onKick() {

        onKick("无");
    }

    @Override
    public void onKick(String message) {

        getBukkitPlayer().kickPlayer("你被服务器踢出,原因: " + message);
    }

    @Override
    public void onBanName() {

        onBanName(null);
    }

    @Override
    public void onBanName(String cause) {

        onBanName(cause, null);
    }

    @Override
    public void onBanName(String cause, Date time) {

        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(getName(), cause, time, null);
    }

    @Override
    public void onBanIp() {

        onBanIp(null);
    }

    @Override
    public void onBanIp(String cause) {

        onBanIp(cause, null);
    }

    @Override
    public void onBanIp(String cause, Date time) {

        Bukkit.getServer().getBanList(BanList.Type.IP).addBan(getName(), cause, time, null);
    }

    @Override
    public void onUnBan() {

        for(BanList.Type type : BanList.Type.values()) {

            if(Bukkit.getServer().getBanList(type).isBanned(getName())) {

                Bukkit.getServer().getBanList(type).pardon(getName());
            }
        }
    }

    @Override
    public boolean isShift() {

        return getBukkitPlayer().isSneaking();
    }

    @Override
    public boolean isSprinting() {

        return getBukkitPlayer().isSprinting();
    }

    @Override
    public boolean isOp() {

        return getBukkitPlayer().isOp();
    }

    @Override
    public void setVelocity(Vector vector) {

        getBukkitPlayer().setVelocity(vector);
    }

    @Override
    public void setNoDamageTicks(int ticks) {

        getBukkitPlayer().setNoDamageTicks(ticks);
    }

    @Override
    public void onEject() {

        getBukkitPlayer().eject();
    }

    @Override
    public void createExplosion(float power) {

        createExplosion(power, true, true);
    }

    @Override
    public void createExplosion(float power, boolean setFire) {

        createExplosion(power, setFire, true);
    }

    @Override
    public void createExplosion(float power, boolean setFire, boolean breakBlock) {

        getWorld().createExplosion(getX(), getY(), getZ(), power, setFire, breakBlock);
    }

    @Override
    public boolean performCommand(String command) {

        return getBukkitPlayer().performCommand(command);
    }

    @Override
    public void onHide(String target) {

        Player targetPlayer = PlayerManager.fromName(target);

        if(targetPlayer != null) {

            getBukkitPlayer().hidePlayer(targetPlayer);
        }
    }

    @Override
    public void onShow(String target) {

        Player targetPlayer = PlayerManager.fromName(target);

        if(targetPlayer != null) {

            getBukkitPlayer().showPlayer(targetPlayer);
        }
    }

    @Override
    public boolean canSee(String target) {

        Player targetPlayer = PlayerManager.fromName(target);

        return targetPlayer != null && getBukkitPlayer().canSee(targetPlayer);
    }

    @Override
    public void setTime(long time) {

        setTime(time, true);
    }

    @Override
    public void setTime(long time, boolean relative) {

        getBukkitPlayer().setPlayerTime(time, relative);
    }

    @Override
    public void setWeather(WeatherType weather) {

        getBukkitPlayer().setPlayerWeather(weather);
    }

    @Override
    public WeatherType getWeather() {

        return getBukkitPlayer().getPlayerWeather();
    }

    @Override
    public long getTime() {

        return getBukkitPlayer().getPlayerTime();
    }

    @Override
    public void resetWeather() {

        getBukkitPlayer().resetPlayerWeather();
    }

    @Override
    public void resetTime() {

        getBukkitPlayer().resetPlayerTime();
    }

    @Override
    public GameMode getGameMode() {

        return getBukkitPlayer().getGameMode();
    }

    @Override
    public void setGameMode(int mode) {

        GameMode gameMode = GameMode.getByValue(mode);

        if(gameMode != null) {

            setGameMode(gameMode);
        }
    }

    @Override
    public void setGameMode(GameMode mode) {

        getBukkitPlayer().setGameMode(mode);
    }

    @Override
    public void resetMaxHealth() {

        getBukkitPlayer().resetMaxHealth();
    }

    @Override
    public boolean isGliding() {

        return getBukkitPlayer().isGliding();
    }

    @Override
    public void setGliding(boolean gliding) {

        getBukkitPlayer().setGliding(gliding);
    }

    @Override
    public void clearPotionEffect() {

        if(getBukkitPlayer() != null && !getBukkitPlayer().getActivePotionEffects().isEmpty()) {

            for(PotionEffect potionEffect : getBukkitPlayer().getActivePotionEffects()) {

                if(potionEffect != null && getBukkitPlayer().hasPotionEffect(potionEffect.getType())) {

                    getBukkitPlayer().removePotionEffect(potionEffect.getType());
                }
            }
        }
    }

    @Override
    public Location getEyeLocation() {

        return getBukkitPlayer().getEyeLocation();
    }

    @Override
    public float getFallDistance() {

        return getBukkitPlayer().getFallDistance();
    }

    @Override
    public void setFallDistance(float fallDistance) {

        getBukkitPlayer().setFallDistance(fallDistance);
    }

    @Override
    public Vector getDirection() {

        return getLocation().getDirection();
    }

    @Override
    public double getLastDamage() {

        return getBukkitPlayer().getLastDamage();
    }

    public EntityDamageEvent getLastDamageCause() {

        return getBukkitPlayer().getLastDamageCause();
    }

    @Override
    public Block getTargetBlock(Set<Material> transparent, int maxDistance) {

        return getBukkitPlayer().getTargetBlock(transparent, maxDistance);
    }

    @Override
    public void setCanPickupItems(boolean pickup) {

        getBukkitPlayer().setCanPickupItems(pickup);
    }

    @Override
    public boolean isCanPickupItems() {

        return getBukkitPlayer().getCanPickupItems();
    }

    @Override
    public void setGlowing(boolean flag) {

        getBukkitPlayer().setGlowing(flag);
    }

    @Override
    public boolean isGlowing() {

        return getBukkitPlayer().isGlowing();
    }

    @Override
    public void setInvulnerable(boolean flag) {

        getBukkitPlayer().setInvulnerable(flag);
    }

    @Override
    public boolean isInvulnerable() {

        return getBukkitPlayer().isInvulnerable();
    }

    @Override
    public boolean isSilent() {

        return getBukkitPlayer().isSilent();
    }

    @Override
    public void setSilent(boolean flag) {

        getBukkitPlayer().setSilent(flag);
    }

    @Override
    public boolean hasGravity() {

        return getBukkitPlayer().hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {

        getBukkitPlayer().setGravity(gravity);
    }

    @Override
    public void setResourcePack(String url) {

        getBukkitPlayer().setResourcePack(url);
    }

    @Override
    public <T extends Projectile> T launcherProjectile(Class<? extends T> projectile) {

        return getBukkitPlayer().launchProjectile(projectile);
    }

    @Override
    public <T extends Projectile> T launcherProjectile(Class<? extends T> projectile, Vector vector) {

        return getBukkitPlayer().launchProjectile(projectile, vector);
    }

    @Override
    public InetSocketAddress getAddress() {

        return getBukkitPlayer().getAddress();
    }

    @Override
    public String getIp() {

        InetAddress address = getAddress().getAddress();

        return address != null ? address.getHostAddress() : "127.0.0.1";
    }

    @Override
    public int getPort() {

        return getAddress().getPort();
    }

    @Override
    public int compareTo(MoonLakePlayer target) {

        return getName().compareTo(target.getName());
    }

    @Override
    public boolean equals(Object object) {

        if(object != null) {

            if(object instanceof MoonLakePlayer) {

                MoonLakePlayer target = (MoonLakePlayer)object;

                return getName().equals(target.getName());
            }
        }
        return false;
    }

    @Override
    public int getPing() {

        return PlayerLibraryFactorys.nmsPlayer().getPing(getName()).get();
    }

    @Override
    public void sendPacket(Packet<?> packet) {

        if(packet != null) {

            packet.send(getName());
        }
    }

    @Override
    public void sendTitlePacket(String title) {

        PlayerLibraryFactorys.nmsPlayer().sendTitlePacket(getName(), StringUtil.toColor(title).get());
    }

    @Override
    public void sendTitlePacket(String title, String subTitle) {

        PlayerLibraryFactorys.nmsPlayer().sendTitlePacket(getName(), StringUtil.toColor(title).get(), StringUtil.toColor(subTitle).get());
    }

    @Override
    public void sendTitlePacket(String title, int fadeIn, int stay, int fadeOut) {

        PlayerLibraryFactorys.nmsPlayer().sendTitlePacket(getName(), StringUtil.toColor(title).get(), fadeIn, stay, fadeOut);
    }

    @Override
    public void sendTitlePacket(String title, String subTitle, int fadeIn, int stay, int fadeOut) {

        PlayerLibraryFactorys.nmsPlayer().sendTitlePacket(getName(), StringUtil.toColor(title).get(), StringUtil.toColor(subTitle).get(), fadeIn, stay, fadeOut);
    }

    @Override
    public void sendMainChatPacket(String message) {

        PlayerLibraryFactorys.nmsPlayer().sendMainChatPacket(getName(), StringUtil.toColor(message).get());
    }

    @Override
    public void sendTabListPacket(String header, String footer) {

        PlayerLibraryFactorys.nmsPlayer().sendTabListPacket(getName(), header, footer);
    }

    @Override
    public void sendItemCooldownPacket(Material type, int tick) {

        PlayerLibraryFactorys.nmsPlayer().sendItemCooldownPacket(getName(), type, tick);
    }

    @Override
    public boolean hasItemCooldown(Material type) {

        return PlayerLibraryFactorys.nmsPlayer().hasItemCooldown(getName(), type).get();
    }
}
