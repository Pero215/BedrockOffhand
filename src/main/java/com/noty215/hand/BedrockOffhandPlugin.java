package com.noty215.hand;

import com.noty215.hand.listeners.*;
import com.noty215.hand.managers.OffhandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedrockOffhandPlugin extends JavaPlugin {
    private static BedrockOffhandPlugin instance;
    private OffhandManager offhandManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        offhandManager = new OffhandManager();
        registerEvents();
        getLogger().info("BedrockOffhandPlugin has been enabled with true offhand support!");
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
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    public static BedrockOffhandPlugin getInstance() {
        return instance;
    }

    public OffhandManager getOffhandManager() {
        return offhandManager;
    }
}