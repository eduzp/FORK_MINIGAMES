package mc.twilight.bedwars.cosmetics.types.killeffects;

import mc.twilight.bedwars.cosmetics.types.KillEffect;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BloodExplosion extends KillEffect {
  
  public BloodExplosion(ConfigurationSection section) {
    super(section.getLong("id"), EnumRarity.fromName(section.getString("rarity")), section.getDouble("coins"), (long) section.getInt("cash"), section.getString("permission"),
        section.getString("name"), section.getString("icon"));
  }
  
  @Override
  public void execute(Player viewer, Location location) {
    if (viewer == null) {
      location.getWorld().playEffect(location.clone().add(0, 0, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
      location.getWorld().playEffect(location.clone().add(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
      location.getWorld().playEffect(location.clone().add(0, 2, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    } else {
      viewer.playEffect(location.clone().add(0, 0, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
      viewer.playEffect(location.clone().add(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
      viewer.playEffect(location.clone().add(0, 2, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }
  }
}