package com.meliochausson.mlgrushreloaded.commands;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
public class setspawn extends Command {

    public setspawn(@NotNull String name) {
        super(name);
        super.setPermission("mlg.setspawn");
        super.permissionMessage(Component.text("Can't do this!"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(commandSender instanceof Player) || args.length != 1) {
            commandSender.sendMessage("uwu");
            return false;
        }

        Player p = (Player)commandSender;
        Location loc = p.getLocation();

        if (!args[0].equals("RED") && !args[0].equals("BLUE")) {
            commandSender.sendMessage(Component.text("Wrong command usage.").color(NamedTextColor.RED));
            return false;
        }

        String teamName = (args[0].equals("RED")) ? "§cRED§r" : "§9BLUE§r";
        MLGRushReloaded._instance.getCustomConfig().setSection("spawn." + args[0], loc);

        p.sendMessage(
            Component.text(teamName + " spawn set to : ").color(NamedTextColor.GRAY).appendNewline().append(
                Component.text(loc.getX() + " / " + loc.getY() + " / " + loc.getZ()).color(NamedTextColor.AQUA)
            )
        );
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return Arrays.asList("RED", "BLUE");
    }

    @Override
    public @NotNull Command setUsage(@NotNull String usage) {
        return super.setUsage("/setspawn RED|BLUE");
    }
}
