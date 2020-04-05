package me.mvabo.verydangerouscaves.mobs;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;

import me.mvabo.verydangerouscaves.cave;
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

import java.util.Random;

import static me.mvabo.verydangerouscaves.utils.removeHand.removeHand;
import static me.mvabo.verydangerouscaves.utils.removeItemNaturally.removeItemNaturally;

public class caveDoMobSpawns implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);
    Random randint = new Random();

    @EventHandler
    public void deleteLightLevel(CreatureSpawnEvent event) {
        //System.out.println("Entered cave deleteLightLevel");

        /*
        if (!cave.worlds.contains(event.getEntity().getWorld().getName())) {
            return;
        }
         */

        World wor = event.getEntity().getWorld();
        if (cave.hungerdark == true || cave.caveents == true) {
            //System.out.println("hungerdark = true and caveents = true");
            Entity e = event.getEntity();
            if (e.getLocation().subtract(0, 1, 0).getBlock() == null) {
                //System.out.println("Block under entity is null");
                return;
            } else if (((randint.nextInt(100) > plugin.getConfig().getInt("darkness_spawn_chance"))) || (cave.hungerdark == false)) {
                //System.out.println("hungerdark dissabled");
                if (cave.caveents == true) {
                    if (e != null && !e.isDead()) {
                        //System.out.println("e is not null or dead");
                        if ((e instanceof Creeper || e instanceof Skeleton || e instanceof Zombie || e instanceof Spider) && (!(e instanceof PigZombie || e instanceof ZombieVillager || e instanceof Husk))) {
                            //System.out.println("e is instance of creeper skelly zomboi or spider");
                            if ((e instanceof Monster
                                    && (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL
                                    && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.REINFORCEMENTS)
                                    && (isStony(e.getLocation().subtract(0, 1, 0).getBlock().getType())))) {
                                if (e.getLocation().getY() <= plugin.getConfig().getInt("monster_highest_y")
                                        && e.getLocation().getY() >= plugin.getConfig().getInt("monster_lowest_y")) {
                                    //System.out.println("Doing mob spawns!");
                                    doMobSpawns(e);
                                }
                            }
                        }
                    }
                } else {
                    return;
                }
            } else {
                if (cave.hungerdark == true) {
                    //System.out.println("hungerdark enabled");
                    if (e != null && !e.isDead()) {
                        //System.out.println("entity exists");
                        if ((e instanceof Creeper
                                || e instanceof Skeleton
                                || e instanceof Zombie
                                || e instanceof Spider)
                                && (!(e instanceof PigZombie || e instanceof ZombieVillager
                                || e instanceof Husk))) {
                            //System.out.println("e is instance of creeper skelly zomboi or spider");
                            if ((e instanceof Monster && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL
                                    && (isStony(e.getLocation().subtract(0, 1, 0).getBlock().getType())))) {
                                //System.out.println("spawnreason natural && has a stone under it");
                                if (e.getLocation().getY() <= plugin.getConfig().getInt("monster_highest_y")
                                        && e.getLocation().getY() >= plugin.getConfig().getInt("monster_lowest_y")) {
                                    //System.out.println("Doing darkness!");
                                    doDarkness(e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMobName(PlayerInteractEntityEvent event) {
        if (!cave.worlds.contains(event.getRightClicked().getWorld().getName())) {
            return;
        }
        World wor = event.getRightClicked().getWorld();
        if (!(event.getRightClicked() instanceof Player)) {
            ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
            if (i.getType() == Material.NAME_TAG) {
                if (i.hasItemMeta()) {
                    if (i.getItemMeta().hasDisplayName()) {
                        String s = i.getItemMeta().getDisplayName();
                        if (cave.mobNames.contains(s)) {
                            removeItemNaturally(event.getPlayer());
                            if (event.getRightClicked() instanceof LivingEntity
                                    && (!(event.getRightClicked() instanceof Player))) {
                                ((LivingEntity) event.getRightClicked()).setCustomName("");
                                event.setCancelled(true);
                            } else {
                                event.getRightClicked().remove();
                            }
                        }
                    }
                }
            }
        }
    }

    public void doMobSpawns(Entity entitye) {
        //System.out.println("Now executing doMobSpawns");
        LivingEntity e = (LivingEntity) entitye;
        String name = mobTypes();
        if (e != null && !e.isDead()) {
            //try {
            //System.out.println("e exists");
            if (name.equals(plugin.getConfig().getString("watcher"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("watcher_chance"))) {
                // now teleports in front of the player if the player looks away from it
                Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.HUSK);
                e.remove();
                e = (LivingEntity) e2;
                e.setSilent(true);
                ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 200));
                EntityEquipment ee = ((LivingEntity) e).getEquipment();
                ItemStack myAwesomeSkull = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
                myAwesomeSkullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Creegn"));
                myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
                ee.setHelmet(myAwesomeSkull);
                ee.setHelmetDropChance(0);
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                e.setMetadata("cavem", new FixedMetadataValue(plugin, 0));
                removeHand(e);
                e.setCanPickupItems(false);
                //System.out.println("watcher spawned");
            } else if (name.equals(plugin.getConfig().getString("tnt_creeper"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("tnt_creeper_chance"))) {
                if (e.getType() != EntityType.CREEPER) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                //System.out.println("tntcreeper spawned");
            } else if (name.equals(plugin.getConfig().getString("lava_creeper"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("lava_creeper_chance"))) {
                if (e.getType() != EntityType.CREEPER) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                //System.out.println("lavacreeper spawned");
            } else if (name.equals(plugin.getConfig().getString("dead_miner"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("dead_miner_chance"))) {
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
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                e.setCanPickupItems(false);
                //System.out.println("dead miner spawned");
            } else if (name.equals(plugin.getConfig().getString("magma_monster"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("magma_monster_chance"))) {
                if (e.getType() != EntityType.ZOMBIE) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                EntityEquipment ee = (e).getEquipment();
                e.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 100, false, false));
                e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 100, false, false));
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
                e.setCustomName(name);
                e.setSilent(true);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                e.setMetadata("cavem", new FixedMetadataValue(plugin, 0));
                e.setCanPickupItems(false);
                //System.out.println("magma monster spawned");
            } else if (name.equals(plugin.getConfig().getString("smoke_demon"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("smoke_demon_chance"))) {
                if (e.getType() != EntityType.ZOMBIE) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                ((LivingEntity) e)
                        .addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
                e.setSilent(true);
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                e.setMetadata("cavem", new FixedMetadataValue(plugin, 0));
                removeHand(e);
                e.setCanPickupItems(false);
                //System.out.println("smoke demon spawned");
            } else if (name.equals(plugin.getConfig().getString("crying_bat"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("crying_bat_chance"))) {
                if (e.getType() != EntityType.BAT) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.BAT);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                cave.effectEnts.add(e);
                //System.out.println("crying bat spawned");
            } else if (name.equals(plugin.getConfig().getString("alpha_spider"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("alpha_spider_chance"))) {
                if (!(e.getType() == EntityType.SPIDER || e.getType() == EntityType.CAVE_SPIDER)) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.SPIDER);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                //System.out.println("alpha spider spawned");
            } else if (name.equals(plugin.getConfig().getString("hexed_armor"))
                    && (randint.nextInt(100) < plugin.getConfig().getInt("hexed_armor_chance"))) {
                if (e.getType() != EntityType.ZOMBIE || e.getType() != EntityType.SKELETON) {
                    Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
                    e.remove();
                    e = (LivingEntity) e2;
                }
                ((LivingEntity) e)
                        .addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 1, false, false));
                dressCursed.dressDGolem((LivingEntity) e);
                e.setSilent(true);
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                e.setCanPickupItems(false);
                //System.out.println("hexed armor spawned");
            } else if (name.equals(plugin.getConfig().getString("dead_diamond_miner"))
                    && (randint.nextDouble() < plugin.getConfig().getDouble("dead_diamond_miner_chance")) && e.getLocation().getY() <= 20) {
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
                e.setCustomName(name);
                e.setMetadata(name, new FixedMetadataValue(plugin, 0));
                e.setMetadata("R", new FixedMetadataValue(plugin, 0));
                e.setCanPickupItems(false);
                //System.out.println("dead miner spawned");
            }
            //} catch (Exception error) {
            //    console.sendMessage(ChatColor.RED + "Uh oh error inside dressing mob.");
            //}
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

    public void doDarkness(Entity entitye) {
        //System.out.println("Definetly doing darkness");
        LivingEntity e = (LivingEntity) entitye;
        if (e != null && !e.isDead()) {
            //System.out.println("darkness if statement says e exists");
            if (e.getType() != EntityType.HUSK) {
                //System.out.println("setting entity type to husk");
                Entity e2 = e.getWorld().spawnEntity(e.getLocation(), EntityType.HUSK);
                e.remove();
                e = (LivingEntity) e2;
            }
            String name = "The Darkness";
            e.setCustomName(name);
            e.setMetadata(name, new FixedMetadataValue(plugin, 0));
            e.setMetadata("R", new FixedMetadataValue(plugin, 0));
            e.setMetadata("cavem", new FixedMetadataValue(plugin, 0));
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

    public String mobTypes() {
        int choice = randint.nextInt(10);
        //try {
        if (choice == 0) {
            if (plugin.getConfig().getBoolean("spawn_watcher")) {
                return plugin.getConfig().getString("watcher");
            } else {
                return "";
            }
        } else if (choice == 1) {
            if (plugin.getConfig().getBoolean("spawn_tnt_creeper")) {
                return plugin.getConfig().getString("tnt_creeper");
            } else {
                return "";
            }
        } else if (choice == 2) {
            if (plugin.getConfig().getBoolean("spawn_dead_miner")) {
                return plugin.getConfig().getString("dead_miner");
            } else {
                return "";
            }
        } else if (choice == 3) {
            if (plugin.getConfig().getBoolean("spawn_lava_creeper")) {
                return plugin.getConfig().getString("lava_creeper");
            } else {
                return "";
            }
        } else if (choice == 4) {
            if (plugin.getConfig().getBoolean("spawn_magma_monster")) {
                return plugin.getConfig().getString("magma_monster");
            } else {
                return "";
            }
        } else if (choice == 5) {
            if (plugin.getConfig().getBoolean("spawn_smoke_demon")) {
                return plugin.getConfig().getString("smoke_demon");
            } else {
                return "";
            }
        } else if (choice == 6) {
            if (plugin.getConfig().getBoolean("spawn_Crying_bat")) {
                if (randint.nextInt(20) == 1) {
                    return plugin.getConfig().getString("crying_bat");
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else if (choice == 7) {
            if (plugin.getConfig().getBoolean("spawn_alpha_spider")) {
                return plugin.getConfig().getString("alpha_spider");
            } else {
                return "";
            }
        } else if (choice == 8) {
            if (plugin.getConfig().getBoolean("spawn_hexed_armor")) {
                return plugin.getConfig().getString("hexed_armor");
            } else {
                return "";
            }
        } else if (choice == 9) {
            if (plugin.getConfig().getBoolean("spawn_dead_diamond_miner")) {
                return plugin.getConfig().getString("dead_diamond_miner");
            } else {
                return "";
            }
        }
        return "";
        //} catch (Exception error) {
        //    console.sendMessage(ChatColor.RED + "Uh oh error inside mob names.");
        //    return "";
        //}
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
