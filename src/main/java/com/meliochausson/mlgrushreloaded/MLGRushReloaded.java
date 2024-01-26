package com.meliochausson.mlgrushreloaded;

import com.meliochausson.mlgrushreloaded.commands.test;
import com.meliochausson.mlgrushreloaded.events.DropEvent;
import com.meliochausson.mlgrushreloaded.events.InteractEvent;
import com.meliochausson.mlgrushreloaded.events.JoinEvent;
import com.meliochausson.mlgrushreloaded.events.QuitEvent;
import com.meliochausson.mlgrushreloaded.managers.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;

import static org.bukkit.Bukkit.getCommandMap;

public final class MLGRushReloaded extends JavaPlugin {
    public final NamespacedKey key = new NamespacedKey(this, "data");

    @Override
    public void onEnable() {
        GameManager.initGame();

        getCommandMap().register("", new test("test"));

        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(this), this);
    }

    @Override
    public void onDisable() {
        GameManager.clearGame();
    }
}
