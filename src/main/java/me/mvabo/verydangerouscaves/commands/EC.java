package me.mvabo.verydangerouscaves.commands;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.mobs.Boss;
import me.mvabo.verydangerouscaves.mobs.Mob;
import me.mvabo.verydangerouscaves.mobs.caveBosses.DeadDiamondMiner;
import me.mvabo.verydangerouscaves.mobs.caveBosses.HeroBraine;
import me.mvabo.verydangerouscaves.mobs.caveMobs.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;

public class EC implements CommandExecutor {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0) {
            sender.sendMessage(ChatColor.YELLOW + "EnhancedCaves Info");
            sender.sendMessage(ChatColor.YELLOW + "/spawn to spawn an mob");
            sender.sendMessage(ChatColor.YELLOW + "/reload to reload config");
            sender.sendMessage(ChatColor.YELLOW + "Thats it");
            return true;
        } else if(args[0].equalsIgnoreCase("spawn")) {
            Location loc = ((Player) sender).getLocation();
            if (args[1].isEmpty()) {
                sender.sendMessage(ChatColor.RED + "The third argument should be a mob name");
                return true;
            }
            String mob = args[1];
            if (mob.equalsIgnoreCase("AlphaSpider")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new AlphaSpider(z);
            } else if (mob.equalsIgnoreCase("cryingbat")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new CryingBat(z);
            } else if (mob.equalsIgnoreCase("deadminer")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new DeadMiner(z);
            } else if (mob.equalsIgnoreCase("hexedarmor")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new HexedArmor(z);
            } else if (mob.equalsIgnoreCase("lavacreeper")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new LavaCreeper(z);
            } else if (mob.equalsIgnoreCase("magmamonster")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new MagmaMonster(z);
            } else if (mob.equalsIgnoreCase("smokedemon")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new SmokeDemon(z);
            } else if (mob.equalsIgnoreCase("tntcreeper")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new TnTCreeper(z);
            } else if (mob.equalsIgnoreCase("watcher")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Mob m = new Watcher(z);
            } else if (mob.equalsIgnoreCase("deaddiamondminer")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Boss m = new DeadDiamondMiner(z);
            } else if (mob.equalsIgnoreCase("herobrine")) {
                Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                Boss m = new HeroBraine(z);
            } //else if (mob.equalsIgnoreCase("poisonousalphaspider")) {
                //Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                //Mob m = new WaterElemental(z);
            //}
        else {
                sender.sendMessage(ChatColor.RED + "Invalid mob name");
            }

            return true;
        } else if (args[0].equalsIgnoreCase("info") || args[0].length() < 2) {
            sender.sendMessage(ChatColor.YELLOW + "EnhancedCaves Info");
            sender.sendMessage(ChatColor.YELLOW + "/spawn to spawn an mob");
            sender.sendMessage(ChatColor.YELLOW + "/reload to reload config");
            sender.sendMessage(ChatColor.YELLOW + "Thats it");
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.getPluginLoader().disablePlugin(plugin);
            plugin.getPluginLoader().enablePlugin(plugin);
            System.out.println(ChatColor.GREEN + "Reload complete");
            return true;
        }
        return false;
    }
}
