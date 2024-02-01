package com.meliochausson.mlgrushreloaded.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

public class ConfigManager {
	private File configFile;
	private FileConfiguration config;

    private final String FILE_NAME = "config.yml";
	
	public ConfigManager(MLGRushReloaded instance) {
		this.configFile = new File(instance.getDataFolder(), FILE_NAME);

	    if (!this.configFile.exists()) {
			this.configFile.getParentFile().mkdirs();
	    	instance.saveResource(FILE_NAME, false);
	    }
		this.config = new YamlConfiguration();
	    try {
			this.config.load(this.configFile);
	    } catch (IOException | InvalidConfigurationException e) {
	        e.printStackTrace();
	    }
    }
	
	public FileConfiguration getConfigObject() {
		return this.config;
	}
	
	public boolean entryExist(String entry) {
		return this.config.contains(entry);
	}

	public Object getValue(String section) {
		return this.config.get(section);
	}

	public @Nullable World getLobbyWorld() {
		if (!this.entryExist("worlds.lobby"))
			return null;
		return Bukkit.getWorld(this.config.get("worlds.lobby").toString());
	}
	
	public @Nullable World getGameWorld() {
		if (!this.entryExist("worlds.game"))
			return null;
		return Bukkit.getWorld(this.config.get("worlds.game").toString());
	}
	
	public void setSection(String section, Object value) {
		this.config.set(section, value);
		this.saveConfig();
	}

	public void modifySection(String section, Object newValue) {
		this.config.set(section, null);
		this.config.set(section, newValue);
		this.saveConfig();
	}

	public void deleteSection(String section) {
		this.config.set(section, null);
		this.saveConfig();
	}
	
	public void saveConfig() {
		try {
			this.config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}