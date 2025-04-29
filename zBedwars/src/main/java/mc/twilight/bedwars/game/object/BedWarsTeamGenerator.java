package mc.twilight.bedwars.game.object;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static mc.twilight.bedwars.game.improvements.UpgradeType.IRON_FORGE;

public class BedWarsTeamGenerator {
  
  private BedWarsTeam team;
  private double iron, gold, emerald;
  private String serialized;
  private boolean tick = false;
  
  
  public BedWarsTeamGenerator(BedWarsTeam team, String serialized) {
    this.team = team;
    this.iron = Language.options$team_generator$iron$countdown;
    this.gold = Language.options$team_generator$gold$countdown;
    this.emerald = Language.options$team_generator$emerald$countdown;
    this.serialized = serialized;
  }
  
  public void update() {
    this.update(true);
  }
  
  
  public void update(boolean upgrade) {
    
    Location location = this.getLocation();
    if (this.iron == 0.0) {
      this.iron = Language.options$team_generator$iron$countdown;
      if (PlayerUtils.getAmountOfItem(Material.IRON_INGOT, location) < 48) {
        Item i = location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT));
        i.setPickupDelay(0);
        i.setVelocity(new Vector());
      }
    } else {
      this.iron -= 0.5;
    }
    
    if (this.gold == 0.0) {
      this.gold = Language.options$team_generator$gold$countdown;
      if (PlayerUtils.getAmountOfItem(Material.GOLD_INGOT, location) < 32) {
        Item i = location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
        i.setPickupDelay(0);
        i.setVelocity(new Vector());
      }
    } else {
      this.gold -= 0.5;
    }
    
    int level = team.getTier(IRON_FORGE);
    if (level > 2) {
      if (this.emerald == 0.0) {
        this.emerald = Language.options$team_generator$emerald$countdown;
        if (PlayerUtils.getAmountOfItem(Material.EMERALD, location) < 4) {
          Item i = location.getWorld().dropItem(location, new ItemStack(Material.EMERALD));
          i.setPickupDelay(0);
          i.setVelocity(new Vector());
        }
      } else {
        this.emerald -= 0.5;
      }
    }
    
    if (upgrade && level > 0) {
      if (level == 1) {
        // 50% uma vez sim outra não
        this.tick = !tick;
        if (this.tick) {
          this.update(false);
        }
      } else if (level == 2 || level == 3) {
        // 100%
        this.update(false);
      } else if (level == 4) {
        // 200%
        this.update(false);
        this.update(false);
      }
    }
  }
  
  public void reset() {
    this.iron = Language.options$team_generator$iron$countdown;
    this.gold = Language.options$team_generator$gold$countdown;
    this.emerald = Language.options$team_generator$emerald$countdown;
  }
  
  public Location getLocation() {
    return BukkitUtils.deserializeLocation(this.serialized).add(0.0D, 1.0D, 0.0D);
  }
}
