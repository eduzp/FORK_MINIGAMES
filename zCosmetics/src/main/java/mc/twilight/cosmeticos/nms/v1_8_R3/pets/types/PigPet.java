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

public class PigPet extends PetController {
  
  public PigPet() {
    NMS.getInstance().registerEntity(MEntityPig.class, "Pig", 90);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityPig pig = new MEntityPig(pet, settings);
    if (pig.world.addEntity(pig, SpawnReason.CUSTOM)) {
      return pig;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.9F, height = 0.9F)
  private class MEntityPig extends EntityAgeablePet {
    
    public MEntityPig(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, (byte) 0);
    }
  }
}
