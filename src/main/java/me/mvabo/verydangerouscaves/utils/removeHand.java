package me.mvabo.verydangerouscaves.utils;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class removeHand {
    public static void removeHand(Entity e) {
        if (e instanceof LivingEntity) {
            EntityEquipment ee = ((LivingEntity) e).getEquipment();
            ee.setItemInMainHand(new ItemStack(Material.AIR));
            ee.setItemInOffHand(new ItemStack(Material.AIR));
        }
    }
}
