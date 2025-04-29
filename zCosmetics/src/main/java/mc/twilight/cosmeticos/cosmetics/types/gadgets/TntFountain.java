package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TntFountain extends GadgetsCosmetic {
  
  private final List<Entity> TNTS = new ArrayList<>();
  
  public TntFountain() {
    super("Fonte Explosiva", EnumRarity.EPICO, "TNT : 1 : nome>Fonte Explosiva : desc>&7Divirta-se com chuva de TNT's!",
        "TNT : 1 : nome>&6Engenhoca: &aFonte Explosiva!");
    
    this.registerListener();
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    GadgetCooldown.setCooldown(player, 25);
    
    new BukkitRunnable() {
      private int time = 10;
      
      @Override
      public void run() {
        if (time == -20 || user.getProfile() == null || user.getProfile().playingGame()) {
          TNTS.forEach(Entity::remove);
          TNTS.clear();
          cancel();
          return;
        }
        
        if (time > 0) {
          if (player.isOnline()) {
            TNTPrimed entityTnt = player.getLocation().getWorld().spawn(player.getLocation().clone().add(0.5, 1.7, 0.5), TNTPrimed.class);
            
            entityTnt.setVelocity(new Vector(0, 0.5, 0));
            EnumSound.FIRE_IGNITE.play(player, player.getLocation(), 1.0F, 1.0F);
            TntFountain.this.TNTS.add(entityTnt);
          }
        }
        
        --time;
      }
    }.runTaskTimer(Main.getInstance(), 0, 12L);
  }
}
