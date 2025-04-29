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

public class BlazePet extends PetController {
  
  public BlazePet() {
    NMS.getInstance().registerEntity(MEntityBlaze.class, "Blaze", 61);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityBlaze blaze = new MEntityBlaze(pet, settings);
    if (blaze.world.addEntity(blaze, SpawnReason.CUSTOM)) {
      return blaze;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 1.7F)
  private class MEntityBlaze extends EntityPet {
    
    public MEntityBlaze(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, (byte) 0);
    }
  }
}
