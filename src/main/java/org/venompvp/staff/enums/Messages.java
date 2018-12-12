package org.venompvp.staff.enums;

import org.bukkit.ChatColor;

public enum Messages {

    YOU_FROZE("&a&l(!) &aYou've frozen &e{player}!"),
    YOU_UNFROZE("&a&l(!) &aYou've unfrozen &e{player}!"),
    YOU_ARE_UNFROZEN("&e&l(!) &eYou have been unfrozen."),
    CANT_FREEZE_YOURSELF("&cWhat are you stupid? You can't freeze yourself."),

    BROADCAST_FROZEN("&c{player} has been frozen!"),
    BROADCAST_UNFROZEN("&e{player} has been unfrozen!"),

    YOU_ARE_FROZEN("&c&l(!) &cYou are frozen!"),
    NO_PERMISSION("&c&l(!) &cYou don't have permission to do this!"),

    RANDOM_TELEPORT("&eYou have teleported to {player}"),
    RANDOM_TELEPORT_ERROR("&c&l(!) &cError teleporting."),

    STAFFCHAT_ENABLE("&eYou have enabled staffchat!"),
    STAFFCHAT_DISABLE("&eYou have disabled staffchat!"),
    STAFFCHAT_FORMAT("{player}&f> &e{message}"),

    NO_STAFF_ONLINE("&cSorry, unable to execute /report due to staff not being online!");

    private String message;

    Messages(String message) {
        this.message = message;
    }

    public String getKey() {
        return name().toLowerCase().replace("_", "-");
    }

    public String getValue() {
        return message;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
