package com.meliochausson.mlgrushreloaded.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.kyori.adventure.text.Component;

public class mlgbridge extends Command {

    public mlgbridge(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return false;
        if (commandSender.isOp()) {
            World world = Bukkit.getWorld("rush");

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
            return true;
        } else {
            commandSender.sendMessage("Bah tu peux pas xDDDDDDDDDDDD");
            return false;
        }
        // return true;
    }

    @Override
    public void setPermission(@Nullable String permission) {
        super.setPermission("mlg.start");
    }

    @Override
    public @Nullable Component permissionMessage() {
        super.permissionMessage(Component.text("tema le nul"));
        return null;
    }
}
