package mc.twilight.caixas.nms.entity;

import mc.twilight.core.libraries.holograms.api.HologramLine;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.Field;

import static mc.twilight.caixas.nms.NMS.ATTACHED_ENTITY;

public class EntityStand extends EntityArmorStand implements IArmorStand {
  
  private final String owner;
  
  public EntityStand(String owner, Location location) {
    super(((CraftWorld) location.getWorld()).getHandle());
    this.owner = owner;
    setArms(false);
    setBasePlate(true);
    setInvisible(true);
    setGravity(false);
    setSmall(true);
    
    try {
      Field field = net.minecraft.server.v1_8_R3.EntityArmorStand.class.getDeclaredField("bi");
      field.setAccessible(true);
      field.set(this, 2147483647);
    } catch (Exception e) {
      e.printStackTrace();
    }
    super.a(new NullBoundingBox());
  }
  
  @Override
  public void a(AxisAlignedBB axisalignedbb) {}
  
  public boolean isInvulnerable(DamageSource source) {
    return true;
  }
  
  public void setCustomName(String customName) {}
  
  public void setCustomNameVisible(boolean visible) {}
  
  @Override
  public void t_() {
    this.ticksLived = 0;
    super.t_();
    if (this.owner == null) {
      this.killEntity();
    }
  }
  
  public void makeSound(String sound, float f1, float f2) {}
  
  @Override
  public void setName(String text) {
    if (text != null && text.length() > 300) {
      text = text.substring(0, 300);
    }
    
    super.setCustomName(text == null ? "" : text);
    super.setCustomNameVisible(text != null && !text.isEmpty());
  }
  
  @Override
  public void killEntity() {
    ATTACHED_ENTITY.remove(this.getId());
    super.die();
  }
  
  @Override
  public HologramLine getLine() {
    return null;
  }
  
  @Override
  public void setPosition(double d0, double d1, double d2) {
    super.setPosition(d0, d1, d2);
    
    PacketPlayOutEntityTeleport teleportPacket =
        new PacketPlayOutEntityTeleport(getId(), MathHelper.floor(this.locX * 32.0D), MathHelper.floor(this.locY * 32.0D), MathHelper.floor(this.locZ * 32.0D),
            (byte) (int) (this.yaw * 256.0F / 360.0F), (byte) (int) (this.pitch * 256.0F / 360.0F), this.onGround);
    
    for (EntityHuman obj : world.players) {
      if (obj instanceof EntityPlayer) {
        EntityPlayer nmsPlayer = (EntityPlayer) obj;
        
        double distanceSquared = (nmsPlayer.locX - this.locX) * (nmsPlayer.locX - this.locX) + (nmsPlayer.locZ - this.locZ) * (nmsPlayer.locZ - this.locZ);
        if (distanceSquared < 8192.0 && nmsPlayer.playerConnection != null) {
          nmsPlayer.playerConnection.sendPacket(teleportPacket);
        }
      }
    }
  }
  
  @Override
  public ArmorStand getEntity() {
    return (ArmorStand) getBukkitEntity();
  }
  
  @Override
  public CraftEntity getBukkitEntity() {
    if (bukkitEntity == null) {
      bukkitEntity = new CraftStand(this);
      bukkitEntity.teleport(new Location(world.getWorld(), locX, locY, locZ));
    }
    
    return super.getBukkitEntity();
  }
  
  @Override
  public void setLocation(double x, double y, double z) {
    setPosition(x, y, z);
  }
  
  @Override
  public boolean isDead() {
    return dead;
  }
  
  static class CraftStand extends CraftArmorStand implements IArmorStand {
    
    public CraftStand(EntityStand entity) {
      super(entity.world.getServer(), entity);
    }
    
    @Override
    public void setHeadPose(EulerAngle pose) {
      ((EntityStand) entity).setHeadPose(new Vector3f((float) pose.getX(), (float) pose.getY(), (float) pose.getZ()));
    }
    
    @Override
    public void setLeftArmPose(EulerAngle pose) {
      ((EntityStand) entity).setLeftArmPose(new Vector3f((float) pose.getX(), (float) pose.getY(), (float) pose.getZ()));
    }
    
    @Override
    public void setLeftLegPose(EulerAngle pose) {
      ((EntityStand) entity).setLeftLegPose(new Vector3f((float) pose.getX(), (float) pose.getY(), (float) pose.getZ()));
    }
    
    @Override
    public void setRightLegPose(EulerAngle pose) {
      ((EntityStand) entity).setRightLegPose(new Vector3f((float) pose.getX(), (float) pose.getY(), (float) pose.getZ()));
    }
    
    @Override
    public int getId() {
      return entity.getId();
    }
    
    @Override
    public void setName(String text) {
      ((EntityStand) entity).setName(text);
    }
    
    @Override
    public void killEntity() {
      ((EntityStand) entity).killEntity();
    }
    
    @Override
    public HologramLine getLine() {
      return ((EntityStand) entity).getLine();
    }
    
    @Override
    public ArmorStand getEntity() {
      return this;
    }
    
    @Override
    public void setLocation(double x, double y, double z) {
      ((EntityStand) entity).setLocation(x, y, z);
    }
  }
}