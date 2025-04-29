package mc.twilight.caixas.cosmetics;

public enum CosmeticType {
  OPENING_ANIMATION("Animação de Abertura");
  
  private final String[] names;
  
  CosmeticType(String... names) {
    this.names = names;
  }
  
  public String getName(long index) {
    return this.names[(int) (index - 1)];
  }
}
