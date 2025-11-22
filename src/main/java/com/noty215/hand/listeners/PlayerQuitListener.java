package com.noty215.hand.listeners;

import com.noty215.hand.BedrockOffhandPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        BedrockOffhandPlugin.getInstance().getOffhandManager().removeBedrockPlayer(event.getPlayer());
    }
}