package com.meliochausson.mlgrushreloaded.events;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedSleepEvent implements Listener {

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }

}