package mc.twilight.cosmeticos.nms.v1_8_R3.morphs;

import mc.twilight.cosmeticos.cosmetics.object.Morph;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.morphs.MorphEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetHorse;
import mc.twilight.cosmeticos.nms.v1_8_R3.util.PathfinderGoalFollowMorph;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import mc.twilight.core.reflection.Accessors;
import mc.twilight.core.reflection.acessors.FieldAccessor;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public class EntityMorph extends EntityCreature implements IAnimal, MorphEntity {
  
  private static FieldAccessor<Boolean> JUMP_FIELD;

  static {
    try {
      JUMP_FIELD = Accessors.getField(EntityLiving.class, "aY", boolean.class);
    } catch (Exception ignored) {}
  }
  
  protected Morph pet;
  private final double moveSpeed;
  private final double rideSpeed;
  
  public EntityMorph(Morph morph) {
    super(((CraftWorld) morph.getMorphOwner().getWorld()).getHandle());
    this.pet = morph;
    this.resetEntitySize();
    this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
    this.moveSpeed = pet.getPetType().getMoveSpeed();
    this.rideSpeed = pet.getPetType().getRideSpeed();
    
    Location petLocation = pet.getMorphOwner().getLocation().clone().add(0.88, 0, 0.88);
    if (!petLocation.getBlock().isEmpty()) {
      petLocation.subtract(0.88, 0, 0.88);
    }
    
    this.setPosition(petLocation.getX(), petLocation.getY(), petLocation.getZ());
    
    NMS.getInstance().clearPathfinderGoal(this);
    NMS.getInstance().look(this, petLocation.getYaw(), petLocation.getPitch());
    
    this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(1, new PathfinderGoalFloat(this));
    this.goalSelector.a(2, new PathfinderGoalFollowMorph(pet, this, moveSpeed));
    
   // this.setPetCustomName("");
  }
  
  @Override
  public void setPetCustomName(String customName) {
    if (customName.length() > 64) {
      customName = customName.substring(0, 64);
    }
    super.setCustomName(customName);
    super.setCustomNameVisible(customName.length() > 0);
  }
  
  @Override
  public void kill() {
    if (this.passenger != null) {
      this.passenger.mount(null);
    }
    
    this.dead = true;
  }
  
  @Override
  public boolean isRiding() {
    if (this.passenger == null || this.pet == null || this.pet.getMorphOwner() == null) {
      return false;
    }
    
    return passenger == ((CraftPlayer) this.pet.getMorphOwner()).getHandle();
  }
  
  public void resetEntitySize() {
    this.resetEntitySize(false);
  }
  
  public void resetEntitySize(boolean baby) {
    EntitySize entitySize = getClass().getAnnotation(EntitySize.class);
    if (entitySize != null) {
      setSize(baby ? entitySize.width() / 2 : entitySize.width(), baby ? entitySize.height() / 2 : entitySize.height());
    }
  }
  
  @Override
  public void t_() {
    super.t_();
    this.repeatTask();
  }
  
  public void repeatTask() {
    if (this.pet == null || this.pet.getMorphOwner() == null || !this.pet.getMorphOwner().isOnline()) {
      this.kill();
      return;
    }
    
    if (this.isRiding() && !onGround && this.motY < 0.0) {
      this.motY *= 1.0;
    }
    
    double currentSpeed = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
    if (this.isRiding()) {
      if (currentSpeed != this.rideSpeed) {
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.rideSpeed);
      }
    } else if (currentSpeed != this.moveSpeed) {
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.moveSpeed);
    }
  }
  
  @Override
  public void g(float f, float f1) {
    if (this.pet == null || this.pet.getMorphOwner() == null || !this.isRiding()) {
      this.S = 0.5F;
      super.g(f, f1);
      return;
    }
    
    EntityPlayer entityPlayer = ((CraftPlayer) this.pet.getMorphOwner()).getHandle();
    this.yaw = entityPlayer.yaw;
    this.lastYaw = this.yaw;
    this.pitch = entityPlayer.pitch * 0.5F;
    setYawPitch(this.yaw, this.pitch);
    this.aK = this.aI = this.yaw;
    this.S = 1.0F;
    f = entityPlayer.aZ * 0.5F;
    f1 = entityPlayer.ba;
    if (f1 <= 0.0F) {
      f1 = (float) (f1 * (0.25D * this.rideSpeed));
    } else {
      f1 = (float) (f1 * this.rideSpeed);
    }
    
    if (!(this instanceof PetHorse)) {
      f *= 0.75F;
    }
    
    k((float) this.rideSpeed);
    if (!this.world.isClientSide) {
      super.g(f, f1);
      if (this instanceof PetHorse) {
        Location currentLocation = this.getBukkitEntity().getLocation();
        this.setPosition(currentLocation.getX(), currentLocation.getY(), currentLocation.getZ());
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(this);
        entityPlayer.playerConnection.sendPacket(packet);
        if (f1 > 0.0F) {
          float sinYaw = MathHelper.sin(this.yaw * 0.017453292F), cosYaw = MathHelper.cos(this.yaw * 0.017453292F);
          this.motX += -0.4F * sinYaw * 0.0F;
          this.motZ += 0.4F * cosYaw * 0.0F;
        }
      }
    }
    
    if (JUMP_FIELD != null) {
      if (!onGround) {
        if (this.pet.getMorphOwner().isFlying()) {
          this.pet.getMorphOwner().setFlying(false);
        }
        
        if (JUMP_FIELD.get(entityPlayer)) {
          this.motY = 0.5;
        }
      } else if (onGround) {
        if (JUMP_FIELD.get(entityPlayer)) {
          this.motY = 0.3;
        }
      }
    }
  }
  
  // right click
  @Override
  protected boolean a(EntityHuman entityhuman) {
    if (this.passenger != null || this.pet == null || this.pet.getMorphOwner() == null) {
      return false;
    }
    
    if (entityhuman == ((CraftPlayer) this.pet.getMorphOwner()).getHandle()) {
      if (!entityhuman.isSneaking()) {
        // remove passenger
        this.mount(null);
        // add human to passenger
        //    entityhuman.mount(this);
      }
    }
    
    return false;
  }
  
  @Override
  public boolean isInvulnerable(DamageSource damagesource) {
    return true;
  }
  
  @Override
  public void setCustomName(String s) {
    this.setPetCustomName(s);
  }
  
  @Override
  public void setCustomNameVisible(boolean flag) {}
  
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
  public void setInvisible(boolean flag) {}
  
  public void a(NBTTagCompound nbttagcompound) {}
  
  public void b(NBTTagCompound nbttagcompound) {}
  
  public boolean c(NBTTagCompound nbttagcompound) {
    return false;
  }
  
  public boolean d(NBTTagCompound nbttagcompound) {
    return false;
  }
  
  public void e(NBTTagCompound nbttagcompound) {}
  
  public void f(NBTTagCompound nbttagcompound) {}
  
  @Override
  public CraftEntity getBukkitEntity() {
    if (this.bukkitEntity == null) {
      this.bukkitEntity = new CraftEntityMorph(this);
    }
    
    return super.getBukkitEntity();
  }
}
