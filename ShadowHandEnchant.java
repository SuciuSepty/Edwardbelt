package com.gods.shadowhand.enchant;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.gods.shadowhand.ShadowHandPlugin;
import es.edwardbelt.edgens.iapi.EdToolsAPI;
import es.edwardbelt.edgens.iapi.enchant.APIEnchant;
import es.edwardbelt.edgens.iapi.enchant.CustomEnchantData;
import es.edwardbelt.edgens.iapi.enchant.EnchantData;

public class ShadowHandEnchant implements APIEnchant {
    @Override
    public void onProc(Player player, EnchantData enchantData) {
        if (!(enchantData instanceof CustomEnchantData)) {
            return;
        }
        
        CustomEnchantData data = (CustomEnchantData) enchantData;
        
        // Schedule task using our plugin instance to avoid issues
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    EdToolsAPI api = EdToolsAPI.getInstance();
                    if (api == null) return;
                    
                    // Get the tool ID first - this doesn't affect zones
                    String toolId = api.getOmniToolAPI().getOmniToolId(player.getInventory().getItemInMainHand());
                    if (toolId == null) {
                        return;  // Silent fail if no valid tool
                    }
                    
                    // Just get the block material at the position - minimal zone interaction
                    var position = data.getPosition();
                    
                    // Use mineBlockAsPlayer with minimal options
                    var result = api.getZonesAPI().mineBlockAsPlayer(
                        player,
                        position,
                        toolId,
                        false,  // Don't affect enchants to avoid recursion
                        true,   // Affect selling
                        true,   // Affect currencies
                        false   // Don't affect lucky blocks
                    );
                    
                    if (result != null) {
                        player.sendMessage("§d[ShadowHand] Your shadow hand breaks an extra crop!");
                    }
                } catch (Exception e) {
                    // Silent fail to avoid console spam
                    ShadowHandPlugin.getInstance().getLogger().warning("Error in enchant proc: " + e.getMessage());
                }
            }
        }.runTask(ShadowHandPlugin.getInstance());
    }
}
