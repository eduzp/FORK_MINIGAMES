package mc.twilight.skywars.cosmetics.types.perk;

import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.api.SWEvent;
import mc.twilight.skywars.cosmetics.types.Perk;
import mc.twilight.skywars.game.AbstractSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class Preparativos extends Perk {
  
  private final int index;
  
  public Preparativos(int index, String key) {
    super(1, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler(priority = EventPriority.HIGHEST)
      public void onFoodLevelChange(FoodLevelChangeEvent evt) {
        if (!evt.isCancelled() && evt.getEntity() instanceof Player) {
          Player player = (Player) evt.getEntity();
          Profile profile = Profile.getProfile(player.getName());
          
          if (profile != null) {
            AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
            if (game != null && !game.isSpectator(player) && game.getMode().getCosmeticIndex() == getIndex() && isSelectedPerk(profile) && has(profile) && canBuy(player)) {
              evt.setCancelled(true);
            }
          }
        }
      }
    }, Main.getInstance());
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
  
  @Override
  public void handleEvent(SWEvent evt) {
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return null;
  }
}
