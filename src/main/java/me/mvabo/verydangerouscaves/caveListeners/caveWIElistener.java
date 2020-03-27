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

public class caveWIElistener implements Listener {

    Plugin plugin = VeryDangerousCaves.getPlugin(VeryDangerousCaves.class);


    @EventHandler
    public void runCaveGenerator(WorldInitEvent event) {
        World w = event.getWorld();
        //Bukkit.getConsoleSender().sendMessage(utils.chat("&6checking if caveModule is enabled"));
        if (plugin.getConfig().getBoolean("cm-enabled")) {
            Bukkit.getConsoleSender().sendMessage(utils.chat("&6VDC enabled"));
            event.getWorld().getPopulators().add(new caveGenerator());
            //event.getWorld().getPopulators().add(new crystalCaveGenerator());
        }
    }
}
