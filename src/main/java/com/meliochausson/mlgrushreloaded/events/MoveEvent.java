package com.meliochausson.mlgrushreloaded.events;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;

import com.meliochausson.mlgrushreloaded.managers.StuffManager;
import com.meliochausson.mlgrushreloaded.managers.GameManager;

public class MoveEvent implements Listener {

    Title.Times titleTimeScore = Title.Times.times(Duration.ofMillis(300), Duration.ofMillis(1000), Duration.ofMillis(300));

    private void endRound(boolean redWin) {
        if (redWin) {
            GameManager.redPoints++;
            Title title = Title.title(Component.text("§c" + GameManager.redPoints + "§r - §9" + GameManager.bluePoints).color(NamedTextColor.GRAY), Component.empty(), titleTimeScore);
            Bukkit.broadcast(Component.text("Team §cred§r has scored a point!").color(NamedTextColor.GRAY));
            this.titleEveryPlayer(title);
            this.resetAllPlayers();
        } else {
            GameManager.bluePoints++;
            Title title = Title.title(Component.text("§c" + GameManager.redPoints + "§r - §9" + GameManager.bluePoints).color(NamedTextColor.GRAY), Component.empty(), titleTimeScore);
            Bukkit.broadcast(Component.text("Team §9blue§r has scored a point!").color(NamedTextColor.GRAY));
            this.titleEveryPlayer(title);
            this.resetAllPlayers();
        }
        // check if someone won the game
        if (GameManager.redPoints == GameManager.pointsToWin) {
            Title title = Title.title(Component.text("Team §cred§r has won the game!").color(NamedTextColor.GRAY), Component.empty(), titleTimeScore);
            this.titleEveryPlayer(title);
            GameManager.clearGame();
            return;
        } else if (GameManager.bluePoints == GameManager.pointsToWin) {
            Title title = Title.title(Component.text("Team §9blue§r has won the game!").color(NamedTextColor.GRAY), Component.empty(), titleTimeScore);
            this.titleEveryPlayer(title);
            GameManager.clearGame();
            return;
        }
    }

    private void titleEveryPlayer(Title title) {
        List<UUID> all_players = new ArrayList<>();
        all_players.addAll(GameManager.RedTeam);
        all_players.addAll(GameManager.BlueTeam);

        for (UUID uuid : all_players) {
            final Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                continue;
            p.showTitle(title);
        }
    }

    private void resetAllPlayers() {
        GameManager.RedTeam.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                return;

            p.teleport(GameManager.redLoc);
            StuffManager.applyGameStuff(p);
            p.setHealth(20);
        });

        GameManager.BlueTeam.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                return;

            p.teleport(GameManager.blueLoc);
            StuffManager.applyGameStuff(p);
            p.setHealth(20);
        });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!GameManager.isGameRunning)
            return;

        if (GameManager.stopMoving)
            event.setCancelled(true);

        Player p = event.getPlayer();
        if (p.getGameMode().equals(GameMode.SURVIVAL) && p.getVelocity().getY() < 0 && p.getLocation().getY() < 57) {
            if (GameManager.BlueTeam.contains(p.getUniqueId())) {
                p.teleport(GameManager.blueLoc);
                p.setFallDistance(0);
            } else if (GameManager.RedTeam.contains(p.getUniqueId())) {
                p.teleport(GameManager.redLoc);
                p.setFallDistance(0);
            }
        }

        if (p.getGameMode().equals(GameMode.SURVIVAL) && p.getLocation().getY() > 60) {
            Material blockUnder = p.getLocation().getBlock().getType();
            
            if (blockUnder == Material.BLUE_BED && GameManager.RedTeam.contains(p.getUniqueId())) {
                endRound(true);
            } else if (blockUnder == Material.RED_BED && GameManager.BlueTeam.contains(p.getUniqueId())) {
                endRound(false);
            }
        }
    }
}
