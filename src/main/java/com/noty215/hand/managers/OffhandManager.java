package com.noty215.hand.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OffhandManager {
    private final Map<UUID, ItemStack> bedrockOffhandItems;
    private final Map<UUID, Boolean> bedrockPlayers;
    private final Map<UUID, Long> lastOffhandUse;

    public OffhandManager() {
        this.bedrockOffhandItems = new HashMap<>();
        this.bedrockPlayers = new HashMap<>();
        this.lastOffhandUse = new HashMap<>();
    }

    public void addBedrockPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        bedrockPlayers.put(playerId, true);
        bedrockOffhandItems.put(playerId, new ItemStack(Material.AIR));
        lastOffhandUse.put(playerId, 0L);
    }

    public void removeBedrockPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        bedrockPlayers.remove(playerId);
        bedrockOffhandItems.remove(playerId);
        lastOffhandUse.remove(playerId);
    }

    public boolean isBedrockPlayer(Player player) {
        return bedrockPlayers.containsKey(player.getUniqueId());
    }

    public ItemStack getOffhandItem(Player player) {
        return bedrockOffhandItems.getOrDefault(player.getUniqueId(), new ItemStack(Material.AIR));
    }

    public boolean setOffhandItem(Player player, ItemStack item) {
        if (!isBedrockPlayer(player)) return false;

        UUID playerId = player.getUniqueId();
        bedrockOffhandItems.put(playerId, item != null ? item.clone() : new ItemStack(Material.AIR));

        // Update player's inventory display if using visual slot
        if (getConfig().getBoolean("visual-slot.enabled", true)) {
            updateVisualOffhandSlot(player, item);
        }

        return true;
    }

    public boolean canUseOffhand(Player player) {
        UUID playerId = player.getUniqueId();
        long lastUse = lastOffhandUse.getOrDefault(playerId, 0L);
        long cooldown = getConfig().getLong("offhand.cooldown-ticks", 5L);

        return (System.currentTimeMillis() - lastUse) > (cooldown * 50); // Convert ticks to ms
    }

    public void setLastOffhandUse(Player player) {
        lastOffhandUse.put(player.getUniqueId(), System.currentTimeMillis());
    }

    private void updateVisualOffhandSlot(Player player, ItemStack item) {
        PlayerInventory inventory = player.getInventory();
        int visualSlot = getConfig().getInt("visual-slot.slot", 8);

        if (visualSlot >= 0 && visualSlot < 9) {
            inventory.setItem(visualSlot, item);
        }
    }

    private org.bukkit.configuration.file.FileConfiguration getConfig() {
        return com.noty215.hand.BedrockOffhandPlugin.getInstance().getConfig();
    }

    public void cleanup() {
        bedrockOffhandItems.clear();
        bedrockPlayers.clear();
        lastOffhandUse.clear();
    }
}