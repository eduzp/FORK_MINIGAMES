package mc.twilight.cosmeticos.cosmetics;

public enum CosmeticType {
  COMPANION("Companheiros"),
  HAT("Chapeús"),
  ANIMATED_HAT("Chapéus animados"),
  BALLOON("Balões"),
  PET("Pets"),
  PARTICLE("Partículas"),
  BANNER("Banners"),
  CLOTHES("Roupas", 4),
  MORPH("Mutações"),
  GADGET("Engenhocas");
  
  private final String name;
  private final int size;
  
  CosmeticType(String name) {
    this(name, 1);
  }
  
  CosmeticType(String name, int size) {
    this.name = name;
    this.size = size;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getSize() {
    return this.size;
  }
}
