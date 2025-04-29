package mc.twilight.core.database.data.container;

import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.interfaces.AbstractContainer;
import mc.twilight.core.titles.Title;
import org.json.simple.JSONArray;

@SuppressWarnings("unchecked")
public class TitlesContainer extends AbstractContainer {
  
  public TitlesContainer(DataContainer dataContainer) {
    super(dataContainer);
  }
  
  public void add(Title title) {
    JSONArray titles = this.dataContainer.getAsJsonArray();
    titles.add(title.getId());
    this.dataContainer.set(titles.toString());
    titles.clear();
  }
  
  public boolean has(Title title) {
    return this.dataContainer.getAsJsonArray().contains(title.getId());
  }
}
