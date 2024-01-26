package com.meliochausson.mlgrushreloaded.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;

public class RespawnEvent implements Listener {
    
    @EventHandler
    public void onRespawn(PlayerPostRespawnEvent event) {
        Player p = event.getPlayer();
        
        if (p.getWorld().getName() == "world")
            p.teleport(new Location(p.getWorld(), 0, -58, 0));
    }
}
