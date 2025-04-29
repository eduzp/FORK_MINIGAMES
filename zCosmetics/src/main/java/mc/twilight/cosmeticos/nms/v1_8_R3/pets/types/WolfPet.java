package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetWolf;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityAgeablePet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import mc.twilight.cosmeticos.utils.enums.MDyeColor;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class WolfPet extends PetController {
  
  public WolfPet() {
    NMS.getInstance().registerEntity(MEntityWolf.class, "Wolf", 95);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityWolf wolf = new MEntityWolf(pet, settings);
    if (wolf.world.addEntity(wolf, SpawnReason.CUSTOM)) {
      return wolf;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 0.8F)
  private class MEntityWolf extends EntityAgeablePet implements PetWolf {
    
    public MEntityWolf(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
      
      this.datawatcher.a(16, (byte) (0 | 0x4));
      this.datawatcher.a(17, pet.getPetOwner().getUniqueId().toString());
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(18, this.getHealth());
      this.datawatcher.a(19, (byte) 0);
      this.datawatcher.a(20, (byte) 14);
    }
    
    @Override
    public MDyeColor getColor() {
      return MDyeColor.getByDyeData(this.datawatcher.getInt(20));
    }
    
    @Override
    public void setColor(MDyeColor color) {
      this.datawatcher.watch(20, (byte) color.getDyeData());
    }
  }
}
