package me.mvabo.verydangerouscaves.mobs.caveBosses;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Boss;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class DeadDiamondMiner extends Boss {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    Random randint = new Random();

    public DeadDiamondMiner(Entity e) {
        if (e.getType() != EntityType.ZOMBIE) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
            e.remove();
            e = (LivingEntity) e2;
        }
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemStack dchest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta c = dchest.getItemMeta();
        c.addEnchant(Enchantment.BINDING_CURSE, 100, true);
        c.addEnchant(Enchantment.THORNS, 2, true);
        c.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        dchest.setItemMeta(c);
        ItemStack dleggs = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta l = dleggs.getItemMeta();
        l.addEnchant(Enchantment.BINDING_CURSE, 100, true);
        l.addEnchant(Enchantment.THORNS, 2, true);
        l.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        dleggs.setItemMeta(l);
        ItemStack dboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta b = dboots.getItemMeta();
        b.addEnchant(Enchantment.BINDING_CURSE, 100, true);
        b.addEnchant(Enchantment.THORNS, 2, true);
        b.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        dboots.setItemMeta(b);
        ee.setBoots(dboots);
        ee.setLeggings(dleggs);
        ee.setChestplate(dchest);
        SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
        myAwesomeSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Wallabee"));
        myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
        ee.setHelmet(myAwesomeSkull);
        ee.setHelmetDropChance(0);
        setDiamondMinerHands(e);
        e.setCustomName(plugin.getConfig().getString("dead_diamond_miner"));
        e.setMetadata(plugin.getConfig().getString("dead_diamond_miner"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        ((LivingEntity) e).setCanPickupItems(false);
        ((LivingEntity) e).setRemoveWhenFarAway(true);
    }

    public void setDiamondMinerHands(Entity e) {
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        int type = randint.nextInt(3);
        boolean holdtorch = randint.nextBoolean();
        int handside = randint.nextInt(2);
        ItemStack sword = null;
        if (type == 0) {
            sword = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta s = sword.getItemMeta();
            s.addEnchant(Enchantment.KNOCKBACK, 2, true);
            sword.setItemMeta(s);
        } else if (type == 1) {
            sword = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta s = sword.getItemMeta();
            s.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            sword.setItemMeta(s);
        } else if (type == 2) {
            sword = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta s = sword.getItemMeta();
            s.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
            sword.setItemMeta(s);
        }
        if (handside == 0) {
            ee.setItemInOffHand(sword);
            if (holdtorch == true) {
                ee.setItemInMainHand(new ItemStack(Material.TORCH, 1));
            }
        } else {
            ee.setItemInMainHand(sword);
            if (holdtorch == true) {
                ee.setItemInOffHand(new ItemStack(Material.TORCH, 1));
            }
        }
    }
}
