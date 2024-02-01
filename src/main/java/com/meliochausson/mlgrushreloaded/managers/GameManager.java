package com.meliochausson.mlgrushreloaded.managers;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import com.meliochausson.mlgrushreloaded.enums.TeamEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class GameManager {
    public static final List<UUID> RedTeam = new ArrayList<>();
    public static final List<UUID> BlueTeam = new ArrayList<>();
    public static int redPoints = 0;
    public static int bluePoints = 0;
    public static int pointsToWin = 5;

    public static MLGRushReloaded _instance;

    public static boolean isGameRunning = false;
    public static boolean stopMoving = false;

    private static final World gameWorld = MLGRushReloaded._instance.getCustomConfig().getGameWorld();
    public static final Location redLoc = new Location(gameWorld, 0.5, 66, -11.5, 0, 0);
    public static final Location blueLoc = new Location(gameWorld, 0.5, 66, 11.5, -180, 0);
    public static String _serverFolder;

    public static void init(MLGRushReloaded instance, String dataFolderPath) {
        _serverFolder = new File(dataFolderPath).getParentFile().getParentFile().getAbsolutePath();
        _instance = instance;
        clearGame();
    }

    public static void ReplaceWorldWithTemplate() {
        // world reset
        // so we wanna unload and then copy the template
        Bukkit.unloadWorld(gameWorld, false);

        String path_gameworld = GameManager._serverFolder + "/" + _instance.getCustomConfig().getConfigObject().get("worlds.game").toString();
        String path_template = GameManager._serverFolder + "/" + _instance.getCustomConfig().getConfigObject().get("worlds.game").toString() + "-template";
    
        try {
            // Delete existing "game" world
            // Files.walk(Paths.get(path_gameworld))
            //     .sorted(Comparator.reverseOrder())
            //     .forEach(path -> {
            //         try {
            //             Files.delete(path);
            //         } catch (IOException e) {
            //             getLogger().info("Exception while deleting files: " + e.getMessage());
            //         }
            //     });
                // Copy new files
                Files.walk(Paths.get(path_template))
                    .forEach(path -> {
                        try {
                            Files.copy(path, Paths.get(path_gameworld).resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            getLogger().info("Exception while copying files: " + e.getMessage());

                        }
                    });
        } catch (IOException e) {
            getLogger().info("Exception while replacing files: " + e.getMessage());
        }

        // debug print to console
        getLogger().info("path_gameworld: " + path_gameworld);
        getLogger().info("path_template: " + path_template);

        // reload "game" world
        Bukkit.createWorld(new WorldCreator(_instance.getCustomConfig().getConfigObject().get("worlds.game").toString()));
    }

    public static void startGame(Player sender) {
        if (gameWorld == null)
            return;
        

            
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

            
            ReplaceWorldWithTemplate();

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
