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
import static me.mvabo.verydangerouscaves.utils.utils.*;

public class cave implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random randint = new Random();

    boolean hasWorlds = false;
    public static List<String> worlds = new ArrayList<String>();
    public boolean canSave = false;
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

