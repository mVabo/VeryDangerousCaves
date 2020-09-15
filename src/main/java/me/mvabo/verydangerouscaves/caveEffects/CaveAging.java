package me.mvabo.verydangerouscaves.caveEffects;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.utils.utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.isAir.isAir;
import static me.mvabo.verydangerouscaves.utils.utils.isStony;
import static me.mvabo.verydangerouscaves.utils.utils.oppisiteBf;

public class CaveAging implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random randint = new Random();

    List worlds = plugin.getConfig().getList("enabled_cave_worlds");

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
}
