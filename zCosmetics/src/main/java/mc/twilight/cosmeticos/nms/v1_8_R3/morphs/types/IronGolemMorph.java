package mc.twilight.cosmeticos.nms.v1_8_R3.morphs.types;

import mc.twilight.cosmeticos.cosmetics.object.Morph;
import mc.twilight.cosmeticos.cosmetics.types.morphs.MorphController;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.morphs.MorphEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.morphs.EntityMorph;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class IronGolemMorph extends MorphController {
  
  public IronGolemMorph() {
    NMS.getInstance().registerEntity(MEntityIronGolem.class, "VillagerGolem", 99);
  }
  
  @Override
  protected MorphEntity createEntity(Morph pet) {
    MEntityIronGolem irongolem = new MEntityIronGolem(pet);
    if (irongolem.world.addEntity(irongolem, SpawnReason.CUSTOM)) {
      return irongolem;
    }
    
    return null;
  }
  
  @EntitySize(width = 1.4F, height = 2.9F)
  private class MEntityIronGolem extends EntityMorph {
    
    public MEntityIronGolem(Morph pet) {
      super(pet);
    }
  }
}
