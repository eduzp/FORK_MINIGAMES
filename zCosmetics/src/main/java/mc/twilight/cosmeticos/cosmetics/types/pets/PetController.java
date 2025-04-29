package mc.twilight.cosmeticos.cosmetics.types.pets;

import mc.twilight.cosmeticos.cosmetics.object.Pet;
import mc.twilight.cosmeticos.cosmetics.types.pets.settings.PetSettingsEntry;
import mc.twilight.cosmeticos.nms.interfaces.pets.*;
import mc.twilight.cosmeticos.utils.enums.*;

import java.util.List;

public abstract class PetController {
  
  private PetEntity petEntity;
  
  protected abstract PetEntity createEntity(Pet pet, List<PetSettingsEntry> settings);
  
  public void spawn(Pet pet, List<PetSettingsEntry> settings) {
    this.petEntity = this.createEntity(pet, settings);
    if (this.petEntity != null) {
      for (PetSettingsEntry settingsEntry : settings) {
        if (settingsEntry.getKey().equals("baby")) {
          ((PetAgeable) this.petEntity).setBaby(settingsEntry.getValue().getAsBoolean());
        } else if (settingsEntry.getKey().equals("wither")) {
          ((PetSkeleton) this.petEntity).setWither(settingsEntry.getValue().getAsBoolean());
        } else if (settingsEntry.getKey().equals("sheepcolor")) {
          ((PetSheep) this.petEntity).setColor(MWoolColor.getByWoolData(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("rainbow")) {
          ((PetSheep) this.petEntity).setRainbow(settingsEntry.getValue().getAsBoolean());
        } else if (settingsEntry.getKey().equals("dyecolor")) {
          ((PetWolf) this.petEntity).setColor(MDyeColor.getByDyeData(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("rabbit")) {
          ((PetRabbit) this.petEntity).setType(MRabbitType.getById(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("profession")) {
          ((PetVillager) this.petEntity).setProfession(MVillagerProfession.getById(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("slimesize")) {
          ((PetSlime) this.petEntity).setSize(MSlimeSize.getBySize(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("charged")) {
          ((PetCreeper) this.petEntity).setPowered(settingsEntry.getValue().getAsBoolean());
        } else if (settingsEntry.getKey().equals("villager")) {
          ((PetZombie) this.petEntity).setVillager(settingsEntry.getValue().getAsBoolean());
        } else if (settingsEntry.getKey().equals("horsecolor")) {
          ((PetHorse) this.petEntity).setColor(MHorseColor.getById(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("horsetype")) {
          ((PetHorse) this.petEntity).setType(MHorseType.getById(settingsEntry.getValue().getAsInt()));
        } else if (settingsEntry.getKey().equals("ocelot")) {
          ((PetOcelot) this.petEntity).setType(MOcelotType.getById(settingsEntry.getValue().getAsInt()));
        }
      }
    }
  }
  
  public void remove() {
    if (this.petEntity == null) {
      return;
    }
    
    this.petEntity.kill();
    this.petEntity = null;
  }
  
  public PetEntity getEntity() {
    return this.petEntity;
  }
}
