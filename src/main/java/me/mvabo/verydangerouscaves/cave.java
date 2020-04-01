package me.mvabo.verydangerouscaves;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.generators.structurefiles;
import me.mvabo.verydangerouscaves.utils.utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.exists.exists;
import static me.mvabo.verydangerouscaves.utils.getBlockInFront.getBlockInFrontOfPlayer;
import static me.mvabo.verydangerouscaves.utils.getLookingAt.getLookingAt2;
import static me.mvabo.verydangerouscaves.utils.isAir.isAir;

public class cave implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random randint = new Random();

    boolean hasWorlds = false;
    public static List<String> worlds = new ArrayList<String>();
    public boolean canSave = false;
    public static List<Entity> effectEnts = new ArrayList<Entity>();
    public static boolean caveins = false;
    public static boolean hungerdark = false;
    public static boolean ambients = false;
    public static boolean cavetemp = false;
    public static boolean caveage = false;
    public static boolean caveents = false;
    public static boolean cavestruct = false;
    public static int cavechance = 3;
    public static int crystalCaveChance = 0;
    public static List<String> mobNames = new ArrayList<String>();
    public static List<String> itemcustom = new ArrayList<String>();
    public static List<String> hotmessage = new ArrayList<String>();
    public static int damage = 0;
    public static int trrate = 0;
    public static int strate = 0;
    public static int blrate = 0;
    public static int plrate = 0;
    public static boolean skulls = true;
    public static boolean easter = true;

    public ArrayList<String> roominfo = new ArrayList<String>();

    public static int roomX = -1;
    public static int roomY = -1;
    public static int roomZ = -1;

    @EventHandler
    public void getCaveConfigs(WorldInitEvent event) {
        //System.out.println(utils.chat("&6Reading thru caveConfigs"));
        roomX = plugin.getConfig().getInt("002roomx");
        roomY = plugin.getConfig().getInt("002roomy");
        roomZ = plugin.getConfig().getInt("002roomz");

        caveins = plugin.getConfig().getBoolean("enable_cave_ins");
        hungerdark = plugin.getConfig().getBoolean("enable_hungering_darkness");
        ambients = plugin.getConfig().getBoolean("enable_ambient_cave_sounds");
        cavetemp = plugin.getConfig().getBoolean("enable_cave_temperature");
        caveage = plugin.getConfig().getBoolean("enable_cave_aging");
        caveents = plugin.getConfig().getBoolean("enable_cave_monsters");
        cavechance = plugin.getConfig().getInt("cave_structure_chance");
        crystalCaveChance = plugin.getConfig().getInt("crystal_cave_chance");
        cavestruct = plugin.getConfig().getBoolean("enable_cave_structures");
        easter = plugin.getConfig().getBoolean("enable_easter_eggs");
        blrate = plugin.getConfig().getInt("boulder_rate");
        trrate = plugin.getConfig().getInt("traps_rate");
        strate = plugin.getConfig().getInt("buildings_rate");
        plrate = plugin.getConfig().getInt("pillar_rate");
        skulls = plugin.getConfig().getBoolean("cave_skulls");
        mobNames.add("The Darkness");
        mobNames.add(plugin.getConfig().getString("magma_monster"));
        mobNames.add(plugin.getConfig().getString("crying_bat"));
        mobNames.add(plugin.getConfig().getString("lava_creeper"));
        mobNames.add(plugin.getConfig().getString("tnt_creeper"));
        mobNames.add(plugin.getConfig().getString("watcher"));
        mobNames.add(plugin.getConfig().getString("smoke_demon"));
        mobNames.add(plugin.getConfig().getString("alpha_spider"));
        mobNames.add(plugin.getConfig().getString("dead_miner"));
        mobNames.add(plugin.getConfig().getString("hexed_armor"));
        itemcustom = (List<String>) plugin.getConfig().getList("custom_items");
        hotmessage = (List<String>) plugin.getConfig().getList("temperature_messages");
        worlds = (List<String>) plugin.getConfig().getList("enabled_cave_worlds");
        int d = plugin.getConfig().getInt("Hungering Darkness Damage ");
        if (d > 200) {
            damage = 200;
        } else if (d < 0) {
            damage = 0;
        } else {
            damage = d;
        }
    }

    @EventHandler
    public void setWorld(PlayerJoinEvent e) {
        if (hasWorlds == false) {
            caveins = plugin.getConfig().getBoolean("enable_cave_ins");
            hungerdark = plugin.getConfig().getBoolean("enable_hungering_darkness");
            ambients = plugin.getConfig().getBoolean("enable_ambient_cave_sounds");
            cavetemp = plugin.getConfig().getBoolean("enable_cave_temperature");
            caveage = plugin.getConfig().getBoolean("enable_cave_aging");
            caveents = plugin.getConfig().getBoolean("enable_cave_monsters");
            cavechance = plugin.getConfig().getInt("cave_structure_chance");
            cavestruct = plugin.getConfig().getBoolean("enable_cave_structures");
        }
        // }
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

    public boolean existTarget(Entity e) {
        if(e == null) {
            return false;
        }
        if(e.isDead()) {
            return false;
        }
        return true;
    }

    public boolean existMonsterBefore(Entity e) {
        if (e == null) {
            return false;
        }
        if (!(e instanceof Monster)) {
            return false;
        }
        Monster m = (Monster) e;
        if (m.isDead()) {
            return false;
        }
        return true;
    }

    public boolean existMonster(Entity e) {
        if (e == null) {
            return false;
        }
        if (!(e instanceof Monster)) {
            return false;
        }
        Monster m = (Monster) e;
        if (m.isDead()) {
            return false;
        }
        if (m.getTarget() == null) {
            return false;
        }
        if (m.getTarget().isDead()) {
            return false;
        }
        return true;
    }

    public boolean existBat(Entity e) {
        if (e == null) {
            return false;
        }
        if (!(e instanceof Bat)) {
            return false;
        }
        if (e.isDead()) {
            return false;
        }
        return true;
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
        if (caveents == true) {
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

    @EventHandler
    public void betterEffectLooper(PlayerMoveEvent event) {
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
                                    if (randint.nextInt(14) == 1) {
                                        e.getLocation().getBlock().setType(Material.FIRE);
                                    }
                                    if (randint.nextInt(28) == 1) {
                                        e.getLocation().subtract(0, 1, 0).getBlock().setType(Material.MAGMA_BLOCK);
                                    }
                                } else if (hasName(plugin.getConfig().getString("lava_creeper"), e)) {
                                    if (randint.nextInt(24) == 1) {
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
                                if (randint.nextInt(30) == 1) {
                                    e.getWorld().playSound(e.getLocation(), Sound.ENTITY_WOLF_WHINE, 1,
                                            (float) (1.4 + (randint.nextInt(5 + 1) / 10.0)));
                                    if (randint.nextInt(5) == 1) {
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

    public boolean hasName(String n, Entity e) {
        if(exists(e)) {
            if(e.hasMetadata(n)) {
                return true;
            }
            if(e.getCustomName() != null) {
                String name = ChatColor.stripColor(e.getCustomName());
                if((!name.equals("")) && (!name.equals(" "))) {
                    if(name.equals(n)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (hungerdark == true) {
            if (!worlds.contains(e.getEntity().getWorld().getName())) {
                return;
            }
            World wor = e.getEntity().getWorld();
            if (hasName("The Darkness", e.getEntity())) {
                if (((LivingEntity) e.getEntity()).getHealth() - e.getFinalDamage() <= 0) {
                    e.setCancelled(true);
                    e.getEntity().remove();
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (hungerdark == true) {
            if (!worlds.contains(e.getEntity().getWorld().getName())) {
                return;
            }
            World wor = e.getEntity().getWorld();
            if (hasName("The Darkness", e.getEntity())) {
                List<ItemStack> drops = e.getDrops();
                for (ItemStack i : drops) {
                    i.setType(Material.AIR);
                }
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

    public void doCaveSounds() {
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
                                p.playSound(p.getLocation(), Sound.MUSIC_NETHER, SoundCategory.AMBIENT, 100,
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

    public boolean inCave(Player p) {
        boolean cave = true;
        if (p.getLocation().getBlock().getLightFromSky() > 0) {
            return false;
        }
        if (p.getLocation().getY() < 49) {
            return false;
        }
        if (p.getGameMode() != GameMode.SURVIVAL) {
            return false;
        }
        if (!isStony(p.getLocation().subtract(0, 1, 0).getBlock().getType())) {
            return false;
        }
        return cave;
    }

    public boolean isStony(Material m) {
        if (m.name().toLowerCase().contains("dirt") || m == Material.STONE || m == Material.MOSSY_COBBLESTONE
                || m == Material.ANDESITE || m == Material.DIORITE || m == Material.COBBLESTONE || m == Material.GRANITE
                || m == Material.GRAVEL) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void doCaveStuff(PlayerBedEnterEvent event) {
        Player p = event.getPlayer();
        if (!worlds.contains(p.getWorld().getName())) {
            return;
        }
        if(inCave(p)) {
            if (randint.nextInt(100) < plugin.getConfig().getInt("cave_aging_chance")) {
                World wor = p.getWorld();
                if (wor != null) {
                    Random rand = new Random();
                    Location loc = p.getLocation();
                    int radius = 45;
                    int cx = loc.getBlockX();
                    int cy = loc.getBlockY();
                    int cz = loc.getBlockZ();
                    for (int x = cx - radius; x <= cx + radius; x++) {
                        for (int z = cz - radius; z <= cz + radius; z++) {
                            for (int y = (cy - radius); y < (cy + radius); y++) {
                                double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));

                                if (dist < radius * radius) {
                                    if (randint.nextInt(100) < plugin.getConfig().getInt("cave_aging_chance_chance")) {
                                        Location l = new Location(loc.getWorld(), x, y, z);
                                        doCaveBlocks(l.getBlock(), p);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void doCaveBlocks(Block b, Player p) {
        if (b.getLocation().getY() > 50 || b.getLightFromSky() > 0) {
            return;
        }
        if (isStony(b.getType()) && randint.nextInt(2) == 1) {
            int chose = randint.nextInt(3);
            if (chose == 0) {
                b.setType(Material.COBBLESTONE);
            } else if (chose == 1) {
                b.setType(Material.ANDESITE);
            } else {
                Block b2 = b.getLocation().subtract(0, 1, 0).getBlock();
                if (randint.nextInt(2) == 1 && isAir(b2.getType())) {
                    b2.setType(Material.COBBLESTONE_WALL);
                }
            }
        }
        if (b.getType() == Material.COBBLESTONE_WALL) {
            if (randint.nextInt(3) == 1) {
                b.setType(Material.COBBLESTONE);
            } else {
                Block b2 = b.getLocation().subtract(0, 1, 0).getBlock();
                if (randint.nextInt(2) == 1 && isAir(b2.getType())) {
                    b2.setType(Material.COBBLESTONE_WALL);
                }
            }
        }
        if ((isStony(b.getType())) && (randint.nextInt(8) == 1)) {
            doVines2(b.getLocation());
        }
        if ((isStony(b.getType())) && randint.nextInt(9) == 1) {
            Block b2 = b.getRelative(BlockFace.UP);
            if (b2 != null) {
                if (isAir(b2.getType())) {
                    if (randint.nextBoolean()) {
                        b2.setType(Material.BROWN_MUSHROOM);
                    } else {
                        b2.setType(Material.RED_MUSHROOM);
                    }
                }
            }
        }
        if ((isStony(b.getType())) && randint.nextInt(6) == 1) {
            Block b2 = b.getRelative(BlockFace.UP);
            if (b2 != null) {
                if (isAir(b2.getType())) {
                    b2.setType(Material.STONE_BUTTON);
                    Switch dr = (Switch) b2.getBlockData();
                    dr.setFace(Switch.Face.FLOOR);
                    b2.setBlockData(dr);
                }
            }
        }
    }

    public void doVines2(Location l) {
        World wor = l.getWorld();
        if (wor != null) {
            Location loc = l;
            Block b = l.getBlock();
            if (b != null) {
                if (b.getType().name().toLowerCase().contains("leave") || isStony(b.getType())) {
                    BlockFace[] blocksf = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
                    for (BlockFace blockface : blocksf) {
                        Block b2 = l.getBlock().getRelative(blockface);
                        if (b2 != null) {
                            if (isAir(b2.getType())) {
                                b2.setType(Material.VINE);
                                if (b2.getBlockData() instanceof MultipleFacing) {
                                    MultipleFacing dr = (MultipleFacing) b2.getBlockData();
                                    dr.setFace(oppisiteBf(blockface), true);
                                    b2.setBlockData(dr);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public BlockFace oppisiteBf(BlockFace be) {
        if (be == BlockFace.EAST) {
            return BlockFace.WEST;
        } else if (be == BlockFace.WEST) {
            return BlockFace.EAST;
        } else if (be == BlockFace.NORTH) {
            return BlockFace.SOUTH;
        } else {
            return BlockFace.NORTH;
        }
    }

    @EventHandler
    public void entityHit(EntityDamageByEntityEvent event) {
        if (!worlds.contains(event.getEntity().getWorld().getName())) {
            return;
        }
        World wor = event.getEntity().getWorld();
        if (event.getDamager() instanceof Monster && event.getEntity() instanceof Player) {
            if (hasName(plugin.getConfig().getString("magma_monster"), event.getDamager())) {
                if (randint.nextInt(2) == 1) {
                    ((LivingEntity) event.getEntity()).setFireTicks(60);
                }
                return;
            }
            if (hasName(plugin.getConfig().getString("alpha_spider"), event.getDamager())) {
                if (randint.nextInt(2) == 1) {
                    if (randint.nextInt(5) == 1) {
                        event.getEntity().getLocation().getBlock().setType(Material.COBWEB);
                        event.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.COBWEB);
                        Location loc = event.getEntity().getLocation();
                        if (randint.nextInt(15) == 1) {
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
                                        if (randint.nextInt(7) == 1) {
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
                if (randint.nextInt(100) < plugin.getConfig().getInt("hexed_armor_drop_chance")) {
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
            if (randint.nextInt(6) == 1) {
                if (hasName(plugin.getConfig().getString("tnt_creeper"), event.getEntity())) {
                    event.getDamager().getWorld().createExplosion(event.getDamager().getLocation(), (float) 0.01);
                    return;
                }
            }
            if (randint.nextInt(6) == 1) {
                if (hasName(plugin.getConfig().getString("dead_miner"), event.getEntity())) {
                    // 0 == cobblestone 1 == dirt 2 == coal 3 == torch
                    int choice = randint.nextInt(4);
                    if (choice == 0) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.COBBLESTONE, randint.nextInt(3) + 1));
                    } else if (choice == 1) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.DIRT, randint.nextInt(3) + 1));
                    } else if (choice == 2) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.COAL, randint.nextInt(2) + 1));
                    } else if (choice == 3) {
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                new ItemStack(Material.TORCH, randint.nextInt(3) + 1));
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
                e1.setVelocity(new Vector(-1 * (randint.nextInt(5 + 1) / 10.0), randint.nextInt(5 + 1) / 10.0,
                        -1 * (randint.nextInt(5 + 1) / 10.0)));
                if (randint.nextBoolean() == true) {
                    Entity e2 = event.getEntity().getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
                    e2.setVelocity(new Vector(randint.nextInt(5 + 1) / 10.0, randint.nextInt(5 + 1) / 10.0,
                            randint.nextInt(5 + 1) / 10.0));
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
                                            if (randint.nextInt(3) == 1) {
                                                l2.getBlock().setType(Material.FIRE);
                                            }
                                        } else if (!isAir(l2.getBlock().getType())) {
                                            if (randint.nextInt(4) == 1) {
                                                l2.getBlock().setType(Material.MAGMA_BLOCK);
                                            } else if (randint.nextInt(5) == 1) {
                                                l2.getBlock().setType(Material.OBSIDIAN);
                                            } else if (randint.nextInt(6) == 1) {
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

    //Smaller eventHandlers
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        if (roominfo.contains(e.getPlayer().getName())) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 85, 1));
            Bukkit.getScheduler().runTaskLater(plugin, () -> manipulateRoomSpace(e.getPlayer()), 60);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().hasMetadata("room1") || e.getBlock().hasMetadata("room2")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().hasMetadata("room1") || e.getBlock().hasMetadata("room2")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.isCancelled() == false) {
            ArrayList<Block> blocks = new ArrayList<Block>(e.blockList());
            for (Block b : blocks) {
                if (b.hasMetadata("room1") || b.hasMetadata("room2")) {
                    e.blockList().remove(b);
                }
            }
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        if (e.isCancelled() == false) {
            ArrayList<Block> blocks = new ArrayList<Block>(e.blockList());
            for (Block b : blocks) {
                if (b.hasMetadata("room1") || b.hasMetadata("room2")) {
                    e.blockList().remove(b);
                }
            }
        }
    }

    @EventHandler
    public void onEnter(PlayerMoveEvent e) {
        if (roominfo.contains(e.getPlayer().getName())) {
            if ((!e.getPlayer().getLocation().getBlock().hasMetadata("room1"))
                    && (!e.getPlayer().getLocation().getBlock().hasMetadata("room2"))) {
                removeRoomWarp(e.getPlayer());
                roominfo.remove(e.getPlayer().getName());
            }
        }
        if (e.getPlayer().getLocation().getBlock().hasMetadata("room2")) {
            if (!roominfo.contains(e.getPlayer().getName())) {
                roominfo.add(e.getPlayer().getName());
            }
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if (((int) e.getTo().getX()) != ((int) e.getFrom().getX())
                || ((int) e.getTo().getY()) != ((int) e.getFrom().getY())
                || ((int) e.getTo().getZ()) != ((int) e.getFrom().getZ())) {
            // check if new location is equal to room location
            if ((((int) e.getFrom().getX()) == roomX) && (((int) e.getFrom().getY()) == roomY)
                    && (((int) e.getFrom().getZ()) == roomZ)) {
                // if entity exits room
                if ((((int) e.getTo().getX()) == roomX + 1) && (((int) e.getTo().getY()) == roomY)
                        && (((int) e.getTo().getZ()) == roomZ)) {
                    removeRoomWarp(e.getPlayer());
                    if (roominfo.contains(e.getPlayer().getName())) {
                        roominfo.remove(e.getPlayer().getName());
                    }
                }
                // if entity enters room
                if ((((int) e.getTo().getX()) == roomX) && (((int) e.getTo().getY()) == roomY)
                        && (((int) e.getTo().getZ()) == roomZ - 1)) {
                    manipulateRoomSpace(e.getPlayer());
                    if (!roominfo.contains(e.getPlayer().getName())) {
                        roominfo.add(e.getPlayer().getName());
                    }
                }
            }
        }
    }

    public void removeRoomWarp(Player p) {
        Location door1 = new Location(p.getWorld(), roomX - 15, roomY - 2, roomZ - 3);
        Location door2 = new Location(p.getWorld(), roomX - 8, roomY - 2, roomZ + 6);
        Location door3 = new Location(p.getWorld(), roomX - 8, roomY - 2, roomZ - 11);
        // door 1
        generateStructure0(structurefiles.zerozerotwo2, door1.clone(), false, "", true, p, 0, true);
        generateStructure0(structurefiles.zerozerotwo3, door2.clone(), false, "", true, p, 0, true);
        generateStructure0(structurefiles.zerozerotwo4, door3.clone(), false, "", true, p, 0, true);
    }

    public void manipulateRoomSpace(Player p) {
        Location door1 = new Location(p.getWorld(), roomX - 15, roomY - 2, roomZ - 3);
        Location door2 = new Location(p.getWorld(), roomX - 8, roomY - 2, roomZ + 6);
        Location door3 = new Location(p.getWorld(), roomX - 8, roomY - 2, roomZ - 11);

        generateStructure0(structurefiles.zerozerotwo2, door1.clone(), false, "", true, p, 0, false);
        generateStructure0(structurefiles.zerozerotwo3, door2.clone(), false, "", true, p, 0, false);
        generateStructure0(structurefiles.zerozerotwo4, door3.clone(), false, "", true, p, 0, false);
    }

    public void decideBlock(int type, Block b, boolean packet, Player p, boolean overwrite) {
        // 0 == air 1 == null 2 == netherwart brick 3 == netherwart block+red concrete
        // powder 4 == barrier 5 ==netherrack & netherwart block
        if (overwrite) {
            if (packet) {
                p.sendBlockChange(b.getLocation(), b.getType().createBlockData());
            } else {
                b.setType(b.getType(), false);
            }
        } else {
            if (type == -1) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), b.getType().createBlockData());
                } else {
                    b.setType(b.getType(), false);
                }
            }
            if (type == 0) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.AIR.createBlockData());
                } else {
                    b.setType(Material.AIR, false);
                }
            } else if (type == 1) {

            } else if (type == 2) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_NETHER_BRICKS.createBlockData());
                } else {
                    b.setType(Material.RED_NETHER_BRICKS, false);
                }
            } else if (type == 3) {
                int choice = randint.nextInt(2);
                if (choice == 0) {
                    if (packet) {
                        p.sendBlockChange(b.getLocation(), Material.NETHER_WART_BLOCK.createBlockData());
                    } else {
                        b.setType(Material.NETHER_WART_BLOCK, false);
                    }
                } else if (choice == 1) {
                    if (packet) {
                        p.sendBlockChange(b.getLocation(), Material.RED_CONCRETE_POWDER.createBlockData());
                    } else {
                        b.setType(Material.RED_CONCRETE_POWDER, false);
                    }
                }
            } else if (type == 4) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.BARRIER.createBlockData());
                } else {
                    b.setType(Material.BARRIER, false);
                }
            } else if (type == 5) {
                int choice = randint.nextInt(2);
                if (choice == 0) {
                    if (packet) {
                        p.sendBlockChange(b.getLocation(), Material.NETHER_WART_BLOCK.createBlockData());
                    } else {
                        b.setType(Material.NETHER_WART_BLOCK, false);
                    }
                } else if (choice == 1) {
                    if (packet) {
                        p.sendBlockChange(b.getLocation(), Material.NETHERRACK.createBlockData());
                    } else {
                        b.setType(Material.NETHERRACK, false);
                    }
                }
            } else if (type == 6) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_CONCRETE_POWDER.createBlockData());
                } else {
                    b.setType(Material.RED_CONCRETE_POWDER, false);
                }
            } else if (type == 7) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.NETHER_WART_BLOCK.createBlockData());
                } else {
                    b.setType(Material.NETHER_WART_BLOCK, false);
                }
            } else if (type == 8) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_STAINED_GLASS_PANE.createBlockData());
                } else {
                    b.setType(Material.RED_STAINED_GLASS_PANE, false);
                }
            } else if (type == 9) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.QUARTZ_BLOCK.createBlockData());
                } else {
                    b.setType(Material.QUARTZ_BLOCK, false);
                }
            } else if (type == 10) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.QUARTZ_SLAB.createBlockData());
                } else {
                    b.setType(Material.QUARTZ_SLAB, false);
                }
            } else if (type == 11) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_NETHER_BRICK_WALL.createBlockData());
                } else {
                    b.setType(Material.RED_NETHER_BRICK_WALL, false);
                }
            } else if (type == 12) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.BONE_BLOCK.createBlockData());
                } else {
                    b.setType(Material.BONE_BLOCK, false);
                }
            } else if (type == 13) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_SHULKER_BOX.createBlockData());
                } else {
                    b.setType(Material.RED_SHULKER_BOX, false);
                }
            } else if (type == 14) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.NETHER_BRICK_SLAB.createBlockData());
                } else {
                    b.setType(Material.NETHER_BRICK_SLAB, false);
                }
            } else if (type == 15) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_WOOL.createBlockData());
                } else {
                    b.setType(Material.RED_WOOL, false);
                }
            } else if (type == 16) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.NETHER_BRICK_SLAB.createBlockData());
                } else {
                    b.setType(Material.NETHER_BRICK_SLAB, false);
                }
            } else if (type == 17) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.REDSTONE_TORCH.createBlockData());
                } else {
                    b.setType(Material.REDSTONE_TORCH, false);
                }
            } else if (type == 18) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.SKELETON_SKULL.createBlockData());
                } else {
                    b.setType(Material.SKELETON_SKULL, false);
                }
            } else if (type == 19) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.RED_CARPET.createBlockData());
                } else {
                    b.setType(Material.RED_CARPET, false);
                }
            } else if (type == 20) {
                if (packet) {
                    p.sendBlockChange(b.getLocation(), Material.REDSTONE_WIRE.createBlockData());
                } else {
                    b.setType(Material.REDSTONE_WIRE, false);
                }
            }
        }
    }

    public void generateStructure0(int[][][] structure, Location loc, boolean hasMeta, String meta, boolean packet,
                                   Player p, int direction, boolean overwrite) {
        //try {
        int randDirection = 0;// randor.nextInt(4);
        if (randDirection == 0) {
            for (int y = 0; y < structure[0].length; y++) {
                for (int x = -1; x < structure.length - 1; x++) {
                    for (int z = -1; z < structure[0][0].length - 1; z++) {
                        Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y,
                                loc.getZ() + z);
                        decideBlock(structure[x + 1][y][z + 1], loc2.getBlock(), packet, p, overwrite);
                        if (hasMeta) {
                            loc2.getBlock().setMetadata(meta, new FixedMetadataValue(plugin, 1));
                        }
                    }
                }
            }
        } else if (randDirection == 1) {
            for (int y = 0; y < structure[0].length; y++) {
                for (int x = -1; x < structure.length - 1; x++) {
                    for (int z = -1; z < structure[0][0].length - 1; z++) {
                        Location loc2 = new Location(loc.getWorld(), loc.getX() - x, loc.getY() + y,
                                loc.getZ() + z);
                        decideBlock(structure[x + 1][y][z + 1], loc2.getBlock(), packet, p, overwrite);
                        if (hasMeta) {
                            loc2.getBlock().setMetadata(meta, new FixedMetadataValue(plugin, 1));
                        }
                    }
                }
            }
        } else if (randDirection == 2) {
            for (int y = 0; y < structure[0].length; y++) {
                for (int x = -1; x < structure.length - 1; x++) {
                    for (int z = -1; z < structure[0][0].length - 1; z++) {
                        Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y,
                                loc.getZ() - z);
                        decideBlock(structure[x + 1][y][z + 1], loc2.getBlock(), packet, p, overwrite);
                        if (hasMeta) {
                            loc2.getBlock().setMetadata(meta, new FixedMetadataValue(plugin, 1));
                        }
                    }
                }
            }
        } else if (randDirection == 3) {
            for (int y = 0; y < structure[0].length; y++) {
                for (int x = -1; x < structure.length - 1; x++) {
                    for (int z = -1; z < structure[0][0].length - 1; z++) {
                        Location loc2 = new Location(loc.getWorld(), loc.getX() - x, loc.getY() + y,
                                loc.getZ() - z);
                        decideBlock(structure[x + 1][y][z + 1], loc2.getBlock(), packet, p, overwrite);
                        if (hasMeta) {
                            loc2.getBlock().setMetadata(meta, new FixedMetadataValue(plugin, 1));
                        }
                    }
                }
            }
        }
        //} catch (Exception error) {
        //}
    }
}

