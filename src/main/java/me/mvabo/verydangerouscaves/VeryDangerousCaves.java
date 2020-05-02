package me.mvabo.verydangerouscaves;

import me.mvabo.verydangerouscaves.caveListeners.caveOnPlayerMove;
import me.mvabo.verydangerouscaves.caveListeners.caveWIElistener;
import me.mvabo.verydangerouscaves.commands.TabCompleter;
import me.mvabo.verydangerouscaves.commands.VDC;
import me.mvabo.verydangerouscaves.mobs.SpawnHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VeryDangerousCaves extends JavaPlugin implements Listener {

    boolean caveEnabled = false;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //get config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //Read config
        caveEnabled = getConfig().getBoolean("cm-enabled");

        //Register general events
        Bukkit.getPluginManager().registerEvents(this, this);

        //Register external events
        if (caveEnabled) {
            Bukkit.getPluginManager().registerEvents(new cave(), this);
            Bukkit.getPluginManager().registerEvents(new caveWIElistener(), this);
            //Bukkit.getPluginManager().registerEvents(new caveDoMobSpawns(), this);
            Bukkit.getPluginManager().registerEvents(new me.mvabo.verydangerouscaves.caveListeners.onBreakBlock(), this);
            Bukkit.getPluginManager().registerEvents(new caveOnPlayerMove(), this);
            Bukkit.getPluginManager().registerEvents(new SpawnHandler(), this);
        }

        this.getCommand("VDC").setExecutor(new VDC());
        this.getCommand("VDC").setTabCompleter(new TabCompleter());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
