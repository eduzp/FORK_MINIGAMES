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

public class SquidPet extends PetController {
  
  public SquidPet() {
    NMS.getInstance().registerEntity(MEntitySquid.class, "Squid", 94);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntitySquid squid = new MEntitySquid(pet, settings);
    if (squid.world.addEntity(squid, SpawnReason.CUSTOM)) {
      return squid;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.95F, height = 0.95F)
  private class MEntitySquid extends EntityPet {
    
    public MEntitySquid(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
  }
}
