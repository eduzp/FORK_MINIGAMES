package mc.twilight.bedwars.utils;

import mc.twilight.core.game.GameState;
import mc.twilight.core.player.Profile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerUtils {
  
  
  public static void giveResources(Player player, Player killer) {
    Profile pf = Profile.getProfile(killer.getName());
    Profile profile = Profile.getProfile(player.getName());
    if (pf != null && profile != null) {
      if (pf.playingGame() && (pf.getGame().getState() != GameState.EMJOGO || pf.getGame().isSpectator(killer)) || profile.playingGame() && profile.getGame().isSpectator(player)) {
        return;
      }
    }
    int iron = 0, gold = 0, diamond = 0, emerald = 0;
    for (ItemStack i : player.getInventory().getContents()) {
      if (i != null) {
        if (i.getType() == Material.EMERALD) {
          emerald += i.getAmount();
          killer.getInventory().addItem(i);
        } else if (i.getType() == Material.DIAMOND) {
          diamond += i.getAmount();
          killer.getInventory().addItem(i);
        } else if (i.getType() == Material.GOLD_INGOT) {
          gold += i.getAmount();
          killer.getInventory().addItem(i);
        } else if (i.getType() == Material.IRON_INGOT) {
          iron += i.getAmount();
          killer.getInventory().addItem(i);
        } else if (i.getType() == Material.FIREBALL || i.getType() == Material.TNT || i.getType() == Material.GOLDEN_APPLE || i.getType() == Material.ENDER_PEARL) {
          killer.getInventory().addItem(i);
        }
      }
    }
    
    if (iron > 0 || gold > 0 || diamond > 0 || emerald > 0) {
      killer.sendMessage("§aItens recebidos de " + player.getDisplayName() + "§a:");
    }
    if (iron > 0) {
      killer.sendMessage("§f+" + iron + " Ferro" + (iron > 1 ? "s" : ""));
    }
    if (gold > 0) {
      killer.sendMessage("§6+" + gold + " Ouro" + (gold > 1 ? "s" : ""));
    }
    if (diamond > 0) {
      killer.sendMessage("§b+" + diamond + " Diamante" + (diamond > 1 ? "s" : ""));
    }
    if (emerald > 0) {
      killer.sendMessage("§2+" + emerald + " Esmeralda" + (emerald > 1 ? "s" : ""));
    }
  }
  
  public static boolean containsSword(List<ItemStack> items) {
    return items.stream().filter(Objects::nonNull).map(ItemStack::getType).anyMatch(i -> i == Material.STONE_SWORD || i == Material.IRON_SWORD || i == Material.DIAMOND_SWORD);
  }
  
  public static boolean containsWoodSword(List<ItemStack> items) {
    return items.stream().filter(Objects::nonNull).map(ItemStack::getType).anyMatch(i -> i == Material.WOOD_SWORD);
  }
  
  public static void renewItems(Player player) {
    boolean update = false;
    for (ItemStack item : player.getInventory().getArmorContents()) {
      if (item != null) {
        if (item.getDurability() != 0) {
          item.setDurability((short) 0);
          update = true;
        }
      }
    }
    
    for (ItemStack item : player.getInventory().getContents()) {
      if (item != null) {
        if (item.getType().name().contains("_SWORD") || item.getType().name().contains("_PICKAXE") || item.getType().name().contains("_AXE")
            || item.getType().name().contains("SHEARS") || item.getType().name().contains("BOW")) {
          if (item.getDurability() != 0) {
            item.setDurability((short) 0);
            update = true;
          }
        }
      }
    }
    
    if (update) {
      player.updateInventory();
    }
  }
  
  public static Player getMoreNearby(Player player, List<Player> targets) {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < targets.size(); i++) {
      list.add(i + " : " + targets.get(i).getLocation().distance(player.getLocation()));
    }
    
    list.sort((o1, o2) -> {
      double i1 = Double.parseDouble(o1.split(" : ")[1]);
      double i2 = Double.parseDouble(o2.split(" : ")[1]);
      return Double.compare(i1, i2);
    });
    
    return targets.get(Integer.parseInt(list.get(0).split(" : ")[0]));
  }
  
  public static boolean replaceItem(Inventory inventory, String equals, ItemStack item) {
    for (int i = 0; i < inventory.getSize(); i++) {
      ItemStack stack = inventory.getItem(i);
      if (stack == null || !stack.getType().name().contains(equals)) {
        continue;
      }
      
      inventory.setItem(i, item);
      return true;
    }
    
    return false;
  }
  
  public static int removeItem(Inventory inventory, Material mat, int quantity) {
    int rest = quantity;
    for (int i = 0; i < inventory.getSize(); i++) {
      ItemStack stack = inventory.getItem(i);
      if (stack == null || stack.getType() != mat) {
        continue;
      }
      
      if (rest >= stack.getAmount()) {
        rest -= stack.getAmount();
        inventory.clear(i);
      } else if (rest > 0) {
        stack.setAmount(stack.getAmount() - rest);
        rest = 0;
      } else {
        break;
      }
    }
    
    return quantity - rest;
  }
  
  public static int getCountFromMaterial(Inventory inv, Material mat) {
    int count = 0;
    for (ItemStack item : inv.getContents()) {
      if (item != null && item.getType() == mat) {
        count += item.getAmount();
      }
    }
    
    return count;
  }
  
  public static int getAmountOfItem(Material material, Location location) {
    return getAmountOfItem(material, location, 1);
  }
  
  public static int getAmountOfItem(Material material, Location location, int distance) {
    int amount = 0;
    for (Entity entity : location.getWorld().getEntities()) {
      if (entity instanceof Item) {
        Item item = (Item) entity;
        if (item.getItemStack().getType().equals(material) && item.getLocation().distance(location) <= distance) {
          amount += item.getItemStack().getAmount();
        }
      }
    }
    
    return amount;
  }
  
  
  public static boolean isBedBlock(Block isBed) {
    if (isBed == null) {
      return false;
    }
    
    return (isBed.getType() == Material.BED || isBed.getType() == Material.BED_BLOCK);
  }
  
  public static Block getBedNeighbor(Block head) {
    if (isBedBlock(head.getRelative(BlockFace.EAST))) {
      return head.getRelative(BlockFace.EAST);
    } else if (isBedBlock(head.getRelative(BlockFace.WEST))) {
      return head.getRelative(BlockFace.WEST);
    } else if (isBedBlock(head.getRelative(BlockFace.SOUTH))) {
      return head.getRelative(BlockFace.SOUTH);
    } else {
      return head.getRelative(BlockFace.NORTH);
    }
  }
  
}

