package mc.twilight.cosmeticos.nms.interfaces.morphs;

import org.bukkit.entity.Entity;

public interface MorphEntity {
  
  void setPetCustomName(String customName);
  
  void kill();
  
  boolean isRiding();
  
  Entity getBukkitEntity();
}
