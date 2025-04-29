package mc.twilight.cosmeticos.nms.interfaces.pets;

import org.bukkit.entity.Entity;

public interface PetEntity {
  
  void setPetCustomName(String customName);
  
  void kill();
  
  boolean isRiding();
  
  Entity getBukkitEntity();
}
