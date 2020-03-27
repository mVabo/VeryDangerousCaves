package me.mvabo.verydangerouscaves.caveListeners;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;

import me.mvabo.verydangerouscaves.cave;
import me.mvabo.verydangerouscaves.utils.utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.isAir.isAir;

public class onBreakBlock implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random randint = new Random();

    @EventHandler
    public void onBreakB(BlockBreakEvent dr) {
        Player p = dr.getPlayer();
        if (!cave.worlds.contains(p.getWorld().getName())) {
            return;
        }
        World wor = p.getWorld();
        if (cave.caveins) {
            if (randint.nextDouble() < plugin.getConfig().getDouble("cave-in_chance")) {
                if (dr.getPlayer().getLocation().getY() < 25) {
                    if ((isStony(dr.getBlock().getType()))
                            && (isStony(p.getLocation().subtract(0, 1, 0).getBlock().getType()))) {
                        if (p.getInventory().contains(Material.RABBIT_FOOT)) {

                        } else {
                            Location loc = p.getLocation();
                            int radius = 7;
                            int cx = loc.getBlockX();
                            int cy = loc.getBlockY();
                            int cz = loc.getBlockZ();
                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 65, 3));
                            for (int x = cx - radius; x <= cx + radius; x++) {
                                for (int z = cz - radius; z <= cz + radius; z++) {
                                    for (int y = (cy - radius); y < (cy + radius); y++) {
                                        double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z)
                                                + ((cy - y) * (cy - y));

                                        if (dist < radius * radius) {
                                            Location l = new Location(loc.getWorld(), x, y + 2, z);
                                            Block b = l.getBlock();
                                            if (isAir(b.getType()) || b.getType() == Material.BEDROCK || b.getType()
                                                    .toString().toLowerCase().contains("shulker_box")) {
                                            } else {
                                                b.getWorld().spawnFallingBlock(b.getLocation(), b.getBlockData());
                                                l.getBlock().setType(Material.AIR);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (cave.cavetemp == true && (!p.getLocation().getBlock().getBiome().toString().contains("OCEAN"))) {
            if (randint.nextDouble() < plugin.getConfig().getDouble("cave_break_block_temp_chance")) {
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
                    } else if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                    } else {
                        if (cave.hotmessage.size() != 0) {
                            int msg = randint.nextInt(cave.hotmessage.size());
                            p.sendMessage(utils.chat(cave.hotmessage.get(msg)));
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 55, 1));
                    }
                }
            }
        }
    }

    private boolean isStony(Material m) {
        if (m.name().toLowerCase().contains("dirt") || m == Material.STONE || m == Material.MOSSY_COBBLESTONE
                || m == Material.ANDESITE || m == Material.DIORITE || m == Material.COBBLESTONE || m == Material.GRANITE
                || m == Material.GRAVEL) {
            return true;
        }
        return false;
    }
}
