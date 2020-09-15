package me.mvabo.verydangerouscaves.caveEffects;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.getBlockInFront.getBlockInFrontOfPlayer;
import static me.mvabo.verydangerouscaves.utils.getLookingAt.getLookingAt2;
import static me.mvabo.verydangerouscaves.utils.utils.*;

public class EffectLooper implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    Random rand = new Random();

    boolean hungerdark = plugin.getConfig().getBoolean("enable_hungering_darkness");
    boolean caveEnts = plugin.getConfig().getBoolean("enable_cave_monsters");

    public List<Entity> effectEnts = new ArrayList<Entity>();
    public List<String> worlds = new ArrayList<String>();

    public void betterEffectLooper() {
        worlds = (List<String>) plugin.getConfig().getList("enabled_cave_worlds");

        if (!effectEnts.isEmpty()) {
            List<Entity> tempEntss = new ArrayList<Entity>(effectEnts);
            for (Entity e : tempEntss) {
                LivingEntity e2 = (LivingEntity) e;
                if (!worlds.contains(e2.getWorld().getName())) {
                    effectEnts.remove(e);
                } else {
                    if (existMonster(e)) {
                        String name = e2.getCustomName();
                        if (name != null) {
                            boolean continuee = true;
                            if (plugin.getConfig().getBoolean("enable_broken_monster_deletion")) {
                                if (name.toLowerCase().contains("elite")) {
                                    continuee = false;
                                    effectEnts.remove(e);
                                    e.remove();
                                } else {
                                    continuee = true;
                                }
                            } else {
                                continuee = true;
                            }
                            if (continuee) {
                                if (hasName("The Darkness", e)) {
                                    if ((e.getLocation().getBlock().getLightLevel() > 0) || (e.getFireTicks() > 0)) {
                                        e.remove();
                                    }
                                    else if (((LivingEntity) (((Monster) e).getTarget())).hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                                        e.remove();
                                    }
                                    else if ((((Monster) e).getTarget().getLocation().getBlock().getLightLevel() == 0)) {
                                        e.getWorld().playSound(e.getLocation(), Sound.ENTITY_CAT_PURR, (float) .5, 0);
                                    }
                                } else if (hasName(plugin.getConfig().getString("watcher"), e)) {
                                    LivingEntity e3 = ((Monster) e).getTarget();
                                    if (e3 instanceof Player) {
                                        if (getLookingAt2(e3, (LivingEntity) e) == false) {
                                            Player p = (Player) e3;
                                            Location loc = getBlockInFrontOfPlayer(p);
                                            double newYaw = 0;
                                            double newPitch = 0;
                                            if (p.getLocation().getYaw() < 0) {
                                                newYaw = p.getLocation().getYaw() + 180;
                                            } else {
                                                newYaw = p.getLocation().getYaw() - 180;
                                            }
                                            newPitch = p.getLocation().getPitch() * -1;
                                            Location jumpLoc = new Location(p.getWorld(), loc.getX(),
                                                    p.getLocation().getY(), loc.getZ(), ((float) newYaw),
                                                    ((float) newPitch));
                                            e.teleport(jumpLoc);
                                            e.setVelocity(new Vector(0, 0, 0));
                                            p.setVelocity(new Vector(0, 0, 0));
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 200));
                                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_HURT, 1, 2);
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 2));
                                        }
                                    }
                                } else if (hasName(plugin.getConfig().getString("dead_miner"), e)) {
                                    if (e.getLocation().getBlock().getLightLevel() == 0) {
                                        e.getLocation().getBlock().setType(Material.TORCH);
                                    }
                                } else if (hasName(plugin.getConfig().getString("magma_monster"), e)) {
                                    if (rand.nextInt(14) == 1) {
                                        e.getLocation().getBlock().setType(Material.FIRE);
                                    }
                                    if (rand.nextInt(28) == 1) {
                                        e.getLocation().subtract(0, 1, 0).getBlock().setType(Material.MAGMA_BLOCK);
                                    }
                                } else if (hasName(plugin.getConfig().getString("lava_creeper"), e)) {
                                    if (rand.nextInt(24) == 1) {
                                        e.getWorld().spawnParticle(Particle.LAVA, e.getLocation().add(0, 1, 0), 1);
                                    }
                                } else if (hasName(plugin.getConfig().getString("smoke_demon"), e)) {
                                    if (e.getLocation().getBlock().getLightLevel() < 12) {
                                        List<Entity> ents = e.getNearbyEntities(3, 3, 3);
                                        for (Entity es : ents) {
                                            if(es == e) {

                                            }
                                            else if (es instanceof LivingEntity) {
                                                ((LivingEntity) es).addPotionEffect(
                                                        new PotionEffect(PotionEffectType.BLINDNESS, 120, 1));
                                                ((LivingEntity) es).addPotionEffect(
                                                        new PotionEffect(PotionEffectType.WITHER, 120, 0));
                                            }
                                        }
                                        e.getWorld().spawnParticle(Particle.CLOUD, e.getLocation().add(0, 1, 0), 60,
                                                1, 1, 1, 0.00f/* , m */);
                                    } else {
                                        e.remove();
                                    }
                                } else {
                                    effectEnts.remove(e);
                                }
                            } else {
                                effectEnts.remove(e);
                            }
                        } else {
                            effectEnts.remove(e);
                        }
                    } else if (existBat(e)) {
                        String name = e2.getCustomName();
                        if (name != null) {
                            if (hasName(plugin.getConfig().getString("crying_bat"), e)) {
                                if (rand.nextInt(30) == 1) {
                                    e.getWorld().playSound(e.getLocation(), Sound.ENTITY_WOLF_WHINE, 1,
                                            (float) (1.4 + (rand.nextInt(5 + 1) / 10.0)));
                                    if (rand.nextInt(5) == 1) {
                                        ((Bat) e).damage(999999);
                                    }
                                }
                            }
                        } else {
                            effectEnts.remove(e);
                        }
                    } else {
                        effectEnts.remove(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void addCryingBat(EntitySpawnEvent e) {
        if (e.getEntityType().equals(EntityType.BAT)) {
            if (e.getEntity().hasMetadata("cryingbat")) {
                effectEnts.add(e.getEntity());
            }
        }
    }

    public boolean fixMonster(LivingEntity e) {
        if (!isMetad(e).equals("null")) {
            String name = isMetad(e);
            if (e != null && !e.isDead()) {
                //try {
                if (name.equals(plugin.getConfig().getString("watcher"))) {
                    ItemStack helmet = e.getEquipment().getHelmet();
                    if (helmet == null) {
                        e.setSilent(false);
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("watcher"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("watcher"))) {
                            e.removeMetadata(plugin.getConfig().getString("watcher"), plugin);
                        }
                        return true;
                    } else if (helmet.getType() != Material.PLAYER_HEAD) {
                        e.setSilent(false);
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("watcher"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("watcher"))) {
                            e.removeMetadata(plugin.getConfig().getString("watcher"), plugin);
                        }
                        return true;
                    }
                } else if (name.equals(plugin.getConfig().getString("tnt_creeper"))) {
                    String cus = e.getCustomName();
                    if (e.getType() != EntityType.CREEPER) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("tnt_creeper"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("tnt_creeper"))) {
                            e.removeMetadata(plugin.getConfig().getString("tnt_creeper"), plugin);
                        }
                        return true;
                    } else if (cus == null) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("tnt_creeper"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("tnt_creeper"))) {
                            e.removeMetadata(plugin.getConfig().getString("tnt_creeper"), plugin);
                        }
                        return true;
                    } else if (!cus.equals(plugin.getConfig().getString("tnt_creeper"))) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("tnt_creeper"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("tnt_creeper"))) {
                            e.removeMetadata(plugin.getConfig().getString("tnt_creeper"), plugin);
                        }
                        return true;
                    }
                } else if (name.equals(plugin.getConfig().getString("lava_creeper"))) {
                    String cus = e.getCustomName();
                    if (e.getType() != EntityType.CREEPER) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("lava_creeper"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("lava_creeper"))) {
                            e.removeMetadata(plugin.getConfig().getString("lava_creeper"), plugin);
                        }
                        return true;
                    } else if (cus == null) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("lava_creeper"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("lava_creeper"))) {
                            e.removeMetadata(plugin.getConfig().getString("lava_creeper"), plugin);
                        }
                        return true;
                    } else if (!cus.equals(plugin.getConfig().getString("lava_creeper"))) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("lava_creeper"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("lava_creeper"))) {
                            e.removeMetadata(plugin.getConfig().getString("lava_creeper"), plugin);
                        }
                        return true;
                    }
                } else if (name.equals(plugin.getConfig().getString("dead_miner"))) {
                    ItemStack helmet = e.getEquipment().getHelmet();
                    if (helmet == null) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("dead_miner"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("dead_miner"))) {
                            e.removeMetadata(plugin.getConfig().getString("dead_miner"), plugin);
                        }
                        e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
                        e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
                        return true;
                    } else if (helmet.getType() != Material.PLAYER_HEAD) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("dead_miner"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("dead_miner"))) {
                            e.removeMetadata(plugin.getConfig().getString("dead_miner"), plugin);
                        }
                        e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
                        e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
                        return true;
                    }
                } else if (name.equals(plugin.getConfig().getString("magma_monster"))) {
                    ItemStack boot = e.getEquipment().getBoots();
                    if (boot == null) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("magma_monster"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("magma_monster"))) {
                            e.removeMetadata(plugin.getConfig().getString("magma_monster"), plugin);
                        }
                        e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
                        e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        }
                        return true;
                    } else if (boot.getType() != Material.LEATHER_BOOTS) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("magma_monster"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("magma_monster"))) {
                            e.removeMetadata(plugin.getConfig().getString("magma_monster"), plugin);
                        }
                        e.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
                        e.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        }
                        return true;
                    }
                } else if (name.equals(plugin.getConfig().getString("smoke_demon"))) {
                    String cus = e.getCustomName();
                    if (e.getType() != EntityType.ZOMBIE) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("smoke_demon"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("smoke_demon"))) {
                            e.removeMetadata(plugin.getConfig().getString("smoke_demon"), plugin);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        return true;
                    } else if (cus == null) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("smoke_demon"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("smoke_demon"))) {
                            e.removeMetadata(plugin.getConfig().getString("smoke_demon"), plugin);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        return true;
                    } else if (!cus.equals(plugin.getConfig().getString("smoke_demon"))) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals(plugin.getConfig().getString("smoke_demon"))) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata(plugin.getConfig().getString("smoke_demon"))) {
                            e.removeMetadata(plugin.getConfig().getString("smoke_demon"), plugin);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        return true;
                    }
                } else if (name.equals("The Darkness")) {
                    String cus = e.getCustomName();
                    if (e.getType() != EntityType.HUSK) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals("The Darkness")) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata("The Darkness")) {
                            e.removeMetadata("The Darkness", plugin);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        }
                        ((LivingEntity) e).setCollidable(true);
                        return true;
                    } else if (cus == null) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals("The Darkness")) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata("The Darkness")) {
                            e.removeMetadata("The Darkness", plugin);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        }
                        ((LivingEntity) e).setCollidable(true);
                        return true;
                    } else if (!cus.equals("The Darkness")) {
                        e.setSilent(false);
                        if (e.getCustomName() != null) {
                            if (e.getCustomName().equals("The Darkness")) {
                                e.setCustomName("");
                            }
                        }
                        if (e.hasMetadata("The Darkness")) {
                            e.removeMetadata("The Darkness", plugin);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.SLOW)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.SLOW);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        }
                        if (((LivingEntity) e).hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                            ((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        }
                        ((LivingEntity) e).setCollidable(true);
                        return true;
                    }
                }
                return false;
                //} catch (Exception error) {
                //    return false;
                //}
            }
        }
        return false;
    }

    public String isMetad(LivingEntity e) {
        if (e.hasMetadata(plugin.getConfig().getString("watcher"))) {
            return plugin.getConfig().getString("watcher");
        } else if (e.hasMetadata(plugin.getConfig().getString("magma_monster"))) {
            return plugin.getConfig().getString("magma_monster");
        } else if (e.hasMetadata(plugin.getConfig().getString("lava_creeper"))) {
            return plugin.getConfig().getString("lava_creeper");
        } else if (e.hasMetadata(plugin.getConfig().getString("tnt_creeper"))) {
            return plugin.getConfig().getString("tnt_creeper");
        } else if (e.hasMetadata(plugin.getConfig().getString("smoke_demon"))) {
            return plugin.getConfig().getString("smoke_demon");
        } else if (e.hasMetadata(plugin.getConfig().getString("alpha_spider"))) {
            return plugin.getConfig().getString("alpha_spider");
        } else if (e.hasMetadata(plugin.getConfig().getString("dead_miner"))) {
            return plugin.getConfig().getString("dead_miner");
        } else if (e.hasMetadata(plugin.getConfig().getString("hexed_armor"))) {
            return plugin.getConfig().getString("hexed_armor");
        } else if (e.hasMetadata("The Darkness")) {
            return "The Darkness";
        }
        return "null";
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if(!existMonsterBefore(event.getEntity())) {
            return;
        }
        else if(!existTarget(event.getTarget())) {
            return;
        }
        //console.sendMessage("yea");
        if (hungerdark == true) {
            if (plugin.getConfig().getBoolean("enable_monster_fixing")) {
                if (!worlds.contains(event.getEntity().getWorld().getName())) {
                    return;
                }
                World wor = event.getEntity().getWorld();
                if (event.getEntity() instanceof Monster) {
                    if (fixMonster((LivingEntity) event.getEntity()) == false) {
                        Monster e = (Monster) event.getEntity();
                        if (hasName("The Darkness", e)) {
                            if (event.getTarget().getLocation().getBlock().getLightLevel() > 0) {
                                event.setTarget(null);
                            } else {
                                effectEnts.add(event.getEntity());
                            }
                        }
                    }
                }
            }
            else {
                if (!worlds.contains(event.getEntity().getWorld().getName())) {
                    return;
                }
                World wor = event.getEntity().getWorld();
                if (event.getEntity() instanceof Monster) {
                    if (fixMonster((LivingEntity) event.getEntity()) == false) {
                        Monster e = (Monster) event.getEntity();
                        if (hasName("The Darkness", e)) {
                            if (event.getTarget().getLocation().getBlock().getLightLevel() > 0) {
                                event.setTarget(null);
                            }
                            else {
                                effectEnts.add(event.getEntity());
                            }
                        }
                    }
                }
            }
        }
        if (caveEnts == true) {
            if (event.getEntity() instanceof Monster) {
                if (plugin.getConfig().getBoolean("enable_monster_fixing")) {
                    if (fixMonster((LivingEntity) event.getEntity()) == false) {
                        Monster e = (Monster) event.getEntity();
                        if (hasName(plugin.getConfig().getString("watcher"), e)) {
                            if (e.getCustomName() != null) {
                                String name = e.getCustomName();
                                if (name.toLowerCase().contains("elite")) {
                                    e.remove();
                                }
                            }
                        }
                        else if (hasName(plugin.getConfig().getString("magma_monster"), e)) {
                            effectEnts.add(event.getEntity());
                        }
                        else if (hasName(plugin.getConfig().getString("dead_miner"), e)) {
                            effectEnts.add(event.getEntity());
                        }
                        else if (hasName(plugin.getConfig().getString("lava_creeper"), e)) {
                            effectEnts.add(event.getEntity());
                        }
                        else if (hasName(plugin.getConfig().getString("smoke_demon"), e)) {
                            effectEnts.add(event.getEntity());
                        }
                    }
                } else {
                    Monster e = (Monster) event.getEntity();
                    if (hasName(plugin.getConfig().getString("watcher"), e)) {
                        effectEnts.add(event.getEntity());
                    }
                    else if (hasName(plugin.getConfig().getString("magma_monster"), e)) {
                        effectEnts.add(event.getEntity());
                    }
                    else if (hasName(plugin.getConfig().getString("dead_miner"), e)) {
                        effectEnts.add(event.getEntity());
                    }
                    else if (hasName(plugin.getConfig().getString("lava_creeper"), e)) {
                        effectEnts.add(event.getEntity());
                    }
                    else if (hasName(plugin.getConfig().getString("smoke_demon"), e)) {
                        effectEnts.add(event.getEntity());
                    }
                }
            }
        }
    }
}
