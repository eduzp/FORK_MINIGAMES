package mc.twilight.cosmeticos.nms.v1_8_R3.util;

import mc.twilight.cosmeticos.cosmetics.object.Morph;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class PathfinderGoalFollowMorph extends PathfinderGoal {
  
  private final Morph pet;
  private final Player owner;
  private final EntityInsentient entity;
  private final double speed;
  private double distance = 0.0;
  private int stay = 1;
  
  public PathfinderGoalFollowMorph(Morph pet, EntityInsentient entity, double speed) {
    this.pet = pet;
    this.owner = pet.getMorphOwner();
    this.entity = entity;
    this.speed = speed;
    if (entity instanceof EntitySlime) {
      EntitySlime slime = (EntitySlime) entity;
      if ((slime.getSize() == 0) | (slime.getSize() == 1)) {
        this.distance = 0.1;
        this.stay = 2;
      }
    } else if (entity instanceof EntityHorse) {
      this.distance = 0.1;
      this.stay = 2;
    }
  }
  
  @Override
  public boolean a() {
    if (!pet.update()) {
      return false;
    }
    
    Location location = this.owner.getLocation();
    if (!this.entity.getBukkitEntity().getWorld().equals(location.getWorld())) {
      this.entity.setPosition(location.getX(), location.getY(), location.getZ());
      this.entity.yaw = location.getYaw();
      this.entity.pitch = location.getPitch();
      return false;
    }
    
    this.c();
    return true;
  }
  
  @Override
  public void c() {
    Location location = this.owner.getLocation();
    int distance = (int) location.distance(this.entity.getBukkitEntity().getLocation());
    if (distance >= 15.0 && this.owner.isOnGround()) {
      this.entity.setPosition(location.getX(), location.getY(), location.getZ());
      this.entity.yaw = location.getYaw();
      this.entity.pitch = location.getPitch();
      return;
    }
    
    PathEntity pathEntity = this.entity.getNavigation().a(location.getX() + stay, location.getY(), location.getZ() + stay);
    if (pathEntity != null) {
      if (distance > this.distance) {
        this.entity.getNavigation().a(pathEntity, speed);
        this.entity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
      } else {
        this.entity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
      }
    }
  }
}
