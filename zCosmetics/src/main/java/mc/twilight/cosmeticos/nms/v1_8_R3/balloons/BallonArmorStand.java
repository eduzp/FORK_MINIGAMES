package mc.twilight.cosmeticos.nms.v1_8_R3.balloons;

import mc.twilight.cosmeticos.nms.interfaces.balloons.BallonEntity;
import mc.twilight.core.nms.v1_8_R3.utils.NullBoundingBox;
import mc.twilight.core.utils.BukkitUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.List;

public class BallonArmorStand extends EntityArmorStand implements BallonEntity {
  
  private final Player owner;
  private final BallonBat bat;
  private final List<String> frames;
  private float poseY = 0.0F;
  private int count, frame = 1;
  
  public BallonArmorStand(Player owner, BallonEntity bat, List<String> frames) {
    super(((CraftWorld) owner.getWorld()).getHandle());
    this.owner = owner;
    this.bat = (BallonBat) bat;
    this.frames = frames;
    
    this.setInvisible(true);
    this.setGravity(true);
    this.setBasePlate(true);
    this.setPosition(this.bat.locX, this.bat.locY - 0.2, this.bat.locZ);
    this.setEquipment(4, CraftItemStack.asNMSCopy(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>" + this.frames.get(0))));
    
    try {
      Field field = net.minecraft.server.v1_8_R3.EntityArmorStand.class.getDeclaredField("bi");
      field.setAccessible(true);
      field.set(this, 2147483647);
    } catch (Exception ignore) {
    }
    this.a(new NullBoundingBox());
  }
  
  @Override
  public void t_() {
    this.ticksLived = 0;
    super.t_();
    if (this.owner == null || !this.owner.isOnline()) {
      this.kill();
      return;
    }
    if (this.bat == null || this.bat.dead) {
      this.kill();
      return;
    }
    
    if (this.frames.size() > 1 && MinecraftServer.currentTick % 10 == 0) {
      if (frame >= this.frames.size()) {
        this.frame = 0;
      }
      
      this.setEquipment(4, CraftItemStack.asNMSCopy(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>" + this.frames.get(frame++))));
    }
    
    this.count++;
    if (this.count >= 24) {
      this.count = 0;
    }
    this.poseY += 2.5F;
    
    super.setHeadPose(new Vector3f(0.0F, poseY, 0.0F));
    
    this.setPosition(this.bat.locX, this.bat.locY - 0.2, this.bat.locZ);
  }
  
  @Override
  public void die() {
  }
  
  @Override
  public void kill() {
    this.dead = true;
  }
  
  @Override
  public boolean isInvulnerable(DamageSource source) {
    return true;
  }
  
  @Override
  public void setCustomName(String customName) {
  }
  
  @Override
  public void setCustomNameVisible(boolean visible) {
  }
  
  @Override
  public boolean a(EntityHuman human, Vec3D vec3d) {
    return true;
  }
}
