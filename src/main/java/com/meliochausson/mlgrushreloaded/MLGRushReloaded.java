package com.meliochausson.mlgrushreloaded;

import com.meliochausson.mlgrushreloaded.commands.mlgstart;
import com.meliochausson.mlgrushreloaded.commands.mlgstop;
import com.meliochausson.mlgrushreloaded.events.BreakBlockEvent;
import com.meliochausson.mlgrushreloaded.events.DropEvent;
import com.meliochausson.mlgrushreloaded.events.FoodLevelEvent;
import com.meliochausson.mlgrushreloaded.events.InteractEvent;
import com.meliochausson.mlgrushreloaded.events.JoinEvent;
import com.meliochausson.mlgrushreloaded.events.MoveEvent;
import com.meliochausson.mlgrushreloaded.events.PlaceBlockEvent;
import com.meliochausson.mlgrushreloaded.events.QuitEvent;
import com.meliochausson.mlgrushreloaded.events.RespawnEvent;

import com.meliochausson.mlgrushreloaded.managers.ConfigManager;
import com.meliochausson.mlgrushreloaded.managers.GameManager;
import com.meliochausson.mlgrushreloaded.managers.StuffManager;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import static org.bukkit.Bukkit.getCommandMap;

public final class MLGRushReloaded extends JavaPlugin {
    public final NamespacedKey key = new NamespacedKey(this, "data");
    public static MLGRushReloaded _instance;
    private final ConfigManager config = new ConfigManager(this);

    public static void runTaskLater(Runnable cb, double delaySeconds) {
        Bukkit.getScheduler().runTaskLater(_instance, cb, (long)(delaySeconds * 20L)); // 20 ticks = 1sec
    }

    public ConfigManager getCustomConfig() {
        return this.config;
    }

    @Override
    public void onEnable() {
        _instance = this;

        GameManager.init(this);
        StuffManager.init(key);

        getCommandMap().register("mlgstart", "", new mlgstart("mlgstart"));
        getCommandMap().register("mlgstop", "", new mlgstop("mlgstop"));

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);

        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelEvent(), this);


        getServer().getPluginManager().registerEvents(new RespawnEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);

        getServer().getPluginManager().registerEvents(new PlaceBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new BreakBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(this), this);
    }

    @Override
    public void onDisable() {
        this.config.saveConfig();
        GameManager.clearGame();
    }
}
