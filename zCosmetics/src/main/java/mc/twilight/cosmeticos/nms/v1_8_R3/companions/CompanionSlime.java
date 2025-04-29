package mc.twilight.cosmeticos.nms.v1_8_R3.companions;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.companions.CompanionEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.util.PathfinderGoalFollowCompanion;
import mc.twilight.core.utils.StringUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CompanionSlime extends EntityCreature implements IAnimal, CompanionEntity {
  
  protected boolean inIdle;
  protected Companion companion;
  protected List<CompanionStructure> structures;
  protected int maxFrames, maxIdleFrames;
  private final double moveSpeed;
  private boolean moveAnimation, idleAnimation;
  private int currentFrame;
  private int currentIdleFrame;
  
  public CompanionSlime(Companion companion) {
    super(((CraftWorld) companion.getCompanionOwner().getWorld()).getHandle());
    this.companion = companion;
    this.structures = new ArrayList<>();
    
    this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
    this.moveSpeed = .60;
    
    Location petLocation = companion.getCompanionOwner().getLocation().clone().add(2, 0, 0.88);
    if (!petLocation.getBlock().isEmpty()) {
      petLocation.subtract(2.0, 0, 0.88);
    }
    
    getBukkitEntity().teleport(petLocation);
    NMS.getInstance().clearPathfinderGoal(this);
    
    this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(1, new PathfinderGoalFloat(this));
    this.goalSelector.a(2, new PathfinderGoalFollowCompanion(companion, this, moveSpeed));
  }
  
  protected void spawn() {
    List<Object> entities = new ArrayList<>(Collections.singleton(this));
    entities.addAll(this.structures.stream().map(structure -> NMS.getInstance().getHandle(structure.getStand())).collect(Collectors.toList()));
    NMS.getInstance().addEntity(entities);
    this.setCompanionName(StringUtils.formatColors(this.companion.getClient().getCompanionNames().get(this.companion.getCosmetic().getNameEnum())));
    this.moveToSlime();
  }
  
  @Override
  protected void h() {
    super.h();
    this.datawatcher.watch(0, (byte) 0x20);
    this.datawatcher.a(16, (byte) 0);
  }
  
  @Override
  public void t_() {
    super.t_();
    this.repeatTask();
  }
  
  @Override
  public void g(float f, float f1) {
    this.S = 0.5F;
    super.g(f, f1);
  }

  @Override
  public void die() {
  }

  @Override
  public void kill() {
    this.dead = true;
  }

  @Override
  protected boolean a(EntityHuman entityhuman) {
    return false;
  }
  
  public void repeatTask() {
    if (this.companion == null || this.companion.getCompanionOwner() == null || !this.companion.getCompanionOwner().isOnline()) {
      this.kill();
      return;
    }
    
    this.inIdle = this.lastX == this.locX && this.lastY == this.locY && this.lastZ == this.locZ;
    double currentSpeed = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
    if (currentSpeed != this.moveSpeed) {
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.moveSpeed);
    }
    
    if (this.inIdle && !this.companion.getCosmetic().hasIdleAnimation()) {
      if (this.maxFrames == 0) {
        return;
      }
      
      this.companion.getCosmetic().getKeyFrames().get(0).forEach(this::animate);
      return;
    }
    
    this.toggleType(this.inIdle);
    this.getFrames(this.inIdle).values().stream().filter(value -> !value.isEmpty()).forEach(value -> this.animate(value.get(this.getCurrent(this.inIdle))));
    this.increase(this.inIdle);
    
    moveToSlime();
  }
  
  private Map<Integer, List<CompanionAnimation>> getFrames(boolean inIdle) {
    return !inIdle ? this.companion.getCosmetic().getFrames() : this.companion.getCosmetic().getIdleFrames();
  }
  
  private int getCurrent(boolean inIdle) {
    return !inIdle ? this.currentFrame : this.currentIdleFrame;
  }
  
  private void toggleType(boolean inIdle) {
    if (!inIdle) {
      if (this.idleAnimation) {
        this.currentFrame = 0;
        this.idleAnimation = false;
        this.moveAnimation = true;
      }
      
      if (this.currentFrame >= this.maxFrames) {
        this.currentFrame = 0;
      }
    } else {
      if (this.moveAnimation) {
        this.currentIdleFrame = 0;
        this.moveAnimation = false;
        this.idleAnimation = true;
      }
      
      if (this.currentIdleFrame >= this.maxIdleFrames) {
        this.currentIdleFrame = 0;
      }
    }
  }
  
  private void animate(CompanionAnimation companionAnimation) {
    CompanionStructure structure = this.getStructureByName(companionAnimation.getName());
    if (structure == null) {
      System.err.println("Failed to find structure: " + companionAnimation.getName());
    }
    if (structure != null) {
      switch (companionAnimation.getMovementType()) {
        case ARM:
          structure.getStand().setRightArmPose(companionAnimation.getAngle());
          break;
        case ARM2:
          structure.getStand().setLeftArmPose(companionAnimation.getAngle());
          break;
        case LEG:
          structure.getStand().setRightLegPose(companionAnimation.getAngle());
          break;
        case LEG2:
          structure.getStand().setLeftLegPose(companionAnimation.getAngle());
          break;
        case HEAD:
          structure.getStand().setHeadPose(companionAnimation.getAngle());
      }
    }
  }
  
  private void increase(boolean inIdle) {
    if (!inIdle) {
      this.currentFrame++;
    } else {
      this.currentIdleFrame++;
    }
  }
  
  private void moveToSlime() {
    this.structures.forEach(structure -> structure.getStand().teleport(structure.getLocation().getFromRelative(this.getBukkitEntity().getLocation())));
  }
  
  protected CompanionStructure buildPart(String name, RelativeLocation relativeLocation, boolean small) {
    CompanionStand stand = new CompanionStand(this, small);
    Location location = relativeLocation.getFromRelative(getBukkitEntity().getLocation());
    stand.setPosition(location.getX(), location.getY(), location.getZ());
    NMS.getInstance().look(stand, location.getYaw(), location.getPitch());
    CompanionStructure structure = new CompanionStructure(name, (ArmorStand) stand.getBukkitEntity(), relativeLocation);
    this.structures.add(structure);
    return structure;
  }
  
  private CompanionStructure getStructureByName(String name) {
    return this.structures.stream().filter(structure -> structure.getName().equals(name)).findFirst().orElse(null);
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
  
  
  protected static class CompanionStand extends EntityArmorStand implements CompanionEntity {
    
    private final CompanionSlime slime;
    
    public CompanionStand(CompanionSlime entity, boolean small) {
      super(entity.world);
      this.slime = entity;
      
      this.setArms(true);
      this.setInvisible(true);
      this.setSmall(small);
      this.setGravity(true);
      this.setBasePlate(false);
    }
    
    @Override
    public void t_() {
      this.ticksLived = 0;
      super.t_();
      if (this.slime == null || this.slime.dead) {
        this.dead = true;
      }
    }
    
    @Override
    public void setCompanionName(String name) {
      super.setCustomName(name);
      super.setCustomNameVisible(!name.isEmpty());
    }
    
    @Override
    public void kill() {
      this.slime.kill();
    }
    
    @Override
    public void die() {
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
      return false;
    }
  }
}
