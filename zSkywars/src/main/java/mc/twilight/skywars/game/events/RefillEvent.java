package mc.twilight.skywars.game.events;

import mc.twilight.core.nms.NMS;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.skywars.game.SkyWarsEvent;
import mc.twilight.skywars.game.object.SkyWarsChest;
import mc.twilight.core.utils.enums.EnumSound;

public class RefillEvent extends SkyWarsEvent {
  
  @Override
  public void execute(AbstractSkyWars game) {
    game.listChests().forEach(SkyWarsChest::refill);
    game.listPlayers(false).forEach(player -> {
      EnumSound.CHEST_OPEN.play(player, 0.5F, 1.0F);
      NMS.sendTitle(player, Language.ingame$titles$refill$header, Language.ingame$titles$refill$footer, 20, 60, 20);
    });
  }
  
  @Override
  public String getName() {
    return Language.options$events$refill;
  }
}
