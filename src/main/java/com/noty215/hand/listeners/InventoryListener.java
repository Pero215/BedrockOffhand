package com.noty215.hand.listeners;

import com.noty215.hand.BedrockOffhandPlugin;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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

        // Check if the click is in the offhand slot
        int offhandSlot = BedrockOffhandPlugin.getInstance().getConfig().getInt("bedrock.offhand-slot", 8);

        if (event.getSlot() == offhandSlot && event.getSlotType() == InventoryType.SlotType.QUICKBAR) {
            // Handle offhand slot interaction for Bedrock players
            ItemStack newItem = event.getCursor();
            offhandManager.setOffhandItem(player, newItem);

            // Optional: Cancel event if you want custom handling
            if (BedrockOffhandPlugin.getInstance().getConfig().getBoolean("bedrock.prevent-offhand-move", false)) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }
}