package mc.twilight.skywars.cosmetics.object.winanimations;

import mc.twilight.skywars.cosmetics.object.AbstractExecutor;
import mc.twilight.skywars.nms.NMS;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class EnderDragonExecutor extends AbstractExecutor {
  
  public EnderDragonExecutor(Player player) {
    super(player);
    NMS.createMountableEnderDragon(player);
  }
  
  @Override
  public void tick() {
    player.launchProjectile(Fireball.class);
  }
}