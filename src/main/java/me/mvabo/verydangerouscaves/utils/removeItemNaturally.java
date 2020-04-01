package me.mvabo.verydangerouscaves.utils;

import org.bukkit.entity.Player;

public class removeItemNaturally {
    public static void removeItemNaturally(Player p) {
        try {
            if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
                //p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                p.getInventory().getItemInMainHand().setAmount(0);
            } else {
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
            }
        }
        catch(Exception error) {

        }
    }

    public static void removeItemNaturally(Player p, boolean side) {
        try {
            if(side == true) {
                if (p.getInventory().getItemInMainHand().getAmount() <= 1) {
                    p.getInventory().getItemInMainHand().setAmount(0);
                }
                else {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                }
            }
            else if(side == true) {
                if (p.getInventory().getItemInOffHand().getAmount() <= 1) {
                    p.getInventory().getItemInOffHand().setAmount(0);
                }
                else {
                    p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount() - 1);
                }
            }
        }
        catch(Exception error) {

        }
    }
}
