package mc.twilight.cosmeticos.cosmetics.object;

import mc.twilight.cosmeticos.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class GadgetCooldown {
  
  private static final Map<UUID, Long> CACHE = new ConcurrentHashMap<>();
  
  static {
    new BukkitRunnable() {
      @Override
      public void run() {
        long current = System.currentTimeMillis();
        CACHE.values().removeIf(aLong -> aLong <= current);
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 200, 200);
  }
  
  public static void setCooldown(Player player, long seconds) {
    CACHE.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
  }
  
  public static boolean hasCooldown(Player player) {
    return CACHE.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis();
  }
  
  public static String getCooldown(Player player) {
    if (hasCooldown(player)) {
      Long value = CACHE.get(player.getUniqueId());
      int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(value - System.currentTimeMillis());
      if (seconds <= 0) {
        return "";
      }
      
      String rSec = seconds % 60 + "s";
      String rMin = seconds / 60 < 1 ? "" : seconds / 60 + "m ";
      return rMin + rSec;
    }
    
    return "";
  }
}
