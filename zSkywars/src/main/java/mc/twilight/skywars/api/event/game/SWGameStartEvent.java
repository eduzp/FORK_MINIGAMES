package mc.twilight.skywars.api.event.game;

import mc.twilight.core.game.Game;
import mc.twilight.core.game.GameTeam;
import mc.twilight.skywars.api.SWEvent;

public class SWGameStartEvent extends SWEvent {
  
  private final Game<? extends GameTeam> game;
  
  public SWGameStartEvent(Game<? extends GameTeam> game) {
    this.game = game;
  }
  
  public Game<? extends GameTeam> getGame() {
    return this.game;
  }
}
