package mc.twilight.bedwars.game.improvements;

import com.google.common.collect.ImmutableList;
import mc.twilight.bedwars.Main;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.BukkitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Upgrades {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("Upgrades");
  private static final KConfig CONFIG = Main.getInstance().getConfig("upgrades");
  private static final List<Upgrade> upgrades = new ArrayList<>();
  
  public static void setupUpgrades() {
    for (String key : CONFIG.getKeys(false)) {
      if (key.equalsIgnoreCase("trap")) {
        continue;
      }
      int slot = CONFIG.getInt(key + ".slot");
      UpgradeType type = UpgradeType.fromName(CONFIG.getString(key + ".type"));
      String icon = CONFIG.getString(key + ".icon");
      if (type == null) {
        LOGGER.log(Level.WARNING, "Invalid UpgradeType on \"" + key + "\" upgrade.");
        continue;
      }
      
      List<Upgrade.UpgradeLevel> levels = new ArrayList<>();
      for (String tier : CONFIG.getSection(key + ".tiers").getKeys(false)) {
        levels.add(new Upgrade.UpgradeLevel(BukkitUtils.deserializeItemStack(CONFIG.getString(key + ".tiers." + tier))));
      }
      
      upgrades.add(new Upgrade(slot, type, icon, levels));
    }
    
  }
  
  public static List<Upgrade> listUpgrades() {
    return ImmutableList.copyOf(upgrades);
  }
  
  public Upgrade getUpgradeByType(UpgradeType type) {
    for (Upgrade upgrade : listUpgrades()) {
      if (upgrade.getType().equals(type)) {
        return upgrade;
      }
    }
    
    return null;
  }
}
