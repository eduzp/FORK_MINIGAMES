package mc.twilight.bedwars.cosmetics.types.winanimations;

import mc.twilight.bedwars.cosmetics.object.AbstractExecutor;
import mc.twilight.bedwars.cosmetics.object.winanimations.CartExecutor;
import mc.twilight.bedwars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Cart extends WinAnimation {
  
  public Cart(ConfigurationSection section) {
    super(section.getLong("id"), "cowboy", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new CartExecutor(player);
  }
}
