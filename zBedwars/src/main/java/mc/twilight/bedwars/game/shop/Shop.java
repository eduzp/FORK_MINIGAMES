package mc.twilight.bedwars.game.shop;

import com.google.common.collect.ImmutableList;
import mc.twilight.bedwars.Main;
import mc.twilight.core.plugin.config.KConfig;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Shop {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("shop");
  public static List<ShopCategory> categories = new ArrayList<>();
  
  public static void setupShop() {
    ConfigurationSection section = CONFIG.getSection("categories");
    for (String key : section.getKeys(false)) {
      categories.add(new ShopCategory(key));
    }
    
  }
  
  public static int getCategoryId(ShopCategory search) {
    if (search == null) {
      return 0;
    }
    
    int id = 1;
    for (ShopCategory category : listCategories()) {
      if (category.equals(search)) {
        break;
      }
      
      id++;
    }
    
    return id;
  }
  
  public static ShopCategory getCategoryById(int id) {
    int find = 1;
    for (ShopCategory category : listCategories()) {
      if (find == id) {
        return category;
      }
      
      find++;
    }
    
    return null;
  }
  
  public static List<ShopCategory> listCategories() {
    return ImmutableList.copyOf(categories);
  }
}
