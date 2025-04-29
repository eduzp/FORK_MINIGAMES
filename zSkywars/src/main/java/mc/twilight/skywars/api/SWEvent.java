package mc.twilight.skywars.api;

import java.util.ArrayList;
import java.util.List;

public class SWEvent {
  
  private static final List<SWEventHandler> HANDLERS = new ArrayList<>();
  
  public static void registerHandler(SWEventHandler handler) {
    HANDLERS.add(handler);
  }
  
  public static void callEvent(SWEvent evt) {
    HANDLERS.stream().filter(handler -> handler.getEventTypes().contains(evt.getClass())).forEach(handler -> handler.handleEvent(evt));
  }
}
