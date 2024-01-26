package com.meliochausson.mlgrushreloaded.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;

public class test extends Command {
    public test(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.setPermission("mlg.test");
        this.permissionMessage(Component.text("UWU"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return false;
        commandSender.sendMessage("hehe boiiiii");
        return true;
    }

    // @Override
    // public void setPermission(@Nullable String permission) {
    //     super.setPermission(permission);
    // }

    // @Override
    // public void permissionMessage(@Nullable Component permissionMessage) {
    //     super.permissionMessage(permissionMessage);
    // }
}
