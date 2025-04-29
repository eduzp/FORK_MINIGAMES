package mc.twilight.cosmeticos.hook.container;

import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class SelectedContainer extends AbstractContainer {
  
  public SelectedContainer(DataContainer dataContainer) {
    super(dataContainer);
    
    JSONObject cosmetics = this.dataContainer.getAsJsonObject();
    for (CosmeticType type : CosmeticType.values()) {
      if (!cosmetics.containsKey(type.name().toLowerCase())) {
        JSONObject object = new JSONObject();
        for (int index = 0; index < type.getSize(); index++) {
          object.put(index + 1, 0);
        }
        cosmetics.put(type.name().toLowerCase(), object);
      }
    }
    
    this.dataContainer.set(cosmetics.toString());
    cosmetics.clear();
  }
  
  public void set(CosmeticType type, long uniqueId) {
    this.set(type, uniqueId, 1);
  }
  
  public void set(CosmeticType type, long uniqueId, int index) {
    JSONObject cosmetics = this.dataContainer.getAsJsonObject();
    ((JSONObject) cosmetics.get(type.name().toLowerCase())).put(index, uniqueId);
    this.dataContainer.set(cosmetics.toString());
    cosmetics.clear();
  }
  
  public long get(CosmeticType type) {
    return this.get(type, 1);
  }
  
  public long get(CosmeticType type, int index) {
    JSONObject cosmetics = this.dataContainer.getAsJsonObject();
    long id = Long.parseLong(((JSONObject) cosmetics.get(type.name().toLowerCase())).get(String.valueOf(index)).toString());
    cosmetics.clear();
    return id;
  }
}
