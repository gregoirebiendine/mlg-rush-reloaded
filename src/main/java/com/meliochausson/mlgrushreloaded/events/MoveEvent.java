package com.meliochausson.mlgrushreloaded.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.meliochausson.mlgrushreloaded.managers.GameManager;

public class MoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!GameManager.isGameRunning)
            return;

        if (GameManager.stopMoving)
            event.setCancelled(true);

        Player p = event.getPlayer();
        if (p.getGameMode().equals(GameMode.SURVIVAL) && p.getVelocity().getY() < 0 && p.getLocation().getY() < 57)
            p.sendMessage("UWU");
    }
}
