package mc.twilight.cosmeticos.utils.enums;

public enum MDyeColor {
  WHITE(15, "Branco", "ORANGE"),
  ORANGE(14, "Laranja", "MAGENTA"),
  MAGENTA(13, "Magenta", "LIGHT_BLUE"),
  LIGHT_BLUE(12, "Azul Claro", "YELLOW"),
  YELLOW(11, "Amarelo", "LIME"),
  LIME(10, "Lima", "PINK"),
  PINK(9, "Rosa", "GRAY"),
  GRAY(8, "Cinza", "LIGHT_GRAY"),
  LIGHT_GRAY(7, "Cinza Claro", "CYAN"),
  CYAN(6, "Ciano", "PURPLE"),
  PURPLE(5, "Roxo", "BLUE"),
  BLUE(4, "Azul", "BROWN"),
  BROWN(3, "Marrom", "GREEN"),
  GREEN(2, "Verde", "RED"),
  RED(1, "Vermelho", "BLACK"),
  BLACK(0, "Preto", "WHITE");
  
  private final int dyeData;
  private final String name;
  private final String nextColor;
  
  MDyeColor(int dyeData, String name, String nextColor) {
    this.dyeData = dyeData;
    this.name = name;
    this.nextColor = nextColor;
  }
  
  public static MDyeColor getByDyeData(int woolData) {
    for (MDyeColor color : values()) {
      if (color.getDyeData() == woolData) {
        return color;
      }
    }
    
    return null;
  }
  
  public int getDyeData() {
    return this.dyeData;
  }
  
  public String getName() {
    return this.name;
  }
  
  public MDyeColor getNextColor() {
    return valueOf(this.nextColor);
  }
}
