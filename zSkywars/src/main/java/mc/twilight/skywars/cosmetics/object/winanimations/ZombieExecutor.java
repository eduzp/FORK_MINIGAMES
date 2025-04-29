package mc.twilight.skywars.cosmetics.object.winanimations;

import mc.twilight.skywars.Main;
import mc.twilight.skywars.cosmetics.object.AbstractExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.ArrayList;

public class ZombieExecutor extends AbstractExecutor implements Listener {
  
  private final ArrayList<Entity> zombies;
  
  public ZombieExecutor(Player player) {
    super(player);
    
    zombies = new ArrayList<>();
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  @Override
  public void tick() {
    Location randomLocation = this.player.getLocation().clone().add(Math.floor(Math.random() * 5.0D), 0, Math.floor(Math.random() * 5.0D));
    Zombie entity = this.player.getWorld().spawn(randomLocation, Zombie.class);
    entity.setNoDamageTicks(Integer.MAX_VALUE);
    entity.setBaby(false);
    entity.setCustomName("§cVítima");
    entity.setVillager(false);
    zombies.add(entity);
  }
  
  @Override
  public void cancel() {
    zombies.forEach(Entity::remove);
    zombies.clear();
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onEntityCombust(EntityCombustEvent event) {
    if (event.getEntity() instanceof Zombie) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onEntityTarget(EntityTargetEvent evt) {
    if (evt.getTarget() instanceof Player) {
      evt.setCancelled(true);
    }
  }
}
