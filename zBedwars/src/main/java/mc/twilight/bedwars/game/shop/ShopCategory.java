package mc.twilight.bedwars.game.shop;

import mc.twilight.bedwars.Main;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopCategory {
  
  private String name;
  private ItemStack icon;
  private List<ShopItem> items;
  
  public ShopCategory(String key) {
    KConfig config = Main.getInstance().getConfig("shop");
    this.name = config.getString("categories." + key + ".name");
    this.icon = BukkitUtils.deserializeItemStack(config.getString("categories." + key + ".icon"));
    this.items = new ArrayList<>();
    
    ConfigurationSection section = config.getSection("categories." + key + ".items");
    for (String item : section.getKeys(false)) {
      boolean lostOnDie = section.getBoolean(item + ".lostOnDie", true);
      String icon2 = section.getString(item + ".icon");
      List<String> blocks = new ArrayList<>();
      if (section.getStringList(item + ".block") != null) {
        blocks.addAll(section.getStringList(item + ".block"));
      }
      
      if (!section.contains(item + ".price")) {
        if (!section.contains(item + ".tiers")) {
          continue;
        }
        
        List<ShopItem.ShopItemTier> tiers = new ArrayList<>();
        for (String tier : section.getConfigurationSection(item + ".tiers").getKeys(false)) {
          ItemStack price = BukkitUtils.deserializeItemStack(section.getString(item + ".tiers." + tier + ".price"));
          String sound = section.getString(item + ".tiers." + tier + ".icon");
          List<ItemStack> content = new ArrayList<>();
          for (String stack : section.getStringList(item + ".tiers." + tier + ".content")) {
            content.add(BukkitUtils.deserializeItemStack(stack));
          }
          
          tiers.add(new ShopItem.ShopItemTier(price, content, sound));
        }
        
        this.items.add(new ShopItem(this, item, lostOnDie, icon2, null, null, blocks, tiers));
        continue;
      }
      
      ItemStack price = BukkitUtils.deserializeItemStack(section.getString(item + ".price"));
      List<ItemStack> content = new ArrayList<>();
      for (String stack : section.getStringList(item + ".content")) {
        content.add(BukkitUtils.deserializeItemStack(stack));
      }
      
      this.items.add(new ShopItem(this, item, lostOnDie, icon2, price, content, blocks, null));
    }
  }
  
  public String getName() {
    return name;
  }
  
  public ItemStack getIcon() {
    return icon;
  }
  
  public List<ShopItem> listItems() {
    return items;
  }
  
  public ShopItem getItem(String item) {
    for (ShopItem si : listItems()) {
      if (si.getName().equals(item)) {
        return si;
      }
    }
    
    return null;
  }
}
