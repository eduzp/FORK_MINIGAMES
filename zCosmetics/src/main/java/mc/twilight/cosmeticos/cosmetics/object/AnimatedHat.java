package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.types.AnimatedHatCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimatedHat extends BukkitRunnable {
  
  private Player owner;
  private AnimatedHatCosmetic.AnimatedHatSlider slider;
  private AnimatedHatCosmetic cosmetic;
  
  public AnimatedHat(CUser owner, AnimatedHatCosmetic cosmetic) {
    this.owner = owner.getPlayer();
    this.cosmetic = cosmetic;
    this.slider = new AnimatedHatCosmetic.AnimatedHatSlider(cosmetic);
    this.runTaskTimer(Main.getInstance(), 0, cosmetic.getTicksPerFrame());
  }
  
  @Override
  public synchronized void cancel() throws IllegalStateException {
    super.cancel();
    this.owner.getInventory().setHelmet(null);
    this.owner = null;
    this.slider = null;
    this.cosmetic = null;
  }
  
  @Override
  public void run() {
    String skinValue = this.slider.next();
    if (skinValue != null) {
      this.owner.getInventory()
          .setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>" + this.cosmetic.getRarity().getColor() + this.cosmetic.getName() + " : skin>" + skinValue));
    }
  }
}
