package me.mvabo.verydangerouscaves.caveEffects;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.utils.inCave;

public class CaveSounds {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    Random randint = new Random();
    List confWorlds = plugin.getConfig().getList("enabled_cave_worlds");
    ArrayList<String> worlds = new ArrayList<>();

    public void doCaveSounds() {
        for (int i = 0; i < confWorlds.size(); i++) {
            worlds.add(confWorlds.get(i).toString());
        }

        for (String namew : worlds) {
            World wor = Bukkit.getWorld(namew);
            if (wor != null) {
                for (Player p : wor.getPlayers()) {
                    if (randint.nextInt(100) < plugin.getConfig().getInt("cave_ambience_chance")) {
                        int choice = randint.nextInt(6);
                        if (inCave(p)) {
                            if (choice == 0) {
                                p.playSound(p.getLocation(), Sound.MUSIC_DRAGON, SoundCategory.AMBIENT, 100,
                                        0);
                            } else if (choice == 1) {
                                p.playSound(p.getLocation(), Sound.MUSIC_NETHER_NETHER_WASTES, SoundCategory.AMBIENT, 100,
                                        (float) .5);
                            } else if (choice == 2) {
                                if (randint.nextInt(5) == 1) {
                                    p.playSound(p.getLocation(), Sound.MUSIC_DISC_11, SoundCategory.AMBIENT,
                                            100, (float) .5);
                                }
                            } else if (choice == 3) {
                                p.playSound(p.getLocation(), Sound.MUSIC_DISC_13, SoundCategory.AMBIENT,
                                        100, (float) .5);
                            } else if (choice == 4) {
                                p.playSound(p.getLocation(), Sound.MUSIC_DISC_MELLOHI,
                                        SoundCategory.AMBIENT, 100, (float) .5);
                            } else if (choice == 5) {
                                if (randint.nextInt(6) == 1) {
                                    p.playSound(p.getLocation(), Sound.ENCHANT_THORNS_HIT,
                                            SoundCategory.AMBIENT, (float) .04, (float) .2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
