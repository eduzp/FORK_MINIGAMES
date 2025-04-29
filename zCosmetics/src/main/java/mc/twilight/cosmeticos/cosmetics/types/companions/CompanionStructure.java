package mc.twilight.cosmeticos.cosmetics.types.companions;

import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import org.bukkit.entity.ArmorStand;

public class CompanionStructure {
  
  private final String name;
  private final ArmorStand stand;
  private final RelativeLocation location;
  
  public CompanionStructure(String name, ArmorStand stand, RelativeLocation location) {
    this.name = name;
    this.stand = stand;
    this.location = location;
  }
  
  public String getName() {
    return this.name;
  }
  
  public ArmorStand getStand() {
    return this.stand;
  }
  
  public RelativeLocation getLocation() {
    return this.location;
  }
}
