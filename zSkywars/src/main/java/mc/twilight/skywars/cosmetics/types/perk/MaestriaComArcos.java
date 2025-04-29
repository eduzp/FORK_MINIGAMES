package mc.twilight.skywars.cosmetics.types.perk;

import mc.twilight.core.player.Profile;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.api.SWEvent;
import mc.twilight.skywars.cosmetics.types.Perk;
import mc.twilight.skywars.game.AbstractSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MaestriaComArcos extends Perk {
  
  private final int index;
  
  public MaestriaComArcos(int index, String key) {
    super(3, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler
      public void onEntityShootBowEvent(EntityShootBowEvent evt) {
        if (evt.getEntity() instanceof Player) {
          Player player = (Player) evt.getEntity();
          Profile profile = Profile.getProfile(player.getName());
          
          if (profile != null) {
            AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
            if (game != null && !game.isSpectator(player) && game.getMode().getCosmeticIndex() == getIndex() && isSelectedPerk(profile) && has(profile) && canBuy(player)) {
              int percentage = getCurrentLevel(profile).getValue("percentage", int.class, 0);
              if (ThreadLocalRandom.current().nextInt(100) < percentage) {
                player.getInventory().addItem(new ItemStack(Material.ARROW));
                player.updateInventory();
              }
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
