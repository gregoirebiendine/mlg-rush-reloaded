package com.meliochausson.mlgrushreloaded.managers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.meliochausson.mlgrushreloaded.enums.TeamEnum;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class StuffManager {
    private static NamespacedKey _key;

    public static void init(NamespacedKey key) {
        _key = key;
    }

    private static ItemStack getLobbyWool(TeamEnum team, String data) {
        ItemStack wool = new ItemStack((team == TeamEnum.RED) ? Material.RED_WOOL : Material.BLUE_WOOL, 1);
        ItemMeta meta = wool.getItemMeta();

        meta.displayName(Component.text((team == TeamEnum.RED) ? "Team Rouge" : "Team Blue").color(NamedTextColor.namedColor(team.value)));
        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(_key, PersistentDataType.STRING, data);
        wool.setItemMeta(meta);
        return wool;
    }

    public static void applyLobbyStuff(Player p) {
        p.getInventory().clear();
        p.getInventory().addItem(
            getLobbyWool(TeamEnum.RED, "red_wool_join"),
            getLobbyWool(TeamEnum.BLUE, "blue_wool_join")
        );
    }

    public static void applyGameStuff(Player p) {
        p.getInventory().clear();

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack sandstone = new ItemStack(Material.SANDSTONE, 128);

        ItemStack stick = new ItemStack(Material.STICK, 1);
        ItemMeta stickMeta = stick.getItemMeta();
        stickMeta.displayName(Component.text("Stick of Destiny").color(NamedTextColor.GOLD));
        stickMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        stick.setItemMeta(stickMeta);

        p.getInventory().addItem(sword, pickaxe, stick, sandstone
        );
    }
}
