package com.meliochausson.mlgrushreloaded.managers;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import com.meliochausson.mlgrushreloaded.enums.TeamEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameManager {
    private static final List<UUID> RedTeam = new ArrayList<>();
    private static final List<UUID> BlueTeam = new ArrayList<>();

    public static MLGRushReloaded _instance;

    public static boolean isGameRunning = false;

    public static void init(MLGRushReloaded instance) {
        _instance = instance;
        clearGame();
    }

    public static void startGame(Player sender) {
        List<UUID> tempList = new ArrayList<>(RedTeam);
        tempList.addAll(BlueTeam);

        if (tempList.size() != 2) {
            sender.sendMessage(Component.text("Les équipes ne sont pas pleines.").color(NamedTextColor.RED));
            return;
        }

        tempList.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                return;

            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            p.sendTitlePart(TitlePart.TITLE, Component.text("Préparation...").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC));
        });

        MLGRushReloaded.runTaskLater(() -> {
            tempList.forEach(uuid -> {
                final Player p = Bukkit.getPlayer(uuid);
                if (p == null)
                    return;

                p.teleport(Objects.requireNonNull(Bukkit.getWorld("rush")).getSpawnLocation());
                StuffManager.applyGameStuff(p);
            });
        }, 1.5);

        isGameRunning = true;
    }

    public static void clearGame() {
        RedTeam.addAll(BlueTeam);

        RedTeam.forEach(uuid -> {
            final Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                return;

            p.playerListName(null);
            StuffManager.applyLobbyStuff(p);
            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
        });

        clearMap();

        RedTeam.clear();
        BlueTeam.clear();
        
        isGameRunning = false;
    }

    private static void clearMap() {
        World world = Bukkit.getWorld("rush");
        if (world == null)
            return;

        // Clear the world
        // world.getEntities().forEach(entity -> entity.remove());
        // Remove blocks
        for (int x = -22; x <= 22; x++) {
            for (int y = -3; y <= 16; y++) {
                for (int z = -22; z <= 22; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.AIR);
                }
            }
        }

        // Building bridge
        int bridgeLength = 36;
        int bridgeHeight = 3;

        int wallHeight = 12;
        int wallDistance = 4;

        // Create the bridge
        int startX = -bridgeLength / 2;
        int endX = startX + bridgeLength - 1;

        for (int x = startX; x <= endX; x++) {
            for (int y = 0; y < bridgeHeight; y++) {
                Block block = world.getBlockAt(x, y, 0);
                block.setType(Material.SMOOTH_SANDSTONE);
            }
        }

        // Create the surrounding walls
        int wallStartX = startX - wallDistance;
        int wallEndX = endX + wallDistance;

        // Walls parallel to the bridge
        for (int z = -4; z <= 4; z += 8) { // 4 blocks of air to the bridge
            for (int x = wallStartX; x <= wallEndX; x++) {
                for (int y = 0; y < wallHeight; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.OBSIDIAN);
                }
            }
        }

        // Walls at the start and end of the bridge
        for (int z = -bridgeLength / 2; z <= bridgeLength / 2; z++) {
            for (int x : new int[]{startX, endX}) { // directly adjacent to the bridge
                for (int y = 0; y < wallHeight; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.OBSIDIAN);
                }
            }
        }
        // Add a layer of Barrier blocks above the walls
        for (int x = wallStartX; x <= wallEndX; x++) {
            for (int z = -bridgeLength / 2; z <= bridgeLength / 2; z++) {
                Block block = world.getBlockAt(x, wallHeight, z);
                block.setType(Material.BARRIER);
            }
        }

        // Spawn beds
        BlockState bedFoot = world.getBlockAt(-17, 3, 0).getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.EAST).getState();

        bedFoot.setType(Material.RED_BED);
        bedHead.setType(Material.RED_BED);

        bedFoot.setRawData((byte) 0x0); // Foot part of the bed
        bedHead.setRawData((byte) 0x8); // Head part of the bed

        bedFoot.update(true, false);
        bedHead.update(true, true);

        // Bed 2
        bedFoot = world.getBlockAt(16, 3, 0).getState();
        bedHead = bedFoot.getBlock().getRelative(BlockFace.WEST).getState();

        bedFoot.setType(Material.RED_BED);
        bedHead.setType(Material.RED_BED);

        bedFoot.setRawData((byte) 0x0); // Foot part of the bed
        bedHead.setRawData((byte) 0x8); // Head part of the bed

        bedFoot.update(true, false);
        bedHead.update(true, true);
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
