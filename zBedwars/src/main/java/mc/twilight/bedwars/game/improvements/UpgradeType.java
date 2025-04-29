package mc.twilight.bedwars.game.improvements;

public enum UpgradeType {
  
  SHARPENED_SWORDS,
  REINFORCED_ARMOR,
  LIFE_POINTS,
  MANIAC_MINER,
  REGENERATION,
  IRON_FORGE;
  
  public static UpgradeType fromName(String name) {
    if (name.equals("SHARPNED_SWORDS")) {
      name = "SHARPENED_SWORDS";
    }
    for (UpgradeType type : values()) {
      if (type.name().equalsIgnoreCase(name)) {
        return type;
      }
    }
    
    return null;
  }
}
