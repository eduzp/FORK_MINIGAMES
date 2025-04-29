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

public class EndermanPet extends PetController {
  
  public EndermanPet() {
    NMS.getInstance().registerEntity(MEntityEnderman.class, "Enderman", 58);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityEnderman enderman = new MEntityEnderman(pet, settings);
    if (enderman.world.addEntity(enderman, SpawnReason.CUSTOM)) {
      return enderman;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 2.9F)
  private class MEntityEnderman extends EntityPet {
    
    public MEntityEnderman(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, Short.valueOf((short) 0));
      this.datawatcher.a(17, Byte.valueOf((byte) 0));
      this.datawatcher.a(18, Byte.valueOf((byte) 0));
    }
  }
}
