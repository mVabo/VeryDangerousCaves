package me.mvabo.verydangerouscaves.mobs.caveMobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Mob;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagmaMonster extends Mob {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    public MagmaMonster(Entity e) {
        if (e.getType() != EntityType.ZOMBIE) {
            Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
            e.remove();
            e = e2;
        }
        EntityEquipment ee = ((LivingEntity) e).getEquipment();
        ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 100, false, false));
        ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 100, false, false));
        ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta lch6 = (LeatherArmorMeta) lchest.getItemMeta();
        lch6.setColor(Color.fromRGB(252, 115, 69));
        lchest.setItemMeta(lch6);
        ItemStack lchest2 = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta lch61 = (LeatherArmorMeta) lchest2.getItemMeta();
        lch61.setColor(Color.fromRGB(252, 115, 69));
        lchest2.setItemMeta(lch61);
        ItemStack lchest3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta lch62 = (LeatherArmorMeta) lchest3.getItemMeta();
        lch62.setColor(Color.fromRGB(252, 115, 69));
        lchest3.setItemMeta(lch62);
        ee.setItemInMainHand(new ItemStack(Material.FIRE, 1));
        ee.setItemInOffHand(new ItemStack(Material.FIRE, 1));
        ee.setChestplate(lchest);
        ee.setLeggings(lchest3);
        ee.setBoots(lchest2);
        e.setFireTicks(999999);
        e.setCustomName(plugin.getConfig().getString("magma_monster"));
        e.setSilent(true);
        e.setMetadata(plugin.getConfig().getString("magma_monster"), new FixedMetadataValue(plugin, 0));
        e.setMetadata("VDC", new FixedMetadataValue(plugin, 0));
        ((LivingEntity) e).setCanPickupItems(false);
        ((LivingEntity) e).setRemoveWhenFarAway(true);
    }
}
