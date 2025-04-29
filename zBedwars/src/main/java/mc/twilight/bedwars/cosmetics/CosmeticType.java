package mc.twilight.bedwars.cosmetics;


public enum CosmeticType {
  KILL_EFFECT("Efeitos de Abate"),
  CAGE("Jaulas"),
  DEATH_CRY("Gritos de Morte"),
  //  PROJECTILE_EFFECT("Efeitos de Projétil"),
  FALL_EFFECT("Efeitos de Queda"),
  // TELEPORT_EFFECT("Efeitos de Teleporte"),
  BALLOON("Balões"),
  BREAK_EFFECT("Efeitos de Cama"),
  SHOPKEEPERSKIN("Skins de Vendedores"),
  WOOD_TYPE("Tipo de Madeira"),
  DEATH_MESSAGE("Mensagens de Morte"),
  WIN_ANIMATION("Comemorações de Vitória");
  
  private String[] names;
  
  CosmeticType(String... names) {
    this.names = names;
  }
  
  public String getName(long index) {
    return this.names[(int) (index - 1)];
  }
}
