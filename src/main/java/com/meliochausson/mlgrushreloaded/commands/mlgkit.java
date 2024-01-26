package com.meliochausson.mlgrushreloaded.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.kyori.adventure.text.Component;

public class mlgkit extends Command {

    public mlgkit(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return false;
        if (commandSender.isOp()) {

            // Clear inventory
            Player player = (Player) commandSender;
            
            player.getInventory().clear();
            // Give diamond sword
            Material sword = Material.DIAMOND_SWORD;
            ItemStack swordItem = new ItemStack(sword);
            swordItem.addEnchantment(Enchantment.KNOCKBACK, 1);
            player.getInventory().addItem(swordItem);

            // Give diamond pickaxe
            Material pickaxe = Material.DIAMOND_PICKAXE;
            ItemStack pickaxeItem = new ItemStack(pickaxe);
            player.getInventory().addItem(pickaxeItem);

            // give 4 stacks of sandstone
            Material sandstone = Material.SANDSTONE;
            ItemStack sandstoneItem = new ItemStack(sandstone, 128);
            player.getInventory().addItem(sandstoneItem);

            // send message
            commandSender.sendMessage("You have been given the MLG kit!");
        } else {
            commandSender.sendMessage("Bah tu peux pas xDDDDDDDDDDDD");
        }
        return true;
    }

    @Override
    public void setPermission(@Nullable String permission) {
        super.setPermission("mlg.kit");
    }

    @Override
    public @Nullable Component permissionMessage() {
        super.permissionMessage(Component.text("tema le nul"));
        return null;
    }
}
