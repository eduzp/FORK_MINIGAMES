package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityAgeablePet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class ChickenPet extends PetController {
  
  public ChickenPet() {
    NMS.getInstance().registerEntity(MEntityChicken.class, "Chicken", 93);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityChicken chicken = new MEntityChicken(pet, settings);
    if (chicken.world.addEntity(chicken, SpawnReason.CUSTOM)) {
      return chicken;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.3F, height = 0.7F)
  private class MEntityChicken extends EntityAgeablePet {
    
    public MEntityChicken(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
  }
}
