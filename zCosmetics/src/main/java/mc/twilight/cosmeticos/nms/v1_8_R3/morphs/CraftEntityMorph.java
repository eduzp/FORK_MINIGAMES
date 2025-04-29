package mc.twilight.cosmeticos.nms.v1_8_R3.morphs;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.nms.interfaces.morphs.MorphEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

public class CraftEntityMorph extends CraftCreature implements MorphEntity {
  
  public CraftEntityMorph(EntityMorph entityPet) {
    super(entityPet.world.getServer(), entityPet);
    this.setMetadata("ZCOSMETICOS_MORPH", new FixedMetadataValue(Main.getInstance(), true));
  }
  
  @Override
  public void remove() {
  }
  
  @Override
  public void setPetCustomName(String customName) {
    this.getHandle().setPetCustomName(customName);
  }
  
  @Override
  public void kill() {
    this.getHandle().kill();
  }
  
  @Override
  public boolean isRiding() {
    return this.getHandle().isRiding();
  }
  
  @Override
  public Entity getBukkitEntity() {
    return this;
  }
  
  @Override
  public EntityMorph getHandle() {
    return (EntityMorph) super.getHandle();
  }
  
  @Override
  public EntityType getType() {
    return EntityType.UNKNOWN;
  }
}
