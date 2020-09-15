package me.mvabo.verydangerouscaves;

import me.mvabo.verydangerouscaves.caveEffects.CaveSounds;
import me.mvabo.verydangerouscaves.caveEffects.CaveTemperature;
import me.mvabo.verydangerouscaves.caveEffects.EffectLooper;
import me.mvabo.verydangerouscaves.caveListeners.caveWIElistener;
import me.mvabo.verydangerouscaves.commands.TabCompleter;
import me.mvabo.verydangerouscaves.commands.EC;
import me.mvabo.verydangerouscaves.mobs.DamageHandler;
import me.mvabo.verydangerouscaves.mobs.DeathHandler;
import me.mvabo.verydangerouscaves.mobs.SpawnHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static me.mvabo.verydangerouscaves.cave.hungerdark;

public final class VeryDangerousCaves extends JavaPlugin implements Listener {

    boolean caveEnabled = false;

    caveWIElistener caveInit;
    CaveSounds caveSounds;
    EffectLooper effectLooper;
    DamageHandler damageHandler;
    CaveTemperature caveTemp;
    DeathHandler deathHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        caveInit = new caveWIElistener();
        caveSounds = new CaveSounds();
        effectLooper = new EffectLooper();
        damageHandler = new DamageHandler();
        damageHandler.getWorlds();
        caveTemp = new CaveTemperature();
        deathHandler = new DeathHandler();

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
            Bukkit.getPluginManager().registerEvents(caveInit, this);
            Bukkit.getPluginManager().registerEvents(effectLooper, this);
            Bukkit.getPluginManager().registerEvents(damageHandler, this);
            Bukkit.getPluginManager().registerEvents(deathHandler, this);
            Bukkit.getPluginManager().registerEvents(new me.mvabo.verydangerouscaves.caveListeners.onBreakBlock(), this);
            Bukkit.getPluginManager().registerEvents(new SpawnHandler(), this);
        }

        this.getCommand("EC").setExecutor(new EC());
        this.getCommand("EC").setTabCompleter(new TabCompleter());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                caveSounds.doCaveSounds();
            }
        }, 0L, 600L);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (getConfig().getBoolean("enable_hungering_darkness") == true || getConfig().getBoolean("enable_cave_monsters") == true) {
                    effectLooper.betterEffectLooper();
                }
            }
        }, 0L, /* 600 */((long) 3));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (getConfig().getBoolean("enable_cave_temperature")) {
                    caveTemp.doCaveTemp();
                }
            }
        }, 0L, 600);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
