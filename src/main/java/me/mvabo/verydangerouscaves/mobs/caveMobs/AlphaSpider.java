package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class AlphaSpider extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public AlphaSpider(Entity e) {
        if(!e.getType().equals(EntityType.SPIDER)) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.SPIDER);
            e.remove();
            e = e2;
        }
        e.setCustomName(plugin.getConfig().getString("alpha_spider"));
        e.setMetadata(plugin.getConfig().getString("alpha_spider"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
    }
}
