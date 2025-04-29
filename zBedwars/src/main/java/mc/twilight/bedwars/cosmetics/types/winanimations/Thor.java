package mc.twilight.bedwars.cosmetics.types.winanimations;

import mc.twilight.bedwars.cosmetics.object.AbstractExecutor;
import mc.twilight.bedwars.cosmetics.object.winanimations.ThorExecutor;
import mc.twilight.bedwars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Thor extends WinAnimation {
  
  public Thor(ConfigurationSection section) {
    super(section.getLong("id"), "cowboy", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new ThorExecutor(player);
  }
}
