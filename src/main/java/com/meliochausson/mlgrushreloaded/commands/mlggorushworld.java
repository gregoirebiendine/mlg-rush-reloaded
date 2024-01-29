package com.meliochausson.mlgrushreloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.meliochausson.mlgrushreloaded.managers.GameManager;

import net.kyori.adventure.text.Component;

public class mlggorushworld extends Command {

    public mlggorushworld(@NotNull String name) {
        super(name);
        super.setPermission("mlg.mlggorushworld");
        super.permissionMessage(Component.text("Can't do this!"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return false;
        // TODO: go to the original rush world in order to build the thing
        return true;
    }
}
