package mc.twilight.core.database.data.container;

import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import mc.twilight.core.titles.Title;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class SelectedContainer extends AbstractContainer {
  
  public SelectedContainer(DataContainer dataContainer) {
    super(dataContainer);
  }
  
  public void setIcon(String id) {
    JSONObject selected = this.dataContainer.getAsJsonObject();
    selected.put("icon", id);
    this.dataContainer.set(selected.toString());
    selected.clear();
  }
  
  public Title getTitle() {
    return Title.getById(this.dataContainer.getAsJsonObject().get("title").toString());
  }
  
  public void setTitle(String id) {
    JSONObject selected = this.dataContainer.getAsJsonObject();
    selected.put("title", id);
    this.dataContainer.set(selected.toString());
    selected.clear();
  }
}
