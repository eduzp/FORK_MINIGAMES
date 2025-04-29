package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.types.MorphCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.morphs.MorphController;
import mc.twilight.cosmeticos.cosmetics.types.morphs.MorphType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.role.Role;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Morph extends BukkitRunnable {
  
  private Player owner;
  private MorphController petController;
  private MorphCosmetic cosmetic;
  
  public Morph(CUser owner, MorphCosmetic cosmetic) {
    this.owner = owner.getPlayer();
    this.cosmetic = cosmetic;
    this.petController = this.cosmetic.getEntityType().createNewEntity();
    this.petController.spawn(this);
    this.petController.getEntity().setPetCustomName(Role.getPrefixed(this.owner.getName()));
    this.runTaskTimer(Main.getInstance(), 0, 20);
  }
  
  @Override
  public synchronized void cancel() throws IllegalStateException {
    super.cancel();
    if (this.owner != null) {
      for (Player player : this.owner.getWorld().getPlayers()) {
        player.showPlayer(this.owner);
      }
      this.owner.spigot().setCollidesWithEntities(true);
      if (this.owner.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
        this.owner.removePotionEffect(PotionEffectType.INVISIBILITY);
      }
    }
    if (this.petController != null) {
      this.petController.remove();
    }
    this.petController = null;
    this.owner = null;
    this.cosmetic = null;
  }
  
  @Override
  public void run() {
    for (Player player : this.owner.getWorld().getPlayers()) {
      player.hidePlayer(this.owner);
    }
    
    this.owner.spigot().setCollidesWithEntities(false);
    this.owner.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
    NMS.sendActionBar(this.owner, "§cSua mutação está ativada!");
  }
  
  public boolean update() {
    if (this.getMorphOwner() == null || !this.getMorphOwner().isOnline()) {
      this.despawn();
      return false;
    }
    
    return true;
  }
  
  public void respawn() {
    this.petController.remove();
    this.petController.spawn(this);
  }
  
  public void despawn() {
    this.cancel();
  }
  
  public Player getMorphOwner() {
    return this.owner;
  }
  
  public MorphType getPetType() {
    return this.cosmetic.getEntityType();
  }
  
  public MorphController getPetController() {
    return this.petController;
  }
}
