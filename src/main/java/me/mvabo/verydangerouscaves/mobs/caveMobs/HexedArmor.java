package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Mob;
import me.mvabo.verydangerouscaves.mobs.dressCursed;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HexedArmor extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public HexedArmor(Entity e) {
        if (e.getType() != EntityType.ZOMBIE || e.getType() != EntityType.SKELETON) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
            e.remove();
            e = (LivingEntity) e2;
        }
        ((LivingEntity) e)
                .addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
        dressCursed.dressDGolem((LivingEntity) e);
        e.setSilent(true);
        e.setCustomName(plugin.getConfig().getString("hexed_armor"));
        e.setMetadata(plugin.getConfig().getString("hexed_armor"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("ec", new FixedMetadataValue(plugin, 0));
        ((LivingEntity) e).setCanPickupItems(false);
        ((LivingEntity) e).setRemoveWhenFarAway(true);
    }
}
