package com.meliochausson.mlgrushreloaded.commands;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class setspawn extends Command {

    public setspawn(@NotNull String name) {
        super(name);
        super.setPermission("mlg.setspawn");
        super.permissionMessage(Component.text("Can't do this!"));
        super.setUsage("/setspawn RED|BLUE");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(commandSender instanceof Player) || args.length != 1)
            return false;

        Player p = (Player)commandSender;

        if (args[0].equals("RED")) {
            MLGRushReloaded._instance.getCustomConfig().setSection("spawn.RED", p.getLocation());
            p.sendMessage(
                Component.text("Spawn of team §cRED§r set to : ").color(NamedTextColor.GRAY).append(
                    Component.text(p.getLocation().toString()).color(NamedTextColor.AQUA)
                )
            );
        } else if (args[0].equals("BLUE")) {
            MLGRushReloaded._instance.getCustomConfig().setSection("spawn.BLUE", p.getLocation());
            p.sendMessage(
                Component.text("Spawn of team §bBLUE§r set to : ").color(NamedTextColor.GRAY).append(
                    Component.text(p.getLocation().toString()).color(NamedTextColor.AQUA)
                )
            );
        } else
            return false;
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return Arrays.asList("RED", "BLUE");
    }
}
