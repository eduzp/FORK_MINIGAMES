package mc.twilight.caixas.hook.container;

import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import mc.twilight.caixas.box.action.BoxContent;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class MysteryBoxQueueContainer extends AbstractContainer {
  
  public MysteryBoxQueueContainer(DataContainer dataContainer) {
    super(dataContainer);
  }
  
  public void addToQueue(BoxContent content) {
    JSONArray queue = this.dataContainer.getAsJsonArray();
    queue.add(content.getUniqueId());
    this.dataContainer.set(queue.toString());
    queue.clear();
  }
  
  public void removeFromQueue(BoxContent content) {
    JSONArray queue = this.dataContainer.getAsJsonArray();
    queue.remove(content.getUniqueId());
    this.dataContainer.set(queue.toString());
    queue.clear();
  }
  
  public List<BoxContent> listQueuedContents() {
    List<BoxContent> contents = new ArrayList<>();
    List<Object> toRemove = new ArrayList<>();
    JSONArray queue = this.dataContainer.getAsJsonArray();
    for (Object object : queue) {
      if (object instanceof String) {
        BoxContent content = BoxContent.getById((String) object);
        if (content != null) {
          contents.add(content);
          continue;
        }
      }
      
      toRemove.add(object);
    }
    
    queue.removeAll(toRemove);
    this.dataContainer.set(queue.toString());
    toRemove.clear();
    queue.clear();
    return contents;
  }
}