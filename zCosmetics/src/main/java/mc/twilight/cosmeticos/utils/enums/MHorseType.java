package mc.twilight.cosmeticos.utils.enums;

public enum MHorseType {
  HORSE(0, "Cavalo", "DONKEY", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JiNGIyODg5OTFlZmI4Y2EwNzQzYmVjY2VmMzEyNThiMzFkMzlmMjQ5NTFlZmIxYzljMThhNDE3YmE0OGY5In19fQ=="),
  DONKEY(1, "Jumento", "MULE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE0NGJkYWQ2YmMxOGEzNzE2YjE5NmRjNGE0YmQ2OTUyNjVlY2NhYWRkMGQ5NDViZWI5NzY0NDNmODI2OTNiIn19fQ=="),
  MULE(2, "Mula", "ZOMBIE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19"),
  ZOMBIE(3, "Zumbi", "SKELETON", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIyOTUwZjJkM2VmZGRiMThkZTg2ZjhmNTVhYzUxOGRjZTczZjEyYTZlMGY4NjM2ZDU1MWQ4ZWI0ODBjZWVjIn19fQ=="),
  SKELETON(4, "Esqueleto", "HORSE", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ==");
  
  private final int id;
  private final String name;
  private final String nextType;
  private final String textures;
  
  MHorseType(int id, String name, String nextType, String textures) {
    this.id = id;
    this.name = name;
    this.nextType = nextType;
    this.textures = textures;
  }
  
  public static MHorseType getById(int id) {
    for (MHorseType horseType : values()) {
      if (horseType.getId() == id) {
        return horseType;
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
  
  public MHorseType getNextType() {
    return valueOf(this.nextType);
  }
  
  public String getTextures() {
    return this.textures;
  }
}
