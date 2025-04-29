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

public class CowPet extends PetController {
  
  public CowPet() {
    NMS.getInstance().registerEntity(MEntityCow.class, "Cow", 92);
    NMS.getInstance().registerEntity(MEntityMushroomCow.class, "MushroomCow", 96);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    PetSettingsEntry mushroomEntry = settings.stream().filter(settingsEntry -> settingsEntry.getKey().equals("mushroom")).findFirst().get();
    EntityAgeablePet cow = mushroomEntry.getValue().getAsBoolean() ? new MEntityMushroomCow(pet, settings) : new MEntityCow(pet, settings);
    if (cow.world.addEntity(cow, SpawnReason.CUSTOM)) {
      return cow;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.9F, height = 1.3F)
  private class MEntityCow extends EntityAgeablePet {
    
    public MEntityCow(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
  }
  
  @EntitySize(width = 0.9F, height = 1.3F)
  private class MEntityMushroomCow extends EntityAgeablePet {
    
    public MEntityMushroomCow(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
  }
}
