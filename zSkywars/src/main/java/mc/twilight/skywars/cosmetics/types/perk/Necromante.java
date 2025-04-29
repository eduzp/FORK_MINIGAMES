package mc.twilight.skywars.cosmetics.types.perk;

import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.api.SWEvent;
import mc.twilight.skywars.api.event.player.SWPlayerDeathEvent;
import mc.twilight.skywars.cosmetics.types.Perk;
import mc.twilight.skywars.game.AbstractSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Necromante extends Perk {
  
  private final int index;
  
  public Necromante(int index, String key) {
    super(16, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    this.register();
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
  
  public void handleEvent(SWEvent evt2) {
    if (evt2 instanceof SWPlayerDeathEvent) {
      SWPlayerDeathEvent evt = (SWPlayerDeathEvent) evt2;
      AbstractSkyWars game = (AbstractSkyWars) evt.getGame();
      Profile profile = evt.getProfile();
      Player player = profile.getPlayer();
      if (game.getMode().getCosmeticIndex() == this.getIndex() && isSelectedPerk(profile) && this.has(profile) && this.canBuy(player)) {
        Location location = player.getLocation();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
          Zombie entity = player.getWorld().spawn(location, Zombie.class);
          entity.setBaby(false);
          entity.setNoDamageTicks(20 * 3);
          entity.setMetadata("OWNER", new FixedMetadataValue(Main.getInstance(), player.getName()));
          entity.setCustomName("§cZumbi de §7" + player.getName());
          entity.setVillager(false);
        });
      }
    }
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return Collections.singletonList(SWPlayerDeathEvent.class);
  }
  
  @EventHandler
  public void onEntityCombust(EntityCombustEvent evt) {
    if (evt.getEntity() instanceof Zombie) {
      evt.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onEntityTarget(EntityTargetEvent evt) {
    if (evt.getTarget() instanceof Player && evt.getEntity() instanceof Zombie) {
      Player player = (Player) evt.getTarget();
      Profile profile = Profile.getProfile(player.getName());
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null && game.isSpectator(player)) {
        evt.setCancelled(true);
      }
    }
  }
}