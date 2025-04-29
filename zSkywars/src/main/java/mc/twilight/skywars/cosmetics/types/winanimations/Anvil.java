package mc.twilight.skywars.cosmetics.types.winanimations;

import mc.twilight.skywars.cosmetics.object.AbstractExecutor;
import mc.twilight.skywars.cosmetics.object.winanimations.AnvilExecutor;
import mc.twilight.skywars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Anvil extends WinAnimation {
  
  public Anvil(ConfigurationSection section) {
    super(section.getLong("id"), "cake", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new AnvilExecutor(player);
  }
}
