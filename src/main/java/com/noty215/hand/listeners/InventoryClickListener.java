package com.noty215.hand.listeners;

import com.noty215.hand.BedrockOffhandPlugin;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    private final OffhandManager offhandManager;

    public InventoryClickListener() {
        this.offhandManager = BedrockOffhandPlugin.getInstance().getOffhandManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        // Only handle Bedrock players
        if (!offhandManager.isBedrockPlayer(player)) return;

        // Check if clicking in the offhand slot (slot 40 in player inventory)
        if (isOffhandSlot(event.getSlot())) {
            handleOffhandSlotClick(player, event);
        }

        // Also handle shift-clicking items into offhand
        if (event.isShiftClick() && event.getCurrentItem() != null) {
            handleShiftClickToOffhand(player, event);
        }
    }

    private boolean isOffhandSlot(int slot) {
        // Offhand slot is slot 40 in the player inventory
        return slot == 40;
    }

    private void handleOffhandSlotClick(Player player, InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        // Player placing item in offhand slot
        if (cursorItem != null && !cursorItem.getType().isAir()) {
            offhandManager.setOffhandItem(player, cursorItem);
            player.sendMessage("§aItem placed in offhand: §e" + cursorItem.getType().toString().toLowerCase().replace("_", " "));
        }
        // Player taking item from offhand slot
        else if (clickedItem != null && !clickedItem.getType().isAir()) {
            offhandManager.setOffhandItem(player, null);
            player.sendMessage("§cItem removed from offhand");
        }
        // Player swapping items
        else if (clickedItem != null && cursorItem != null) {
            offhandManager.setOffhandItem(player, cursorItem);
            player.sendMessage("§aItem swapped in offhand: §e" + cursorItem.getType().toString().toLowerCase().replace("_", " "));
        }
    }

    private void handleShiftClickToOffhand(Player player, InventoryClickEvent event) {
        // Check if player is shift-clicking an item from their inventory
        // and the offhand slot is empty
        ItemStack currentOffhand = offhandManager.getOffhandItem(player);
        ItemStack clickedItem = event.getCurrentItem();

        if ((currentOffhand == null || currentOffhand.getType().isAir()) &&
                clickedItem != null && !clickedItem.getType().isAir()) {

            // Only allow shift-click for compatible offhand items
            if (isCompatibleOffhandItem(clickedItem)) {
                offhandManager.setOffhandItem(player, clickedItem.clone());
                event.setCurrentItem(null); // Remove from original slot
                player.sendMessage("§aItem moved to offhand: §e" + clickedItem.getType().toString().toLowerCase().replace("_", " "));
            }
        }
    }

    private boolean isCompatibleOffhandItem(ItemStack item) {
        // Allow most items in offhand, but restrict some that don't make sense
        Material type = item.getType();

        // Don't allow blocks to be placed in offhand (they should be used in main hand)
        if (type.isBlock()) {
            return false;
        }

        // Allow these item types in offhand
        return true;
    }
}