package mc.twilight.cosmeticos.nms;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.nms.interfaces.balloons.BallonEntity;
import mc.twilight.core.plugin.logger.KLogger;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class NMS {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("NMS");
  private static NMS instance;
  
  public static boolean setupNMS() {
    try {
      instance = new mc.twilight.cosmeticos.nms.v1_8_R3.NMS();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
  
  public static NMS getInstance() {
    return instance;
  }
 
  public abstract void spawnFirework(Location location, FireworkEffect effect);
  
  public abstract BallonEntity createBalloonBat(Player owner);
  
  public abstract BallonEntity createBalloonArmorStand(Player owner, BallonEntity bat, List<String> frames);

  public abstract void registerEntity(Class<?> entityClass, String entityName, int entityId);

  public abstract void look(Object entity, float yaw, float pitch);

  public abstract void clearPathfinderGoal(Object entity);
  
  public abstract boolean addEntity(List<Object> entities);
  
  public abstract boolean addEntity(Object... entities);
  
  public abstract Object getHandle(Entity entity);
}
