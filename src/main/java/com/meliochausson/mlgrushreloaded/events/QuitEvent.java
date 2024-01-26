package com.meliochausson.mlgrushreloaded.events;

import com.meliochausson.mlgrushreloaded.managers.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener  {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        GameManager.removeFromTeam(p);
    }
}