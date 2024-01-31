package com.meliochausson.mlgrushreloaded.events;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import com.meliochausson.mlgrushreloaded.managers.StuffManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.TitlePart;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        Component msg = Component.text("@" + p.getName()).color(NamedTextColor.AQUA).append(
            Component.text(" has joined the game (" + Bukkit.getOnlinePlayers().size() + "/2)").color(NamedTextColor.GRAY)
        );

        event.joinMessage(msg);

        String w = MLGRushReloaded._instance.getCustomConfig().getLobbyWorld();
        if (w != null)
            p.teleport(Bukkit.getWorld(w).getSpawnLocation());

        p.setHealth(20);
        p.setFoodLevel(20);

        StuffManager.applyLobbyStuff(p);

        p.sendTitlePart(TitlePart.TITLE, Component.text("[MLG Rush]").color(NamedTextColor.GOLD));
        p.sendTitlePart(TitlePart.SUBTITLE, Component.text("@Melio, @MrChausson").color(NamedTextColor.AQUA));
    }
}
