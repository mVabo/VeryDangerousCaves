package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.mvabo.verydangerouscaves.utils.removeHand.removeHand;

public class SmokeDemon extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public SmokeDemon(Entity e) {
        if (e.getType() != EntityType.ZOMBIE) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
            e.remove();
            e = (LivingEntity) e2;
        }
        ((LivingEntity) e)
                .addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
        e.setSilent(true);
        e.setCustomName(plugin.getConfig().getString("smoke_demon"));
        e.setMetadata(plugin.getConfig().getString("smoke_demon"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        removeHand(e);
        ((LivingEntity) e).setCanPickupItems(false);
        ((LivingEntity) e).setRemoveWhenFarAway(true);
    }
}
