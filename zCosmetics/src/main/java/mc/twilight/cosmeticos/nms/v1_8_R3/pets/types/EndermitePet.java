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

public class EndermitePet extends PetController {
  
  public EndermitePet() {
    NMS.getInstance().registerEntity(MEntityEndermite.class, "Endermite", 67);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityEndermite endermite = new MEntityEndermite(pet, settings);
    if (endermite.world.addEntity(endermite, SpawnReason.CUSTOM)) {
      return endermite;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.4F, height = 0.3F)
  private class MEntityEndermite extends EntityPet {
    
    public MEntityEndermite(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
  }
}
