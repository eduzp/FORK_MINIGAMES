package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetRabbit;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityAgeablePet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import mc.twilight.cosmeticos.utils.enums.MRabbitType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RabbitPet extends PetController {
  
  public RabbitPet() {
    NMS.getInstance().registerEntity(MEntityRabbit.class, "Rabbit", 101);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityRabbit rabbit = new MEntityRabbit(pet, settings);
    if (rabbit.world.addEntity(rabbit, SpawnReason.CUSTOM)) {
      return rabbit;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.6F, height = 0.7F)
  private class MEntityRabbit extends EntityAgeablePet implements PetRabbit {
    
    private int jumpDelay;
    
    public MEntityRabbit(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
      this.jumpDelay = ThreadLocalRandom.current().nextInt(15) + 10;
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(18, (byte) 0);
    }
    
    @Override
    public MRabbitType getType() {
      return MRabbitType.getById(this.datawatcher.getInt(18));
    }
    
    @Override
    public void setType(MRabbitType type) {
      this.datawatcher.watch(18, (byte) type.getId());
    }
    
    @Override
    public void repeatTask() {
      super.repeatTask();
      if (this.onGround && --jumpDelay <= 0 && this.passenger == null && !this.getBukkitEntity().isInsideVehicle()) {
        this.jumpDelay = ThreadLocalRandom.current().nextInt(15) + 10;
        getControllerJump().a();
      }
    }
  }
}
