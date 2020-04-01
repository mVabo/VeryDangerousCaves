package me.mvabo.verydangerouscaves.randomizers;

import me.mvabo.verydangerouscaves.cave;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class chestRandomizer {

    static Random randint = new Random();

    //Causes weird error, try is to stop it.
    public static void fillChest(Block b) {
        Chest chest = (Chest) b.getState();
        Inventory inv = chest.getInventory();
        List<ItemStack> items = getItems();
        for(ItemStack i : items) {
            try {
            inv.setItem(randint.nextInt(inv.getSize()), i);
            } catch(Exception e) {}
        }
        //chest.update();
    }

    public static List<ItemStack> getItems(){
        List<ItemStack> items = new ArrayList<ItemStack>();
        int randomAmmount = randint.nextInt(10)+2;
        for(int i = 0; i < randomAmmount; i++) {
            int choice = randint.nextInt(24);
            if(choice == 0) {
                items.add(new ItemStack(Material.OAK_PLANKS, randint.nextInt(7)+1));
            }
            else if(choice == 1) {
                items.add(new ItemStack(Material.TORCH, randint.nextInt(7)+1));
            }
            else if(choice == 2) {
                items.add(new ItemStack(Material.COBWEB, randint.nextInt(4)+1));
            }
            else if(choice == 3) {
                items.add(new ItemStack(Material.BONE, randint.nextInt(7)+1));
            }
            else if(choice == 4) {
                items.add(new ItemStack(Material.STICK, randint.nextInt(7)+1));
            }
            else if(choice == 5) {
                items.add(new ItemStack(Material.OAK_LOG, randint.nextInt(7)+1));
            }
            else if(choice == 6) {
                items.add(new ItemStack(Material.WATER_BUCKET, 1));
            }
            else if(choice == 7) {
                items.add(new ItemStack(Material.WOODEN_PICKAXE, 1));
            }
            else if(choice == 8) {
                items.add(new ItemStack(Material.STONE_PICKAXE, 1));
            }
            else if(choice == 9) {
                items.add(new ItemStack(Material.OAK_SAPLING, randint.nextInt(2)+1));
            }
            else if(choice == 10) {
                items.add(new ItemStack(Material.COAL, randint.nextInt(3)+1));
            }
            else if(choice == 11) {
                items.add(new ItemStack(Material.BEEF, randint.nextInt(3)+1));
            }
            else if(choice == 12) {
                items.add(new ItemStack(Material.APPLE, randint.nextInt(3)+1));
            }
            else if(choice == 13) {
                items.add(new ItemStack(Material.CHICKEN, randint.nextInt(3)+1));
            }
            else if(choice == 14) {
                items.add(new ItemStack(Material.WHITE_WOOL, randint.nextInt(3)+1));
            }
            else if(choice == 15) {
                items.add(new ItemStack(Material.BREAD, randint.nextInt(3)+1));
            }
            else if(choice == 16) {
                items.add(new ItemStack(Material.DIRT, randint.nextInt(5)+1));
            }
            else if(choice == 17) {
                items.add(new ItemStack(Material.CARROT, randint.nextInt(3)+1));
            }
            else if(choice == 18) {
                items.add(new ItemStack(Material.COOKIE, randint.nextInt(3)+1));
            }
            else if(choice == 19) {
                items.add(new ItemStack(Material.WOODEN_AXE, 1));
            }
            else if(choice == 20) {
                items.add(new ItemStack(Material.STONE_AXE, 1));
            }
            else if(choice == 21) {
                items.add(new ItemStack(Material.PAPER, randint.nextInt(5)+1));
            }
            else if(choice == 22) {
                items.add(new ItemStack(Material.SUGAR_CANE, randint.nextInt(3)+1));
            }
            else if(choice == 23) {
                //try {
                if(cave.itemcustom.size()>0) {
                    items.add(new ItemStack(Material.getMaterial(cave.itemcustom.get(randint.nextInt(cave.itemcustom.size()))), randint.nextInt(3)+1));
                }
                //}catch(Exception e) { }
            }
        }
        return items;
    }

}
