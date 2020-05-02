package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Mob;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.mvabo.verydangerouscaves.utils.removeHand.removeHand;

public class Watcher extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public Watcher(Entity e) {
        Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.HUSK);
        e.remove();
        e = (LivingEntity) e2;
        e.setSilent(true);
        ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 200));
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
        myAwesomeSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Creegn"));
        myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
        ee.setHelmet(myAwesomeSkull);
        ee.setHelmetDropChance(0);
        e.setCustomName(plugin.getConfig().getString("watcher"));
        e.setMetadata(plugin.getConfig().getString("watcher"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        removeHand(e);
        ((LivingEntity) e).setCanPickupItems(false);
    }
}
