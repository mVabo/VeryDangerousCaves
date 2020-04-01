package me.mvabo.verydangerouscaves.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class getBlockInFront {

    public static Location getBlockInFrontOfPlayer(LivingEntity entsa) {
        Vector inverseDirectionVec = entsa.getLocation().getDirection().normalize().multiply(1);
        return entsa.getLocation().add(inverseDirectionVec);
    }
}
