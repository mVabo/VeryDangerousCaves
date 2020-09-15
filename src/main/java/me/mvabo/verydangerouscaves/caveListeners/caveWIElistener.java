package me.mvabo.verydangerouscaves.caveListeners;

import me.mvabo.verydangerouscaves.VeryDangerousCaves;
import me.mvabo.verydangerouscaves.generators.caveGenerator;
import me.mvabo.verydangerouscaves.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class caveWIElistener implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);

    @EventHandler
    public void runCaveGenerator(WorldInitEvent event) {
        World w = event.getWorld();
        List worlds = plugin.getConfig().getList("enabled_cave_worlds");
        //Bukkit.getConsoleSender().sendMessage(utils.chat("&6checking if caveModule is enabled"));
        if (plugin.getConfig().getBoolean("cm-enabled")) {
            if (w.getEnvironment().equals(World.Environment.NORMAL) && worlds.contains(w.toString())) {
                Bukkit.getConsoleSender().sendMessage(utils.chat("&6EnhancedCaves enabled on " + w.toString()));
                event.getWorld().getPopulators().add(new caveGenerator());
            }
        }
    }
}
