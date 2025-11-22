package com.noty215.hand.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OffhandManager {
    private final Map<UUID, ItemStack> bedrockOffhandItems;
    private final Map<UUID, Boolean> bedrockPlayers;

    public OffhandManager() {
        this.bedrockOffhandItems = new HashMap<>();
        this.bedrockPlayers = new HashMap<>();
    }

    public void addBedrockPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        bedrockPlayers.put(playerId, true);

        // Initialize with empty offhand
        bedrockOffhandItems.put(playerId, null);
    }

    public void removeBedrockPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        bedrockPlayers.remove(playerId);
        bedrockOffhandItems.remove(playerId);
    }

    public boolean isBedrockPlayer(Player player) {
        return bedrockPlayers.containsKey(player.getUniqueId());
    }

    public void setOffhandItem(Player player, ItemStack item) {
        if (!isBedrockPlayer(player)) return;

        UUID playerId = player.getUniqueId();
        bedrockOffhandItems.put(playerId, item);

        // For Bedrock players, we'll simulate offhand in their inventory
        updatePlayerInventory(player);
    }

    public ItemStack getOffhandItem(Player player) {
        return bedrockOffhandItems.get(player.getUniqueId());
    }

    private void updatePlayerInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack offhandItem = getOffhandItem(player);

        // We'll use a specific slot to simulate offhand for Bedrock players
        int offhandSlot = getConfig().getInt("bedrock.offhand-slot", 8);

        if (offhandSlot >= 0 && offhandSlot < 9) {
            inventory.setItem(offhandSlot, offhandItem);
        }
    }

    public void syncOffhandToInventory(Player player) {
        if (!isBedrockPlayer(player)) return;

        PlayerInventory inventory = player.getInventory();
        int offhandSlot = getConfig().getInt("bedrock.offhand-slot", 8);

        ItemStack currentItem = inventory.getItem(offhandSlot);
        setOffhandItem(player, currentItem);
    }

    private org.bukkit.configuration.file.FileConfiguration getConfig() {
        return com.noty215.hand.BedrockOffhandPlugin.getInstance().getConfig();
    }

    public void cleanup() {
        bedrockOffhandItems.clear();
        bedrockPlayers.clear();
    }
}