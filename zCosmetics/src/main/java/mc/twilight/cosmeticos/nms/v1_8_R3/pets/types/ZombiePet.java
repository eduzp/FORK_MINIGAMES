package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetZombie;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityAgeablePet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class ZombiePet extends PetController {
  
  public ZombiePet() {
    NMS.getInstance().registerEntity(MEntityZombie.class, "Zombie", 54);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityZombie zombie = new MEntityZombie(pet, settings);
    if (zombie.world.addEntity(zombie, SpawnReason.CUSTOM)) {
      return zombie;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 1.8F)
  private class MEntityZombie extends EntityAgeablePet implements PetZombie {
    
    public MEntityZombie(Pet pet, List<PetSettingsEntry> settings) {
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
    public void setBaby(boolean baby) {
      this.datawatcher.watch(12, baby ? (byte) 1 : (byte) 0);
    }
    
    @Override
    public void setVillager(boolean villager) {
      this.datawatcher.watch(13, villager ? (byte) 1 : (byte) 0);
    }
  }
}
