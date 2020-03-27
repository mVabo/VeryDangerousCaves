package me.mvabo.verydangerouscaves.utils;

import org.bukkit.Material;

public class isAir {
    public static boolean isAir(Material m) {
        if (m == Material.AIR || m == Material.CAVE_AIR || m == Material.VOID_AIR) {
            return true;
        }
        return false;
    }
}
