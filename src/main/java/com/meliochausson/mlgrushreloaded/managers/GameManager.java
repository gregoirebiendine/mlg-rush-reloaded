package com.meliochausson.mlgrushreloaded.managers;

import com.meliochausson.mlgrushreloaded.enums.TeamEnum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    private static final List<UUID> RedTeam = new ArrayList<>();
    private static final List<UUID> BlueTeam = new ArrayList<>();

    public static void initGame() {
        clearGame();
    }

    public static void clearGame() {
        RedTeam.clear();
        BlueTeam.clear();
    }

    public static void joinTeam(Player p, TeamEnum team) {
        if (!RedTeam.contains(p.getUniqueId()) && !BlueTeam.contains(p.getUniqueId())) {
            if (team == TeamEnum.RED)
                RedTeam.add(p.getUniqueId());
            else
                BlueTeam.add(p.getUniqueId());

            p.sendMessage(Component.text("Tu as rejoins l'équipe ").color(NamedTextColor.GRAY).append(
                    Component.text((team == TeamEnum.RED) ? "Rouge" : "Bleu").color(NamedTextColor.namedColor(team.value))
            ));
            p.playerListName(Component.text(p.getName()).color(NamedTextColor.namedColor(team.value)));
        }
    }

    public static void removeFromTeam(Player p) {
        RedTeam.remove(p.getUniqueId());
        BlueTeam.remove(p.getUniqueId());
    }
}
