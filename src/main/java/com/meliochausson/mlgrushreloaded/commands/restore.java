package com.meliochausson.mlgrushreloaded.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.codehaus.plexus.util.FileUtils;
import org.jetbrains.annotations.NotNull;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;

public class restore extends Command {

    public restore(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
 
        try {
            final String serverFolder = Bukkit.getWorldContainer().getAbsolutePath().replace(".", "");
            final String nameOfGameWorld = MLGRushReloaded._instance.getCustomConfig().getValue("worlds.game").toString();
    
            final String path_gameworld = serverFolder + nameOfGameWorld;
            final String path_template = serverFolder + "/backups/" + nameOfGameWorld;

            final File source = new File(path_template);
            final File destination = new File(path_gameworld);

            // Unload world before replacing
            final World w = Bukkit.getWorld(nameOfGameWorld);
            if (w != null)
                if (!Bukkit.unloadWorld(w, false))
                    throw new Exception("Exception while unloading the world");

            // Check if world folder exist in case of user delete
            if (!destination.exists()) {
                if (!destination.mkdir())
                    throw new Exception("Exception while creating the game world folder");
            }

            // Copy files from template to real world
            FileUtils.copyDirectory(source, destination);
            
            // Instanciate the new world in Bukkit
            final WorldCreator wc = new WorldCreator(nameOfGameWorld);
            Bukkit.createWorld(wc);

        } catch (Exception e) {
            Bukkit.getLogger().info("Error while replacing game world : " + e.getMessage());
        }

        return true;
    }
}
