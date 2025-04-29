package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetCreeper;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityPet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class CreeperPet extends PetController {
  
  public CreeperPet() {
    NMS.getInstance().registerEntity(MEntityCreeper.class, "Creeper", 50);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityCreeper creeper = new MEntityCreeper(pet, settings);
    if (creeper.world.addEntity(creeper, SpawnReason.CUSTOM)) {
      return creeper;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 1.9F)
  private class MEntityCreeper extends EntityPet implements PetCreeper {
    
    public MEntityCreeper(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, (byte) -1);
      this.datawatcher.a(17, (byte) 0);
      this.datawatcher.a(18, (byte) 0);
    }
    
    @Override
    public void setPowered(boolean powered) {
      this.datawatcher.watch(17, powered ? (byte) 1 : (byte) 0);
    }
  }
}
