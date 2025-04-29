package mc.twilight.cosmeticos.nms.v1_8_R3.morphs.types;

import mc.twilight.cosmeticos.cosmetics.object.Morph;
import mc.twilight.cosmeticos.cosmetics.types.morphs.MorphController;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.morphs.MorphEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.morphs.EntityAgeableMorph;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ChickenMorph extends MorphController {
  
  public ChickenMorph() {
    NMS.getInstance().registerEntity(KEntityChicken.class, "Chicken", 93);
  }
  
  @Override
  protected MorphEntity createEntity(Morph pet) {
    KEntityChicken chicken = new KEntityChicken(pet);
    if (chicken.world.addEntity(chicken, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
      return chicken;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.3F, height = 0.7F)
  private class KEntityChicken extends EntityAgeableMorph {
    
    public KEntityChicken(Morph pet) {
      super(pet);
    }
  }
}
