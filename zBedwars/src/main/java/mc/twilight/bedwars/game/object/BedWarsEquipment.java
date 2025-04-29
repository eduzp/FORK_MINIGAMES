package mc.twilight.bedwars.game.object;

import mc.twilight.bedwars.cosmetics.CosmeticType;
import mc.twilight.bedwars.cosmetics.types.WoodTypes;
import mc.twilight.bedwars.game.shop.ShopItem;
import mc.twilight.bedwars.hook.container.SelectedContainer;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BedWarsEquipment {
  
  private int deaths = -1;
  private Player player;
  private ItemStack sword;
  private ItemStack bow;
  private ItemStack pickaxe;
  private ItemStack axe;
  private ItemStack helmet;
  private ItemStack chestplate;
  private ItemStack leggings;
  private ItemStack boots;
  private ItemStack compass;
  private String tracking;
  private String colorId;
  
  private List<ShopItem> shopItems = new ArrayList<>();
  private Map<ShopItem, Integer> tiers = new HashMap<>();
  
  public BedWarsEquipment(Player player, String color, String colorId) {
    this.player = player;
    this.colorId = colorId;
    this.sword = BukkitUtils.deserializeItemStack("WOOD_SWORD");
    this.helmet = BukkitUtils.deserializeItemStack("LEATHER_HELMET : 1 : pintar>" + color);
    this.chestplate = BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : pintar>" + color);
    this.leggings = BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : pintar>" + color);
    this.boots = BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : pintar>" + color);
    this.compass = BukkitUtils.deserializeItemStack("COMPASS : 1 : nome>&aLocalizador");
    this.tracking = "";
  }
  
  public boolean update() {
    boolean refreshUpgrades = false;
    if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
      for (ItemStack stack : player.getInventory().getArmorContents()) {
        if (stack != null && !stack.getType().name().contains("AIR")) {
          refreshUpgrades = true;
          break;
        }
      }
      
      if (refreshUpgrades) {
        this.player.getInventory().setArmorContents(null);
        this.player.updateInventory();
      }
    } else {
      for (ItemStack stack : player.getInventory().getArmorContents()) {
        if (stack == null || stack.getType().name().contains("AIR")) {
          refreshUpgrades = true;
          break;
        }
      }
      
      if (refreshUpgrades) {
        this.player.getInventory().setItem(8, this.compass);
        this.player.getInventory().setHelmet(this.helmet);
        this.player.getInventory().setChestplate(this.chestplate);
        this.player.getInventory().setLeggings(this.leggings);
        this.player.getInventory().setBoots(this.boots);
        this.player.updateInventory();
      }
    }
    
    return refreshUpgrades;
  }
  
  public void removeTier(ShopItem item) {
    if (this.tiers.containsKey(item) && this.getTier(item) != 1) {
      this.tiers.put(item, this.getTier(item) - 2);
      
      this.addItem(item);
    }
  }
  
  public void refresh() {
    List<ShopItem> toRemove = this.shopItems.stream().filter(ShopItem::lostOnDie).collect(Collectors.toList());
    this.shopItems.removeAll(toRemove);
    toRemove.clear();
  
    this.deaths++;
    this.player.getInventory().setItem(0, this.sword);
    if (this.bow != null) {
      this.player.getInventory().setItem(1, this.bow);
    }
    if (this.pickaxe != null) {
      this.player.getInventory().setItem(2, this.pickaxe);
    }
    if (this.axe != null) {
      this.player.getInventory().setItem(3, this.axe);
    }
    this.player.getInventory().setItem(8, this.compass);
    this.player.getInventory().setHelmet(this.helmet);
    this.player.getInventory().setChestplate(this.chestplate);
    this.player.getInventory().setLeggings(this.leggings);
    this.player.getInventory().setBoots(this.boots);
    
    for (ShopItem item : shopItems) {
      removeTier(item);
      
      for (ItemStack is : item.getContent(getTier(item))) {
        if (!is.getType().name().contains("SWORD") && !is.getType().name().contains("HELMET") && !is.getType().name().contains("CHESTPLATE")
            && !is.getType().name().contains("LEGGINGS") && !is.getType().name().contains("BOOTS") && !is.getType().name().contains("BOW") && !is.getType().name().contains("AXE")) {
          if (is.getType().name().contains("WOOL") || is.getType().name().contains("STAINED_CLAY") || is.getType().name().contains("STAINED_GLASS")) {
            ItemStack clone = is.clone();
            clone.setDurability((short) Integer.parseInt(colorId));
            player.getInventory().addItem(clone);
            continue;
          }
          
          player.getInventory().addItem(is);
        }
      }
    }
  }
  
  public void addItem(ShopItem item) {
    if (item.isTieable()) {
      this.tiers.put(item, this.getTier(item) + 1);
    }
    
    List<ItemStack> give = new ArrayList<>();
    for (ItemStack is : item.getContent(this.getTier(item))) {
      if (is.getType().name().contains("SWORD")) {
        if (!item.lostOnDie()) {
          this.sword = is;
        }
        PlayerUtils.replaceItem(player.getInventory(), "SWORD", is);
      } else if (is.getType().name().contains("HELMET")) {
        if (!item.lostOnDie()) {
          this.helmet = is;
        }
        player.getInventory().setHelmet(is);
      } else if (is.getType().name().contains("CHESTPLATE")) {
        if (!item.lostOnDie()) {
          this.chestplate = is;
        }
        player.getInventory().setChestplate(is);
      } else if (is.getType().name().contains("LEGGINGS")) {
        if (!item.lostOnDie()) {
          this.leggings = is;
        }
        player.getInventory().setLeggings(is);
      } else if (is.getType().name().contains("BOOTS")) {
        if (!item.lostOnDie()) {
          this.boots = is;
        }
        player.getInventory().setBoots(is);
      } else if (is.getType().name().contains("BOW")) {
        if (!item.lostOnDie()) {
          this.bow = is;
        }
        boolean bol = PlayerUtils.replaceItem(player.getInventory(), "BOW", is);
        if (!bol) {
          player.getInventory().addItem(is);
        }
      } else if (is.getType().name().contains("_PICKAXE")) {
        if (!item.lostOnDie()) {
          this.pickaxe = is;
        }
        boolean bol = PlayerUtils.replaceItem(player.getInventory(), "_PICKAXE", is);
        if (!bol) {
          player.getInventory().addItem(is);
        }
      } else if (is.getType().name().contains("_AXE")) {
        if (!item.lostOnDie()) {
          this.axe = is;
        }
        boolean bol = PlayerUtils.replaceItem(player.getInventory(), "_AXE", is);
        if (!bol) {
          player.getInventory().addItem(is);
        }
      } else {
        give.add(is);
      }
    }
    
    if (!shopItems.contains(item)) {
      shopItems.add(item);
    }
    for (ItemStack is : item.getContent().stream().filter(give::contains).collect(Collectors.toList())) {
      if (is.getType().name().contains("WOOL") || is.getType().name().contains("STAINED_CLAY") || is.getType().name().contains("STAINED_GLASS")) {
        ItemStack clone = is.clone();
        clone.setDurability((short) Integer.parseInt(colorId));
        player.getInventory().addItem(clone);
        continue;
      }
      if (is.getType().name().contains("WOOD")) {
        WoodTypes dc = Profile.getProfile(player.getName()).getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.WOOD_TYPE, WoodTypes.class);
        if (dc != null) {
          ItemStack clone = dc.getItem();
          clone.setAmount(is.getAmount());
          player.getInventory().addItem(clone);
          continue;
        }
      }
      
      player.getInventory().addItem(is);
    }
  }
  
  public void destroy() {
    this.player = null;
    this.sword = null;
    this.bow = null;
    this.pickaxe = null;
    this.axe = null;
    this.helmet = null;
    this.chestplate = null;
    this.leggings = null;
    this.boots = null;
    this.compass = null;
    this.tracking = null;
    this.colorId = null;
    this.shopItems.clear();
    this.shopItems = null;
    this.tiers.clear();
    this.tiers = null;
  }
  
  public boolean cantBuy(ShopItem item) {
    boolean anyBlockThat = this.shopItems.stream().filter(si -> si.getCategory().equals(item.getCategory()) && si.isBlocked(item)).count() > 0;
    if (anyBlockThat) {
      return true;
    }
    
    boolean alreadyHaves = this.shopItems.contains(item);
    if (alreadyHaves && item.isTieable()) {
      return item.getMaxTier() == this.getTier(item);
    }
    
    return alreadyHaves && !item.lostOnDie();
  }
  
  public boolean contains(ShopItem item) {
    return this.shopItems.contains(item) && (!item.isTieable() || item.getMaxTier() == this.getTier(item));
  }
  
  public int getTier(ShopItem item) {
    return tiers.getOrDefault(item, 0);
  }
  
  public String getTracking() {
    return tracking;
  }
  
  public void setTracking(String tracking) {
    this.tracking = tracking;
  }
  
  public int getDeaths() {
    return deaths;
  }
}