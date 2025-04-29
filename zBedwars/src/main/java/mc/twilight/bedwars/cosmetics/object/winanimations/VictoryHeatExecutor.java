package mc.twilight.bedwars.cosmetics.object.winanimations;

import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.cosmetics.object.AbstractExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class VictoryHeatExecutor extends AbstractExecutor implements Listener {
  
  public VictoryHeatExecutor(Player player) {
    super(player);
    
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  @Override
  public void tick() {
    Location randomLocation = this.player.getLocation().clone().add(Math.floor(Math.random() * 3.0D), 0, Math.floor(Math.random() * 3.0D));
    
    for (int i = 0; i < 5; i++) {
      randomLocation.getBlock().setType(Material.FIRE);
      randomLocation = this.player.getLocation().clone().add(Math.floor(Math.random() * 3.0D), 0, Math.floor(Math.random() * 3.0D));
    }
  }
  
  @Override
  public void cancel() {
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onEntityCombust(EntityCombustEvent evt) {
    if (evt.getEntity() instanceof Player) {
      if (evt.getEntity().equals(this.player)) {
        evt.setCancelled(true);
      }
    }
  }
}