package com.noty215.hand.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void sendOffhandMessage(Player player, String message) {
        // Try to use action bar if available (Paper), otherwise use chat
        try {
            player.sendActionBar(message);
        } catch (NoSuchMethodError e) {
            // Fallback to title or chat message for Spigot
            player.sendTitle("", ChatColor.translateAlternateColorCodes('&', message), 5, 20, 5);
        }
    }

    public static void sendActionBar(Player player, String message) {
        try {
            // Reflection to check if sendActionBar exists
            player.getClass().getMethod("sendActionBar", String.class);
            player.sendActionBar(ChatColor.translateAlternateColorCodes('&', message));
        } catch (Exception e) {
            // Fallback for Spigot - use titles
            player.sendTitle("", ChatColor.translateAlternateColorCodes('&', message), 5, 20, 5);
        }
    }
}