package com.noty215.hand.listeners;

import com.noty215.hand.BedrockOffhandPlugin;
import com.noty215.hand.utils.BedrockUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Detect if player is from Bedrock edition
        if (BedrockUtils.isBedrockPlayer(player)) {
            BedrockOffhandPlugin.getInstance().getOffhandManager().addBedrockPlayer(player);

            // Send welcome message with offhand instructions
            if (BedrockOffhandPlugin.getInstance().getConfig().getBoolean("messages.welcome-enabled", true)) {
                player.sendMessage(BedrockUtils.colorize(
                        BedrockOffhandPlugin.getInstance().getConfig().getString("messages.welcome-message",
                                "&aOffhand system enabled! Use slot 8 for offhand items."))
                );
            }
        }
    }
}