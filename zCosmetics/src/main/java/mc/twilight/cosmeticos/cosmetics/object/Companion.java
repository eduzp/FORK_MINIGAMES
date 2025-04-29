package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.cosmeticos.nms.interfaces.CompanionEntities;
import mc.twilight.cosmeticos.nms.interfaces.companions.CompanionEntity;
import org.bukkit.entity.Player;

public class Companion {
  
  private CUser user;
  private Player owner;
  private final CompanionCosmetic cosmetic;
  private CompanionEntity entity;
  
  public Companion(CUser owner, CompanionCosmetic cosmetic) {
    this.user = owner;
    this.owner = owner.getPlayer();
    this.cosmetic = cosmetic;
    this.entity = this.spawn();
  }
  
  public void changeName(String name) {
    this.entity.setCompanionName(name);
  }
  
  public CompanionEntity spawn() {
    return CompanionEntities.createForType(this.cosmetic.getClass(), this);
  }
  
  public boolean update() {
    if (this.getCompanionOwner() == null || !this.getCompanionOwner().isOnline()) {
      this.despawn();
      return false;
    }
    
    return true;
  }
  
  public void despawn() {
    this.user = null;
    this.owner = null;
    if (this.entity != null) {
      this.entity.kill();
    }
    this.entity = null;
  }
  
  public CUser getClient() {
    return this.user;
  }
  
  public Player getCompanionOwner() {
    return this.owner;
  }
  
  public CompanionCosmetic getCosmetic() {
    return this.cosmetic;
  }
}
