package me.mvabo.verydangerouscaves.mobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.isAir.isAir;
import static me.mvabo.verydangerouscaves.utils.utils.hasName;

public class DamageHandler implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    Random rand = new Random();

    public List<String> worlds = new ArrayList<String>();

    @EventHandler
    public void entityHit(EntityDamageByEntityEvent event) {
        if (!worlds.contains(event.getEntity().getWorld().getName())) {
            return;
        }
        World wor = event.getEntity().getWorld();
        if (event.getDamager() instanceof Monster && event.getEntity() instanceof Player) {
            if (hasName(plugin.getConfig().getString("magma_monster"), event.getDamager())) {
                if (rand.nextInt(2) == 1) {
                    ((LivingEntity) event.getEntity()).setFireTicks(60);
                }
                return;
            }
            if (hasName(plugin.getConfig().getString("alpha_spider"), event.getDamager())) {
                if (rand.nextInt(2) == 1) {
                    if (rand.nextInt(5) == 1) {
                        event.getEntity().getLocation().getBlock().setType(Material.COBWEB);
                        event.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.COBWEB);
                        Location loc = event.getEntity().getLocation();
                        if (rand.nextInt(15) == 1) {
                            event.getDamager().getWorld().spawnEntity(event.getDamager().getLocation(),
                                    EntityType.CAVE_SPIDER);
                        }
                        int radius = 4;
                        int cx = loc.getBlockX();
                        int cy = loc.getBlockY();
                        int cz = loc.getBlockZ();
                        for (int x = cx - radius; x <= cx + radius; x++) {
                            for (int z = cz - radius; z <= cz + radius; z++) {
                                for (int y = (cy - radius); y < (cy + radius); y++) {
                                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

                                    if (dist < radius * radius) {
                                        Location l = new Location(loc.getWorld(), x, y, z);
                                        if (rand.nextInt(7) == 1) {
                                            if (isAir(l.getBlock().getType())) {
                                                l.getBlock().setType(Material.COBWEB);
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                    ((LivingEntity) event.getEntity())
                            .addPotionEffect(new PotionEffect(PotionEffectType.POISON, 75, 1));
                }
                return;
            }
            if (hasName(plugin.getConfig().getString("hexed_armor"), event.getDamager())) {
                if (rand.nextInt(100) < plugin.getConfig().getInt("hexed_armor_drop_chance")) {
                    Monster m = (Monster) event.getDamager();
                    Player p = (Player) event.getEntity();
                    ItemStack[] armor = p.getInventory().getArmorContents();
                    for (ItemStack i2 : armor) {
                        if (i2 != null) {
                            if (i2.getType() != null) {
                                if (i2.getType() != Material.AIR) {
                                    p.getWorld().dropItemNaturally(m.getLocation(), i2);
                                }
                            }
                        }
                    }
                    p.getInventory().setArmorContents(m.getEquipment().getArmorContents());
                    m.getEquipment().clear();
                    m.remove();
                }
                return;
            }

        }
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Monster) {
            if (rand.nextInt(6) == 1) {
                if (hasName(plugin.getConfig().getString("tnt_creeper"), event.getEntity())) {
                    event.getDamager().getWorld().createExplosion(event.getDamager().getLocation(), (float) 0.01);
                    return;
                }
            }
            if (rand.nextInt(6) == 1) {
                if (hasName(plugin.getConfig().getString("dead_miner"), event.getEntity())) {
                    // 0 == cobblestone 1 == dirt 2 == coal 3 == torch
                    int choice = rand.nextInt(4);
                    if (choice == 0) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.COBBLESTONE, rand.nextInt(3) + 1));
                    } else if (choice == 1) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.DIRT, rand.nextInt(3) + 1));
                    } else if (choice == 2) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.COAL, rand.nextInt(2) + 1));
                    } else if (choice == 3) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.TORCH, rand.nextInt(3) + 1));
                    }
                    return;
                }
            }
            if (hasName(plugin.getConfig().getString("watcher"), event.getEntity())) {
                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_SLIME_SQUISH,
                        1, (float) 1.1);
                return;
            }
        }
    }

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        if (!worlds.contains(event.getEntity().getWorld().getName())) {
            return;
        }
        World wor = event.getEntity().getWorld();
        if (event.getEntity().getType() == EntityType.CREEPER) {
            if (hasName(plugin.getConfig().getString("tnt_creeper"), event.getEntity())) {
                Location l = event.getEntity().getLocation();
                Entity e1 = event.getEntity().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
                e1.setVelocity(new Vector(-1 * (rand.nextInt(5 + 1) / 10.0), rand.nextInt(5 + 1) / 10.0,
                        -1 * (rand.nextInt(5 + 1) / 10.0)));
                if (rand.nextBoolean() == true) {
                    Entity e2 = event.getEntity().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
                    e2.setVelocity(new Vector(rand.nextInt(5 + 1) / 10.0, rand.nextInt(5 + 1) / 10.0,
                            rand.nextInt(5 + 1) / 10.0));
                }
                return;
            }
            if (hasName(plugin.getConfig().getString("lava_creeper"), event.getEntity())) {
                Location l = event.getEntity().getLocation();
                if (wor != null) {
                    Random rand = new Random();
                    Location loc = l;
                    int radius = 4;
                    int cx = loc.getBlockX();
                    int cy = loc.getBlockY();
                    int cz = loc.getBlockZ();
                    for (int x = cx - radius; x <= cx + radius; x++) {
                        for (int z = cz - radius; z <= cz + radius; z++) {
                            for (int y = (cy - radius); y < (cy + radius); y++) {
                                double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

                                if (dist < radius * radius) {
                                    Location l2 = new Location(loc.getWorld(), x, y, z);
                                    if (l2.getBlock().getType() != Material.BEDROCK) {
                                        if (isAir(l2.getBlock().getType())) {
                                            if (rand.nextInt(3) == 1) {
                                                l2.getBlock().setType(Material.FIRE);
                                            }
                                        } else if (!isAir(l2.getBlock().getType())) {
                                            if (rand.nextInt(4) == 1) {
                                                l2.getBlock().setType(Material.MAGMA_BLOCK);
                                            } else if (rand.nextInt(5) == 1) {
                                                l2.getBlock().setType(Material.OBSIDIAN);
                                            } else if (rand.nextInt(6) == 1) {
                                                l2.getBlock().setType(Material.LAVA);
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                return;
            }
        }
    }

    @EventHandler
    public void onDamageP(EntityDamageByEntityEvent e) {
        if (!worlds.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        World wor = e.getEntity().getWorld();
        if (e.getEntity() instanceof Player) {
            if (hasName("The Darkness", e.getDamager())) {
                if (e.getEntity().getLocation().getBlock().getLightLevel() > 0) {
                    e.setCancelled(true);
                    e.getDamager().remove();
                }
            }
        }
    }

    public void getWorlds() {
        this.worlds = (List<String>) plugin.getConfig().getList("enabled_cave_worlds");
    }
}
