package mc.twilight.core.nms.interfaces.entity;

import mc.twilight.core.libraries.holograms.api.HologramLine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public interface IItem {
  
  void setPassengerOf(Entity entity);
  
  void setItemStack(ItemStack item);
  
  void setLocation(double x, double y, double z);
  
  boolean isDead();
  
  void killEntity();
  
  Item getEntity();
  
  HologramLine getLine();
}
