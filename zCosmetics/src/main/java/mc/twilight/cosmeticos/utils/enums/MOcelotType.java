package mc.twilight.cosmeticos.utils.enums;

public enum MOcelotType {
  WILD(0, "Selvagem", "BLACK", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFmMDdlM2YyZTVmMjU2YmZhZGU2NjZhOGRlMWI1ZDMwMjUyYzk1ZTk4ZjhhOGVjYzZlM2M3YjdmNjcwOTUifX19"),
  BLACK(1, "Preto", "RED", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDcyMGY1MjlmYzFiMTU0YjRkZDk4NDEyMzY4NzA3ODk3ZGQ3YzIyMjNmZmVhYTEyNjFlNjk3ZWZjZWRiNDkifX19"),
  RED(2, "Vermelho", "SIAMESE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGViMmU0ZGIzYmNmYjA1MDNmMWZmZmIzMzk4ODhlNWQ0MzIzOThiMjc2OTljNWExYTFjZDgwNzljOGQ5N2QifX19"),
  SIAMESE(3, "Siamês", "WILD", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E3ZTFjYzZhYzZmYmUyOWIyYWE2YzZmYmJlYzViZDc4NWIxMmRmM2RjNjU5MDQxYzA0NjRiODMyN2FkOTMzIn19fQ==");
  
  private final int id;
  private final String name;
  private final String nextType;
  private final String textures;
  
  MOcelotType(int id, String name, String nextType, String textures) {
    this.id = id;
    this.name = name;
    this.nextType = nextType;
    this.textures = textures;
  }
  
  public static MOcelotType getById(int id) {
    for (MOcelotType ocelotType : values()) {
      if (ocelotType.getId() == id) {
        return ocelotType;
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
  
  public MOcelotType getNextType() {
    return valueOf(this.nextType);
  }
  
  public String getTextures() {
    return this.textures;
  }
}
