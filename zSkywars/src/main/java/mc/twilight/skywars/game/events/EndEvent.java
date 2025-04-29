package mc.twilight.skywars.game.events;

import mc.twilight.skywars.Language;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.skywars.game.SkyWarsEvent;

public class EndEvent extends SkyWarsEvent {
  
  @Override
  public void execute(AbstractSkyWars game) {
    game.stop(null);
  }
  
  @Override
  public String getName() {
    return Language.options$events$end;
  }
}
