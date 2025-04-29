package mc.twilight.cosmeticos.nms.v1_8_R3.pets.types;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.interfaces.pets.PetEntity;
import mc.twilight.cosmeticos.nms.v1_8_R3.pets.EntityPet;
import mc.twilight.cosmeticos.utils.entity.EntitySize;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

public class BatPet extends PetController {
  
  public BatPet() {
    NMS.getInstance().registerEntity(MEntityBat.class, "Bat", 65);
  }
  
  @Override
  protected PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings) {
    MEntityBat bat = new MEntityBat(pet, settings);
    if (bat.world.addEntity(bat, SpawnReason.CUSTOM)) {
      return bat;
    }
    
    return null;
  }
  
  @EntitySize(width = 0.5F, height = 0.9F)
  private class MEntityBat extends EntityPet {
    
    public MEntityBat(Pet pet, List<PetSettingsEntry> settings) {
      super(pet, settings);
    }
    
    @Override
    protected void h() {
      super.h();
      this.datawatcher.a(16, (byte) 0);
    }
    
    public boolean isHanging() {
      return (this.datawatcher.getByte(16) & 0x1) != 0;
    }
    
    @Override
    public void repeatTask() {
      super.repeatTask();
      super.repeatTask();
      if (isHanging()) {
        this.motX = (this.motY = this.motZ = 0.0D);
        this.locY = (MathHelper.floor(this.locY) + 1.0D - this.length);
      } else {
        this.motY *= 0.6000004238418579D;
      }
    }
  }
}
