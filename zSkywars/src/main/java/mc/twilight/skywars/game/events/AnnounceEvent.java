package mc.twilight.skywars.game.events;

import mc.twilight.core.nms.NMS;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.skywars.game.SkyWarsEvent;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;

public class AnnounceEvent extends SkyWarsEvent {
  
  @Override
  public void execute(AbstractSkyWars game) {
    int minutes = game.getTimeUntilEvent() / 60;
    
    game.listPlayers(false).forEach(player -> {
      EnumSound.CLICK.play(player, 0.5F, 2.0F);
      NMS.sendTitle(player, Language.ingame$titles$end$header,
          Language.ingame$titles$end$footer.replace("{time}", StringUtils.formatNumber(minutes)).replace("{s}", minutes > 1 ? "s" : ""), 20, 60, 20);
    });
  }
  
  @Override
  public String getName() {
    return "";
  }
}