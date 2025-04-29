package mc.twilight.skywars.cosmetics.types.perk;

import mc.twilight.core.player.Profile;
import mc.twilight.skywars.api.SWEvent;
import mc.twilight.skywars.api.event.player.SWPlayerDeathEvent;
import mc.twilight.skywars.cosmetics.types.Perk;
import mc.twilight.skywars.game.AbstractSkyWars;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vinganca extends Perk {
  
  private final int index;
  
  public Vinganca(int index, String key) {
    super(9, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
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
      if (evt.hasKiller()) {
        AbstractSkyWars game = (AbstractSkyWars) evt.getGame();
        Profile profile = evt.getProfile();
        
        Player player = profile.getPlayer();
        if (!game.isSpectator(player) && game.getMode().getCosmeticIndex() == this.getIndex() && isSelectedPerk(profile) && this.has(profile) && this.canBuy(player)) {
          // criar explosão
          player.getWorld().createExplosion(player.getLocation(), 3.0F);
        }
      }
    }
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return Collections.singletonList(SWPlayerDeathEvent.class);
  }
}
