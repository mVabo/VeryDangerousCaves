package me.mvabo.verydangerouscaves.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class getLookingAt {
    public static boolean getLookingAt2(LivingEntity player, LivingEntity player1) {
        Location eye = player.getEyeLocation();
        Vector toEntity = ((LivingEntity) player1).getEyeLocation().toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());

        return dot > 0.70D;
    }
}
