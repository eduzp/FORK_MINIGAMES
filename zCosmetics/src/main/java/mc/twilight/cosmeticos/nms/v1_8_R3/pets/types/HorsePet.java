package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetHorse;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityAgeablePet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import mc.twilight.cosmeticos.utils.enums.MHorseColor;
import mc.twilight.cosmeticos.utils.enums.MHorseType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class HorsePet extends PetController {
  
  public HorsePet() {
    NMS.getInstance().registerEntity(MEntityHorse.class, "EntityHorse", 100);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityHorse horse = new MEntityHorse(pet, settings);
    if (horse.world.addEntity(horse, SpawnReason.CUSTOM)) {
      return horse;
    }
    
    return null;
  }
  
  @EntitySize(width = 1.4F, height = 1.6F)
  private class MEntityHorse extends EntityAgeablePet implements PetHorse {
    
    public MEntityHorse(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, 0);
      this.datawatcher.a(19, (byte) 0);
      this.datawatcher.a(20, 0);
      this.datawatcher.a(21, "");
      this.datawatcher.a(22, 0);
    }
    
    @Override
    public MHorseColor getColor() {
      return MHorseColor.getById(this.datawatcher.getInt(20));
    }
    
    @Override
    public void setColor(MHorseColor color) {
      this.datawatcher.watch(20, color.getId());
    }
    
    @Override
    public MHorseType getType() {
      return MHorseType.getById(this.datawatcher.getInt(19));
    }
    
    @Override
    public void setType(MHorseType type) {
      this.datawatcher.watch(19, (byte) type.getId());
    }
  }
}
