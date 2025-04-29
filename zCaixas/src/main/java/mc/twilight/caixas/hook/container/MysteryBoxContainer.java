package mc.twilight.caixas.hook.container;

import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import mc.twilight.caixas.box.Box;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class MysteryBoxContainer extends AbstractContainer {
  
  
  /**
   * Agora as Cápsulas Mágicas são via {@link Long} do Perfil,
   * e não mais {@link AbstractContainer}.
   */
  
  public MysteryBoxContainer(DataContainer dataContainer) {
    super(dataContainer);
  }
  
  public void addLootChests(Box lootChest) {
    this.addLootChests(lootChest, 1L);
  }
  
  public void addLootChests(Box lootChest, long amount) {
    JSONObject lootChests = this.dataContainer.getAsJsonObject();
    lootChests.put(lootChest.getId(), this.getLootChests(lootChest) + amount);
    if ((long) lootChests.get(lootChest.getId()) < 0) {
      lootChests.put(lootChest.getId(), 0L);
    }
    this.dataContainer.set(lootChests.toString());
    lootChests.clear();
  }
  
  public void removeLootChests(Box lootChest) {
    removeLootChests(lootChest, 1L);
  }
  
  public void removeLootChests(Box lootChest, long amount) {
    JSONObject lootChests = this.dataContainer.getAsJsonObject();
    lootChests.put(lootChest.getId(), this.getLootChests(lootChest) - amount);
    if ((long) lootChests.get(lootChest.getId()) < 0) {
      lootChests.put(lootChest.getId(), 0L);
    }
    this.dataContainer.set(lootChests.toString());
    lootChests.clear();
  }
  
  public long getLootChests(Box lootChest) {
    JSONObject lootChests = this.dataContainer.getAsJsonObject();
    long amount = lootChests.containsKey(lootChest.getId()) ? (long) lootChests.get(lootChest.getId()) : 0L;
    lootChests.clear();
    return amount;
  }
}