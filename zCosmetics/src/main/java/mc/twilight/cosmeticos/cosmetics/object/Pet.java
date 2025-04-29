package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.cosmetics.types.PetCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetController;
import mc.twilight.cosmeticos.cosmetics.types.pets.PetType;
import mc.twilight.cosmeticos.hook.player.CUser;
import org.bukkit.entity.Player;

public class Pet {
  
  private CUser user;
  private Player owner;
  private final PetCosmetic cosmetic;
  private PetController petController;
  
  public Pet(CUser owner, PetCosmetic cosmetic) {
    this.user = owner;
    this.owner = owner.getPlayer();
    this.cosmetic = cosmetic;
    this.petController = this.cosmetic.getPetType().createNewEntity();
    this.petController.spawn(this, owner.getPetSettings(this.cosmetic.getPetType()));
  }
  
  public boolean update() {
    if (this.getPetOwner() == null || !this.getPetOwner().isOnline()) {
      this.despawn();
      return false;
    }
    
    return true;
  }
  
  public void respawn() {
    this.petController.remove();
    this.petController.spawn(this, this.user.getPetSettings(this.cosmetic.getPetType()));
  }
  
  public void despawn() {
    this.user = null;
    this.owner = null;
    if (this.petController != null) {
      this.petController.remove();
    }
    this.petController = null;
  }
  
  public Player getPetOwner() {
    return this.owner;
  }
  
  public PetType getPetType() {
    return this.cosmetic.getPetType();
  }
  
  public PetController getPetController() {
    return this.petController;
  }
}
