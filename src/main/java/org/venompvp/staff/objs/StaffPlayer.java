package org.venompvp.staff.objs;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.venompvp.staff.CachedInventory;
import org.venompvp.staff.Staff;

import java.util.UUID;

public class StaffPlayer {

    private UUID uuid;
    private CachedInventory cachedInventory;
    private boolean vanish, staffChat, staffMode;
    private Location previousLocation;

    public StaffPlayer(UUID uuid) {
        this.uuid = uuid;
        this.vanish = false;
        this.staffChat = false;
        this.staffMode = false;
    }

    public void addStaffMode() {
        if (!staffMode) {
            this.cachedInventory = new CachedInventory(getPlayer().getInventory().getContents(), getPlayer().getInventory().getArmorContents());
            this.previousLocation = getPlayer().getLocation();
            this.vanish = true;
            this.staffMode = true;
            getPlayer().getInventory().clear();
            getPlayer().setGameMode(GameMode.CREATIVE);
            getPlayer().getInventory().setItem(0, Staff.getInstance().getVanishItemStack());
            getPlayer().getInventory().setItem(1, Staff.getInstance().getFreezePlayerItemStack());
            getPlayer().getInventory().setItem(2, Staff.getInstance().getRandomPlayerItemStack());
        }
    }

    public void removeStaffMode() {
        if (staffMode) {
            getPlayer().teleport(previousLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
            getPlayer().setGameMode(GameMode.SURVIVAL);
            getPlayer().getInventory().clear();
            cachedInventory.restoreInventory(getPlayer());
            this.cachedInventory = null;
            this.previousLocation = null;
            this.vanish = false;
            this.staffMode = false;
        }
    }

    public void toggleVanish() {
        vanish = !vanish;
        if (vanish) {
            Bukkit.getOnlinePlayers().stream().filter(player -> !player.getUniqueId().toString().equals(getPlayer().getUniqueId().toString())).forEach(player -> player.hidePlayer(getPlayer()));
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(getPlayer()));
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public CachedInventory getCachedInventory() {
        return cachedInventory;
    }

    public void setCachedInventory(CachedInventory cachedInventory) {
        this.cachedInventory = cachedInventory;
    }

    public boolean isVanish() {
        return vanish;
    }

    public void setVanish(boolean vanish) {
        this.vanish = vanish;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    public boolean isStaffChat() {
        return staffChat;
    }

    public void setStaffChat(boolean staffChat) {
        this.staffChat = staffChat;
    }

    public boolean isStaffMode() {
        return staffMode;
    }

    public void setStaffMode(boolean staffMode) {
        this.staffMode = staffMode;
    }

}
