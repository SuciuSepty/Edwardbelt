package com.gods.shadowhand;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gods.shadowhand.enchant.ShadowHandEnchant;

import es.edwardbelt.edgens.iapi.EdToolsAPI;

public final class ShadowHandPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register this plugin as a listener
        Bukkit.getPluginManager().registerEvents(this, this);
        
        // Wait a tick to let EdTools fully initialize
        Bukkit.getScheduler().runTaskLater(this, () -> {
            EdToolsAPI api = EdToolsAPI.getInstance();
            if (api != null) {
                api.getEnchantAPI().registerEnchant("crop-shadowhand", new ShadowHandEnchant());
                getLogger().info("ShadowHand enchant registered with EdTools");
            }
        }, 1L);
    }

    @Override
    public void onDisable() {
        getLogger().info("ShadowHand enchant disabled");
    }
}
