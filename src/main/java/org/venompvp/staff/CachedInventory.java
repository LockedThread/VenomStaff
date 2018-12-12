package org.venompvp.staff;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CachedInventory {

    private ItemStack[] inventoryContents;
    private ItemStack[] armorContents;

    public CachedInventory(ItemStack[] inventoryContents, ItemStack[] armorContents) {
        this.inventoryContents = inventoryContents;
        this.armorContents = armorContents;
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public void setInventoryContents(ItemStack[] inventoryContents) {
        this.inventoryContents = inventoryContents;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public void setArmorContents(ItemStack[] armorContents) {
        this.armorContents = armorContents;
    }

    public void restoreInventory(Player player) {
        player.getInventory().setContents(inventoryContents);
        player.getInventory().setArmorContents(armorContents);
        player.updateInventory();
    }
}
