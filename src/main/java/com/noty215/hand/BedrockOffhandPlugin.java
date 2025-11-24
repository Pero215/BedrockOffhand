package com.noty215.hand;

import com.noty215.hand.commands.OffhandCommand;
import com.noty215.hand.listeners.InventoryClickListener;
import com.noty215.hand.listeners.PlayerInteractListener;
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

        // Register events and commands
        registerEvents();
        registerCommands();

        getLogger().info("BedrockOffhandPlugin has been enabled with true offhand support!");
        getLogger().info("Features: Simultaneous offhand usage, Wind charges, Armor swapping, Inventory management");
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
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        getLogger().info("Registered all event listeners");
    }

    private void registerCommands() {
        // Register offhand command
        getCommand("offhand").setExecutor(new OffhandCommand());

        getLogger().info("Registered commands");
    }

    public static BedrockOffhandPlugin getInstance() {
        return instance;
    }

    public OffhandManager getOffhandManager() {
        return offhandManager;
    }
}