package me.mvabo.verydangerouscaves.caveListeners;


import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.cave;
import me.mvabo.verydangerouscaves.utils.utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static me.mvabo.verydangerouscaves.cave.cavetemp;
import static me.mvabo.verydangerouscaves.cave.worlds;


public class caveOnPlayerMove implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random randint = new Random();

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {
        //Test that makes the chances of getting hot really high
        //int tempChance = randor.nextInt(20);
        if (cavetemp == true) {
            Player p = event.getPlayer();
            if ((!worlds.contains(p.getWorld().getName()))
                    || (p.getLocation().getBlock().getBiome().toString().contains("OCEAN"))) {
                return;
            }

            World wor = p.getWorld();
            if (randint.nextDouble() < plugin.getConfig().getDouble("cave_walk_temp_chance")) {
                //Test that prints to console
                //Bukkit.getServer().getConsoleSender().sendMessage("You are now inside the random int if!");
                if (p.getLocation().getY() < 30 && (p.getGameMode() != GameMode.CREATIVE)) {
                    if (p.getInventory().contains(Material.POTION)
                            || p.getInventory().contains(Material.SPLASH_POTION)
                            || p.getInventory().contains(Material.SNOW)
                            || p.getInventory().contains(Material.SNOWBALL)
                            || p.getInventory().contains(Material.SNOW_BLOCK)
                            || p.getInventory().contains(Material.WATER_BUCKET)
                            || p.getInventory().contains(Material.ICE)
                            || p.getInventory().contains(Material.FROSTED_ICE)
                            || p.getInventory().contains(Material.PACKED_ICE)) {
                        //Test that prints to console
                        //Bukkit.getServer().getConsoleSender().sendMessage("Still keeping cool!");
                    } else if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                        //Test that prints to console
                        //Bukkit.getServer().getConsoleSender().sendMessage("Saved by fire resistance!");
                    } else {
                        if (cave.hotmessage.size() != 0) {
                            int msg = randint.nextInt(cave.hotmessage.size());
                            p.sendMessage(utils.chat(cave.hotmessage.get(msg)));
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 1));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 70, 1));
                    }
                }
            }
        }
    }
}
