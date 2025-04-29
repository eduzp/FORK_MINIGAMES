package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.types.ParticleCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Particle extends BukkitRunnable {
  
  private Player owner;
  private int initFrame;
  private ParticleCosmetic cosmetic;
  
  public Particle(CUser owner, ParticleCosmetic cosmetic) {
    this.owner = owner.getPlayer();
    this.cosmetic = cosmetic;
    this.initFrame = 2;
    this.runTaskTimer(Main.getInstance(), 0, 5);
  }
  
  @Override
  public synchronized void cancel() throws IllegalStateException {
    super.cancel();
    this.owner.getInventory().setHelmet(null);
    this.owner = null;
    this.initFrame = 2;
    this.cosmetic = null;
  }
  
  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      this.cosmetic.getParticleType().display(0.1F, +Float.parseFloat(String.valueOf(initFrame)), 0.1F, 0.0F, 1, this.owner.getLocation(), player);
      initFrame = (int) (initFrame - 0.3F);
      this.cosmetic.getParticleType().display(0.2F, +Float.parseFloat(String.valueOf(initFrame)), 0.5F, 0.0F, 1, this.owner.getLocation(), player);
      initFrame = (int) (initFrame - 0.3F);
      this.cosmetic.getParticleType().display(-0.2F, +Float.parseFloat(String.valueOf(initFrame)), -0.5F, 0.0F, 1, this.owner.getLocation(), player);
    });
    
    if (initFrame < 1) {
      initFrame = 2;
    }
    initFrame--;
  }
  
}
