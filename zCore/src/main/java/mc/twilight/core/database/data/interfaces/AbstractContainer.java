package mc.twilight.core.database.data.interfaces;

import mc.twilight.core.database.data.DataContainer;

public abstract class AbstractContainer {
  
  protected DataContainer dataContainer;
  
  public AbstractContainer(DataContainer dataContainer) {
    this.dataContainer = dataContainer;
  }
  
  public void gc() {
    this.dataContainer = null;
  }
}
