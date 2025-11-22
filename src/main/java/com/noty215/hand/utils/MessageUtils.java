package com.noty215.hand.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void sendOffhandMessage(Player player, String message) {
        // For Spigot compatibility, use titles or chat messages
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);

        // Use title for important messages (less spammy than chat)
        player.sendTitle("", coloredMessage, 5, 20, 5);
    }

    public static void sendQuickMessage(Player player, String message) {
        // Use chat messages for quick feedback
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}