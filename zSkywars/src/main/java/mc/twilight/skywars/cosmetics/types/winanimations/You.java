package mc.twilight.skywars.cosmetics.types.winanimations;

import mc.twilight.skywars.cosmetics.object.AbstractExecutor;
import mc.twilight.skywars.cosmetics.object.winanimations.YouExecutor;
import mc.twilight.skywars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class You extends WinAnimation {
  
  public You(ConfigurationSection section) {
    super(section.getLong("id"), "cowboy", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new YouExecutor(player);
  }
}
