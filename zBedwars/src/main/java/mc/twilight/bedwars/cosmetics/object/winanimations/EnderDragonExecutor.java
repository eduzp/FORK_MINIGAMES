package mc.twilight.bedwars.cosmetics.object.winanimations;

import mc.twilight.bedwars.cosmetics.object.AbstractExecutor;
import mc.twilight.bedwars.nms.NMS;
import org.bukkit.entity.Player;

public class EnderDragonExecutor extends AbstractExecutor {
  
  public EnderDragonExecutor(Player player) {
    super(player);
    NMS.createMountableEnderDragon(player);
  }
}

