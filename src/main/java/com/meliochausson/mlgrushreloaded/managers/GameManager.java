package com.meliochausson.mlgrushreloaded.managers;

import com.meliochausson.mlgrushreloaded.enums.TeamEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    private static final List<UUID> RedTeam = new ArrayList<>();
    private static final List<UUID> BlueTeam = new ArrayList<>();

    public static boolean isGameRunning = false;

    public static void init() {
        clearGame();
    }

    public static void startGame() {
        RedTeam.addAll(BlueTeam);

        RedTeam.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            p.teleport(Bukkit.getWorld("rush").getSpawnLocation());
            StuffManager.applyGameStuff(p);
        });

        isGameRunning = true;
    }

    public static void clearGame() {
        RedTeam.addAll(BlueTeam);

        RedTeam.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            
            p.playerListName(null);
            StuffManager.applyLobbyStuff(p);
            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
        });

        RedTeam.clear();
        BlueTeam.clear();
        
        isGameRunning = false;
    }

    public static void joinTeam(Player p, TeamEnum team) {
        if (isGameRunning)
            return;

        if (team == TeamEnum.RED && !RedTeam.contains(p.getUniqueId())) {
            BlueTeam.remove(p.getUniqueId());
            RedTeam.add(p.getUniqueId());
        } else if (team == TeamEnum.BLUE && !BlueTeam.contains(p.getUniqueId())) {
            RedTeam.remove(p.getUniqueId());
            BlueTeam.add(p.getUniqueId());
        } else {
            return;
        }

        p.sendMessage(Component.text("Tu as rejoins l'équipe ").color(NamedTextColor.GRAY).append(
                Component.text((team == TeamEnum.RED) ? "Rouge" : "Bleu").color(NamedTextColor.namedColor(team.value))
        ));
        p.playerListName(Component.text(p.getName()).color(NamedTextColor.namedColor(team.value)));
    }

    public static void removeFromTeam(Player p) {
        RedTeam.remove(p.getUniqueId());
        BlueTeam.remove(p.getUniqueId());
    }
}
