package com.meliochausson.mlgrushreloaded.events;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class JoinEvent implements Listener {
    private MLGRushReloaded _instance;

    public JoinEvent(MLGRushReloaded instance) {
        this._instance = instance;
    }
    private ItemStack getWool(Material woolMaterial, Component name, String data) {
        ItemStack wool = new ItemStack(woolMaterial, 1);
        ItemMeta meta = wool.getItemMeta();

        meta.displayName(name);
        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(_instance.key, PersistentDataType.STRING, data);
        wool.setItemMeta(meta);
        return wool;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        Component msg = Component.text("Bienvenue en MLG Rush ").color(NamedTextColor.YELLOW).append(
            Component.text("@" + p.getName()).color(NamedTextColor.AQUA)
        );

        event.joinMessage(msg);

        p.teleport(new Location(p.getWorld(), 0, -59, 0));

        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20);
        p.setFoodLevel(20);

        p.getInventory().clear();
        p.getInventory().setItem(0, getWool(Material.RED_WOOL, Component.text("Join Red Team").color(NamedTextColor.RED), "red_wool_join"));
        p.getInventory().setItem(1, getWool(Material.BLUE_WOOL, Component.text("Join Blue Team").color(NamedTextColor.BLUE), "blue_wool_join"));

        p.sendTitlePart(TitlePart.TITLE, Component.text("[MLG Rush]").color(NamedTextColor.GOLD));
        p.sendTitlePart(TitlePart.SUBTITLE, Component.text("@Melio, @MrChausson").color(NamedTextColor.AQUA));
    }
}
