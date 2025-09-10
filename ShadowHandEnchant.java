package com.gods.shadowhand.enchant;

import org.bukkit.entity.Player;

import es.edwardbelt.edgens.iapi.enchant.APIEnchant;
import es.edwardbelt.edgens.iapi.enchant.EnchantData;

public class ShadowHandEnchant implements APIEnchant {
    @Override
    public void onProc(Player player, EnchantData enchantData) {
        // Just show a message to test if EdTools handles everything else
        player.sendMessage("§d[ShadowHand] Test message - enchant proc!");
    }
}
