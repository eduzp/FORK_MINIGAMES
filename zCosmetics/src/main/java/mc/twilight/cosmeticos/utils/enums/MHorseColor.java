package mc.twilight.cosmeticos.utils.enums;

public enum MHorseColor {
  WHITE(0, "Branco", "CREAMY", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjBhMmRiMmYxZWI5M2U1OTc4ZDJkYzkxYTc0ZGY0M2Q3Yjc1ZDllYzBlNjk0ZmQ3ZjJhNjUyZmJkMTUifX19"),
  CREAMY(1, "Creme", "CHESTNUT", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JiNGIyODg5OTFlZmI4Y2EwNzQzYmVjY2VmMzEyNThiMzFkMzlmMjQ5NTFlZmIxYzljMThhNDE3YmE0OGY5In19fQ=="),
  CHESTNUT(2, "Castanha", "BROWN", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJlYjk2N2FiOTRmZGQ0MWE2MzI1ZjEyNzdkNmRjMDE5MjI2ZTVjZjM0OTc3ZWVlNjk1OTdmYWZjZjVlIn19fQ=="),
  BROWN(3, "Marrom", "BLACK", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVkZjczZWExMmNlNmJkOTBhNGFlOWE4ZDE1MDk2NzQ5Y2ZlOTE4MjMwZGM4MjliMjU4MWQyMjNiMWEyYTgifX19"),
  BLACK(4, "Preto", "GRAY", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRiN2ZjNWY3YTlkZGZkZDFhYTc5MzE3NDEwZmMxOTI5ZjkxYmRkZjk4NTg1OTM4YTJhNTYxOTlhNjMzY2MifX19"),
  GRAY(5, "Cinza", "DARK_BROWN", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY2NzZjNGQ2ZjBmNWVkNjA2YTM1NmYzY2M1YTI5ZDE0YWFmZTY1NzIxYmExYTFhOTVjNWFjNGM1ZTIzOWU1In19fQ=="),
  DARK_BROWN(6, "Marrom Escuro", "WHITE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY2MWYyM2ZiNzY2MjRmZmJhYmJkYTMxY2E0YTM4YjQwNGZlNjNlZjM3ZDRiYTRlNGM1NDQxYTIxZTNhNiJ9fX0=");
  
  private final int id;
  private final String name;
  private final String nextColor;
  private final String textures;
  
  MHorseColor(int id, String name, String nextColor, String textures) {
    this.id = id;
    this.name = name;
    this.nextColor = nextColor;
    this.textures = textures;
  }
  
  public static MHorseColor getById(int id) {
    for (MHorseColor horseColor : values()) {
      if (horseColor.getId() == id) {
        return horseColor;
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
  
  public MHorseColor getNextColor() {
    return valueOf(this.nextColor);
  }
  
  public String getTextures() {
    return this.textures;
  }
}
