package mc.twilight.cosmeticos.nms.v1_8_R3.pets;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetAgeable;

import java.util.List;

public class EntityAgeablePet extends EntityPet implements PetAgeable {
  
  public EntityAgeablePet(Pet pet, List<PetSettingsEntry> settings) {
    super(pet, settings);
  }
  
  @Override
  protected void h() {
    super.h();
    this.datawatcher.a(12, (byte) 0);
  }
  
  @Override
  public void setBaby(boolean baby) {
    this.datawatcher.watch(12, baby ? (byte) -1 : 0);
    this.resetEntitySize(baby);
  }
}
