package mc.twilight.bedwars.hook.container;

import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import org.json.simple.JSONObject;

import java.util.Map;

@SuppressWarnings("unchecked")
public class FavoritesContainer extends AbstractContainer {
  
  public FavoritesContainer(DataContainer dataContainer) {
    super(dataContainer);
    if (this.dataContainer.getAsString().equals("[]")) {
      this.dataContainer.set("{}");
    }
    JSONObject cosmetics = this.dataContainer.getAsJsonObject();
    if (!cosmetics.containsKey("quickBuy")) {
      cosmetics.put("quickBuy", new JSONObject());
    }
    this.dataContainer.set(cosmetics.toString());
    cosmetics.clear();
  }
  
  public void setQuickBuy(int slot, String id) {
    JSONObject favorites = this.dataContainer.getAsJsonObject();
    
    if (id == null) {
      ((JSONObject) favorites.get("quickBuy")).remove(String.valueOf(slot));
    } else {
      ((JSONObject) favorites.get("quickBuy")).put(String.valueOf(slot), id);
    }
    this.dataContainer.set(favorites.toString());
    favorites.clear();
  }
  
  public boolean hasQuickBuy(int slot) {
    JSONObject favorites = this.dataContainer.getAsJsonObject();
    
    boolean has = ((JSONObject) favorites.get("quickBuy")).containsKey(String.valueOf(slot));
    favorites.clear();
    return has;
  }
  
  public boolean hasQuickBuy(String item) {
    JSONObject favorites = this.dataContainer.getAsJsonObject();
    
    return ((JSONObject) favorites.get("quickBuy")).containsValue(item);
  }
  
  public int getQuickBuy(String item) {
    JSONObject favorites = this.dataContainer.getAsJsonObject();
    
    Map.Entry<?, ?> entry =
        (Map.Entry<?, ?>) ((JSONObject) favorites.get("quickBuy")).entrySet().stream().filter(e -> ((Map.Entry<?, ?>) e).getValue().toString().equals(item)).findFirst()
            .orElse(null);
    if (entry != null) {
      return Integer.parseInt(entry.getKey().toString());
    }
    
    return -1;
  }
  
  public String getQuickBuy(int slot) {
    JSONObject favorites = this.dataContainer.getAsJsonObject();
    
    return ((JSONObject) favorites.get("quickBuy")).get(String.valueOf(slot)).toString();
  }
  
}