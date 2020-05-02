package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class LavaCreeper extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public LavaCreeper(Entity e) {
        if (e.getType() != EntityType.CREEPER) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
            e.remove();
            e = (LivingEntity) e2;
        }
        e.setCustomName(plugin.getConfig().getString("lava_creeper"));
        e.setMetadata(plugin.getConfig().getString("lava_creeper"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
    }
}
