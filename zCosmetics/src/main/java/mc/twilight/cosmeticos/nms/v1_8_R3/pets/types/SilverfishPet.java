package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityPet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class SilverfishPet extends PetController {
  
  public SilverfishPet() {
    NMS.getInstance().registerEntity(MEntitySilverfish.class, "Silverfish", 60);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntitySilverfish silverfish = new MEntitySilverfish(pet, settings);
    if (silverfish.world.addEntity(silverfish, SpawnReason.CUSTOM)) {
      return silverfish;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.3F, height = 0.7F)
  private class MEntitySilverfish extends EntityPet {
    
    public MEntitySilverfish(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
  }
}
