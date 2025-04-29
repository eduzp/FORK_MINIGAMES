package mc.twilight.cosmeticos.cosmetics.types.pets.settings;

public class PetSettingsValue {
  
  private Object value;
  
  public PetSettingsValue(Object value) {
    this.value = value;
  }
  
  public void set(Object value) {
    this.value = value;
  }
  
  public Object get() {
    return value;
  }
  
  public String getAsString() {
    return value.toString();
  }
  
  public int getAsInt() {
    return Integer.parseInt(this.getAsString());
  }
  
  public boolean getAsBoolean() {
    return (boolean) value;
  }
}
