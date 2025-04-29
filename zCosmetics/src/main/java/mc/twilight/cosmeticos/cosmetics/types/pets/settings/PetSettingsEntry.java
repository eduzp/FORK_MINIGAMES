package mc.twilight.cosmeticos.cosmetics.types.pets.settings;

public class PetSettingsEntry {
  
  private String key;
  private PetSettingsValue value;
  
  public PetSettingsEntry(String key, Object value) {
    this.key = key;
    this.value = new PetSettingsValue(value);
  }
  
  public void destroy() {
    this.key = null;
    this.value.set(null);
    this.value = null;
  }
  
  public String getKey() {
    return key;
  }
  
  public PetSettingsValue getValue() {
    return this.value;
  }
}
