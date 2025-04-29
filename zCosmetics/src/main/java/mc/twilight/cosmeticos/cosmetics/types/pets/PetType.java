package mc.twilight.cosmeticos.cosmetics.types.pets;

import mc.twilight.cosmeticos.nms.interfaces.PetEntities;
import org.bukkit.entity.EntityType;

public enum PetType {
  ENDERMAN(.7, .5),
  ENDERMITE(.6, .4),
  COW(.7, .4),
  SILVERFISH(.6, .4),
  CHICKEN(.7, .4),
  SKELETON(.7, .45),
  IRON_GOLEM(.7, .45),
  SHEEP(.7, .45),
  WOLF(.7, .4),
  PIG(.7, .4),
  RABBIT(.5, .4),
  VILLAGER(.7, .45),
  SPIDER(.7, .45),
  SLIME(.7, .45),
  CREEPER(.7, .45),
  ZOMBIE(.6, .4),
  SQUID(.7, .45),
  HORSE(.7, .45),
  OCELOT(.7, .4),
  BLAZE(.7, .45),
  BAT(.7, .45);
  
  private final double moveSpeed;
  private final double rideSpeed;
  private EntityType entityType;
  
  PetType(double moveSpeed, double rideSpeed) {
    this.moveSpeed = moveSpeed;
    this.rideSpeed = rideSpeed;
    try {
      this.entityType = EntityType.valueOf(this.name());
    } catch (Exception ex) {
      // não é compatível com essa versão.
    }
  }
  
  public static PetType fromName(String name) {
    for (PetType type : values()) {
      if (type.name().equals(name.toUpperCase())) {
        return type;
      }
    }
    
    throw new IllegalArgumentException("Tipo de pet \"" + name + "\" invalido!");
  }
  
  public PetController createNewEntity() {
    return PetEntities.createForType(entityType);
  }
  
  public double getMoveSpeed() {
    return moveSpeed;
  }
  
  public double getRideSpeed() {
    return rideSpeed;
  }
  
  public EntityType getEntityType() {
    return this.entityType;
  }
  
  public boolean isCompatible() {
    return this.entityType != null && PetEntities.existsForType(this.entityType);
  }
  
  public boolean canFly() {
    if (this == BLAZE) {
      return true;
    } else return this == BAT;
  }
}
