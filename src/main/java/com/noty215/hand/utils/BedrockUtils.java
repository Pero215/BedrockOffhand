package com.noty215.hand.utils;

import org.bukkit.entity.Player;

public class BedrockUtils {

    public static boolean isBedrockPlayer(Player player) {
        // Method 1: Check for Floodgate (if installed)
        if (isFloodgatePlayer(player)) {
            return true;
        }

        // Method 2: Check player name format (Bedrock players often have spaces)
        if (player.getName().contains(" ")) {
            return true;
        }

        // Method 3: Check for specific metadata
        if (player.hasMetadata("isBedrockPlayer")) {
            return true;
        }

        // Method 4: Use Geyser if available
        if (isGeyserPlayer(player)) {
            return true;
        }

        return false;
    }

    private static boolean isFloodgatePlayer(Player player) {
        try {
            Class<?> floodgateApi = Class.forName("org.geysermc.floodgate.api.FloodgateApi");
            Object instance = floodgateApi.getMethod("getInstance").invoke(null);
            return (boolean) floodgateApi.getMethod("isFloodgatePlayer", java.util.UUID.class)
                    .invoke(instance, player.getUniqueId());
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isGeyserPlayer(Player player) {
        try {
            Class<?> geyserApi = Class.forName("org.geysermc.geyser.api.GeyserApi");
            Object api = geyserApi.getMethod("api").invoke(null);
            return (boolean) geyserApi.getMethod("isBedrockPlayer", java.util.UUID.class)
                    .invoke(api, player.getUniqueId());
        } catch (Exception e) {
            return false;
        }
    }

    public static String colorize(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
}