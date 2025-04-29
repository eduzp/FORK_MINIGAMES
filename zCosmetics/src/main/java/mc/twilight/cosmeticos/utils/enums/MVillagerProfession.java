package mc.twilight.cosmeticos.utils.enums;

public enum MVillagerProfession {
  FARMER(0, "Agricultor", "LIBRARIAN",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM2MzVjNzA5OTliMzQ0MDAwNWNiZmViZDFjZDZmNmMxOTAzODhhOWViYmMzMGJjMzdiYzUxOGEyYjJmYzQ2MSJ9fX0="),
  LIBRARIAN(1, "Bibliotecário", "PRIEST",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk5N2Y5ZjIzMDA4YjYxNDhkNWQzYjM2MWUxMzI4ZmMxMGUxN2RhZjI4Yjk5YWVjNTZmMWRjMDRhMzE2NzA2In19fQ=="),
  PRIEST(2, "Sacerdote", "BLACKSMITH",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVjMGRhNjg0Nzc2OTE4ZDAyNWUxZjJhMzg5MGI4Yzk3YzA2YjUwMjVkZjZlYzQ4Njg1MzU2NGM4Yzg1MGUxZSJ9fX0="),
  BLACKSMITH(3, "Ferreiro", "BUTCHER",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdkMWMzNDVlYjQwOTk0NDBmNzE0Y2NmNmY3ODM2NGVlODViZDBiMDFjMWMxODZkODQ4MTUxYmEwMWY3ZGM4NiJ9fX0="),
  BUTCHER(4, "Açougueiro", "FARMER",
      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNiZDFlNzg2NjRkNzZhYWIxMzcwNmY3Y2U5MWY3NWE1MmNlYzAxN2Q2MDQ0NmZiNDNiZDhiZjIwNDJkZDNiMyJ9fX0=");
  
  private final int id;
  private final String name;
  private final String nextProfession;
  private final String textures;
  
  MVillagerProfession(int id, String name, String nextProfession, String textures) {
    this.id = id;
    this.name = name;
    this.nextProfession = nextProfession;
    this.textures = textures;
  }
  
  public static MVillagerProfession getById(int id) {
    for (MVillagerProfession villagerProfession : values()) {
      if (villagerProfession.getId() == id) {
        return villagerProfession;
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
  
  public MVillagerProfession getNextProfession() {
    return valueOf(this.nextProfession);
  }
  
  public String getTextures() {
    return this.textures;
  }
}
