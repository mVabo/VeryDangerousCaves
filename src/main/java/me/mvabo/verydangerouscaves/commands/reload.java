package me.mvabo.verydangerouscaves.commands;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.cave;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class reload implements CommandExecutor {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(player.hasPermission("verydangerouscaves.reload")){

        }

        return false;
    }

    public void reloadConfigs() {
        cave.roomX = plugin.getConfig().getInt("002roomx");
        cave.roomY = plugin.getConfig().getInt("002roomy");
        cave.roomZ = plugin.getConfig().getInt("002roomz");

        cave.caveins = plugin.getConfig().getBoolean("enable_cave_ins");
        cave.hungerdark = plugin.getConfig().getBoolean("enable_hungering_darkness");
        cave.ambients = plugin.getConfig().getBoolean("enable_ambient_cave_sounds");
        cave.cavetemp = plugin.getConfig().getBoolean("enable_cave_temperature");
        cave.caveage = plugin.getConfig().getBoolean("enable_cave_aging");
        cave.caveents = plugin.getConfig().getBoolean("enable_cave_monsters");
        cave.cavechance = plugin.getConfig().getInt("cave_structure_chance");
        cave.crystalCaveChance = plugin.getConfig().getInt("crystal_cave_chance");
        cave.cavestruct = plugin.getConfig().getBoolean("enable_cave_structures");
        cave.easter = plugin.getConfig().getBoolean("enable_easter_eggs");
        cave.blrate = plugin.getConfig().getInt("boulder_rate");
        cave.trrate = plugin.getConfig().getInt("traps_rate");
        cave.strate = plugin.getConfig().getInt("buildings_rate");
        cave.plrate = plugin.getConfig().getInt("pillar_rate");
        cave.skulls = plugin.getConfig().getBoolean("cave_skulls");
        cave.mobNames.add("The Darkness");
        cave.mobNames.add(plugin.getConfig().getString("magma_monster"));
        cave.mobNames.add(plugin.getConfig().getString("crying_bat"));
        cave.mobNames.add(plugin.getConfig().getString("lava_creeper"));
        cave.mobNames.add(plugin.getConfig().getString("tnt_creeper"));
        cave.mobNames.add(plugin.getConfig().getString("watcher"));
        cave.mobNames.add(plugin.getConfig().getString("smoke_demon"));
        cave.mobNames.add(plugin.getConfig().getString("alpha_spider"));
        cave.mobNames.add(plugin.getConfig().getString("dead_miner"));
        cave.mobNames.add(plugin.getConfig().getString("hexed_armor"));
        cave.itemcustom = (List<String>) plugin.getConfig().getList("custom_items");
        cave.hotmessage = (List<String>) plugin.getConfig().getList("temperature_messages");
        cave.worlds = (List<String>) plugin.getConfig().getList("enabled_cave_worlds");
        int d = plugin.getConfig().getInt("Hungering Darkness Damage ");
        if (d > 200) {
            cave.damage = 200;
        } else if (d < 0) {
            cave.damage = 0;
        } else {
            cave.damage = d;
        }
    }
}
