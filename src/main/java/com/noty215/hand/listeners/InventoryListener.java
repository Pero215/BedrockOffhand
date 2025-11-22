package com.noty215.hand.listeners;

import com.noty215.hand.BedrockOffhandPlugin;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    private final OffhandManager offhandManager;

    public InventoryListener() {
        this.offhandManager = BedrockOffhandPlugin.getInstance().getOffhandManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        if (!offhandManager.isBedrockPlayer(player)) return;

        // Check if the click is in the offhand slot (hotbar slot 8)
        int offhandSlot = BedrockOffhandPlugin.getInstance().getConfig().getInt("bedrock.offhand-slot", 8);

        // For Spigot, hotbar slots are 0-8 in the inventory view
        if (event.getSlot() == offhandSlot && event.getSlot() < 9) {
            // Handle offhand slot interaction for Bedrock players
            ItemStack newItem = event.getCursor();
            offhandManager.setOffhandItem(player, newItem);

            if (BedrockOffhandPlugin.getInstance().getConfig().getBoolean("bedrock.prevent-offhand-move", false)) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }
}