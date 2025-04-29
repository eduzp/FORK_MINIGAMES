package mc.twilight.cosmeticos.nms.v1_8_R3.balloons;

import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.balloons.BallonEntity;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class BallonBat extends EntityBat implements BallonEntity {
  
  private final Player owner;
  
  public BallonBat(Player owner) {
    super(((CraftWorld) owner.getWorld()).getHandle());
    this.owner = owner;
    
    super.setInvisible(true);
    this.setLeashHolder(((CraftPlayer) owner).getHandle(), true);
    
    Location batLocation = owner.getLocation().clone().add(0.88, 2.0, 0.88);
    if (!batLocation.getBlock().isEmpty()) {
      batLocation.subtract(0.88, 0, 0.88);
    }
    
    NMS.getInstance().look(this, batLocation.getYaw(), batLocation.getPitch());
    this.setPosition(batLocation.getX(), batLocation.getY(), batLocation.getZ());
    
    NMS.getInstance().clearPathfinderGoal(this);
    
    this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(1, new PathfinderGoalFloat(this));
  }
  
  @Override
  public void kill() {
    this.dead = true;
  }
  
  @Override
  public void t_() {
    if (this.owner == null || !this.owner.isOnline()) {
      this.kill();
      return;
    }
    
    EntityPlayer entityPlayer = ((CraftPlayer) this.owner).getHandle();
    Location playerLocation = this.owner.getLocation();
    Location batLocation = this.getBukkitEntity().getLocation();
    if (entityPlayer.world != this.world) {
      this.setPosition(entityPlayer.locX, entityPlayer.locY, entityPlayer.locZ);
      NMS.getInstance().look(this, entityPlayer.yaw, entityPlayer.pitch);
    } else {
      float f1 = (float) (Math.toDegrees(Math.atan2(entityPlayer.locZ - this.locZ, entityPlayer.locX - this.locX)) - 90.0F + ThreadLocalRandom.current().nextFloat());
      float f2 = (float) (Math.toDegrees(Math.atan2(entityPlayer.locZ - this.locZ, entityPlayer.locX - this.locX)) - 90.0F);
      double batY = entityPlayer.locY + 2.0;
      if (playerLocation.distance(batLocation) >= 3.2) {
        batLocation = batLocation.add(playerLocation.toVector().subtract(batLocation.toVector()).setY(0).normalize().multiply(0.6));
        batLocation.setY(batY);
        batLocation.setYaw(f1);
        batLocation.setPitch(f2);
      }
      if (playerLocation.distance(batLocation) >= 32.0) {
        batLocation = batLocation.add(playerLocation.toVector().subtract(batLocation.toVector()).setY(0).normalize().multiply(1.6));
        batLocation.setY(batY);
        batLocation.setYaw(f1);
        batLocation.setPitch(f2);
      }
      if (playerLocation.distance(batLocation) >= 64.0) {
        batLocation = playerLocation.clone();
        batLocation.setY(batY);
        batLocation.setYaw(f1);
        batLocation.setPitch(f2);
      }
      
      this.setPosition(batLocation.getX(), batLocation.getY(), batLocation.getZ());
      NMS.getInstance().look(this, batLocation.getYaw(), batLocation.getPitch());
    }
  }
  
  @Override
  protected boolean a(EntityHuman entityhuman) {
    return false;
  }
  
  @Override
  public boolean isInvulnerable(DamageSource damagesource) {
    return true;
  }
  
  @Override
  public void setCustomName(String s) {
  }
  
  @Override
  public void setCustomNameVisible(boolean flag) {
  }
  
  @Override
  public boolean d(int i, ItemStack itemstack) {
    return false;
  }
  
  @Override
  public void die() {
  }
  
  @Override
  public boolean damageEntity(DamageSource damagesource, float f) {
    return false;
  }
  
  @Override
  public void setInvisible(boolean flag) {
  }
  
  public void a(NBTTagCompound nbttagcompound) {
  }
  
  public void b(NBTTagCompound nbttagcompound) {
  }
  
  public boolean c(NBTTagCompound nbttagcompound) {
    return false;
  }
  
  public boolean d(NBTTagCompound nbttagcompound) {
    return false;
  }
  
  public void e(NBTTagCompound nbttagcompound) {
  }
  
  public void f(NBTTagCompound nbttagcompound) {
  }
}
