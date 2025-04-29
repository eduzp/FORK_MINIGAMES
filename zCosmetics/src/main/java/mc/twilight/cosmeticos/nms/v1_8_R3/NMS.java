package mc.twilight.cosmeticos.nms.v1_8_R3;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.types.companions.variants.*;
import mc.twilight.cosmeticos.nms.interfaces.CompanionEntities;
import mc.twilight.cosmeticos.nms.interfaces.MorphEntities;
import mc.twilight.cosmeticos.nms.interfaces.PetEntities;
import mc.twilight.cosmeticos.nms.interfaces.balloons.BallonEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.balloons.BallonArmorStand;
import mc.twilight.cosmeticos.nms.v1_8_R3.balloons.BallonBat;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.types.*;
import mc.twilight.cosmeticos.nms.v1_8_R3.gadgets.CustomEntityFirework;
import mc.twilight.cosmeticos.nms.v1_8_R3.morphs.types.ChickenMorph;
import mc.twilight.cosmeticos.nms.v1_8_R3.morphs.types.EndermanMorph;
import mc.twilight.cosmeticos.nms.v1_8_R3.morphs.types.IronGolemMorph;
import mc.twilight.cosmeticos.nms.v1_8_R3.morphs.types.VillagerMorph;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.types.*;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.reflection.Accessors;
import mc.twilight.core.reflection.acessors.FieldAccessor;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class NMS extends mc.twilight.cosmeticos.nms.NMS {
  
  private final FieldAccessor<Map> CLASS_TO_ID;
  private final FieldAccessor<Map> CLASS_TO_NAME;
  private final FieldAccessor<List> PATHFINDERGOAL_B, PATHFINDERGOAL_C;
  
  public NMS() {
    CLASS_TO_ID = Accessors.getField(EntityTypes.class, "f", Map.class);
    CLASS_TO_NAME = Accessors.getField(EntityTypes.class, "d", Map.class);
    PATHFINDERGOAL_B = Accessors.getField(PathfinderGoalSelector.class, 0, List.class);
    PATHFINDERGOAL_C = Accessors.getField(PathfinderGoalSelector.class, 1, List.class);
    
    // COMPANIONS
    this.registerEntity(CompanionSlime.class, "Slime", 55);
    CompanionEntities.setCompanionType(HP8Companion.class, HP8Entity.class);
    CompanionEntities.setCompanionType(DuckCompanion.class, DuckEntity.class);
    CompanionEntities.setCompanionType(PandaCompanion.class, PandaEntity.class);
    CompanionEntities.setCompanionType(GorillaCompanion.class, GorillaEntity.class);
    CompanionEntities.setCompanionType(GiraffeCompanion.class, GiraffeEntity.class);
    CompanionEntities.setCompanionType(R3D3Companion.class, R3D3Entity.class);
    CompanionEntities.setCompanionType(PugCompanion.class, PugEntity.class);
    CompanionEntities.setCompanionType(PenguinCompanion.class, PenguinEntity.class);
    CompanionEntities.setCompanionType(FireDragonCompanion.class, FireDragonEntity.class);
    CompanionEntities.setCompanionType(IceDragonCompanion.class, IceDragonEntity.class);
    CompanionEntities.setCompanionType(MagicDragonCompanion.class, MagicDragonEntity.class);
    CompanionEntities.setCompanionType(MythicDragonCompanion.class, MythicDragonEntity.class);
    CompanionEntities.setCompanionType(PolarBearCompanion.class, PolarBearEntity.class);
    CompanionEntities.setCompanionType(DigletCompanion.class, DigletEntity.class);
    CompanionEntities.setCompanionType(KoalaCompanion.class, KoalaEntity.class);
    CompanionEntities.setCompanionType(LionCompanion.class, LionEntity.class);
    CompanionEntities.setCompanionType(MiniMeCompanion.class, MiniMeEntity.class);
    CompanionEntities.setCompanionType(PerryThePlatipusCompanion.class, PerryThePlatipusEntity.class);
    CompanionEntities.setCompanionType(TurtleCompanion.class, TurtleEntity.class);
    CompanionEntities.setCompanionType(ChimpCompanion.class, ChimpEntity.class);
    
    // BALLOONS
    this.registerEntity(BallonBat.class, "Bat", 65);
    this.registerEntity(BallonArmorStand.class, "ArmorStand", 30);
    
    // MORPHS
    MorphEntities.setPetEntityForType(EntityType.IRON_GOLEM, IronGolemMorph.class);
    MorphEntities.setPetEntityForType(EntityType.VILLAGER, VillagerMorph.class);
    MorphEntities.setPetEntityForType(EntityType.ENDERMAN, EndermanMorph.class);
    MorphEntities.setPetEntityForType(EntityType.CHICKEN, ChickenMorph.class);
  
    // PETS
    PetEntities.setPetEntityForType(EntityType.ENDERMAN, EndermanPet.class);
    PetEntities.setPetEntityForType(EntityType.ENDERMITE, EndermitePet.class);
    PetEntities.setPetEntityForType(EntityType.COW, CowPet.class);
    PetEntities.setPetEntityForType(EntityType.SILVERFISH, SilverfishPet.class);
    PetEntities.setPetEntityForType(EntityType.CHICKEN, ChickenPet.class);
    PetEntities.setPetEntityForType(EntityType.SKELETON, SkeletonPet.class);
    PetEntities.setPetEntityForType(EntityType.IRON_GOLEM, IronGolemPet.class);
    PetEntities.setPetEntityForType(EntityType.SHEEP, SheepPet.class);
    PetEntities.setPetEntityForType(EntityType.WOLF, WolfPet.class);
    PetEntities.setPetEntityForType(EntityType.PIG, PigPet.class);
    PetEntities.setPetEntityForType(EntityType.RABBIT, RabbitPet.class);
    PetEntities.setPetEntityForType(EntityType.VILLAGER, VillagerPet.class);
    PetEntities.setPetEntityForType(EntityType.SPIDER, SpiderPet.class);
    PetEntities.setPetEntityForType(EntityType.SLIME, SlimePet.class);
    PetEntities.setPetEntityForType(EntityType.CREEPER, CreeperPet.class);
    PetEntities.setPetEntityForType(EntityType.ZOMBIE, ZombiePet.class);
    PetEntities.setPetEntityForType(EntityType.SQUID, SquidPet.class);
    PetEntities.setPetEntityForType(EntityType.HORSE, HorsePet.class);
    PetEntities.setPetEntityForType(EntityType.OCELOT, OcelotPet.class);
    PetEntities.setPetEntityForType(EntityType.BLAZE, BlazePet.class);
    PetEntities.setPetEntityForType(EntityType.BAT, BatPet.class);
  }
  
  @Override
  public void spawnFirework(Location location, FireworkEffect effect) {
    CustomEntityFirework entityFirework = new CustomEntityFirework(((CraftWorld) location.getWorld()).getHandle());
    Firework firework = (Firework) entityFirework.getBukkitEntity();
    FireworkMeta meta = firework.getFireworkMeta();
    meta.addEffect(effect);
    firework.setFireworkMeta(meta);
    entityFirework.setPosition(location.getX(), location.getY(), location.getZ());
    entityFirework.setInvisible(true);
    entityFirework.world.addEntity(entityFirework, SpawnReason.CUSTOM);
  }
  
  @Override
  public BallonEntity createBalloonBat(Player owner) {
    BallonBat bat = new BallonBat(owner);
    if (this.addEntity(bat)) {
      return bat;
    }
    
    return null;
  }
  
  @Override
  public BallonEntity createBalloonArmorStand(Player owner, BallonEntity bat, List<String> frames) {
    BallonArmorStand armorstand = new BallonArmorStand(owner, bat, frames);
    if (this.addEntity(armorstand)) {
      return armorstand;
    }
    
    return null;
  }
  
  @Override
  public void registerEntity(Class<?> entityClass, String entityName, int entityId) {
    CLASS_TO_ID.get(null).put(entityClass, entityId);
    CLASS_TO_NAME.get(null).put(entityClass, "zCosmeticos-" + entityName);
  }
  
  @Override
  public void look(Object entity, float yaw, float pitch) {
    if (entity instanceof Entity) {
      entity = ((CraftEntity) entity).getHandle();
    }
    
    yaw = MathUtils.clampYaw(yaw);
    net.minecraft.server.v1_8_R3.Entity handle = (net.minecraft.server.v1_8_R3.Entity) entity;
    handle.yaw = yaw;
    handle.pitch = pitch;
    if (handle instanceof EntityLiving) {
      ((EntityLiving) handle).aJ = yaw;
      if (handle instanceof EntityHuman) {
        ((EntityHuman) handle).aI = yaw;
      }
      ((EntityLiving) handle).aK = yaw;
    }
  }
  
  @Override
  public void clearPathfinderGoal(Object entity) {
    if (entity instanceof Entity) {
      entity = ((CraftEntity) entity).getHandle();
    }
    
    net.minecraft.server.v1_8_R3.Entity handle = (net.minecraft.server.v1_8_R3.Entity) entity;
    if (handle instanceof EntityInsentient) {
      EntityInsentient entityInsentient = (EntityInsentient) handle;
      PATHFINDERGOAL_B.get(entityInsentient.goalSelector).clear();
      PATHFINDERGOAL_C.get(entityInsentient.targetSelector).clear();
    }
  }
  
  @Override
  public boolean addEntity(List<Object> entities) {
    return this.addEntity(entities.toArray());
  }
  
  @Override
  public boolean addEntity(Object... entities) {
    boolean bol = true;
    try {
      for (Object entity : entities) {
        net.minecraft.server.v1_8_R3.Entity handle = (net.minecraft.server.v1_8_R3.Entity) entity;
        handle.getBukkitEntity().setMetadata("ZCOSMETICOS_ENTITY", new FixedMetadataValue(Main.getInstance(), true));
        bol = handle.world.addEntity(handle, SpawnReason.CUSTOM);
      }
      
      return bol;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }
  
  @Override
  public Object getHandle(Entity entity) {
    return ((CraftEntity) entity).getHandle();
  }
}
