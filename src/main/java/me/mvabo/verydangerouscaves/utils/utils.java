package me.mvabo.verydangerouscaves.utils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.exists.exists;

public class utils {

    Random randint = new Random();

    public static String chat (String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public Location getRandLoc(Location l, int radi) {
        if(l != null) {
            double radius = radi;
            double x0 = l.getX();
            double y0 = l.getY();
            double z0 = l.getZ();
            double u = Math.random();
            double v = Math.random();
            double theta = 2 * Math.PI * u;
            double phi = Math.acos(2 * v - 1);
            double x = x0 + (radius * Math.sin(phi) * Math.cos(theta));
            double y = y0;
            double z = z0 + (radius * Math.cos(phi));
            return new Location(l.getWorld(), x, y, z, randint.nextInt(360), randint.nextInt(360));
        }
        return null;
    }

    public static boolean inCave(Player p) {
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

    public static boolean isStony(Material m) {
        if (m.name().toLowerCase().contains("dirt") || m == Material.STONE || m == Material.MOSSY_COBBLESTONE
                || m == Material.ANDESITE || m == Material.DIORITE || m == Material.COBBLESTONE || m == Material.GRANITE
                || m == Material.GRAVEL) {
            return true;
        }
        return false;
    }

    public static BlockFace oppisiteBf(BlockFace be) {
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

    public static boolean hasName(String n, Entity e) {
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

    public static boolean existMonster(Entity e) {
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

    public static boolean existBat(Entity e) {
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

    public static boolean existMonsterBefore(Entity e) {
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

    public static boolean existTarget(Entity e) {
        if(e == null) {
            return false;
        }
        if(e.isDead()) {
            return false;
        }
        return true;
    }
}
