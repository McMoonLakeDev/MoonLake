package com.minecraft.moonlake.api.player;

import com.minecraft.moonlake.api.fancy.FancyMessage;
import com.minecraft.moonlake.nms.packet.Packet;
import com.minecraft.moonlake.manager.PlayerManager;
import com.minecraft.moonlake.property.*;
import com.minecraft.moonlake.util.StringUtil;
import com.minecraft.moonlake.validate.Validate;
import com.mojang.authlib.GameProfile;
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
 * <hr />
 * <div>
 *     <h1>Minecraft MoonLake Player Wrapped Abstract</h1>
 *     <p>By Month_Light Ver: 1.7</p>
 * </div>
 * <hr />
 *
 * @version 1.7
 * @author Month_Light
 * @see MoonLakePlayer
 */
public abstract class AbstractPlayer implements MoonLakePlayer {

    private final ReadOnlyStringProperty nameProperty;
    private final ReadOnlyObjectProperty<Player> playerProperty;

    public AbstractPlayer(String name) {

        this(Bukkit.getPlayer(name));
    }

    public AbstractPlayer(Player player) {

        this.nameProperty = new SimpleStringProperty(player.getName());
        this.playerProperty = new SimpleObjectProperty<>(player);
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
    public ReadOnlyStringProperty getNames() {

        return nameProperty;
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
    public ReadOnlyStringProperty getDisplayName() {

        return new SimpleStringProperty(getBukkitPlayer().getDisplayName());
    }

    @Override
    public void setDisplayName(String displayName) {

        getBukkitPlayer().setDisplayName(displayName);
    }

    @Override
    public ReadOnlyStringProperty getListName() {

        return new SimpleStringProperty(getBukkitPlayer().getPlayerListName());
    }

    @Override
    public void setListName(String listName) {

        getBukkitPlayer().setPlayerListName(listName);
    }

    @Override
    public void chat(String message) {

        Validate.notNull(message, "The chat message object is null.");

        getBukkitPlayer().chat(message);
    }

    @Override
    public void send(String message) {

        getBukkitPlayer().sendMessage(StringUtil.toColor(message).get());
    }

    @Override
    public void send(String[] message) {

        getBukkitPlayer().sendMessage(StringUtil.toColor(message).get());
    }

    @Override
    public void send(FancyMessage fancyMessage) {

        Validate.notNull(fancyMessage, "The fancy message object is null.");

        fancyMessage.send(getBukkitPlayer());
    }

    @Override
    public void send(FancyMessage... fancyMessages) {

        Validate.notNull(fancyMessages, "The fancy message object is null.");

        for(FancyMessage fancyMessage : fancyMessages) {

            send(fancyMessage);
        }
    }

    @Override
    public ReadOnlyDoubleProperty getHealth() {

        return new SimpleDoubleProperty(getBukkitPlayer().getHealth());
    }

    @Override
    public ReadOnlyDoubleProperty getMaxHealth() {

        return new SimpleDoubleProperty(getBukkitPlayer().getMaxHealth());
    }

    @Override
    public void giveHealth(double amount) {

        if(getHealth().get() + amount >= getMaxHealth().get()) {

            setHealth(getMaxHealth().get());
            return;
        }
        setHealth(getHealth().add(amount).get());
    }

    @Override
    public void takeHealth(double amount) {

        if(getHealth().get() - amount <= 0d) {

            setHealth(0d);
            return;
        }
        setHealth(getHealth().subtract(amount).get());
    }

    @Override
    public ReadOnlyFloatProperty getXp() {

        return new SimpleFloatProperty(getBukkitPlayer().getExp());
    }

    @Override
    public ReadOnlyIntegerProperty getLevel() {

        return new SimpleIntegerProperty(getBukkitPlayer().getLevel());
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

        setXp(getXp().add(xp).get());
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
    public ReadOnlyFloatProperty getFlySpeed() {

        return new SimpleFloatProperty(getBukkitPlayer().getFlySpeed());
    }

    @Override
    public ReadOnlyIntegerProperty getFoodLevel() {

        return new SimpleIntegerProperty(getBukkitPlayer().getFoodLevel());
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
    public ReadOnlyBooleanProperty hasBeforePlayed() {

        return new SimpleBooleanProperty(getBukkitPlayer().hasPlayedBefore());
    }

    @Override
    public ReadOnlyBooleanProperty isOnGround() {

        return new SimpleBooleanProperty(getBukkitPlayer().isOnGround());
    }

    @Override
    public ReadOnlyBooleanProperty isFly() {

        return new SimpleBooleanProperty(getBukkitPlayer().isFlying());
    }

    @Override
    public ReadOnlyBooleanProperty isAllowFly() {

        return new SimpleBooleanProperty(getBukkitPlayer().getAllowFlight());
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
    public ReadOnlyFloatProperty getWalkSpeed() {

        return new SimpleFloatProperty(getBukkitPlayer().getWalkSpeed());
    }

    @Override
    public void setWalkSpeed(float speed) {

        getBukkitPlayer().setWalkSpeed(speed);
    }

    @Override
    public ReadOnlyIntegerProperty getX() {

        return new SimpleIntegerProperty(getLocation().getBlockX());
    }

    @Override
    public ReadOnlyIntegerProperty getY() {

        return new SimpleIntegerProperty(getLocation().getBlockY());
    }

    @Override
    public ReadOnlyIntegerProperty getZ() {

        return new SimpleIntegerProperty(getLocation().getBlockZ());
    }

    @Override
    public ReadOnlyDoubleProperty getDoubleX() {

        return new SimpleDoubleProperty(getLocation().getX());
    }

    @Override
    public ReadOnlyDoubleProperty getDoubleY() {

        return new SimpleDoubleProperty(getLocation().getY());
    }

    @Override
    public ReadOnlyDoubleProperty getDoubleZ() {

        return new SimpleDoubleProperty(getLocation().getZ());
    }

    @Override
    public void teleport(Entity entity) {

        getBukkitPlayer().teleport(entity);
    }

    @Override
    public void teleport(MoonLakePlayer player) {

        Validate.notNull(player, "The teleport player object is null.");

        teleport(player.getLocation());
    }

    @Override
    public void teleport(Location location) {

        getBukkitPlayer().teleport(location);
    }

    @Override
    public void teleport(World world) {

        Validate.notNull(world, "The teleport world object is null.");

        teleport(world.getSpawnLocation());
    }

    @Override
    public void teleport(String world) {

        Validate.notNull(world, "The teleport world string object is null.");

        World target = Bukkit.getServer().getWorld(world);

        teleport(target);
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
    public void teleport(String world, int x, int y, int z) {

        teleport(world, (double)x, (double)y, (double)z);
    }

    @Override
    public void teleport(World world, double x, double y, double z) {

        getBukkitPlayer().teleport(new Location(world, x, y, z));
    }

    @Override
    public void teleport(String world, double x, double y, double z) {

        Validate.notNull(world, "The teleport world string object is null.");

        World target = Bukkit.getServer().getWorld(world);

        teleport(target, x, y, z);
    }

    @Override
    public Block getTargetBlock(int distance) {

        return getTargetBlock(null, distance);
    }

    @Override
    public void playSound(String sound, float volume, float pitch) {

        Validate.notNull(sound, "The sound string object is null.");

        getBukkitPlayer().playSound(getLocation(), sound, volume, pitch);
    }

    @Override
    public void playSound(Sound sound, float volume, float pitch) {

        Validate.notNull(sound, "The sound object is null.");

        getBukkitPlayer().playSound(getLocation(), sound, volume, pitch);
    }

    @Override
    public void playNote(Instrument instrument, Note note) {

        Validate.notNull(instrument, "The instrument object is null.");
        Validate.notNull(note, "The note object is null.");

        getBukkitPlayer().playNote(getLocation(), instrument, note);
    }

    @Override
    public void stopSound(String sound) {

        Validate.notNull(sound, "The sound string object is null.");

        getBukkitPlayer().stopSound(sound);
    }

    @Override
    public void stopSound(Sound sound) {

        Validate.notNull(sound, "The sound object is null.");

        getBukkitPlayer().stopSound(sound);
    }

    @Override
    public ReadOnlyDoubleProperty distance(Location target) {

        Validate.notNull(target, "The distance target object is null.");

        return new SimpleDoubleProperty(getLocation().distance(target));
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
    public void setItemInMainHand(ItemStack itemStack) {

        getInventory().setItemInMainHand(itemStack);
    }

    @Override
    public void setItemInOffHand(ItemStack itemStack) {

        getInventory().setItemInOffHand(itemStack);
    }

    @Override
    public void openInventory(Inventory inventory) {

        getBukkitPlayer().openInventory(inventory);
    }

    @Override
    public Inventory getTopInventory() {

        return getBukkitPlayer().getOpenInventory() != null ? getBukkitPlayer().getOpenInventory().getTopInventory() : null;
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time) {

        Validate.notNull(type, "The potion effect type object is null.");

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1));
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient) {

        Validate.notNull(type, "The potion effect type object is null.");

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient));
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles) {

        Validate.notNull(type, "The potion effect type object is null.");

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles));
    }

    @Override
    public void addPotionEffect(PotionEffectType type, int level, int time, boolean ambient, boolean particles, Color color) {

        Validate.notNull(type, "The potion effect type object is null.");

        getBukkitPlayer().addPotionEffect(new PotionEffect(type, time, level - 1, ambient, particles, color));
    }

    @Override
    public ReadOnlyBooleanProperty hasPotionEffect(PotionEffectType type) {

        Validate.notNull(type, "The potion effect type object is null.");

        return new SimpleBooleanProperty(getBukkitPlayer().hasPotionEffect(type));
    }

    @Override
    public void removePotionEffect(PotionEffectType type) {

        Validate.notNull(type, "The potion effect type object is null.");

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
    public ReadOnlyBooleanProperty hasPermission(String permission) {

        Validate.notNull(permission, "The permission string object is null.");

        return new SimpleBooleanProperty(getBukkitPlayer().hasPermission(permission));
    }

    @Override
    public void onKick() {

        onKick("无");
    }

    @Override
    public void onKick(String message) {

        Validate.notNull(message, "The message string object is null.");

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
    public ReadOnlyBooleanProperty isShift() {

        return new SimpleBooleanProperty(getBukkitPlayer().isSneaking());
    }

    @Override
    public ReadOnlyBooleanProperty isSprinting() {

        return new SimpleBooleanProperty(getBukkitPlayer().isSprinting());
    }

    @Override
    public ReadOnlyBooleanProperty isOp() {

        return new SimpleBooleanProperty(getBukkitPlayer().isOp());
    }

    @Override
    public void setVelocity(Vector vector) {

        Validate.notNull(vector, "The vector object is null.");

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

        getWorld().createExplosion(getX().get(), getY().get(), getZ().get(), power, setFire, breakBlock);
    }

    @Override
    public ReadOnlyBooleanProperty performCommand(String command) {

        Validate.notNull(command, "The preform command string object is null.");

        return new SimpleBooleanProperty(getBukkitPlayer().performCommand(command));
    }

    @Override
    public void onHide(Player target) {

        Validate.notNull(target, "The hide player target object is null.");

        getBukkitPlayer().hidePlayer(target);
    }

    @Override
    public void onHide(MoonLakePlayer target) {

        Validate.notNull(target, "The hide moonlake player target object is null.");

        onHide(target.getBukkitPlayer());
    }

    @Override
    public void onHide(String target) {

        onHide(PlayerManager.fromName(target));
    }

    @Override
    public void onShow(Player target) {

        Validate.notNull(target, "The show moonlake player target object is null.");

        target.showPlayer(target);
    }

    @Override
    public void onShow(MoonLakePlayer target) {

        Validate.notNull(target, "The show moonlake player target object is null.");

        onShow(target.getBukkitPlayer());
    }

    @Override
    public void onShow(String target) {

        onShow(PlayerManager.fromName(target));
    }

    @Override
    public ReadOnlyBooleanProperty canSee(Player target) {

        Validate.notNull(target, "The can see player target object is null.");

        return new SimpleBooleanProperty(getBukkitPlayer().canSee(target));
    }

    @Override
    public ReadOnlyBooleanProperty canSee(MoonLakePlayer target) {

        Validate.notNull(target, "The can see moonlake player target object is null.");

        return canSee(target.getBukkitPlayer());
    }

    @Override
    public ReadOnlyBooleanProperty canSee(String target) {

        return canSee(PlayerManager.fromName(target));
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

        Validate.notNull(weather, "The weather type object is null.");

        getBukkitPlayer().setPlayerWeather(weather);
    }

    @Override
    public WeatherType getWeather() {

        return getBukkitPlayer().getPlayerWeather();
    }

    @Override
    public ReadOnlyLongProperty getTime() {

        return new SimpleLongProperty(getBukkitPlayer().getPlayerTime());
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

        Validate.notNull(mode, "The game mode type object is null.");

        getBukkitPlayer().setGameMode(mode);
    }

    @Override
    public void resetMaxHealth() {

        getBukkitPlayer().resetMaxHealth();
    }

    @Override
    public ReadOnlyBooleanProperty isGliding() {

        return new SimpleBooleanProperty(getBukkitPlayer().isGliding());
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
    public ReadOnlyFloatProperty getFallDistance() {

        return new SimpleFloatProperty(getBukkitPlayer().getFallDistance());
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
    public ReadOnlyDoubleProperty getLastDamage() {

        return new SimpleDoubleProperty(getBukkitPlayer().getLastDamage());
    }

    public EntityDamageEvent getLastDamageCause() {

        return getBukkitPlayer().getLastDamageCause();
    }

    @Override
    public Block getTargetBlock(Set<Material> transparent, int maxDistance) {

        Validate.notNull(transparent, "The transparent collection object is null.");

        return getBukkitPlayer().getTargetBlock(transparent, maxDistance);
    }

    @Override
    public void setCanPickupItems(boolean pickup) {

        getBukkitPlayer().setCanPickupItems(pickup);
    }

    @Override
    public ReadOnlyBooleanProperty isCanPickupItems() {

        return new SimpleBooleanProperty(getBukkitPlayer().getCanPickupItems());
    }

    @Override
    public void setGlowing(boolean flag) {

        getBukkitPlayer().setGlowing(flag);
    }

    @Override
    public ReadOnlyBooleanProperty isGlowing() {

        return new SimpleBooleanProperty(getBukkitPlayer().isGlowing());
    }

    @Override
    public void setInvulnerable(boolean flag) {

        getBukkitPlayer().setInvulnerable(flag);
    }

    @Override
    public ReadOnlyBooleanProperty isInvulnerable() {

        return new SimpleBooleanProperty(getBukkitPlayer().isInvulnerable());
    }

    @Override
    public ReadOnlyBooleanProperty isSilent() {

        return new SimpleBooleanProperty(getBukkitPlayer().isSilent());
    }

    @Override
    public void setSilent(boolean flag) {

        getBukkitPlayer().setSilent(flag);
    }

    @Override
    public ReadOnlyBooleanProperty hasGravity() {

        return new SimpleBooleanProperty(getBukkitPlayer().hasGravity());
    }

    @Override
    public void setGravity(boolean gravity) {

        getBukkitPlayer().setGravity(gravity);
    }

    @Override
    public void setResourcePack(String url) {

        Validate.notNull(url, "The resource pack url string object is null.");

        getBukkitPlayer().setResourcePack(url);
    }

    @Override
    public <T extends Projectile> T launcherProjectile(Class<? extends T> projectile) {

        Validate.notNull(projectile, "The projectile class object is null.");

        return getBukkitPlayer().launchProjectile(projectile);
    }

    @Override
    public <T extends Projectile> T launcherProjectile(Class<? extends T> projectile, Vector vector) {

        Validate.notNull(projectile, "The projectile class object is null.");
        Validate.notNull(vector, "The projectile vector object is null.");

        return getBukkitPlayer().launchProjectile(projectile, vector);
    }

    @Override
    public InetSocketAddress getAddress() {

        return getBukkitPlayer().getAddress();
    }

    @Override
    public ReadOnlyStringProperty getIp() {

        InetAddress address = getAddress().getAddress();

        return new SimpleStringProperty(address != null ? address.getHostAddress() : "127.0.0.1");
    }

    @Override
    public ReadOnlyIntegerProperty getPort() {

        return new SimpleIntegerProperty(getAddress().getPort());
    }

    @Override
    public int compareTo(MoonLakePlayer target) {

        return getName().compareTo(target == null ? null : target.getName());
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
    public ReadOnlyIntegerProperty getPing() {

        return PlayerLibraryFactorys.nmsPlayer().getPing(getName());
    }

    @Override
    public void sendPacket(Packet<?> packet) {

        Validate.notNull(packet, "The packet object is null.");

        packet.send(getBukkitPlayer());
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
    public void sendTabListPacket(String header) {

        PlayerLibraryFactorys.nmsPlayer().sendTabListPacket(getName(), header);
    }

    @Override
    public void sendTabListPacket(String header, String footer) {

        PlayerLibraryFactorys.nmsPlayer().sendTabListPacket(getName(), header, footer);
    }

    @Override
    public void setItemCooldown(Material type, int tick) {

        PlayerLibraryFactorys.player().setItemCooldown(getName(), type, tick);
    }

    @Override
    public ReadOnlyBooleanProperty hasItemCooldown(Material type) {

        return PlayerLibraryFactorys.player().hasItemCooldown(getName(), type);
    }
}
