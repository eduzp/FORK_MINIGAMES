package mc.twilight.cosmeticos.utils.enums;

public enum MSlimeSize {
  SMALL(1, "Pequeno", "MEDIUM"),
  MEDIUM(2, "Médio", "BIG"),
  BIG(3, "Grande", "SMALL");
  
  private final int size;
  private final String name;
  private final String nextSize;
  
  MSlimeSize(int size, String name, String nextSize) {
    this.size = size;
    this.name = name;
    this.nextSize = nextSize;
  }
  
  public static MSlimeSize getBySize(int size) {
    for (MSlimeSize slimeSize : values()) {
      if (slimeSize.getSize() == size) {
        return slimeSize;
      }
    }
    
    return null;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public String getName() {
    return this.name;
  }
  
  public MSlimeSize getNextSize() {
    return valueOf(this.nextSize);
  }
}
