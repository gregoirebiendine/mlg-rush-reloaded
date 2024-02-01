package com.meliochausson.mlgrushreloaded.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.meliochausson.mlgrushreloaded.managers.GameManager;

public class PlaceBlockEvent implements Listener {
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!GameManager.isGameRunning)
            return;

        ItemStack item = event.getItemInHand();
        if (item.getType() == Material.SANDSTONE && item.getAmount() > 50)
            item.setAmount(64);
    }
}
