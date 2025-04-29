package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetVillager;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityAgeablePet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import mc.twilight.cosmeticos.utils.enums.MVillagerProfession;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class VillagerPet extends PetController {
  
  public VillagerPet() {
    NMS.getInstance().registerEntity(MEntityVillager.class, "Villager", 120);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityVillager villager = new MEntityVillager(pet, settings);
    if (villager.world.addEntity(villager, SpawnReason.CUSTOM)) {
      return villager;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 1.8F)
  private class MEntityVillager extends EntityAgeablePet implements PetVillager {
    
    public MEntityVillager(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, 0);
    }
    
    @Override
    public MVillagerProfession getProfession() {
      return MVillagerProfession.getById(this.datawatcher.getInt(16));
    }
    
    @Override
    public void setProfession(MVillagerProfession profession) {
      this.datawatcher.watch(16, profession.getId());
    }
  }
}
