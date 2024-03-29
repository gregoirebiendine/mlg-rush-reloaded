package com.meliochausson.mlgrushreloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.meliochausson.mlgrushreloaded.managers.GameManager;

import net.kyori.adventure.text.Component;

public class mlgstart extends Command {

    public mlgstart(@NotNull String name) {
        super(name);
        super.setPermission("mlg.start");
        super.permissionMessage(Component.text("Can't do this!"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return false;
        GameManager.startGame((Player)commandSender);
        return true;
    }
}
