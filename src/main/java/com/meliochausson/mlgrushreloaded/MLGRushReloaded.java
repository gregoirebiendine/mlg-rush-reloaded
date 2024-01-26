package com.meliochausson.mlgrushreloaded;

import com.meliochausson.mlgrushreloaded.commands.mlgbridge;
import com.meliochausson.mlgrushreloaded.commands.mlgkit;
import com.meliochausson.mlgrushreloaded.commands.mlgstart;
import com.meliochausson.mlgrushreloaded.commands.mlgstop;
import com.meliochausson.mlgrushreloaded.commands.test;
import com.meliochausson.mlgrushreloaded.events.DropEvent;
import com.meliochausson.mlgrushreloaded.events.FoodLevelEvent;
import com.meliochausson.mlgrushreloaded.events.InteractEvent;
import com.meliochausson.mlgrushreloaded.events.JoinEvent;
import com.meliochausson.mlgrushreloaded.events.PlaceBlockEvent;
import com.meliochausson.mlgrushreloaded.events.QuitEvent;
import com.meliochausson.mlgrushreloaded.events.RespawnEvent;
import com.meliochausson.mlgrushreloaded.managers.GameManager;
import com.meliochausson.mlgrushreloaded.managers.StuffManager;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

import static org.bukkit.Bukkit.getCommandMap;

import java.util.ArrayList;

public final class MLGRushReloaded extends JavaPlugin {
    public final NamespacedKey key = new NamespacedKey(this, "data");

    private void initGamerule() {
        World world = Bukkit.getWorld("world");

        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setPVP(false);
    }

    @Override
    public void onEnable() {
        GameManager.init();
        StuffManager.init(key);

        this.initGamerule();

        getCommandMap().register("test", "", new test("test", "a test command", "/test", new ArrayList<String>()));
        getCommandMap().register("mlgkit", "", new mlgkit("mlgkit"));
        getCommandMap().register("mlgstart", "", new mlgstart("mlgstart"));
        getCommandMap().register("mlgstop", "", new mlgstop("mlgstop"));
        getCommandMap().register("mlgbridge", "", new mlgbridge("mlgbridge"));


        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodLevelEvent(), this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(this), this);
    }

    @Override
    public void onDisable() {
        GameManager.clearGame();
    }
}
