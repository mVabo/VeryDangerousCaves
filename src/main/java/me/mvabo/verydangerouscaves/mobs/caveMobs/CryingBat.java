package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.cave;
import me.mvabo.verydangerouscaves.mobs.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class CryingBat extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public CryingBat(Entity e) {
        if(!e.getType().equals(EntityType.BAT)){
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.BAT);
            e.remove();
            e = e2;
        }

        e.setCustomName(plugin.getConfig().getString("crying_bat"));
        e.setMetadata(plugin.getConfig().getString("crying_Bat"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        cave.effectEnts.add(e);
        ((LivingEntity) e).setRemoveWhenFarAway(true);
    }
}
