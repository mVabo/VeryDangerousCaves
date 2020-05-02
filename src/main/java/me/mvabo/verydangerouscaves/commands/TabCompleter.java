package me.mvabo.verydangerouscaves.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    List<String> arguments = new ArrayList<String>();
    List<String> mobs = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("spawn");
            arguments.add("reload");
        }

        if(args[0].toLowerCase().equals("spawn")) {
            mobs.add("AlphaSpider");
            mobs.add("CryingBat");
            mobs.add("DeadMiner");
            mobs.add("HexedArmor");
            mobs.add("LavaCreeper");
            mobs.add("MagmaMonster");
            mobs.add("SmokeDemon");
            mobs.add("TnTCreeper");
            mobs.add("Watcher");
            mobs.add("Herobrine");
            mobs.add("DeadDiamondMiner");
            return mobs;
        }

        List<String> result = new ArrayList<String>();

        if(args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }

            return result;
        }

        if(args.length == 2) {
            for (String a : mobs) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }



        return null;
    }
}
