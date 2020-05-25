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

import java.util.Random;

public class DeadMiner extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    Random randint = new Random();

    public DeadMiner(Entity e) {
        if (e.getType() != EntityType.ZOMBIE) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
            e.remove();
            e = (LivingEntity) e2;
        }
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
        myAwesomeSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Wallabee"));
        myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
        ee.setHelmet(myAwesomeSkull);
        ee.setHelmetDropChance(0);
        setMinerHands(e);
        e.setCustomName(plugin.getConfig().getString("dead_miner"));
        e.setMetadata(plugin.getConfig().getString("dead_miner"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        ((LivingEntity) e).setCanPickupItems(false);
        ((LivingEntity) e).setRemoveWhenFarAway(true);
    }

    public void setMinerHands(Entity e) {
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        // 0 == wood 1 == stone 2 == iron
        // 0 == left handed 1 == right handed
        // boolean true = torch in other hand
        int type = randint.nextInt(3);
        boolean holdtorch = randint.nextBoolean();
        int handside = randint.nextInt(2);
        boolean hasboots = randint.nextBoolean();
        boolean haschest = randint.nextBoolean();
        ItemStack pickaxe = null;
        if (type == 0) {
            pickaxe = new ItemStack(Material.WOODEN_PICKAXE);
        } else if (type == 1) {
            pickaxe = new ItemStack(Material.STONE_PICKAXE);
        } else if (type == 2) {
            pickaxe = new ItemStack(Material.IRON_PICKAXE);
        }
        if (handside == 0) {
            ee.setItemInOffHand(pickaxe);
            if (holdtorch == true) {
                ee.setItemInMainHand(new ItemStack(Material.TORCH, 1));
            }
        } else {
            ee.setItemInMainHand(pickaxe);
            if (holdtorch == true) {
                ee.setItemInOffHand(new ItemStack(Material.TORCH, 1));
            }
        }
        if (hasboots == true) {
            if (randint.nextBoolean() == true) {
                ee.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
            } else {
                ee.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
            }
        }
        if (haschest == true) {
            if (randint.nextBoolean() == true) {
                ee.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
            } else {
                ee.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
            }
        }
    }
}
