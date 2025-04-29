package mc.twilight.bedwars.game.improvements;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Upgrade {
  
  private int slot;
  private UpgradeType type;
  private String icon;
  private List<UpgradeLevel> levels;
  
  public Upgrade(int slot, UpgradeType type, String icon, List<UpgradeLevel> levels) {
    this.slot = slot;
    this.type = type;
    this.icon = icon;
    this.levels = levels;
  }
  
  public int getSlot() {
    return slot;
  }
  
  public String getIcon() {
    return icon;
  }
  
  public UpgradeType getType() {
    return type;
  }
  
  public ItemStack getPrice(int index) {
    return levels.size() == index ? levels.get(levels.size() - 1).getPrice() : levels.get(index - 1).getPrice();
  }
  
  public int getMaxTier() {
    return levels.size();
  }
  
  static class UpgradeLevel {
    
    protected ItemStack price;
    
    public UpgradeLevel(ItemStack price) {
      this.price = price;
    }
    
    public ItemStack getPrice() {
      return price;
    }
  }
}