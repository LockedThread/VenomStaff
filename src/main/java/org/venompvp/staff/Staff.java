package org.venompvp.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.venompvp.staff.commands.FreezeCommand;
import org.venompvp.staff.commands.ReportCommand;
import org.venompvp.staff.commands.StaffChatCommand;
import org.venompvp.staff.commands.StaffModeCommand;
import org.venompvp.staff.enums.Messages;
import org.venompvp.staff.listeners.EntityListener;
import org.venompvp.staff.objs.StaffPlayer;
import org.venompvp.venom.module.Module;
import org.venompvp.venom.module.ModuleInfo;
import org.venompvp.venom.utils.Utils;

import java.util.*;

@ModuleInfo(name = "Staff", author = "LilProteinShake", version = "1.0", description = "Administration features")
public class Staff extends Module {

    private static Staff instance;
    private HashMap<UUID, StaffPlayer> staffPlayers = new HashMap<>();
    private ArrayList<UUID> frozenPlayers = new ArrayList<>();
    private ItemStack randomPlayerItemStack, freezePlayerItemStack, vanishItemStack;

    public static Staff getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        setupModule(this);
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        Arrays.stream(Messages.values()).forEach(message -> {
            if (getConfig().isSet("messages." + message.getKey())) {
                message.setMessage(getConfig().getString("messages." + message.getKey()));
            } else {
                getConfig().set("messages." + message.getKey(), message.getValue());
            }
        });
        saveConfig();

        randomPlayerItemStack = getVenom().itemStackFromConfig(getConfig(), "staffmode.random-player-item");
        freezePlayerItemStack = getVenom().itemStackFromConfig(getConfig(), "staffmode.freeze-player-item");
        vanishItemStack = getVenom().itemStackFromConfig(getConfig(), "staffmode.vanish-item");

        getCommandHandler().register(this, new FreezeCommand(this));
        getCommandHandler().register(this, new StaffModeCommand(this));
        getCommandHandler().register(this, new StaffChatCommand(this));
        getCommandHandler().register(this, new ReportCommand(this));
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
    }

    @Override
    public void onDisable() {
        staffPlayers.values().stream().filter(StaffPlayer::isStaffMode).forEach(StaffPlayer::removeStaffMode);
        frozenPlayers.stream().map(uuid -> getServer().getPlayer(uuid)).forEach(player -> player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType())));
    }

    public ArrayList<UUID> getFrozenPlayers() {
        return frozenPlayers;
    }

    public void unfreeze(Player target) {
        broadcast(Messages.BROADCAST_UNFROZEN, Collections.singletonMap("{player}", target.getName()), target);
        target.sendMessage(Messages.YOU_ARE_UNFROZEN.toString());
        getConfig().getStringList("frozen.potion-effects").stream().map(s -> s.split("#")).map(potionParameters -> PotionEffectType.getByName(potionParameters[0].toUpperCase())).forEach(target::removePotionEffect);
        getFrozenPlayers().remove(target.getUniqueId());
    }

    public void freeze(Player target) {
        target.closeInventory();
        getFrozenPlayers().add(target.getUniqueId());
        broadcast(Messages.BROADCAST_FROZEN, Collections.singletonMap("{player}", target.getName()), target);
        target.sendMessage(Messages.YOU_ARE_FROZEN.toString());
        if (getConfig().getBoolean("frozen.potion-effects-enabled")) {
            getConfig().getStringList("frozen.potion-effects").stream().map(s -> s.split("#")).forEach(potionParameters -> {
                PotionEffectType potionEffectType = PotionEffectType.getByName(potionParameters[0].toUpperCase());
                if (potionEffectType == null) {
                    throw new RuntimeException(potionParameters[0] + " is unable to be parsed as a PotionEffectType. Contact Lil Protein Shake");
                } else if (!Utils.isInt(potionParameters[1])) {
                    throw new NumberFormatException(potionParameters[1] + " is unable to parse as an Integer. Contact Lil Protein Shake");
                } else {
                    int amplifier = Integer.parseInt(potionParameters[1]);
                    target.addPotionEffect(new PotionEffect(potionEffectType, Integer.MAX_VALUE, amplifier));
                }
            });
        }
    }

    private void broadcast(Messages message, Map<String, String> placeholders, Player... blacklistedPlayers) {
        getServer().getOnlinePlayers().forEach(player -> Arrays.stream(blacklistedPlayers).filter(player1 -> !player.getUniqueId().toString().equals(player1.getUniqueId().toString())).forEach((player1) -> {
            String s = message.toString();
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                s = s.replace(entry.getKey(), entry.getValue());
            }
            player.sendMessage(s);
        }));
    }

    public StaffPlayer getStaffPlayer(Player target) {
        return staffPlayers.getOrDefault(target.getUniqueId(), null);
    }

    public Optional<? extends Player> getRandomPlayer(String bypassPermission) {
        return Bukkit.getOnlinePlayers().stream().filter(o -> !o.hasPermission(bypassPermission)).skip(getVenom().random.nextInt(Bukkit.getOnlinePlayers().size() - 1)).findFirst();
    }

    public ItemStack getRandomPlayerItemStack() {
        return randomPlayerItemStack;
    }

    public ItemStack getFreezePlayerItemStack() {
        return freezePlayerItemStack;
    }

    public ItemStack getVanishItemStack() {
        return vanishItemStack;
    }

    public HashMap<UUID, StaffPlayer> getStaffPlayers() {
        return staffPlayers;
    }
}
