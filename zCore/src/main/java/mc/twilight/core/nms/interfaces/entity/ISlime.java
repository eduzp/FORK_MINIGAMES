package mc.twilight.core.nms.interfaces.entity;

import mc.twilight.core.libraries.holograms.api.HologramLine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;

public interface ISlime {
  
  void setPassengerOf(Entity entity);
  
  void setLocation(double x, double y, double z);
  
  boolean isDead();
  
  void killEntity();
  
  Slime getEntity();
  
  HologramLine getLine();
}
