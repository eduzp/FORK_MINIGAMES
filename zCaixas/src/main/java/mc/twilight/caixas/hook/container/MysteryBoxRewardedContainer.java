package mc.twilight.caixas.hook.container;

import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import mc.twilight.caixas.box.action.BoxContent;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class MysteryBoxRewardedContainer extends AbstractContainer {
  
  public MysteryBoxRewardedContainer(DataContainer dataContainer) {
    super(dataContainer);
  }
  
  public void addContent(BoxContent content) {
    JSONArray rewarded = this.dataContainer.getAsJsonArray();
    rewarded.add(content.getUniqueId());
    this.dataContainer.set(rewarded.toString());
    rewarded.clear();
  }
  
  public boolean alreadyRewarded(BoxContent content) {
    return this.dataContainer.getAsJsonArray().contains(content.getUniqueId());
  }
  
  public String getLastItems() {
    StringBuilder sb = new StringBuilder();
    JSONArray cosmetics = this.dataContainer.getAsJsonArray();
    List<String> list = new ArrayList<>();
    
    if (!cosmetics.isEmpty()) {
      for (Object content : cosmetics) {
        BoxContent contenT = BoxContent.getById((String) content);
        if (contenT == null) {
          continue;
        }
        list.add(contenT.getName());
      }
    }
    
    Collections.reverse(list);
    
    for (int slot = 0; slot < 5; ++slot) {
      sb.append("\n §7").append(slot + 1).append(". ").append(list.size() > slot ? list.get(slot) : "");
    }
    
    return sb.toString();
  }
}
