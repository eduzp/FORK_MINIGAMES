package mc.twilight.bedwars.game.events;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsEvent;

public class EndEvent extends BedWarsEvent {
  
  @Override
  public void execute(BedWars game) {
    game.stop(null);
  }
  
  @Override
  public String getName() {
    return Language.options$events$end;
  }
}
