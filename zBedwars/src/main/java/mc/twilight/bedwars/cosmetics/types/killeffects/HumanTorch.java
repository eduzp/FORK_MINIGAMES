package mc.twilight.bedwars.cosmetics.types.killeffects;

import mc.twilight.bedwars.cosmetics.types.KillEffect;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HumanTorch extends KillEffect {
  
  public HumanTorch(ConfigurationSection section) {
    super(section.getLong("id"), EnumRarity.fromName(section.getString("rarity")), section.getDouble("coins"), (long) section.getInt("cash"), section.getString("permission"),
        section.getString("name"), section.getString("icon"));
  }
  
  @Override
  public void execute(Player viewer, Location location) {
    if (viewer == null) {
      for (Player player : Bukkit.getOnlinePlayers()) {
        ParticleEffect.SMOKE_NORMAL.display((float) Math.floor(Math.random() * 2.0F), 0.0F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, player);
        ParticleEffect.SMOKE_NORMAL.display((float) Math.floor(Math.random() * 2.0F), 1.0F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, player);
        ParticleEffect.SMOKE_NORMAL.display((float) Math.floor(Math.random() * 2.0F), 1.5F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, player);
      }
    } else {
      ParticleEffect.SMOKE_NORMAL.display((float) Math.floor(Math.random() * 2.0F), 0.0F, 0.0F, (float) Math.floor(Math.random() * 2.0F), 5, location, viewer);
      ParticleEffect.SMOKE_NORMAL.display((float) Math.floor(Math.random() * 2.0F), 1.0F, 0.0F, (float) Math.floor(Math.random() * 2.0F), 5, location, viewer);
      ParticleEffect.SMOKE_NORMAL.display((float) Math.floor(Math.random() * 2.0F), 1.5F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, viewer);
    }
  }
}