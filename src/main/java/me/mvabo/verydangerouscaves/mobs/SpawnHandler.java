package me.mvabo.verydangerouscaves.mobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.cave;
import me.mvabo.verydangerouscaves.mobs.caveBosses.DeadDiamondMiner;
import me.mvabo.verydangerouscaves.mobs.caveBosses.HeroBraine;
import me.mvabo.verydangerouscaves.mobs.caveMobs.*;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SpawnHandler implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    Random rand = new Random();

    @EventHandler
    public void checkSpawn(CreatureSpawnEvent event) {
        Entity e = event.getEntity();

        Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            if (!e.hasMetadata("VDC") && isStony(e.getLocation().subtract(0, 1, 0).getBlock().getType()) && e.getLocation().getBlockY() < 90) {
                String world;
                for (int i = 0; i < cave.worlds.size(); i++) {
                    world = cave.worlds.get(i);
                    if (e.getWorld().getName().equalsIgnoreCase(world)) {
                        doSpawn(e);
                    }
                }
            } else if (!e.hasMetadata("VDC") && isStony(e.getLocation().subtract(0, 1, 0).getBlock().getType()) && e.getLocation().getBlockY() < 70 && e.getLocation().getBlock().getLightLevel() == 0) {
                doDarkness(e);
            } else if(!e.hasMetadata("VDC") && isStony(e.getLocation().subtract(0, 1, 0).getBlock().getType()) && e.getLocation().getBlockY() < 12) {
                doBoss(e);
            }
        }
    }

    public void doSpawn(Entity e) {
        int choice = rand.nextInt(8);
        Mob mob;
        switch(choice) {
            case 0:
                if(rand.nextInt(100)<plugin.getConfig().getInt("watcher_chance")){
                    mob = new Watcher(e);
                }
            case 1:
                if(rand.nextInt(100)<plugin.getConfig().getInt("alpha_spider_chance")) {
                    mob = new AlphaSpider(e);
                }
            case 2:
                if(rand.nextInt(100)<plugin.getConfig().getInt("crying_bat_chance")) {
                    mob = new CryingBat(e);
                }
            case 3:
                if(rand.nextInt(100)<plugin.getConfig().getInt("dead_miner_chance")) {
                    mob = new DeadMiner(e);
                }
            case 4:
                if(rand.nextInt(100)<plugin.getConfig().getInt("hexed_armor_chance")) {
                    mob = new HexedArmor(e);
                }
            case 5:
                if(rand.nextInt(100)<plugin.getConfig().getInt("lava_creeper_chance")){
                    mob = new LavaCreeper(e);
                }
            case 6:
                if(rand.nextInt(100)<plugin.getConfig().getInt("magma_monster_chance")) {
                    mob = new MagmaMonster(e);
                }
            case 7:
                if(rand.nextInt(100)<plugin.getConfig().getInt("smoke_demon_chance")) {
                    mob = new SmokeDemon(e);
                }
            case 8:
                if(rand.nextInt(100)<plugin.getConfig().getInt("tnt_creeper_chance")) {
                    mob = new TnTCreeper(e);
                }

        }
    }

    public void doBoss(Entity e) {
        int choice = rand.nextInt(1);
        if (choice == 0) {
            if(rand.nextDouble() < plugin.getConfig().getInt("dead_diamond_miner_chance")) {
                Boss b = new DeadDiamondMiner(e);
            }
        } else if (choice == 1) {
            if (rand.nextDouble() < 0.0001) {
                Boss b = new HeroBraine(e);
            }
        }
    }

    public void doDarkness(Entity entitye) {
        LivingEntity e = (LivingEntity) entitye;
        if (e != null && !e.isDead()) {
            if (e.getType() != EntityType.HUSK) {
                Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.HUSK);
                e.remove();
                e = (LivingEntity) e2;
            }
            String name = "The Darkness";
            e.setCustomName(name);
            e.setMetadata(name, new FixedMetadataValue(plugin, 0));
            e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
            e.setMetadata("darkness", new FixedMetadataValue(plugin, 0));
            e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 200, false, false));
            e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2, false, false));
            e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, cave.damage, false, false));
            e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 3, false, false));
            e.setSilent(true);
            e.setCanPickupItems(false);
            e.setCustomNameVisible(false);
            e.setCollidable(false);
        }
        return;
    }

    public boolean isStony(Material m) {
        if (m.name().toLowerCase().contains("dirt") || m == Material.STONE || m == Material.MOSSY_COBBLESTONE
                || m == Material.ANDESITE || m == Material.DIORITE || m == Material.COBBLESTONE || m == Material.GRANITE
                || m == Material.GRAVEL) {
            return true;
        }
        return false;
    }
}
