package me.mvabo.verydangerouscaves.mobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

public class DeathHandler implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random rand = new Random();

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (plugin.getConfig().getBoolean("enable_loot_splosion")) {
            if (entity.hasMetadata("ec")) {
                if (rand.nextInt(200) < 1) {
                    doLootSplosion(entity);
                }
            }
        }
    }

    public void doLootSplosion(Entity e) {
        Location l = e.getLocation();
        List items = plugin.getConfig().getList("lootsplosion-list");
        for (int i = 0; i < items.size(); i++) {
            Material m = Material.getMaterial(items.get(i).toString());
            l.getWorld().dropItem(l, new ItemStack(m, 1));
        }
    }
}
