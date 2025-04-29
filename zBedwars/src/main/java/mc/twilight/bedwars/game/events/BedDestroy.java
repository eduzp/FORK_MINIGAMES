package mc.twilight.bedwars.game.events;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsEvent;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.core.utils.enums.EnumSound;

public class BedDestroy extends BedWarsEvent {
  
  @Override
  public void execute(BedWars game) {
    game.listTeams().forEach(BedWarsTeam::breakBed);
    game.listPlayers(false).forEach(player -> {
      EnumSound.ENDERDRAGON_GROWL.play(player, 1.0F, 1.0F);
      player.sendMessage("§aTodas as camas foram destruidas.");
    });
  }
  
  @Override
  public String getName() {
    return Language.options$events$beddestroy;
  }
}
