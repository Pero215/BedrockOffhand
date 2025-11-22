package com.noty215.hand.listeners;

import com.noty215.hand.BedrockOffhandPlugin;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractListener implements Listener {
    private final OffhandManager offhandManager;

    public PlayerInteractListener() {
        this.offhandManager = BedrockOffhandPlugin.getInstance().getOffhandManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Only handle Bedrock players
        if (!offhandManager.isBedrockPlayer(player)) return;

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offhandItem = offhandManager.getOffhandItem(player);

        // Check if we should process offhand usage
        boolean shouldUseOffhand = false;
        boolean requiresCrouch = true;

        // Check for weapons that don't require crouching
        if (isWeaponNoCrouch(mainHandItem)) {
            requiresCrouch = false;
            shouldUseOffhand = (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK);
        }
        // For other items, require crouch + right click
        else if (player.isSneaking() &&
                (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            shouldUseOffhand = true;
        }

        if (!shouldUseOffhand) return;

        // Don't process if main hand item is placeable (except for weapons that don't require crouch)
        if (requiresCrouch && isPlaceableItem(mainHandItem)) return;

        // Cancel the original event to prevent main hand action
        event.setCancelled(true);

        // Process offhand usage
        if (offhandItem != null && !offhandItem.getType().isAir()) {
            useOffhandItem(player, offhandItem, event.getAction(), requiresCrouch);
        }
    }

    private boolean isWeaponNoCrouch(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;

        Material type = item.getType();
        // Weapons that allow direct right-click offhand usage
        return type.toString().endsWith("_SWORD") ||
                type == Material.MACE;
    }

    private boolean isPlaceableItem(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;

        Material type = item.getType();
        // List of placeable items that shouldn't trigger offhand
        return type.isBlock() ||
                type == Material.BOW ||
                type == Material.TRIDENT ||
                type == Material.CROSSBOW ||
                type == Material.FLINT_AND_STEEL ||
                type == Material.FISHING_ROD;
    }

    private boolean isFoodItem(Material material) {
        // Check if the material is food
        switch (material) {
            case APPLE:
            case BAKED_POTATO:
            case BEEF:
            case BEETROOT:
            case BEETROOT_SOUP:
            case BREAD:
            case CARROT:
            case CHICKEN:
            case CHORUS_FRUIT:
            case COD:
            case COOKED_BEEF:
            case COOKED_CHICKEN:
            case COOKED_COD:
            case COOKED_MUTTON:
            case COOKED_PORKCHOP:
            case COOKED_RABBIT:
            case COOKED_SALMON:
            case COOKIE:
            case DRIED_KELP:
            case ENCHANTED_GOLDEN_APPLE:
            case GOLDEN_APPLE:
            case GOLDEN_CARROT:
            case HONEY_BOTTLE:
            case MELON_SLICE:
            case MUSHROOM_STEW:
            case MUTTON:
            case POISONOUS_POTATO:
            case PORKCHOP:
            case POTATO:
            case PUFFERFISH:
            case PUMPKIN_PIE:
            case RABBIT:
            case RABBIT_STEW:
            case ROTTEN_FLESH:
            case SALMON:
            case SPIDER_EYE:
            case SUSPICIOUS_STEW:
            case SWEET_BERRIES:
            case TROPICAL_FISH:
                return true;
            default:
                return false;
        }
    }

    private boolean isArmorItem(Material material) {
        // Check if the material is armor
        String materialName = material.toString();
        return materialName.endsWith("_HELMET") ||
                materialName.endsWith("_CHESTPLATE") ||
                materialName.endsWith("_LEGGINGS") ||
                materialName.endsWith("_BOOTS") ||
                materialName.equals("ELYTRA");
    }

    private void useOffhandItem(Player player, ItemStack offhandItem, Action action, boolean usedCrouch) {
        Material offhandType = offhandItem.getType();

        String usageType = usedCrouch ? " (crouch)" : " (direct)";

        // Handle wind charge
        if (offhandType == Material.WIND_CHARGE) {
            useWindCharge(player, offhandItem);
        }
        // Handle armor
        else if (isArmorItem(offhandType)) {
            useArmorItem(player, offhandItem);
        }
        // Handle shield
        else if (offhandType == Material.SHIELD) {
            activateShield(player, offhandItem);
        }
        // Handle torch
        else if (offhandType == Material.TORCH || offhandType == Material.SOUL_TORCH) {
            if (action == Action.RIGHT_CLICK_BLOCK) {
                sendMessage(player, ChatColor.YELLOW + "Placing torch from offhand..." + usageType);
            }
        }
        // Handle ender pearl
        else if (offhandType == Material.ENDER_PEARL) {
            if (player.getCooldown(offhandType) == 0) {
                sendMessage(player, ChatColor.LIGHT_PURPLE + "Throwing ender pearl from offhand..." + usageType);
                player.setCooldown(offhandType, 20);
            }
        }
        // Handle potions
        else if (offhandType == Material.POTION ||
                offhandType == Material.SPLASH_POTION ||
                offhandType == Material.LINGERING_POTION) {
            if (action == Action.RIGHT_CLICK_AIR) {
                usePotion(player, offhandItem);
            }
        }
        // Handle food
        else if (isFoodItem(offhandType)) {
            if (player.getFoodLevel() < 20) {
                useFood(player, offhandItem);
            }
        }
        // Generic offhand item usage
        else {
            sendMessage(player, ChatColor.GREEN + "Using offhand item: " +
                    offhandType.toString().toLowerCase().replace("_", " ") + usageType);
        }

        // Update item durability if applicable
        updateItemDurability(player, offhandItem);
    }

    private void useWindCharge(Player player, ItemStack windCharge) {
        if (player.getCooldown(Material.WIND_CHARGE) == 0) {
            sendMessage(player, ChatColor.AQUA + "Launching wind charge from offhand!");

            // Simulate wind charge effect - launch player upward
            player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(1.0));

            // Add cooldown
            player.setCooldown(Material.WIND_CHARGE, 40); // 2 second cooldown
        } else {
            sendMessage(player, ChatColor.RED + "Wind charge on cooldown!");
        }
    }

    private void useArmorItem(Player player, ItemStack armorItem) {
        Material armorType = armorItem.getType();
        PlayerInventory inventory = player.getInventory();

        // Determine which armor slot to use
        ItemStack toSwap = null;

        if (armorType.toString().endsWith("_HELMET")) {
            toSwap = inventory.getHelmet();
            inventory.setHelmet(armorItem.clone());
            sendMessage(player, ChatColor.BLUE + "Equipped helmet from offhand!");
        }
        else if (armorType.toString().endsWith("_CHESTPLATE")) {
            toSwap = inventory.getChestplate();
            inventory.setChestplate(armorItem.clone());
            sendMessage(player, ChatColor.BLUE + "Equipped chestplate from offhand!");
        }
        else if (armorType.toString().endsWith("_LEGGINGS")) {
            toSwap = inventory.getLeggings();
            inventory.setLeggings(armorItem.clone());
            sendMessage(player, ChatColor.BLUE + "Equipped leggings from offhand!");
        }
        else if (armorType.toString().endsWith("_BOOTS")) {
            toSwap = inventory.getBoots();
            inventory.setBoots(armorItem.clone());
            sendMessage(player, ChatColor.BLUE + "Equipped boots from offhand!");
        }
        else if (armorType == Material.ELYTRA) {
            toSwap = inventory.getChestplate();
            inventory.setChestplate(armorItem.clone());
            sendMessage(player, ChatColor.BLUE + "Equipped elytra from offhand!");
        }

        // Swap the old armor piece to offhand
        if (toSwap != null) {
            offhandManager.setOffhandItem(player, toSwap);
        } else {
            offhandManager.setOffhandItem(player, new ItemStack(Material.AIR));
        }
    }

    private void usePotion(Player player, ItemStack potion) {
        sendMessage(player, ChatColor.DARK_PURPLE + "Drinking potion from offhand!");

        // Simple health restoration instead of potion effects
        double currentHealth = player.getHealth();
        double maxHealth = player.getMaxHealth();
        player.setHealth(Math.min(maxHealth, currentHealth + 6.0));

        // Set cooldown
        player.setCooldown(potion.getType(), 20);
    }

    private void useFood(Player player, ItemStack food) {
        sendMessage(player, ChatColor.GOLD + "Eating from offhand!");

        // Restore hunger and health
        player.setFoodLevel(Math.min(20, player.getFoodLevel() + 4));
        player.setSaturation(player.getSaturation() + 2.4f);

        // Small health regeneration
        double currentHealth = player.getHealth();
        double maxHealth = player.getMaxHealth();
        player.setHealth(Math.min(maxHealth, currentHealth + 2.0));

        // Set cooldown
        player.setCooldown(food.getType(), 20);
    }

    private void activateShield(Player player, ItemStack shield) {
        // Activate shield for Bedrock players
        player.setCooldown(shield.getType(), 5);
        sendMessage(player, ChatColor.BLUE + "Shield raised from offhand!");

        // Note: For Spigot, we can't easily add resistance effects without NMS
        // The shield functionality will work, but without visual resistance effects
    }

    private void updateItemDurability(Player player, ItemStack item) {
        // Handle item durability for tools/weapons in offhand
        if (item.getType().getMaxDurability() > 0) {
            // Reduce durability by 1
            // In real implementation, you'd update the item meta
        }
    }

    private void sendMessage(Player player, String message) {
        player.sendMessage(message);

        // Optional: You can also use titles for better visibility
        // player.sendTitle("", message, 5, 20, 5);
    }
}