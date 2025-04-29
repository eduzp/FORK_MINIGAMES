package mc.twilight.skywars.cosmetics.types.winanimations;

import mc.twilight.skywars.cosmetics.object.AbstractExecutor;
import mc.twilight.skywars.cosmetics.object.winanimations.WitherExecutor;
import mc.twilight.skywars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Wither extends WinAnimation {
  
  public Wither(ConfigurationSection section) {
    super(section.getLong("id"), "wither", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new WitherExecutor(player);
  }
}
