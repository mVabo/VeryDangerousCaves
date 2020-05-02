package me.mvabo.verydangerouscaves.mobs.caveBosses;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Boss;
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

public class HeroBraine extends Boss {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public HeroBraine(Entity e){
        if (e.getType() != EntityType.ZOMBIE) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
            e.remove();
            e = (LivingEntity) e2;
        }
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
        myAwesomeSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Herobrine"));
        myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
        ee.setHelmet(myAwesomeSkull);
        ee.setHelmetDropChance(0);
        ee.setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD, 1));
        ee.setItemInMainHandDropChance(0);
        e.setCustomName("Herobrine");
        e.setMetadata("Herobrine", new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        ((LivingEntity) e).setCanPickupItems(false);
    }
}
