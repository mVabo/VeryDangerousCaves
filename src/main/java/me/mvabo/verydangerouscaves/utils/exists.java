package me.mvabo.verydangerouscaves.utils;

import org.bukkit.entity.Entity;

public class exists {
    public static boolean exists(Entity e) {
        if(e == null) {
            return false;
        }
        if(e.isDead()) {
            return false;
        }
        return true;
    }
}
