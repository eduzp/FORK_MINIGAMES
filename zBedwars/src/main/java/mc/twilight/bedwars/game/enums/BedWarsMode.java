package mc.twilight.bedwars.game.enums;

public enum BedWarsMode {
  SOLO("Solo", "1v1", 1, 1),
  DUPLA("Duplas", "2v2", 2, 1),
  QUARTETO("Quartetos", "4v4", 4, 1);
  
  private int size;
  private String stats;
  private String name;
  private int cosmeticIndex;
  
  BedWarsMode(String name, String stats, int size, int cosmeticIndex) {
    this.name = name;
    this.stats = stats;
    this.size = size;
    this.cosmeticIndex = cosmeticIndex;
  }
  
  private static final BedWarsMode[] VALUES = values();
  
  public static BedWarsMode fromName(String name) {
    for (BedWarsMode mode : VALUES) {
      if (name.equalsIgnoreCase(mode.name())) {
        return mode;
      }
    }
    
    return null;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public String getStats() {
    return this.stats;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getCosmeticIndex() {
    return cosmeticIndex;
  }
  
}
