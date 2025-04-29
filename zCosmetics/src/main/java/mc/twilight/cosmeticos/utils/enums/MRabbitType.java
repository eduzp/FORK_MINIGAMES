package mc.twilight.cosmeticos.utils.enums;

public enum MRabbitType {
  BROWN(0, "Marrom", "BLACK",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QxMTY5YjI2OTRhNmFiYTgyNjM2MDk5MjM2NWJjZGE1YTEwYzg5YTNhYTJiNDhjNDM4NTMxZGQ4Njg1YzNhNyJ9fX0="),
  WHITE(1, "Branco", "RED",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjRkY2ZlZDY4OTdhMThhN2FiOTk1YTY2MTM0ZDQxYTFjYTgyMWI2OWJjYjdkMTRjZjI2OWI0YTk4ZGY0OWE4In19fQ=="),
  BLACK(2, "Preto", "BLACK_AND_WHITE",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJjNTgxMTZhMTQ3ZDFhOWEyNjI2OTIyNGE4YmUxODRmZThlNWYzZjNkZjliNjE3NTEzNjlhZDg3MzgyZWM5In19fQ=="),
  BLACK_AND_WHITE(3, "Preto e Branco", "GOLD",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2I4Y2ZmNGIxNWI4Y2EzN2UyNTc1MGYzNDU3MThmMjg5Y2IyMmM1YjNhZDIyNjI3YTcxMjIzZmFjY2MifX19"),
  GOLD(4, "Dourado", "SALT_AND_PEPPER",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk3N2EzMjY2YmYzYjllYWYxN2U1YTAyZWE1ZmJiNDY4MDExNTk4NjNkZDI4OGI5M2U2YzEyYzljYiJ9fX0="),
  SALT_AND_PEPPER(5, "Sal e Pimenta", "THE_KILLER_BUNNY",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMyOWFlYmU4MDNlNWQ0ZGMzYjAxMGM5ZGQ1NjQ0NGY4NjNiMmQ4NmE2YTJhZmQ0YTZhNzIxNzQ4YmE0ZmEifX19"),
  THE_KILLER_BUNNY(99, "Coelho Assassino", "BROWN",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzc0ZDgyOTg3OTdlNzEyYmIxZjc1YWQ2ZmZhNzczNGFjNDIzN2VhNjliZTFkYjkyZjBlNDExMTVhMmMxNzBjZiJ9fX0=");
  
  private final int id;
  private final String name;
  private final String nextType;
  private final String textures;
  
  MRabbitType(int id, String name, String nextType, String textures) {
    this.id = id;
    this.name = name;
    this.nextType = nextType;
    this.textures = textures;
  }
  
  public static MRabbitType getById(int id) {
    for (MRabbitType rabbitType : values()) {
      if (rabbitType.getId() == id) {
        return rabbitType;
      }
    }
    
    return null;
  }
  
  public int getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public MRabbitType getNextType() {
    return valueOf(this.nextType);
  }
  
  public String getTextures() {
    return this.textures;
  }
}
