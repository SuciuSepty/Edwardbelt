package com.gods.shadowhand;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import com.gods.shadowhand.enchant.ShadowHandEnchant;
import es.edwardbelt.edgens.iapi.EdToolsAPI;

public final class ShadowHandPlugin extends JavaPlugin implements Listener {
    private static ShadowHandPlugin instance;
    
    public static ShadowHandPlugin getInstance() {
        return instance;
    }
    
    @Override
    public void onLoad() {
        instance = this;
        getLogger().info("ShadowHand loading...");
    }

    @Override
    public void onEnable() {
        // Register for events
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ShadowHand enabled, waiting for server to be fully loaded");
    }
    
    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.STARTUP) {
            // Register our enchant after server is fully loaded
            Bukkit.getScheduler().runTaskLater(this, this::registerEnchant, 40L);
        }
    }
    
    private void registerEnchant() {
        try {
            EdToolsAPI api = EdToolsAPI.getInstance();
            if (api != null && api.getEnchantAPI() != null) {
                api.getEnchantAPI().registerEnchant("crop-shadowhand", new ShadowHandEnchant());
                getLogger().info("ShadowHand enchant registered successfully");
            } else {
                getLogger().warning("Failed to register enchant - EdTools API not available");
            }
        } catch (Exception e) {
            getLogger().severe("Error registering enchant: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ShadowHand disabled");
        instance = null;
    }
}

