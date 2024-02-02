package com.meliochausson.mlgrushreloaded.managers;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import com.meliochausson.mlgrushreloaded.enums.TeamEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    public static final List<UUID> RedTeam = new ArrayList<>();
    public static final List<UUID> BlueTeam = new ArrayList<>();

    public static MLGRushReloaded _instance;

    public final static int pointsToWin = 5;
    public static int redPoints = 0;
    public static int bluePoints = 0;

    public static boolean isGameRunning = false;
    public static boolean stopMoving = false;

    private static World gameWorld;
    public static Location redLoc;
    public static Location blueLoc;

    public static void init(MLGRushReloaded instance) {
        _instance = instance;
    }

    public static void startGame(Player sender) {
        gameWorld = Bukkit.getWorld("game");
        redLoc = new Location(gameWorld, 0.5, 66, -11.5, 0, 0);
        blueLoc = new Location(gameWorld, 0.5, 66, 11.5, -180, 0);

        if (gameWorld == null) {
            getLogger().info("null?");
            return;
        }
            
        List<UUID> tempList = new ArrayList<>(RedTeam);
        tempList.addAll(BlueTeam);

        if (tempList.size() != 2 && !sender.isOp()) {
            sender.sendMessage(Component.text("Teams are not full.").color(NamedTextColor.RED));
            return;
        }

        tempList.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                return;

            GameManager.stopMoving = true;
            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            p.sendTitlePart(TitlePart.TITLE, Component.text("Setup...").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC));
        });

        MLGRushReloaded.runTaskLater(() -> {
            RedTeam.forEach(uuid -> {
                final Player p = Bukkit.getPlayer(uuid);
                p.teleport(redLoc);
                StuffManager.applyGameStuff(p);
            });
            BlueTeam.forEach(uuid -> {
                final Player p = Bukkit.getPlayer(uuid);
                p.teleport(blueLoc);
                StuffManager.applyGameStuff(p);
            });

            MLGRushReloaded.runTaskLater(() -> {
                tempList.forEach(uuid -> {
                    final Player p = Bukkit.getPlayer(uuid);
                    if (p == null)
                        return;
                    p.playSound(p, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                    p.sendTitlePart(TitlePart.TITLE, Component.text("GO!").color(NamedTextColor.RED).decorate(TextDecoration.ITALIC));
                });
                GameManager.stopMoving = false;
            }, 1);
        }, 2);

        isGameRunning = true;
    }

    public static void clearGame() {
        RedTeam.addAll(BlueTeam);

        RedTeam.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            final World lobby = _instance.getCustomConfig().getLobbyWorld();

            if (p == null || lobby == null)
                return;

            p.playerListName(null);
            StuffManager.applyLobbyStuff(p);
            p.teleport(lobby.getSpawnLocation());
        });

        RedTeam.clear();
        BlueTeam.clear();

        // clear points
        redPoints = 0;
        bluePoints = 0;
        
        isGameRunning = false;

        for (short x = -5; x <= 18; x++) {
            for (short y = 60; y <= 74; y++) {
                for (short z = -18; z <= 18; z++) {
                    Block b = gameWorld.getBlockAt(x, y, z);
                    if (b.getType() == Material.SANDSTONE)
                        b.setType(Material.AIR);
                }
            }
        }

        for (short z = -9; z <= 9; z++) {
            Block b = gameWorld.getBlockAt(0, 64, z);
            b.setType(Material.SANDSTONE);
        }

        // -5 -> 18
        // 60 -> 74
        // -18 -> 18

        // /fill -5 60 -18 5 74 18 minecraft:air replace minecraft:sandstone
        // /fill 0 64 9 0 64 -9
    }

    public static void joinTeam(Player p, TeamEnum team) {
        if (isGameRunning)
            return;

        if (team == TeamEnum.RED && !RedTeam.contains(p.getUniqueId()) && RedTeam.isEmpty()) {
            BlueTeam.remove(p.getUniqueId());
            RedTeam.add(p.getUniqueId());
        } else if (team == TeamEnum.BLUE && !BlueTeam.contains(p.getUniqueId()) && BlueTeam.isEmpty()) {
            RedTeam.remove(p.getUniqueId());
            BlueTeam.add(p.getUniqueId());
        } else
            return;

        p.sendMessage(Component.text("You have joined the team: ").color(NamedTextColor.GRAY).append(
            Component.text((team == TeamEnum.RED) ? "Red" : "Blue").color(NamedTextColor.namedColor(team.value))
        ));
        p.playerListName(Component.text(p.getName()).color(NamedTextColor.namedColor(team.value)));
    }

    public static void removeFromTeam(Player p) {
        RedTeam.remove(p.getUniqueId());
        BlueTeam.remove(p.getUniqueId());
    }
}
