package com.meliochausson.mlgrushreloaded.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.meliochausson.mlgrushreloaded.managers.GameManager;
import com.meliochausson.mlgrushreloaded.managers.StuffManager;

import net.kyori.adventure.text.Component;

public class mlgstop extends Command {

    public mlgstop(@NotNull String name) {
        super(name);
        this.setPermission("mlg.stop");
        this.permissionMessage(Component.text("Can't do it"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return false;
        // Player player = (Player) commandSender;

        // Stop game and reset teams
        GameManager.clearGame();
        return true;

        // Clear and apply lobby stuff
        // StuffManager.applyLobbyStuff(player);

        // Teleport the player to "word"
        // return player.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }
}
