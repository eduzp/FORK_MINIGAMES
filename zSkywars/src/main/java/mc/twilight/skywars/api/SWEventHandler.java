package mc.twilight.skywars.api;

import java.util.List;

public interface SWEventHandler {
  
  void handleEvent(SWEvent evt);
  
  List<Class<?>> getEventTypes();
}
