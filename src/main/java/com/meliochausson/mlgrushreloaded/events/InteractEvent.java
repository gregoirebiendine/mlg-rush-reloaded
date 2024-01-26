package com.meliochausson.mlgrushreloaded.events;

import com.meliochausson.mlgrushreloaded.MLGRushReloaded;
import com.meliochausson.mlgrushreloaded.enums.TeamEnum;
import com.meliochausson.mlgrushreloaded.managers.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InteractEvent implements Listener {
    private MLGRushReloaded _instance;
    
    public InteractEvent(MLGRushReloaded instance) {
        this._instance = instance;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() !=  Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack item = event.getItem();
        if (item == null)
            return;

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.has(_instance.key, PersistentDataType.STRING)) {
            String value = container.get(_instance.key, PersistentDataType.STRING);

            if (value != null && value.equals("red_wool_join"))
                GameManager.joinTeam(event.getPlayer(), TeamEnum.RED);
            else if (value != null && value.equals("blue_wool_join"))
                GameManager.joinTeam(event.getPlayer(), TeamEnum.BLUE);
        }
    }
}
