package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetSkeleton;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityPet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class SkeletonPet extends PetController {
  
  public SkeletonPet() {
    NMS.getInstance().registerEntity(MEntitySkeleton.class, "Skeleton", 51);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntitySkeleton skeleton = new MEntitySkeleton(pet, settings);
    if (skeleton.world.addEntity(skeleton, SpawnReason.CUSTOM)) {
      return skeleton;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 1.9F)
  private class MEntitySkeleton extends EntityPet implements PetSkeleton {
    
    public MEntitySkeleton(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(13, (byte) 0);
    }
    
    @Override
    public void repeatTask() {
      super.repeatTask();
      super.setEquipment(4, CraftItemStack.asNMSCopy(this.pet.getPetOwner().getInventory().getHelmet()));
      super.setEquipment(3, CraftItemStack.asNMSCopy(this.pet.getPetOwner().getInventory().getChestplate()));
      super.setEquipment(2, CraftItemStack.asNMSCopy(this.pet.getPetOwner().getInventory().getLeggings()));
      super.setEquipment(1, CraftItemStack.asNMSCopy(this.pet.getPetOwner().getInventory().getBoots()));
    }
    
    @Override
    public void setWither(boolean wither) {
      this.datawatcher.watch(13, wither ? (byte) 1 : (byte) 0);
    }
  }
}
