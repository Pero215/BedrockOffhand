package com.noty215.hand;

import com.noty215.hand.listeners.InventoryListener;
import com.noty215.hand.listeners.PlayerJoinListener;
import com.noty215.hand.listeners.PlayerQuitListener;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedrockOffhandPlugin extends JavaPlugin {
    private static BedrockOffhandPlugin instance;
    private OffhandManager offhandManager;

    @Override
    public void onEnable() {
        instance = this;

        // Save default config
        saveDefaultConfig();

        // Initialize manager
        offhandManager = new OffhandManager();

        // Register events
        registerEvents();

        getLogger().info("BedrockOffhandPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        if (offhandManager != null) {
            offhandManager.cleanup();
        }
        getLogger().info("BedrockOffhandPlugin has been disabled!");
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    public static BedrockOffhandPlugin getInstance() {
        return instance;
    }

    public OffhandManager getOffhandManager() {
        return offhandManager;
    }
}