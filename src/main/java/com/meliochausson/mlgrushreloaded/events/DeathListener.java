package com.meliochausson.mlgrushreloaded.events;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import com.meliochausson.mlgrushreloaded.managers.GameManager;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // This code will run when a player dies
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        
        p.sendMessage("Player " + p.getName() + " died");
        
        if (GameManager.isGameRunning && GameManager.RedTeam.contains(uuid) || GameManager.BlueTeam.contains(uuid))
        {
            event.setKeepInventory(true);
            event.getDrops().clear();
            //add delay
            MLGRushReloaded.runTaskLater(() -> {
                p.spigot().respawn();
                // cancel drop of items
                if (GameManager.RedTeam.contains(uuid)) {
                    p.teleport(GameManager.redLoc);
                } else if (GameManager.BlueTeam.contains(uuid)) {
                    p.teleport(GameManager.blueLoc);
                }
                p.setHealth(20);
                return;
            }, 0.01);
            // player does not have to click respawn and respawn to it's spawnpoint
            return;
        }
    }
}