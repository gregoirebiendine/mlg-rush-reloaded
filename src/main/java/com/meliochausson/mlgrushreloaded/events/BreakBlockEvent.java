package com.meliochausson.mlgrushreloaded.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.meliochausson.mlgrushreloaded.managers.GameManager;

public class BreakBlockEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!GameManager.isGameRunning)
            return;

        Block block = event.getBlock();
        if (block.getType() != Material.SANDSTONE)
            event.setCancelled(true);
    }
    
}